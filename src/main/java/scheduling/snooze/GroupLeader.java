package scheduling.snooze;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public static class GroupLeader {
    ArrayList<GroupManagerCharge> gMCs = null;

    GroupLeader() {
        GLHeartbeat.add(this);
    }

    abstract void storeSummaryInfo() {

    }

    abstract void dispatchVMRequest() {

    }

    abstract void assignLCToGM() {

    }

    abstract void anouncePresence() {

    }

}
