package scheduling.entropyBased.dvms2;

import org.simgrid.msg.*;


public class MsgForSG extends Task {
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Instance variable
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static private int ARBITRARY_MSG_SIZE = 1024; 
	//Message to send to the destination node
	private final Object message;
	private final String sendBox; 
	private final String replyBox;
    private final String origin;


	public MsgForSG(Object message, String sendBox , String origin, String replyBox) {
		super("",0,ARBITRARY_MSG_SIZE);
		this.message = message ;
		this.sendBox = sendBox ;
		this.replyBox = replyBox ;
        this.origin = origin;
	}
	
	public Object getMessage(){
		return message; 
	}
	public String getReplyBox(){
		return replyBox; 
	}

	public String getSendBox() {
		return this.sendBox; 
	}

    public String getOrigin() {
        return this.origin;
    }

	public void send() {

//        try {
//            this.send(this.getSendBox());
//        } catch (TransferFailureException e) {
//            e.printStackTrace();
//        } catch (HostFailureException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
        this.isend(this.getSendBox());
//        this.dsend(this.getSendBox());
	}
}
