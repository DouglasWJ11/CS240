package shared.model;

import org.w3c.dom.Element;

import dataImporter.DataImporter;

public class fields {
	private int ID;
	private int fieldnumber;
	private String title;
	private int xcoord;
	private int width;
	private String helphtml;
	private String knownData;
	private int ProjectID;
	
	public fields(int id, String title, int xcoord, int width, String help, String known, int project,int fieldnum){
		ID=id;
		this.title=title;
		this.xcoord=xcoord;
		this.width=width;
		helphtml=help;
		knownData=known;
		ProjectID=project;
		fieldnumber=fieldnum;
	}
	
	public fields(Element fieldElement,int projectID,int num){
		this.ProjectID=projectID;
		title=DataImporter.getValue((Element)fieldElement.getElementsByTagName("title").item(0));
		xcoord=Integer.parseInt(DataImporter.getValue((Element)fieldElement.getElementsByTagName("xcoord").item(0)));
		width=Integer.parseInt(DataImporter.getValue((Element)fieldElement.getElementsByTagName("width").item(0)));
		helphtml=DataImporter.getValue((Element)fieldElement.getElementsByTagName("helphtml").item(0));
		if(fieldElement.getElementsByTagName("knowndata").getLength()>0){
			knownData=DataImporter.getValue((Element)fieldElement.getElementsByTagName("knowndata").item(0));
		}
		fieldnumber=num;
	}
	public int getFieldNumber(){
		return fieldnumber;
	}
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getXcoord() {
		return xcoord;
	}
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getHelphtml() {
		return helphtml;
	}
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}
	public String getKnownData() {
		return knownData;
	}
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}

}
