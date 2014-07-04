package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */

import configuration.XHost;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import java.net.UnknownHostException;

public class LocalController extends Process {

    private String name;
    private XHost host;
    private String groupManagerHostName;
    private int procCharge = 0;
    private String lcCharge;
    private String entryPointInbox = "entryPointInbox";
    private String myBox;

    LocalController(String name, XHost host, String n) throws UnknownHostException {
        this.name = name;
        this.host = host;
        this.groupManagerHostName = n;
        this.myBox = host.getName() + "myBox";
        this.lcCharge = n + "lcCharge";
    }

    void join() {
        JoinLCMsg joinCLMsg = new JoinLCMsg(host.getName(), entryPointInbox, name, myBox);
        joinCLMsg.send();
    }

    void rejoin() {

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

    @Override
    public void main(String[] args) throws MsgException {
        while (true) {
            try{
            } catch (Exception e) {
            }
        }
    }

}
