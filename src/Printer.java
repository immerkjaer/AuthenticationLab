package src;

import java.util.LinkedList;

public class Printer {

    public String printerName;
    public Status status;
    public LinkedList<String> queue = new LinkedList();;

    public Printer(String printerName) {
        this.printerName = printerName;
        this.status = Status.Stopped;
    }

    public void addToQueue(String filename) {
        queue.add(filename);
    }

    public void clearQueue() {
        queue.clear();
    }

    public void prioritizeJob(int jobIdx) {
        var job = queue.get(jobIdx);
        queue.remove(jobIdx);
        queue.addFirst(job);
    }

    public LinkedList getQueue() {
        return queue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPrinterName() {
        return printerName;
    }
}
