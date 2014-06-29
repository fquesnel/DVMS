package scheduling.snooze;

import org.simgrid.msg.*;
import org.simgrid.msg.Process;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GLHeartbeatGroup extends Process {
    private static GLHeartbeatGroup glhg = null;
    private static GroupLeader gl = null;
    private String glHeartbeatNew = "glHeartbeatNew";
    private String glHeartbeatBeat = "glHeartbeatBeat";

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

    public static GLHeartbeatGroup getGlhg() {
        if (glhg == null) new GLHeartbeatGroup();
        return glhg;
    }

    void newGL() {
        try {
            if (gl == null) {
                NewGLMsg req = (NewGLMsg) Task.receive(glHeartbeatNew);
                GLHeartbeatGroup.setGl((GroupLeader) req.getMessage());
            } else {
                Comm c = Task.irecv(glHeartbeatNew);
                Logger.log("GLHeartbeatGroup:newGL, ERROR: 2nd GroupLeader" + c.getTask());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void beatGLs() {
        try{
            BeatGLMsg req = (BeatGLMsg) Task.receive(glHeartbeatBeat, 2);
            Logger.log(Host.currentHost().getName() + ": received " + req.getMessage());
            GroupLeader gl = (GroupLeader) req.getMessage();

            if (GLHeartbeatGroup.gl != null && GLHeartbeatGroup.gl != gl)
                Logger.log("[GLHeartbeatGroup] Err: multiple GLs");
        } catch (org.simgrid.msg.TimeoutException te) {
            Logger.log("GLHeartbeatGroup::receiveAnnounceGLMsg: timeout, GL dead");
            te.printStackTrace();
        } catch (Exception e) {
            Logger.log(e);
        }
    }
}
