package scheduling.snooze;

/**
 * Created by sudholt on 24/06/2014.
 */

public class JoinLCMsg extends SnoozeMsg {
    private LocalController lc = null;

    JoinLCMsg(String lcHostname, String sendBox , String origin, String replyBox) {
        super(lcHostname, sendBox, origin, replyBox);
    }
}
