package scheduling.snooze;

/**
 * Created by sudholt on 23/06/2014.
 */
public class GMHeartbeat {
    private GroupManager gm = null;
    private long ts = 0;

    GMHeartbeat(GroupManager gm, long ts) {
        this.gm = gm; this.ts = ts;
    }

    public GroupManager getGm() {
        return gm;
    }

    public void setGm(GroupManager gm) {
        this.gm = gm;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
