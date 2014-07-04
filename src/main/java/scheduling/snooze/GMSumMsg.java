package scheduling.snooze;

/**
 * Created by sudholt on 04/07/2014.
 */
public class GMSumMsg extends SnoozeMsg {
    GMSumMsg(GMChargeSummary gms, String sendBox, String origin, String replyBox) {
        super(gms, sendBox, origin, replyBox);
    }
}
