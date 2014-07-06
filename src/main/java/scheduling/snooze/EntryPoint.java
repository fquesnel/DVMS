package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;
import scheduling.snooze.msg.NewGLMsg;
import scheduling.snooze.msg.NewGMMsg;
import scheduling.snooze.msg.NewLCMsg;
import scheduling.snooze.msg.SnoozeMsg;

/**
 * Created by sudholt on 22/06/2014.
 */
public class EntryPoint extends Process {
    private Host host;
    private String glHostname = "";

    EntryPoint() {
        host = Host.currentHost();
    }

    public void main(String args[]) {
        while (true) {
            try {
                SnoozeMsg m = (SnoozeMsg) Task.receive(CONST.epInbox);
                handle(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void handle(SnoozeMsg m) {
        Logger.log("[EP.handle] Unknown message" + m);
    }

    void handle(NewGLMsg m) {
        glHostname = (String) m.getMessage();
    }

    void handle (NewGMMsg m) {
        if (glHostname != "") {
            NewGMMsg mGl = new NewGMMsg((String) m.getMessage(), CONST.glInbox, m.getOrigin(), m.getReplyBox());
            mGl.send();
        } else {
            // TODO: Leader election
            Logger.log("[EP.handle] New GM without GroupLeader");
        }
    }

    void handle(NewLCMsg m) { // Join/rejoin LC
        if (glHostname != "") {
            NewLCMsg mGl = new NewLCMsg((String) m.getMessage(), CONST.glInbox, m.getOrigin(), m.getReplyBox());
            mGl.send();
        } else Logger.log("[EP.handle] New LC without GroupLeader");
    }
}