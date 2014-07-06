package scheduling.snooze;

import org.simgrid.msg.*;
import org.simgrid.msg.Process;
import scheduling.snooze.msg.BeatGLMsg;
import scheduling.snooze.msg.NewGLMsg;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GLHeartbeatGroup extends Process {
    private static GroupLeader gl = null;
    private String glHeartbeatNew = "glHeartbeatNew";
    private String glHeartbeatBeat = "glHeartbeatBeat";
    private String epInbox = "ipInbox";



    public static void setGl(GroupLeader gl) {
        GLHeartbeatGroup.gl = gl;
    }

    public void main(String[] args) throws MsgException {
        while (true) {
            newGL();
            beatGLs();
        }
    }

    protected GLHeartbeatGroup() {}

    /**
     * Register new GroupLeader and notify EP.
     */
    void newGL() {
        try {
            // Store GroupLeader
            NewGLMsg m = (NewGLMsg) Task.receive(glHeartbeatNew);
            if (gl == null) GLHeartbeatGroup.setGl((GroupLeader) m.getMessage());
            else Logger.log("GLHeartbeatGroup:newGL, ERROR: 2nd GroupLeader" + m);
            // Notify EP
            m = new NewGLMsg((String) m.getMessage(), epInbox, null, null);
            m.send();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void beatGLs() {
        try{
            BeatGLMsg m = (BeatGLMsg) Task.receive(glHeartbeatBeat, 2);
            Logger.log(Host.currentHost().getName() + ": received " + m.getMessage());
            GroupLeader gl = (GroupLeader) m.getMessage();

            if (GLHeartbeatGroup.gl != null && GLHeartbeatGroup.gl != gl)
                Logger.log("[GLHeartbeatGroup] Err: multiple GLs");
        } catch (org.simgrid.msg.TimeoutException te) {
            Logger.log("GLHeartbeatGroup::receiveAnnounceGLMsg: timeout, GroupLeader dead");
            te.printStackTrace();
        } catch (Exception e) {
            Logger.log(e);
        }
    }
}
