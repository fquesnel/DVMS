package scheduling.snooze;

/**
 * Created by sudholt on 04/07/2014.
 */
public class LCChargeMsg extends SnoozeMsg {
    LCChargeMsg(LocalControllerCharge lc, String sendBox, String origin, String replyBox) {
        super(lc, sendBox, origin, replyBox);
    }
}
