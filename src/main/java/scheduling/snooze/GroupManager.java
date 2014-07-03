package scheduling.snooze;

import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManager extends Process {
    private GroupLeader gl;
    private ArrayList<LocalControllerCharge> lCCs;
    private String gmHeartbeatNew = "gmHeartbeatNew";
    private String gmHeartbeatBeat = "gmHeartbeatBeat";
    private String myHeartbeat;

    GroupManager() {
        myHeartbeat = msgName() + "myHeartbeat";
    }

    @Override
    public void main(String[] strings) throws MsgException {
        NewGMMsg m = new NewGMMsg(this, gmHeartbeatNew, null, myHeartbeat);
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

    GMCharge calcGMCharge() {
        int proc = 0;
        int mem = 0;
        int s = getlCCs().size();
        for(LocalControllerCharge lc: getlCCs()) {
            proc += lc.getProcCharge();
            mem += lc.getMemUsed();
        }
        proc /= s; mem /= s;
        return new GMCharge(this, proc, mem);
    }

    public ArrayList<LocalControllerCharge> getlCCs() {
        return lCCs;
    }

    public void setlCCs(ArrayList<LocalControllerCharge> lCCs) {
        this.lCCs = lCCs;
    }
}
