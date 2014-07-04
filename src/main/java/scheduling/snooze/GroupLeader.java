package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupLeader extends Process {
    private Host host = null;
    private ArrayList<GMChargeSummary> gMCs = null;
    private String glHeartbeatNew = "glHeartbeatNew";
    private String glHeartbeatBeat = "glHeartbeatBeat";

    GroupLeader() {
    }

    @Override
    public void main(String[] strings) throws MsgException {
        NewGLMsg m = new NewGLMsg(this, glHeartbeatNew, null, null);
        m.send();

        while (true) {
            beat(); sleep(1000);
        }
    }

    void storeSummaryInfo() {
        for (GMChargeSummary s: gMCs) {

        }
    }

    void dispatchVMRequest() {

    }

    void assignLCToGM() {

    }

    void beat() {
        BeatGLMsg m = new BeatGLMsg(this, glHeartbeatBeat, null, null);
        m.send();
    }
}
