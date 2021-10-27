package src;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import javax.sound.sampled.SourceDataLine;

public class PrinterServer implements IPrinterServer {

    private HashMap<String, String> params = new HashMap();
    private ArrayList<Printer> printers = new ArrayList();
    private Boolean isRunning = false;
    private Set<Ticket> tickets = new HashSet<Ticket>();
    public passwordManager passwordManager;
    public PrinterServer() {
        passwordManager = new passwordManager();
        printers.add(new Printer("printer1"));
        printers.add(new Printer("printer2"));
        printers.add(new Printer("printer3"));
    }

    @Override
    public ServerResponse print(String filename, String printerName, Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to print "+ filename+ " on printer "+printerName;
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" authorized");
        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return printers.stream()
                .filter(p -> p.getPrinterName().equals(printerName))
                .findFirst()
                .map(r -> {
                    r.addToQueue(filename);
                    return new ServerResponse(null).build();
                })
                .orElseGet(() -> new ServerResponse(null).withErr("No printer found").build());
    }

    @Override
    public ServerResponse queue(String printerName, Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to see queue "+ " on printer "+printerName;
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" authorized");
        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return printers.stream()
                .filter(p -> p.getPrinterName().equals(printerName))
                .findFirst()
                .map(r -> new ServerResponse(r.queue).build())
                .orElseGet(() -> new ServerResponse(null).withErr("No printer found").build());
    }

    @Override
    public ServerResponse topQueue(String printerName, Integer jobIdx, Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to see topqueue "+ " on printer "+printerName;
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return printers.stream()
                .filter(p -> p.getPrinterName().equals(printerName))
                .findFirst()
                .map(r -> {
                    r.prioritizeJob(jobIdx);
                    return new ServerResponse(null).build();
                })
                .orElseGet(() -> new ServerResponse(null).withErr("No printer found").build());
    }

    @Override
    public ServerResponse start(Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to start ";
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        printers.stream()
                .forEach(p -> p.setStatus(Status.Ready));
        isRunning = true;
        return new ServerResponse(null).build();
    }

    @Override
    public ServerResponse stop(Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to stop ";
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        printers.stream()
                .forEach(p -> p.setStatus(Status.Stopped));
        isRunning = false;
        return new ServerResponse(null).build();
    }

    @Override
    public ServerResponse restart(Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to restart ";
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        isRunning = false;
        printers.stream()
                .forEach(p -> p.clearQueue());
        isRunning = true;

        return new ServerResponse(null).build();
    }

    @Override
    public ServerResponse status(String printerName, Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to see status ";
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return printers.stream()
                .filter(p -> p.getPrinterName().equals(printerName))
                .findFirst()
                .map(r -> new ServerResponse(r.getStatus()).build())
                .orElseGet(() -> new ServerResponse(null).withErr("No printer found").build());
    }

    @Override
    public ServerResponse readConfig(String param, Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to read config ";
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return params.containsKey(param)
                ? new ServerResponse(params.get(param)).build()
                : new ServerResponse(null).withErr("No such parameter").build();
    }

    @Override
    public ServerResponse setConfig(String param, String value, Ticket ticket) {
        String log="User: ";
        log+=ticket.user;
        log+=" tried to set Config ";
        if (checkTicket(ticket)){
            System.out.println(log +" unauthorized");
            return new ServerResponse(false).authErr("authentication failed").build();
        }
        System.out.println(log +" aurthorized");
        var cond = checkConditions();
        


        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        params.put(param, value);
        return new ServerResponse(null).build();
    }

    private String checkConditions() {
        
        if (!isRunning) {
            return "Print server is offline";
        }

        return "";
    }

    @Override
    public ServerResponse authenticate(String userId, String password) throws RemoteException {
        // Do validation in valid
        boolean valid=false;
        
        try {
            valid = passwordManager.authorizeUser(userId, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (valid){
            Ticket newTicket = new Ticket(userId);
            tickets.add(newTicket);
            return new ServerResponse(newTicket).build();
        }
        return new ServerResponse(false).authErr("authentication failed").build();
    }


    private boolean checkTicket(Ticket ticket){
        if (tickets.contains(ticket)){
            if(ticket.isActive()){ 
                return false;
            }
        }
        return true;
        
    }

}
