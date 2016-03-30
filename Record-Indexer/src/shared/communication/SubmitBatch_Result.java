package shared.communication;

public class SubmitBatch_Result {
	private boolean submitted;
	
	public SubmitBatch_Result(boolean success){
		submitted=success;
	}
	
	public boolean success(){
		return submitted;
	}
}
