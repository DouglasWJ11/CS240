package shared.model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import dataImporter.DataImporter;

public class images {
	private String file;
	private ArrayList<indexedData> records=new ArrayList<indexedData>();
	private int projectID;
	private int checkedOut;
	private int id;
	private int hasrecords;
	
	public images(int id,String file, int projectID,int checkedOut, int records){
		this.id=id;
		this.file=file;
		this.projectID=projectID;
		this.checkedOut=checkedOut;
		this.hasrecords=records;
	}
	
	public images(Element imageElement,int projectID){
		this.projectID=projectID;
		file=DataImporter.getValue((Element)imageElement.getElementsByTagName("file").item(0));
		Element recordElement=(Element)imageElement.getElementsByTagName("records").item(0);
		if(recordElement!=null){
			hasrecords=1;
			NodeList recordElements=recordElement.getElementsByTagName("record");
			for(int i=0;i<recordElements.getLength();i++){
				records.add(new indexedData((Element)recordElements.item(i),i+1));
			}
		}
	}
	
	public int getId() {
		return id;
	}

	public int getHasrecords() {
		return hasrecords;
	}

	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public int getProjectID(){
		return projectID;
	}
	
	public void setProjectID(int ID){
		this.projectID=ID;
	}
	public int getCheckedOut(){
		return checkedOut;
	}
	public void setCheckedOut(int check){
		checkedOut=check;
	}
	public int getID(){
		return id;
	}
	public void setID(int id){
		this.id=id;
	}
	public int recordSize(){
		return records.size();
	}

	public ArrayList<indexedData> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<indexedData> records) {
		this.records = records;
	}
	
	@Override
	public String toString(){
		StringBuilder sb= new StringBuilder();
		sb.append(id+"\n"+projectID+"\n"+file+"\n"+checkedOut);
		return sb.toString();
	}
}
