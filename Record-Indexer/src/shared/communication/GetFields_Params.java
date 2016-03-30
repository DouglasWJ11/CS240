package shared.communication;

public class GetFields_Params {
	private String username;
	private String password;
	private int projectID;
	
	public GetFields_Params(String username, String password, int projectID){
		this.username=username;
		this.password=password;
		this.projectID=projectID;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getProjectID() {
		return projectID;
	}
	
}
