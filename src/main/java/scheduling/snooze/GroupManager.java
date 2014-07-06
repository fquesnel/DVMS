package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManager extends Process {
    private Host host;
    private String glHost;
    private Hashtable<String, LCInfo> lcInfo;  // ConcurrentHashMap more efficient?
    // one mailbox per LC: lcHostname+"beat"
    private GMChargeSummary chargeSummary = new GMChargeSummary(host.getName(), 0, 0, null);
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
            handleInbox();
            updateLCCharge();
            recvLCBeats();
            deadLCs();
            summaryInfoToGL();
            beat();
            sleep(1000);
        }
    }

    void handleInbox() {
        try {
            SnoozeMsg m = (SnoozeMsg) Task.receive(inbox);
            handle(m);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void handle(SnoozeMsg m) { Logger.log("[GroupLeader.handle] Unknown message" + m); }

    /**
     * Integrate new LCs
     */
    void handle(NewLCMsg m) {
        String lcHostname = (String) m.getMessage();
        Date   ts  = new Date();
        // Init LC charge and heartbeat
        LCInfo    lci = new LCInfo(new LCCharge(0, 0, ts), ts);
        lcInfo.put(lcHostname, lci);
        // Send acknowledgment
        m = new NewLCMsg(host.getName(), m.getReplyBox(), null, null);
        m.send();
    }

    /**
     * Listens async. for heartbeats from all known LCs
     */
    void recvLCBeats() {
        BeatLCMsg m = null;
        try{
            for (String lcHostname: lcInfo.keySet()) {
                String lcBeatBox = lcHostname+"lcBeat";
                if (Task.listen(lcBeatBox)) {
                    m = (BeatLCMsg) Task.receive(lcBeatBox);
                }
                Logger.log(Host.currentHost().getName() + ": received " + m.getMessage());
            }
        } catch (org.simgrid.msg.TimeoutException te) {
            Logger.log("GLHeartbeatGroup::receiveAnnounceGLMsg: timeout, GroupLeader dead");
            te.printStackTrace();
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    /**
     * Identify and handle dead LCs
     */
    void deadLCs() {
        // Identify dead LCs
        HashSet<String> deadLCs = new HashSet<String>();
        for (String lcHostname: lcInfo.keySet()) {
            long curTime = new Date().getTime();
            long lcTime  = lcInfo.get(lcHostname).heartbeatTimestamp.getTime();
            if (curTime-lcTime > CONST.HeartbeatTimeout) {
                deadLCs.add(lcHostname);
                Logger.log("[deadLCs] LC " + lcHostname + "is dead");
            }
        }

        // Remove dead LCs
        for (String lcHostname: deadLCs) {
            lcInfo.remove(lcHostname);
            // TODO: interaction with scheduling (other operations)?
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

    /**
     * Sends GM charge summary info to GL
     */
    void summaryInfoToGL() {
        updateChargeSummary();
        GMSumMsg m = new GMSumMsg(chargeSummary, glSummary, null, null);
        m.send();
    }

    /**
     * Sends a GM heatbeat
     */
    void beat() {
        BeatGMMsg m = new BeatGMMsg(host.getName(), gmHeartbeatBeat, null, null);
        m.send();
    }

    /**
     * Updates charge summary based on local LC charge info
     */
    void updateChargeSummary() {
        int proc = 0;
        int mem = 0;
        int s = lcInfo.size();
        for(String lcHostname: lcInfo.keySet()) {
            LCInfo lci = lcInfo.get(lcHostname);
            proc += lci.charge.procCharge;
            mem += lci.charge.memUsed;
        }
        proc /= s; mem /= s;
        chargeSummary.setProcCharge(proc); chargeSummary.setMemUsed(mem);
    }

    /**
     * Accepts async. all pending LC charge messages, adds time stamps and updates lcInfo
     */
    void updateLCCharge() {
        while (Task.listen(glSummary)) {
            try {
                SnoozeMsg m = (SnoozeMsg) Task.receive(glSummary);
                m = (GMSumMsg) m;
                String lcHostname = (String) m.getOrigin();
                LCChargeMsg.LCCharge cs = (LCChargeMsg.LCCharge) m.getMessage();
                LCCharge newCharge = new LCCharge(cs.procCharge, cs.memUsed, new Date());
                Date oldBeat = lcInfo.get(lcHostname).heartbeatTimestamp;
                lcInfo.put(lcHostname, new LCInfo(newCharge, oldBeat));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Stores LC charge info (cpu, mem, timestamp)
     */
    class LCCharge {
        double procCharge;
        int memUsed;
        Date timeStamp;

        LCCharge(double proc, int mem, Date ts) {
            this.procCharge = proc; this.memUsed = mem; this.timeStamp = ts;
        }
    }

    /**
     * Contains all LC-related info (charge info, heartbeat timestamps)
     */
    class LCInfo {
        LCCharge charge;
        Date heartbeatTimestamp;

        LCInfo(LCCharge c, Date ts) {
            this.charge = c; this.heartbeatTimestamp = ts;
        }
    }
}
