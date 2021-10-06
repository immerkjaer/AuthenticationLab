package src;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends ImplCompute {
    public Server() {}

    public static void main(String args[]) {
//        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        try {
//            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            ImplCompute engine = new ImplCompute();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("RMI_test", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
