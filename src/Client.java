package src;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.*;

public class Client {
    private Client() {} // Making sure client can't be instantiated elsewhere
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn=false;
        int choice = 0;
        String input ="";
        Ticket authTicket;
        String printer;
        String file;
        int jobID;
        String txt;
        LinkedList<String> stringList;
        while(true){
            ReadJson rd = new ReadJson();
            rd.read("ACL");
            System.out.println("Write username");
            String userName = scanner.nextLine();
            System.out.println("Write password");
            String password = scanner.nextLine();
            ServerResponse resp;
            try {
                Registry registry = LocateRegistry.getRegistry(null);
                IPrinterServer stub = (IPrinterServer) registry.lookup("RMI_test");
                resp = stub.authenticate(userName, password);
                authTicket=(Ticket)resp.res();
                
                                 
                while (authTicket.active){
                    

                    // Below we are simulating:
                    //      Starting the server
                    //      Queuing printer1 with 2 files
                    //      Printing the content of queue for printer1
                    // Note that each method returns a generic response with a possible error message.
                    // TODO: Below should be incorporated in some UI + implement for all other methods.
                    
                    System.out.println("Enter 1 for: print");
                    System.out.println("Enter 2 for: queue");
                    System.out.println("Enter 3 for: topQueue");
                    System.out.println("Enter 4 for: start");
                    System.out.println("Enter 5 for: stop");
                    System.out.println("Enter 6 for: restart");
                    System.out.println("Enter 7 for: status");
                    System.out.println("Enter 8 for: readconfig");
                    System.out.println("Enter 9 for: setconfig");
                    input=scanner.nextLine();
                    try{
                        switch (Integer.parseInt(input)) {
                            case 1:
                                System.out.println("Write file name");
                                file = scanner.nextLine();
                                System.out.println("Write printer name");
                                printer = scanner.nextLine();
                                stub.print(file, printer,authTicket).res();
                                System.out.println("Success");
                                break;
                            case 2:
                                System.out.println("Write printer name");
                                printer = scanner.nextLine();
                                resp = stub.queue(printer,authTicket);
                                stringList = (LinkedList<String>) resp.res();
                                for (var val : stringList) {
                                    System.out.println(val);
                                }
                                if (resp.isErr()) {
                                    System.out.println(resp.errMsg());
                                }
                                break;
                            case 3:
                                System.out.println("Write printer name");
                                printer = scanner.nextLine();
                                System.out.println("Write Job ID");
                                jobID = Integer.parseInt(scanner.nextLine());
                                resp = stub.topQueue(printer,jobID,authTicket);
                                stringList = (LinkedList<String>) resp.res();
                                for (var val : stringList) {
                                    System.out.println(val);
                                }
                                if (resp.isErr()) {
                                    System.out.println(resp.errMsg());
                                }
                                break;
                            case 4:
                                stub.start(authTicket).res();
                                System.out.println("Printer server started");
                                break;
                            case 5:
                                stub.stop(authTicket).res();
                                System.out.println("Printer server stopped");
                                break;
                            case 6:
                                stub.restart(authTicket).res();
                                System.out.println("Printer server restarted");
                                break;
                            case 7:
                                System.out.println("Write printer name");
                                printer = scanner.nextLine();
                                resp =stub.status(printer, authTicket);
                                System.out.println((String)resp.res().toString());
                                break;
                            case 8:
                                System.out.println("Write parameter");
                                file = scanner.nextLine();
                                resp=stub.readConfig(file, authTicket);
                                txt=(String)resp.res();
                                System.out.println(txt);
                                break;
                            case 9:
                                System.out.println("Write parameter");
                                file = scanner.nextLine();
                                System.out.println("Write value");
                                printer = scanner.nextLine();
                                resp = stub.setConfig(file,printer,authTicket);
                                resp.res();
                                System.out.println("Success");
                                break;
                        }
                    } catch (NumberFormatException|NullPointerException e){
                        System.err.println("" + e.toString()); 
                    }                  
                }
                                
            } catch (Exception e) {
                System.err.println("" + e.toString());
                e.printStackTrace();
                loggedIn=false;;
            }
        }
        
    }
}
