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
        while(true){
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
                                stub.print("test_file", "printer1",authTicket);
                                break;
                            case 2:
                                resp = stub.queue("printer1",authTicket);
                                var res = (LinkedList<String>) resp.res();
                                for (var val : res) {
                                    System.out.println(val);
                                }
                                if (resp.isErr()) {
                                    System.out.println(resp.errMsg());
                                }
                                break;
                            case 3:
                                System.out.println("Wednesday");
                                break;
                            case 4:
                                stub.start(authTicket);
                                System.out.println("Printer server started");
                                break;
                            case 5:
                                stub.stop(authTicket);
                                System.out.println("Printer server stopped");
                                break;
                            case 6:
                                System.out.println("Saturday");
                                authTicket.sessionKey="d";
                                break;
                            case 7:
                                System.out.println("Sunday");
                                break;
                            case 8:
                                System.out.println("Sunday");
                                break;
                            case 9:
                                resp = stub.setConfig(" "," ",authTicket);
                                authTicket =(Ticket)resp.res();
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
