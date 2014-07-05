package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManager extends Process {
    private Host host;
    private String glHost;
    private HashSet<LocalControllerCharge> lCCs;  // ConcurrentHashMap more efficient?
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
        LocalControllerCharge lc = new LocalControllerCharge((String) m.getMessage(), 0, 0, new Date());
        lCCs.add(lc);
        // Send acknowledgment
        m = new NewLCMsg(host.getName(), m.getReplyBox(), null, null);
        m.send();
    }

    void recvLCBeats() {
/*
        try{

            for (LocalControllerCharge lc: lCCs) {
                BeatLCMsg m = (BeatLCMsg) Task.receive(lc.getHostName()+"beat", 2);
                Logger.log(Host.currentHost().getName() + ": received " + req.getMessage());
            }
        } catch (org.simgrid.msg.TimeoutException te) {
            Logger.log("GLHeartbeatGroup::receiveAnnounceGLMsg: timeout, GL dead");
            te.printStackTrace();
        } catch (Exception e) {
            Logger.log(e);
        }
*/
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
        for(LocalControllerCharge lc: lCCs) {
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
                lCCs.add(cs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
