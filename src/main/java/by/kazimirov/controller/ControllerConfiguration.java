package by.kazimirov.controller;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
public class ControllerConfiguration implements Serializable {
    public enum State {FORWARD, REDIRECT, AJAX, RESPONSE}
    private ControllerConfiguration.State state;
    private String command;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerConfiguration that = (ControllerConfiguration) o;
        return state == that.state &&
                Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, command);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ControllerConfiguration{");
        sb.append("state=").append(state);
        sb.append(", command='").append(command).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
