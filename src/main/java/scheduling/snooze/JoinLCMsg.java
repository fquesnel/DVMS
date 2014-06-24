package scheduling.snooze;

/**
 * Created by sudholt on 24/06/2014.
 */

public class JoinLCMsg extends SnoozeMsg {
    private LocalController lc = null;

    JoinLCMsg(LocalController lc, String sendBox , String origin, String replyBox) {
        super(lc, sendBox, origin, replyBox);
    }
}
