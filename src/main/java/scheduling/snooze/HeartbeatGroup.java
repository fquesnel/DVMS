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
    private String glHostname = null;
    private static final String glHeartbeatNew = "glHeartbeatNew";
    private static final String glHeartbeatBeat = "glHeartbeatBeat";
    private static final String gmHeartbeatNew = "gmHeartbeatNew";
    private static final String gmHeartbeatBeat = "gmHeartbeatBeat";
    private HashSet<GMHeartbeat> gms = new HashSet<GMHeartbeat>();



    public void setGl(String gl) {
        glHostname = gl;
    }

    public void main(String[] args) throws MsgException {
        while (true) {
            newGL();
            recvGLbeat();
            newGMs();
            recvGMsBeat();
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
            if (glHostname == null) setGl((String) m.getMessage());
            else Logger.log("HeartbeatGroup:newGL, ERROR: 2nd GroupLeader" + m);
            // Notify EP
            m = new NewGLMsg((String) m.getMessage(), AUX.epInbox, null, null);
            m.send();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void recvGLbeat() {
        try{
            BeatGLMsg m = (BeatGLMsg) AUX.arecv(getGlHeartbeatBeat());
            Logger.log(Host.currentHost().getName() + ": received " + m.getMessage());
            String gl = (String) m.getMessage();

            if (glHostname != null && glHostname != gl)
                Logger.log("[HeartbeatGroup] Err: multiple GLs");
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    void newGMs(){
        NewGMMsg req = (NewGMMsg) AUX.arecv(gmHeartbeatNew);
        gms.add(new GMHeartbeat((String) req.getMessage(), req.getReplyBox()));
    }

    void recvGMsBeat() {
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


    public static String getGlHeartbeatBeat() {
        return glHeartbeatBeat;
    }
}
