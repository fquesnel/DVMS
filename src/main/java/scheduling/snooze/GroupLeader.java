package scheduling.snooze;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupLeader {
    ArrayList<GroupManagerCharge> gMCs = null;
    private String glHeartbeatInbox = "glHeartbeatInbox";

    GroupLeader() {
    }

    void storeSummaryInfo() {

    }

    void dispatchVMRequest() {

    }

    void assignLCToGM() {

    }

    void announcePresence() {
        AnnounceGLMsg m = new AnnounceGLMsg(this, glHeartbeatInbox, null, null);
    }
}
