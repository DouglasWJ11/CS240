package shared.model;

import org.w3c.dom.Element;

import dataImporter.DataImporter;

public class Value {
	private String value;
	private int column;
	private int row;
	private int recordID;
	private int imageID;
	
	public Value(Element valueElement,int row,int column){
		value = DataImporter.getValue(valueElement);
		this.column=column;
		this.row=row;
	}
	
	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public Value(String v,int r,int c,int recordid,int imageID){
		value=v;
		row=r;
		column=c;
		recordID=recordid;
		this.imageID=imageID;
	}
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public String getValue(){
		return value;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setValue(String v){
		value=v;
	}
}
