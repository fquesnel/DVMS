package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */

import configuration.XHost;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import java.net.UnknownHostException;

public class LocalController extends Process {

    private String name = null;
    private XHost host = null;
    private GroupManager gm = null;
    private int procCharge = 0;
    private String entryPointInbox = "entryPointInbox";
    private String replyBox;

    LocalController(String name, XHost host, GroupManager gm) throws UnknownHostException {
        this.name = name;
        this.host = host;
        this.gm = gm;
        this.replyBox = name + "ReplyBox";
    }

    void join() {
        SnoozeMsg joinCLMsg = new JoinLCMsg(this, entryPointInbox, name, replyBox);
        joinCLMsg.send();
    }

    void rejoin() {

    }

    void totalHostCapacity() {
        HostCapacity hc = new HostCapacity(host.getCPUCapacity(), host.getMemSize());
    }

    void vmMonitoring() {
        HostCapacity hc = new HostCapacity(host.getCPUDemand(), host.getMemDemand());
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
