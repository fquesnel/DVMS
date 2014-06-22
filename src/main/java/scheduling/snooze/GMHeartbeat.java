package scheduling.snooze;

import java.util.ArrayList;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GMHeartbeat {
    static ArrayList<GroupManager> gms = null;

    static void add(GroupManager gm) {
        gms.add(gm);
    }
}
