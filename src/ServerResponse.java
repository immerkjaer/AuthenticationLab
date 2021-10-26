package src;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {

    private Boolean failed;
    private T result;
    private String error;

    public ServerResponse(T result) {
        this.failed = false;
        this.result = result;
        this.error = "";
    }

    public Boolean isErr() {
        return failed;
    }

    public String errMsg() {
        return error;
    }

    public T res() {
        return result;
    }

    public ServerResponse withErr(String errMsg) {
        this.failed = true;
        this.error = errMsg;
        return this;
    }

    public ServerResponse build() {
        return this;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "failed=" + failed +
                ", result=" + result +
                ", error='" + error + '\'' +
                '}';
    }
}
