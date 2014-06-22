package scheduling.snooze;

import java.util.ArrayList;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GLHeartbeat {
    static ArrayList<GroupLeader> gls = null;

    static void add(GroupLeader gl) {
        gls.add(gl);
    }
}
