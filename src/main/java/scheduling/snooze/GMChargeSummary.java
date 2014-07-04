package scheduling.snooze;

/**
 * Created by sudholt on 03/07/2014.
 */
public class GMChargeSummary {
    private GroupManager gm;
    private double procCharge;
    private int memUsed;

    GMChargeSummary(GroupManager gm, double proc, int mem) {
        this.gm = gm; this.procCharge = proc; this.memUsed = mem;
    }

    public GroupManager getGm() {
        return gm;
    }

    public void setGm(GroupManager lc) {
        this.gm = gm;
    }

    public double getProcCharge() {
        return procCharge;
    }

    public void setProcCharge(double procCharge) {
        this.procCharge = procCharge;
    }

    public int getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(int memUsed) {
        this.memUsed = memUsed;
    }
}
