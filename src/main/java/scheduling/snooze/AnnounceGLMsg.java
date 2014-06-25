package scheduling.snooze;

/**
 * Created by sudholt on 25/06/2014.
 */
public class AnnounceGLMsg extends SnoozeMsg {
    private GroupLeader gl = null;

    AnnounceGLMsg(GroupLeader gl, String sendBox, String origin, String replyBox) {
        super(gl, sendBox, origin, null);
    }

}
