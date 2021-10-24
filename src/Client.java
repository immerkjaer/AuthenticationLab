package src;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private Client() {} // Making sure client can't be instantiated elsewhere
    public static void main(String[] args) {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object
            Compute stub = (Compute) registry.lookup("RMI_test");

            // Calling the remote method using the obtained object
            stub.authenticate("cooluser", "coolpassword");
            stub.printMsg("Hello from client");

            // System.out.println("Remote method invoked");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
