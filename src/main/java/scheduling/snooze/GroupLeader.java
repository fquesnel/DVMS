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
    private Hashtable<String, GMChargeSummary> gMCs = new Hashtable<>(); // ConcurrentHashMap more efficient
    private String inbox = "glInbox";
    private String glHeartbeatNew = "glHeartbeatNew";
    private String glHeartbeatBeat = "glHeartbeatBeat";
    private String glSummary = "glSummary";

    GroupLeader() {
        this.host = Host.currentHost();
    }

    @Override
    public void main(String[] strings) throws MsgException {
        SnoozeMsg m;
        m = new NewGLMsg(host.getName(), glHeartbeatNew, null, null);
        m.send();

        while (true) {
            try {
                m = (SnoozeMsg) Task.receive(inbox);
                handle(m);
            } catch(Exception e) {
                e.printStackTrace();
            }
            updateSummaryInfo();
            beat();
            sleep(1000);
        }
    }

    void handle(SnoozeMsg m) { Logger.log("[GroupLeader.handle] Unknown message" + m); }

    void handle(NewLCMsg m) {
        // join/rejoin LC: assign LC to least charged GroupManager

        // identify least charge GroupManager
        String gmHost = "";
        double minCharge = 2, curCharge;
        GMChargeSummary cs;
        for (String s: gMCs.keySet()) {
            cs        = gMCs.get(s);
            curCharge = cs.getProcCharge() + cs.getMemUsed();
            if (minCharge > curCharge) { minCharge = curCharge; gmHost = s; }
        };
        // relay message
        m = new NewLCMsg(gmHost, gmHost+"gmInbox", m.getOrigin(), m.getReplyBox());
        m.send();
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
