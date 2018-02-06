package by.kazimirov.command;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
public class ErrorHolder implements Serializable {//TODO: hashCode, equals, toString
    private String causeMessage;
    private String toDoMessage;
    private String currentPage;
    private Exception exception;

    public ErrorHolder() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorHolder that = (ErrorHolder) o;
        return Objects.equals(causeMessage, that.causeMessage) &&
                Objects.equals(toDoMessage, that.toDoMessage) &&
                Objects.equals(currentPage, that.currentPage) &&
                Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(causeMessage, toDoMessage, currentPage, exception);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorHolder{");
        sb.append("causeMessage='").append(causeMessage).append('\'');
        sb.append(", toDoMessage='").append(toDoMessage).append('\'');
        sb.append(", currentPage='").append(currentPage).append('\'');
        sb.append(", exception=").append(exception);
        sb.append('}');
        return sb.toString();
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public void setCauseMessage(String causeMessage) {
        this.causeMessage = causeMessage;
    }

    public String getToDoMessage() {
        return toDoMessage;
    }

    public void setToDoMessage(String toDoMessage) {
        this.toDoMessage = toDoMessage;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
