package by.kazimirov.exception;

/**
 *
 */
public class LogicException extends Exception {
    public LogicException() {
        super();
    }

    public LogicException(String s) {
        super(s);
    }

    public LogicException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public LogicException(Throwable throwable) {
        super(throwable);
    }
}
