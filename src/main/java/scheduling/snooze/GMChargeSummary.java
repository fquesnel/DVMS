package scheduling.snooze;

import java.util.Date;

/**
 * Created by sudholt on 03/07/2014.
 */
public class GMChargeSummary {
    private String hostName;
    private double procCharge;
    private int memUsed;
    private Date timeStamp;

    GMChargeSummary(String hostName, double proc, int mem, Date ts) {
        this.hostName = hostName; this.procCharge = proc; this.memUsed = mem; this.timeStamp = ts;
    }

    public String getHostName() { return hostName; }

    public void setHostName(String hostName) { this.hostName = hostName; }

    public double getProcCharge() {
        return procCharge;
    }

    public void setProcCharge(double procCharge) {
        this.procCharge = procCharge;
    }

    public int getMemUsed() { return memUsed; }

    public void setMemUsed(int memUsed) { this.memUsed = memUsed; }

    public Date getTimeStamp() { return timeStamp; }

    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }
}
