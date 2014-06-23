package scheduling.snooze;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManager {
    GroupLeader gl;
    ArrayList<LocalControllerCharge> lCCs = null;

    GroupManager() {

    }

    void receiveHostQuery() {

    }

    void answerHostQuery() {

    }

    void receiveVMQuery() {

    }

    void answerVMQuery() {

    }

    void scheduleVMs() {

    }

    void sendVMCommandsLC() {

    }

    void summaryInfoToGL() {

    }

    void announcePresence() {
        GMHeartbeatGroup.getGmhg().add(this);
    }
}
