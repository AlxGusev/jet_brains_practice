package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

public class Request {

    @Parameter(names = "-t")
    private String type;

    @Parameter(names = "-k")
    private String key;

    @Parameter(names = "-v", variableArity = true)
    private String value;

    @Parameter(names = "-in")
    private String file;

    public Request() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Request{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
