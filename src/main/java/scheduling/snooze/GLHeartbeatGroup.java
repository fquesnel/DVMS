package scheduling.snooze;

import org.simgrid.msg.Host;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Task;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by sudholt on 22/06/2014.
 */
public class GLHeartbeatGroup extends Process {
    private static GroupLeader gl = null;
    private static long ts = 0;
    private String glHeartbeatInbox = "glHeartbeatInbox";

    public static void setGl(GroupLeader gl) {
        if (GLHeartbeatGroup.gl != null && GLHeartbeatGroup.gl != gl) System.out.println("[GLHeartbeatGroup] Err: multiple GLs");
        else {
            GLHeartbeatGroup.gl = gl;
            ts = new Date().getTime();
        }
    }

    public void main(String[] args) throws MsgException {
        while (true) {
            // get GL heartbeat message
            setGl(receiveAnnounceGLMsg());
        }
    }

    protected GLHeartbeatGroup() {}

    GroupLeader receiveAnnounceGLMsg() {
        GroupLeader gl = null;
        try{
            AnnounceGLMsg req = (AnnounceGLMsg) Task.receive(glHeartbeatInbox, 2);
            Logger.log(Host.currentHost().getName() + ": received " + req.getMessage());
            gl = (GroupLeader) req.getMessage();
        } catch (org.simgrid.msg.TimeoutException te) {
            Logger.log("GLHeartbeatGroup::receiveAnnounceGLMsg: timeout, GL dead");
            te.printStackTrace();
        } catch (Exception e) {
            Logger.log(e);
        }
        return gl;
    }

    @Override
    public void destroy() {}

    @Override
    public int waitFor() throws InterruptedException {
        return 0;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public int exitValue() {
        return 0;
    }
}
