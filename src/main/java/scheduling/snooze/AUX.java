package scheduling.snooze;

import org.simgrid.msg.Task;
import scheduling.snooze.msg.SnoozeMsg;

/**
 * Created by sudholt on 06/07/2014.
 */
public class AUX {
    static final String epInbox = "epInbox";
    static final String glInbox = "glInbox";
    static final long HeartbeatTimeout = 2000;

    static SnoozeMsg arecv(String mbox) {
        if (Task.listen(mbox))
            try {
                return (SnoozeMsg) Task.receive(mbox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
}
