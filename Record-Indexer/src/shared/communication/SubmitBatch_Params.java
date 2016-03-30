package shared.communication;

import java.util.HashMap;

public class SubmitBatch_Params {
	private String username;
	private String password;
	private int batchID;
	private String fieldValues;
	public SubmitBatch_Params(String username,String password,int batchID, String fieldValues){
		this.username=username;
		this.password=password;
		this.batchID=batchID;
		this.fieldValues=fieldValues;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getBatchID() {
		return batchID;
	}
	public HashMap<Integer,String> getRecordValues() {
		String delims="[;]";
		String[] records=fieldValues.split(delims);
		HashMap<Integer,String> split=new HashMap<Integer,String>();
		for(int i=0;i<records.length;i++){
			split.put(i, records[i]);
		}
		return split;
	}
	
}
