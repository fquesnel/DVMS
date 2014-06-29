package scheduling.snooze;

import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManager extends Process {
    GroupLeader gl;
    ArrayList<LocalControllerCharge> lCCs = null;
    private String gmHeartbeatNew = "gmHeartbeatNew";
    private String gmHeartbeatBeat = "gmHeartbeatBeat";

    GroupManager() {

    }

    @Override
    public void main(String[] strings) throws MsgException {
        NewGMMsg m = new NewGMMsg(this, gmHeartbeatNew, null, null);
        m.send();

        while (true) {
            beat(); sleep(1000);
        }
    }

    void receiveHostQuery() {

    }

    void answerHostQuery() {

    }

    void receiveVMQuery() {

    }

    void answerVMQuery() {

    }

    void scheduleVMs() {

    }

    void sendVMCommandsLC() {

    }

    void summaryInfoToGL() {

    }

    void beat() {
        BeatGMMsg m = new BeatGMMsg(this, gmHeartbeatBeat, null, null);
        m.send();
    }
}
