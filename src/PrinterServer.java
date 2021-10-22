package src;

import java.util.*;

public class PrinterServer implements IPrinterServer {

    private HashMap<String, String> params = new HashMap();
    private ArrayList<Printer> printers = new ArrayList();
    private Boolean isRunning = false;

    public PrinterServer() {
        printers.add(new Printer("printer1"));
        printers.add(new Printer("printer2"));
        printers.add(new Printer("printer3"));
    }

    @Override
    public ServerResponse print(String filename, String printerName) {

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
    public ServerResponse queue(String printerName) {

        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return printers.stream()
                .filter(p -> p.getPrinterName().equals(printerName))
                .findFirst()
                .map(r -> new ServerResponse(r.queue).build())
                .orElseGet(() -> new ServerResponse(null).withErr("No printer found").build());
    }

    @Override
    public ServerResponse topQueue(String printerName, Integer jobIdx) {

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
    public ServerResponse start() {

        printers.stream()
                .forEach(p -> p.setStatus(Status.Ready));
        isRunning = true;
        return new ServerResponse(null).build();
    }

    @Override
    public ServerResponse stop() {
        printers.stream()
                .forEach(p -> p.setStatus(Status.Stopped));
        isRunning = false;
        return new ServerResponse(null).build();
    }

    @Override
    public ServerResponse restart() {

        isRunning = false;
        printers.stream()
                .forEach(p -> p.clearQueue());
        isRunning = true;

        return new ServerResponse(null).build();
    }

    @Override
    public ServerResponse status(String printerName) {

        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return printers.stream()
                .filter(p -> p.getPrinterName().equals(printerName))
                .findFirst()
                .map(r -> new ServerResponse(r.getStatus()).build())
                .orElseGet(() -> new ServerResponse(null).withErr("No printer found").build());
    }

    @Override
    public ServerResponse readConfig(String param) {

        var cond = checkConditions();
        if (!cond.isEmpty()) { return new ServerResponse(null).withErr(cond).build(); }

        return params.containsKey(param)
                ? new ServerResponse(params.get(param)).build()
                : new ServerResponse(null).withErr("No such parameter").build();
    }

    @Override
    public ServerResponse setConfig(String param, String value) {

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

}
