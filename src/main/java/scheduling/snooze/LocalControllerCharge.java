package scheduling.snooze;

/**
 * Created by sudholt on 25/05/2014.
 */
public class LocalControllerCharge {
    private LocalController lc = null;
    private int procCharge = 0;

    public LocalController getLc() {
        return lc;
    }

    public void setLc(LocalController lc) {
        this.lc = lc;
    }

    public int getProcCharge() {
        return procCharge;
    }

    public void setProcCharge(int procCharge) {
        this.procCharge = procCharge;
    }
}
