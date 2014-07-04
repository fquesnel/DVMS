package scheduling.snooze;

import java.util.Date;

/**
 * Created by sudholt on 25/05/2014.
 */
public class LocalControllerCharge {
    private String hostName;
    private double procCharge;
    private int memUsed;
    private Date timeStamp;

    LocalControllerCharge(String n, double proc, int mem, Date ts) {
        this.setHostName(n); this.procCharge = proc; this.memUsed = mem; this.setTimeStamp(ts);
    }

    public String getHostName() { return hostName; }

    public void setHostName(String hostName) { this.hostName = hostName; }

    public double getProcCharge() {
        return procCharge;
    }

    public void setProcCharge(int procCharge) {
        this.procCharge = procCharge;
    }

    public int getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(int memUsed) {
        this.memUsed = memUsed;
    }

    public Date getTimeStamp() { return timeStamp; }

    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }
}
