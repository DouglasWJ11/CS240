package shared.communication;

import java.util.List;

import shared.model.fields;

public class GetFields_Result {
	private List<fields> fields;
	
	public GetFields_Result(List<fields> fields){
		this.fields=fields;
	}
	public List<fields> getFields() {
		return fields;
	}
	public void setFields(List<fields> fields) {
		this.fields = fields;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(fields f:fields){
			sb.append(f.getProjectID()+"\n"+f.getID()+"\n"+f.getTitle()+"\n");
		}
		return sb.toString();
	}
}
