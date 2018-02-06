package by.kazimirov.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();

    private static AtomicBoolean poolInstanceCreated = new AtomicBoolean(false);
    private static AtomicBoolean poolClosed = new AtomicBoolean(false);
    private static Lock connectionPoolLock = new ReentrantLock(true);
    private static Lock closePoolLock = new ReentrantLock(true);
    private static int connectionAmount = 0;

    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> takenConnections;

    private static DatabaseInitializer dbInitializer;
    private static ConnectionPool poolInstance;

    private ConnectionPool() {
        dbInitializer = new DatabaseInitializer();
        freeConnections = new ArrayBlockingQueue<>(dbInitializer.POOL_SIZE);
        takenConnections = new ArrayBlockingQueue<>(dbInitializer.POOL_SIZE);

        try {
            Class.forName(dbInitializer.DRIVER).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            LOG.fatal("Error while initializing database driver.", e);
            throw new RuntimeException(e);
        }

        for (int i = 0; i < dbInitializer.POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(dbInitializer.URL, dbInitializer.LOGIN, dbInitializer.PASSWORD);
                freeConnections.put(new ProxyConnection(connection));
                ++connectionAmount;
                LOG.info("Get connection " + i + ".");
            } catch (SQLException | InterruptedException e) {
                LOG.error("Can't get connection " + i + ".", e);
            }
        }
        if (connectionAmount < 1) {
            LOG.fatal("Can't initialize connections. No connections are available.");
            throw new RuntimeException("Error while initializing connections. No connections are available.");
        }
        LOG.info(connectionAmount+ " connections where taken.");
    }

    public static ConnectionPool getInstance() {
        if (!poolInstanceCreated.get()) {
            connectionPoolLock.lock();
            try {
                if (poolInstance == null) {
                    poolInstance = new ConnectionPool();
                    poolInstanceCreated.set(true);
                }
            } finally {
                connectionPoolLock.unlock();
            }
        }
        return poolInstance;
    }

    public Connection getConnection() {
        Connection connection = null;
        if (!poolClosed.get()) {
            closePoolLock.lock();
            if (!poolClosed.get()) {
                try {
                    connection = freeConnections.take();
                    takenConnections.put((ProxyConnection) connection);
                } catch (InterruptedException e) {
                    LOG.error("Can't take connection from available connections.", e);
                } finally {
                    closePoolLock.unlock();
                }
            }
        }
        return connection;
    }

    void freeConnection(ProxyConnection connection) {
        takenConnections.remove(connection);
        try {
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            LOG.error("Can't free connection.", e);
        }
    }

    public void closePool() {
        if (!poolClosed.get()) {
            closePoolLock.lock();
            try {
                if (!poolClosed.get()) {
                    poolClosed.set(true);
                    for (int i = 0; i < connectionAmount; ++i) {
                        freeConnections.take().finalClose();
                        LOG.info("Close connection " + i + ".");
                    }
                }
            } catch (SQLException | InterruptedException e) {
                LOG.error("Can't close connection pool.", e);
            } finally {
                closePoolLock.unlock();
            }
        }
    }
}
