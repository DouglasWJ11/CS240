package shared.model;

public class SearchTuple {
	private int batchID;
	private String imageURL;
	private int recordNumber;
	private int fieldID;
		
	public SearchTuple(int recordnum, String url, int imageID, int fieldID){
		this.batchID=imageID;
		this.imageURL=url;
		this.recordNumber=recordnum;
		this.fieldID=fieldID;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(batchID+"\n"+imageURL+"\n"+recordNumber+"\n"+fieldID+"\n");
		return sb.toString();
	}
	public int getBatchID() {
		return batchID;
	}
	public String getImageURL() {
		return imageURL;
	}
	public int getRecordNumber() {
		return recordNumber;
	}
	public int getFieldID() {
		return fieldID;
	}
}
