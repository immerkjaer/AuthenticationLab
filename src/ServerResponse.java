package src;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {

    private Boolean failed;
    private Boolean authenticated;
    private T result;
    private String error;

    public ServerResponse(T result) {
        this.failed = false;
        this.authenticated = true;
        this.result = result;
        this.error = "";
    }

    public Boolean isErr() {
        return failed;
    }

    public Boolean isAuthErr() {
        return authenticated;
    }

    public String errMsg() {
        return error;
    }

    public T res() throws Exception {
        if (!isAuthErr()){ throw new Exception(errMsg());}
        return result;
    }

    public ServerResponse withErr(String errMsg) {
        this.failed = true;
        this.error = errMsg;
        return this;
    }


    public ServerResponse authErr(String errMsg) {
        this.authenticated = false;
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
