package dev.rubric.qurantracker.types;

public class SuccessResponse {
    private boolean success;
    private String message;

    public SuccessResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SuccessResponse() {}

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean key) {
        this.success = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponse(boolean success, String message) {
        setSuccess(success);
        setMessage(message);
    }
}
