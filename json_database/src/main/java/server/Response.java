package server;

public class Response {
    private Status response;
    private String value;
    private String reason;

    public Status getResponse() {
        return response;
    }

    public void setStatus(Status response) {
        this.response = response;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response=" + response +
                ", value='" + value + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
