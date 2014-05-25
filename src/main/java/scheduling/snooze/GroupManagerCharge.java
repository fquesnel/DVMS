package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManagerCharge {
    private GroupManager gm = null;
    private int procCharge = 0;

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
}
