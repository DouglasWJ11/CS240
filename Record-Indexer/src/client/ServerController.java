package client;

import java.util.List;

import client.clientcommunicator.ClientCommunicator;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetProjects_Result;
import shared.communication.ValidateUser_Result;
import shared.model.Project;

public class ServerController {
	private static String username;
	private static String password;
	
	public static void setUsername(String user){
		username=user;
	}
	
	public static void setPassword(char[] pass){
		StringBuilder sb=new StringBuilder();
		for(char c:pass){
			sb.append(c);
		}
		password=sb.toString();
	}
	
	public static DownloadBatch_Result downloadBatch(int projectID){
		return UserState.getUser().getComm().DownloadBatch(username, password, projectID);	
	}
	
	public static String getSampleImage(int project){
		return UserState.getUser().getComm().getSampleImage(username, password, project);
	}
	
	public static Object[] getProjects(){
		GetProjects_Result result=UserState.getUser().getComm().getProjectInfo(username,password);
		List<Project> projectInfo=result.getProjects();
		Object[] projects= new Object[projectInfo.size()];
		int size=projectInfo.size();
		for (int i=0;i<size;i++){
			projects[i]=projectInfo.remove(0).getTitle();
		}
		return projects;
	}
	public static ValidateUser_Result validateUser(String username,char[] password){
		StringBuilder sb=new StringBuilder();
		for(char c:password){
			sb.append(c);
		}
		return UserState.getUser().getComm().validateUser(username, sb.toString());
	}
	
	public static void submitBatch(Object[][] myArray){
		StringBuilder sb=new StringBuilder();
		int k=0;
		while(k<myArray.length){
			for(int i=0;i<myArray.length;i++){
				for(int j=1;j<myArray[i].length;j++){
					sb.append(myArray[i][j]);
					if(j!=myArray[i].length-1){
						sb.append(",");
					}
				}
				sb.append(";");
				k++;
			}
		}
		UserState.getUser().getComm().SubmitBatch(username, password, UserState.getUser().getBatchID(), sb.toString());
	}
}
