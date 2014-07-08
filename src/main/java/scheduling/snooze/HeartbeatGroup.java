package scheduling.snooze;

import org.simgrid.msg.*;
import org.simgrid.msg.Process;
import scheduling.snooze.msg.BeatGLMsg;
import scheduling.snooze.msg.BeatGMMsg;
import scheduling.snooze.msg.NewGLMsg;
import scheduling.snooze.msg.NewGMMsg;

import java.util.HashSet;

/**
 * Created by sudholt on 22/06/2014.
 */
public class HeartbeatGroup extends Process {
    private static GroupLeader gl = null;
    private String glHeartbeatNew = "glHeartbeatNew";
    private String glHeartbeatBeat = "glHeartbeatBeat";
    private String gmHeartbeatNew = "gmHeartbeatNew";
    private String gmHeartbeatBeat = "gmHeartbeatBeat";
    private HashSet<GMHeartbeat> gms = new HashSet<GMHeartbeat>();



    public static void setGl(GroupLeader gl) {
        HeartbeatGroup.gl = gl;
    }

    public void main(String[] args) throws MsgException {
        while (true) {
            newGL();
            beatGLs();
            newGMs();
            recvGMBeats();
        }
    }

    protected HeartbeatGroup() {}

    /**
     * Register new GroupLeader and notify EP.
     */
    void newGL() {
        try {
            // Store GroupLeader
            NewGLMsg m = (NewGLMsg) Task.receive(glHeartbeatNew);
            if (gl == null) HeartbeatGroup.setGl((GroupLeader) m.getMessage());
            else Logger.log("HeartbeatGroup:newGL, ERROR: 2nd GroupLeader" + m);
            // Notify EP
            m = new NewGLMsg((String) m.getMessage(), AUX.epInbox, null, null);
            m.send();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void beatGLs() {
        try{
            // TODO: use non-blocking receive
            BeatGLMsg m = (BeatGLMsg) AUX.arecv(glHeartbeatBeat);
            Logger.log(Host.currentHost().getName() + ": received " + m.getMessage());
            GroupLeader gl = (GroupLeader) m.getMessage();

            if (HeartbeatGroup.gl != null && HeartbeatGroup.gl != gl)
                Logger.log("[HeartbeatGroup] Err: multiple GLs");
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    void newGMs(){
        NewGMMsg req = (NewGMMsg) AUX.arecv(gmHeartbeatNew);
        gms.add(new GMHeartbeat((String) req.getMessage(), req.getReplyBox()));
    }

    void recvGMBeats() {
        try{
            for (GMHeartbeat gm : gms) {
                BeatGMMsg req = (BeatGMMsg) AUX.arecv(gmHeartbeatBeat);
                Logger.log(Host.currentHost().getName() + ": received " + req.getMessage());
            }
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    class GMHeartbeat {
        String groupManager;
        String replyBox;

        GMHeartbeat(String gm, String rb) {
            this.groupManager = gm; this.replyBox = rb;
        }
    }
}
