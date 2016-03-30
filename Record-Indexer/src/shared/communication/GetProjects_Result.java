package shared.communication;

import java.util.List;

import shared.model.Project;

public class GetProjects_Result {
	private List<Project> projects;
	
	public GetProjects_Result(List<Project> projects){
		this.projects=projects;
	}
	public List<Project> getProjects(){
		return projects;
	}
	public  String toString(){
		StringBuilder sb=new StringBuilder();
		for(Project p:projects){
			sb.append(p.getID()+"\n");
			sb.append(p.getTitle()+"\n");
		}
		return sb.toString();
	}
}
