package scheduling.snooze;

/**
 * Created by sudholt on 04/07/2014.
 */
public class LCChargeMsg extends SnoozeMsg {
    LCChargeMsg(LCCharge lc, String sendBox, String origin, String replyBox) {
        super(lc, sendBox, origin, replyBox);
    }

    public class LCCharge {
        double procCharge;
        int memUsed;

        LCCharge(double proc, int mem) {
            this.procCharge = proc; this.memUsed = mem;
        }
    }
}
