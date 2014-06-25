package scheduling.snooze;

import java.util.Date;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GLHeartbeatGroup {
    private static GroupLeader gl = null;
    private static long ts = 0;

    protected GLHeartbeatGroup() {}

    public static GroupLeader getGl() {
        return gl;
    }

    public static void setGl(GroupLeader gl) {
        if (GLHeartbeatGroup.gl != null) System.out.println("[GLHeartbeatGroup] Err: multiple GLs");
        else {
            GLHeartbeatGroup.gl = gl;
            ts = new Date().getTime();
        }
    }
}
