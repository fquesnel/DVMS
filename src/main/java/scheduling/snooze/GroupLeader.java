package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;

import java.util.Date;
import java.util.Hashtable;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupLeader extends Process {
    private Host host;
    private Hashtable<String, GMChargeSummary> gMCs = new Hashtable<>();
    private String glHeartbeatNew = "glHeartbeatNew";
    private String glHeartbeatBeat = "glHeartbeatBeat";
    private String glSummary = "glSummary";

    GroupLeader() {
        this.host = Host.currentHost();
    }

    @Override
    public void main(String[] strings) throws MsgException {
        NewGLMsg m = new NewGLMsg(host.getName(), glHeartbeatNew, null, null);
        m.send();

        while (true) {
            beat();
            updateSummaryInfo();
            sleep(1000);
        }
    }

    void updateSummaryInfo() {
        // accepts all pending summary messages, adds time stamps and stores the entries in gMCs
        while (Task.listen(glSummary)) {
            try {
                SnoozeMsg m = (SnoozeMsg) Task.receive(glSummary);
                m = (GMSumMsg) m;
                GMChargeSummary cs = (GMChargeSummary) m.getMessage();
                cs.setTimeStamp(new Date());
                gMCs.put(cs.getHostName(), cs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void dispatchVMRequest() {

    }

    void assignLCToGM() {

    }

    void beat() {
        BeatGLMsg m = new BeatGLMsg(host.getName(), glHeartbeatBeat, null, null);
        m.send();
    }
}
