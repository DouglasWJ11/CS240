package shared.communication;

public class ValidateUser_Result {
	private boolean validated;
	private	String firstName;
	private	String lastName;
	private	int recordsIndexed;
	private int current_batchID;
	/**
	 *@param params ValidatedUser_Params containing username and password
	 */
	public ValidateUser_Result(boolean validated,String firstname,String lastname,int recordsindexed,int batchID){
		this.validated=validated;
		this.firstName=firstname;
		this.lastName=lastname;
		this.recordsIndexed=recordsindexed;
		this.current_batchID=batchID;
	}
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		if(validated==true){
		sb.append("TRUE\n"+firstName+"\n"+lastName+"\n"+recordsIndexed);
		}
		else sb.append("FALSE");
		return sb.toString();
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getRecordsIndexed() {
		return recordsIndexed;
	}
	public void setRecordsIndexed(int recordsIndexed) {
		this.recordsIndexed = recordsIndexed;
	}
	public int getBatchID(){
		return current_batchID;
	}
}
