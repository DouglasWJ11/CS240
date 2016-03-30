package shared.communication;

public class ValidateUser_Params {
	private String userName;
	private String password;

	
	/**
	 * an object containing login information.
	 * @param uName
	 * @param pWord
	 */
	public ValidateUser_Params(String uName, String pWord){
		userName=uName;
		password=pWord;		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
