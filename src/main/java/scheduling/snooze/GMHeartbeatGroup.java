package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;

import java.util.HashSet;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GMHeartbeatGroup extends Process {
    private String gmHeartbeatNew = "gmHeartbeatNew";
    private String gmHeartbeatBeat = "gmHeartbeatBeat";
    private HashSet<GroupManagerHeartbeat> gms = new HashSet<GroupManagerHeartbeat>();

    protected GMHeartbeatGroup() {};

    class GroupManagerHeartbeat {
        private GroupManager gm;
        private String mb;

        GroupManagerHeartbeat(GroupManager gm, String mb) {
            this.gm = gm;
            this.mb = mb;
        }
    }

    @Override
    public void main(String[] strings) throws MsgException {
        while (true) {
            newGMs();
            recvGMBeats();
        }

    }

    void newGMs(){
        if (Task.listen(gmHeartbeatNew)) {
            try {
                NewGMMsg req = (NewGMMsg) Task.receive(gmHeartbeatNew);
                gms.add(new GroupManagerHeartbeat((GroupManager) req.getMessage(), req.getReplyBox()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void recvGMBeats() {
        try{
            for (GroupManagerHeartbeat gm : gms) {
                BeatGMMsg req = (BeatGMMsg) Task.receive(gm.mb, 2);
                Logger.log(Host.currentHost().getName() + ": received " + req.getMessage());
            }
        } catch (org.simgrid.msg.TimeoutException te) {
            Logger.log("GLHeartbeatGroup::receiveAnnounceGLMsg: timeout, GroupLeader dead");
            te.printStackTrace();
        } catch (Exception e) {
            Logger.log(e);
        }
    }


}
