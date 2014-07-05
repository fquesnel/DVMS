package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */

import configuration.XHost;
import org.simgrid.msg.*;
import org.simgrid.msg.Process;

import java.net.UnknownHostException;

public class LocalController extends Process {

    private String name;
    private XHost host;
    private String gmHostname;
    private int procCharge = 0;
    private String inbox;
    private String lcCharge;
    private String epInbox;

    LocalController(String name, XHost host) throws UnknownHostException {
        this.name = name;
        this.host = host;
        this.epInbox = "epInbox";
        this.inbox = host.getName() + "lcInbox";
    }

    @Override
    public void main(String[] args) throws MsgException {
        join();
        while (true) {
            try{
            } catch (Exception e) {
            }
        }
    }

    /**
     * Send join request to EP and wait for GM acknowledgement
     */
    void join() {
        // Send join request to EP
        NewLCMsg m = new NewLCMsg(host.getName(), epInbox, name, inbox);
        m.send();
        try {
            // Wait for GM acknowledgement
            m = (NewLCMsg) Task.receive(inbox, 2);
            gmHostname = (String) m.getMessage();
            lcCharge = gmHostname + "lcCharge";
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
        LocalControllerCharge
            lc = new LocalControllerCharge(host.getName(), host.getCPUDemand(), host.getMemDemand(), null);
        LCChargeMsg m = new LCChargeMsg(lc, lcCharge, null, null);
        m.send();
    }

    void startVM() {

    }

    void shutdownVM() {

    }

    void migrateVM() {

    }

}
