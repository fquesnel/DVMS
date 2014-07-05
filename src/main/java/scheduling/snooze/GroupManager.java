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
public class GroupManager extends Process {
    private Host host;
    private String glHost;
    private Hashtable<String, LocalControllerCharge> lCCs;  // ConcurrentHashMap more efficient?
    private GMChargeSummary cs = new GMChargeSummary(host.getName(), 0, 0, null);
    private String inbox;
    private String gmHeartbeatNew = "gmHeartbeatNew";
    private String gmHeartbeatBeat = "gmHeartbeatBeat";
    private String glSummary = "glSummary";
    private String lcCharge;
    private String myHeartbeat;

    GroupManager() {
        this.host = Host.currentHost();
        this.inbox = host.getName() + "gmInbox";
        this.myHeartbeat = host.getName() + "myHeartbeat";
        this.lcCharge = host.getName() + "lcCharge";
    }

    @Override
    public void main(String[] strings) throws MsgException {
        SnoozeMsg m = new NewGMMsg(host.getName(), gmHeartbeatNew, null, myHeartbeat);
        m.send();

        while (true) {
            try {
                m = (SnoozeMsg) Task.receive(inbox);
                handle(m);
            } catch(Exception e) {
                e.printStackTrace();
            }
            updateLCCharge();
            summaryInfoToGL();
            beat();
            sleep(1000);
        }
    }

    void handle(SnoozeMsg m) { Logger.log("[GL.handle] Unknown message" + m); }

    void handle(NewLCMsg m) {
        // Get join request
        String lcHost = (String) m.getMessage();
        LocalControllerCharge lc = new LocalControllerCharge(lcHost, 0, 0, new Date());
        lCCs.put(lcHost, lc);
        // Send acknowledgment
        m = new NewLCMsg(host.getName(), m.getReplyBox(), null, null);
        m.send();
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
        calcSummary();
        GMSumMsg m = new GMSumMsg(cs, glSummary, null, null);
        m.send();
    }

    void beat() {
        BeatGMMsg m = new BeatGMMsg(host.getName(), gmHeartbeatBeat, null, null);
        m.send();
    }

    void calcSummary() {
        int proc = 0;
        int mem = 0;
        int s = lCCs.size();
        for(LocalControllerCharge lc: lCCs.values()) {
            proc += lc.getProcCharge();
            mem += lc.getMemUsed();
        }
        proc /= s; mem /= s;
        cs.setProcCharge(proc); cs.setMemUsed(mem);
    }

    void updateLCCharge() {
        // accepts all pending charge messages, adds time stamps and stores the entries in gMCs
        while (Task.listen(glSummary)) {
            try {
                SnoozeMsg m = (SnoozeMsg) Task.receive(glSummary);
                m = (GMSumMsg) m;
                LocalControllerCharge cs = (LocalControllerCharge) m.getMessage();
                cs.setTimeStamp(new Date());
                lCCs.put(cs.getHostName(), cs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
