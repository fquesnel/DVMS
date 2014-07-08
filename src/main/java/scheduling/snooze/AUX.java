package scheduling.snooze;

import org.simgrid.msg.Task;
import scheduling.snooze.msg.SnoozeMsg;

import java.util.Date;

/**
 * Created by sudholt on 06/07/2014.
 */
public class AUX {
    static final String epInbox = "epInbox";
    static final String glInbox = "glInbox";
    static final long HeartbeatInterval = 2500;
    static final long HeartbeatTimeout = 5000;
    static final long JoinAcknowledgementTimeout = 5000;

    static SnoozeMsg arecv(String mbox) {
        if (Task.listen(mbox))
            try {
                return (SnoozeMsg) Task.receive(mbox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    static long timeDiff(Date d) {
        long curTime = new Date().getTime();
        long oldTime = d.getTime();
        return curTime-oldTime;
    }
}
