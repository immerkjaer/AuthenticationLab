package src;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrinterServer extends Remote {
    ServerResponse print(String filename, String printerName) throws RemoteException;

    ServerResponse queue(String printerName) throws RemoteException;

    ServerResponse topQueue(String printerName, Integer jobIdx) throws RemoteException;

    ServerResponse start() throws RemoteException;

    ServerResponse stop() throws RemoteException;

    ServerResponse restart() throws RemoteException;

    ServerResponse status(String printerName) throws RemoteException;

    ServerResponse readConfig(String param) throws RemoteException;

    ServerResponse setConfig(String param, String value) throws RemoteException;
}
