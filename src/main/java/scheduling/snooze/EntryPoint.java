package scheduling.snooze;

/**
 * Created by sudholt on 22/06/2014.
 */
public class EntryPoint extends org.simgrid.msg.Process {
    private GroupLeader GL = null;
    private String inBox = "entryPointInbox";

    public GroupLeader getGL() {
        return GL;
    }

    public void setGL(GroupLeader GL) {
        this.GL = GL;
    }

    public String getInBox() {
        return inBox;
    }

    public void setInBox(String inBox) {
        this.inBox = inBox;
    }

    public void main(String args[]) {

    }
}