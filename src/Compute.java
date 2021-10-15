package src;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Creating Remote interface for our application
public interface Compute extends Remote {
    void printMsg(String msg) throws RemoteException;
}
