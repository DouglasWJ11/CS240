package shared.communication;

import java.util.ArrayList;

import shared.model.Project;
import shared.model.fields;
import shared.model.images;

public class DownloadBatch_Result {
	private Project project;
	private images batch;
	
	public DownloadBatch_Result(Project project,images batch){
		this.project=project;
		this.batch=batch;
	}
	
	public Project getProject(){
		return project;
	}
	public images getBatch(){
		return batch;
	}
	public String toString(String urlPrefix){
		ArrayList<fields> myFields=project.getFields();
		StringBuilder sb=new StringBuilder();
		sb.append(batch.getID()+"\n");
		sb.append(project.getID()+"\n");
		sb.append(urlPrefix+"/"+batch.getFile()+"\n");
		sb.append(project.getFirstycoord()+"\n");
		sb.append(project.getRecordheight()+"\n");
		sb.append(project.getRecordsperimage() +"\n");
		sb.append(myFields.size()+"\n");
		for(fields f:myFields){
			sb.append(f.getID()+"\n");
			sb.append(f.getFieldNumber()+"\n");
			sb.append(f.getTitle()+"\n");
			sb.append(urlPrefix+"/"+f.getHelphtml()+"\n");
			sb.append(f.getXcoord()+"\n");
			sb.append(f.getWidth()+"\n");
			if(f.getKnownData()!=null){
				sb.append(urlPrefix+"/"+f.getKnownData()+"\n");
			}
		}
		return sb.toString();
	}
}
