package server;

import com.google.gson.JsonElement;

public class Response {
    private Status response;
    private JsonElement value;
    private String reason;

    public Status getResponse() {
        return response;
    }

    public void setStatus(Status response) {
        this.response = response;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
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
