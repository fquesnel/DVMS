package scheduling.snooze;

import java.util.ArrayList;

/**
 * Created by sudholt on 25/05/2014.
 */
public class GroupManager {
    GroupLeader gl;
    ArrayList<LocalControllerCharge> lCCs = null;

    GroupManager() {
        GMHeartbeat.add(this);
    }

    abstract void receiveHostQuery() {

    }

    abstract void answerHostQuery() {

    }

    abstract void receiveVMQuery() {

    }

    abstract void answerVMQuery() {

    }

    abstract void scheduleVMs() {

    }

    abstract void sendVMCommandsLC() {

    }

    abstract void summaryInfoToGL() {

    }

    abstract void announcePresence() {

    }
}
