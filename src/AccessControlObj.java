package src;

import java.util.Arrays;

public class AccessControlObj {
    public String method;
    public String[] users;

    @Override
    public String toString() {
        return "AccessControlObj{" +
                "method='" + method + '\'' +
                ", users=" + Arrays.toString(users) +
                '}';
    }
}