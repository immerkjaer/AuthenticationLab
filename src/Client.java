package src;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class Client {
    private Client() {} // Making sure client can't be instantiated elsewhere
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null);
            IPrinterServer stub = (IPrinterServer) registry.lookup("RMI_test");

            // Below we are simulating:
            //      Starting the server
            //      Queuing printer1 with 2 files
            //      Printing the content of queue for printer1
            // Note that each method returns a generic response with a possible error message.
            // TODO: Below should be incorporated in some UI + implement for all other methods.


            //Start stub
            stub.start();
            
            // Calling the remote method using the obtained object
            stub.authenticate("cooluser", "coolpassword");
            stub.printMsg("Hello from client");

            stub.print("test_file", "printer1");
            stub.print("test_file_other", "printer1");
            ServerResponse resp = stub.queue("printer1");
            var res = (LinkedList<String>) resp.res();
            for (var val : res) {
                System.out.println(val);
            }
            if (resp.isErr()) {
                System.out.println(resp.errMsg());
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
