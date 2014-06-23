package scheduling.snooze;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GMHeartbeatGroup extends Observable {
    private static GMHeartbeatGroup gmhg = null;
    private static ArrayList<GMHeartbeat> gmhs = null;

    protected GMHeartbeatGroup() {}

    public static GMHeartbeatGroup getGmhg() {
        if (gmhg == null) new GMHeartbeatGroup();
        return gmhg;
    }

    void add(GroupManager gm) {
        gmhs.add(new GMHeartbeat(gm, new Date().getTime()));
        this.notifyObservers();
    }
}
