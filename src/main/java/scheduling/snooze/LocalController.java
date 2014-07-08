package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */

import configuration.XHost;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;
import org.simgrid.msg.TimeoutException;
import scheduling.snooze.msg.BeatLCMsg;
import scheduling.snooze.msg.LCChargeMsg;
import scheduling.snooze.msg.NewLCMsg;

import java.net.UnknownHostException;

public class LocalController extends Process {

    private String name;
    private XHost host;
    private String gmHostname;
    private int procCharge = 0;
    private String inbox;
    private String lcCharge; // GM mbox
    private String lcBeat;   // GM mbox

    LocalController(String name, XHost host) throws UnknownHostException {
        this.name = name;
        this.host = host;
        this.inbox = host.getName() + "lcInbox";
    }

    @Override
    public void main(String[] args) throws MsgException {
        join();
        while (true) {
            try{
                beat();
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send join request to EP and wait for GroupManager acknowledgement
     */
    void join() {
        // Send join request to EP
        NewLCMsg m = new NewLCMsg(host.getName(), AUX.epInbox, name, inbox);
        m.send();
        try {
            // Wait for GroupManager acknowledgement
            m = (NewLCMsg) Task.receive(inbox, 2);
            gmHostname = (String) m.getMessage();
            lcCharge = gmHostname + "lcCharge";
            lcBeat = host.getName() + "lcBeat";
        } catch (TimeoutException e) {
            Logger.log("[LC.join] No joining" + this);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void rejoin() {
        join();
    }

    void totalHostCapacity() {
        HostCapacity hc = new HostCapacity(host.getCPUCapacity(), host.getMemSize());
    }

    void lcChargeToGM() {
        LCChargeMsg.LCCharge lc = new LCChargeMsg.LCCharge(host.getCPUDemand(), host.getMemDemand());
        LCChargeMsg m = new LCChargeMsg(lc, lcCharge, host.getName(), null);
        m.send();
    }


    void beat() {
        BeatLCMsg m = new BeatLCMsg(host.getName(), lcBeat, null, null);
        m.send();
    }


    void startVM() {

    }

    void shutdownVM() {

    }

    void migrateVM() {

    }

}
