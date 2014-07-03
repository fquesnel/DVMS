package scheduling.snooze;

/**
 * Created by sudholt on 03/07/2014.
 */
public class GMCharge {
    private GroupManager gm;
    private int procCharge;
    private int memUsed;

    GMCharge(GroupManager gm, int proc, int mem) {
        this.gm = gm; this.procCharge = proc; this.memUsed = mem;
    }

    public GroupManager getGm() {
        return gm;
    }

    public void setGm(GroupManager lc) {
        this.gm = gm;
    }

    public int getProcCharge() {
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
}
