package by.kazimirov.logic;

/**
 *
 */
public class LogicActionResult {
    public enum State {
        SUCCESS, FAILURE
    }

    private State state;
    private ActionResult result;
    private String message;
    private String target;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ActionResult getResult() {
        return result;
    }

    public void setResult(ActionResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}