package shared;

public enum TaskStatus {
    OPEN("Open"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    CLOSED("Closed");

    private String text;

    TaskStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
