package src;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrinterServer extends Remote {
    ServerResponse print(String filename, String printerName, Ticket ticket) throws RemoteException;

    ServerResponse authenticate(String userId, String password)throws RemoteException;

    ServerResponse queue(String printerName, Ticket ticket) throws RemoteException;

    ServerResponse topQueue(String printerName, Integer jobIdx, Ticket ticket) throws RemoteException;

    ServerResponse start(Ticket ticket) throws RemoteException;

    ServerResponse stop(Ticket ticket) throws RemoteException;

    ServerResponse restart(Ticket ticket) throws RemoteException;

    ServerResponse status(String printerName, Ticket ticket) throws RemoteException;

    ServerResponse readConfig(String param, Ticket ticket) throws RemoteException;

    ServerResponse setConfig(String param, String value, Ticket ticket) throws RemoteException;
}
