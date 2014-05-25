package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import java.net.UnknownHostException;

public class LocalController extends Process {

    String name;
    GroupManager gm;

    LocalController(Host host, String name, String hostname, int port, GroupManager gm) throws UnknownHostException {
        super(host, String.format("%s", hostname, port));

        this.name = String.format("%s", hostname, port);
        this.gm = gm;

        // this.dvms = new DvmsActor(new SGNodeRef(String.format("%s", hostname, port), id));
    }


    @Override
    public void main(String[] args) throws MsgException {}
}
