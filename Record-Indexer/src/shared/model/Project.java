package shared.model;

import java.awt.List;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dataImporter.DataImporter;

public class Project {
	private int ID;
	private String title=null;
	private int recordsperimage=-1;
	private int firstycoord=-1;
	private int recordheight=-1;
	
	private ArrayList<fields> fields=new ArrayList<fields>();
	private ArrayList<images> images=new ArrayList<images>();
	
	public Project(int ID,String title, int records,int firsty,int height){
		this.ID=ID;
		this.title=title;
		recordsperimage=records;
		firstycoord=firsty;
		recordheight=height;
	}
	public Project(Element projectElement,int id){
		this.ID=id;
		title=DataImporter.getValue((Element)projectElement.getElementsByTagName("title").item(0));
		recordsperimage = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("recordsperimage").item(0)));
		firstycoord = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("firstycoord").item(0)));
		recordheight = Integer.parseInt(DataImporter.getValue((Element)projectElement.getElementsByTagName("recordheight").item(0)));
		Element fieldsElement=(Element)projectElement.getElementsByTagName("fields").item(0);
		NodeList fieldElements= fieldsElement.getElementsByTagName("field");
		for(int i=0;i<fieldElements.getLength();i++){
			fields.add(new fields((Element)fieldElements.item(i),id,i+1));
		}
		Element imageElement=(Element)projectElement.getElementsByTagName("images").item(0);
		NodeList imageElements=imageElement.getElementsByTagName("image");
		for(int i=0;i<imageElements.getLength();i++){
			images.add(new images((Element)imageElements.item(i),id));
		}
		/*ArrayList<Element> rootElements=DataImporter.getChildElements(projectElement);
		ArrayList<Element> fieldElements=DataImporter.getChildElements(rootElements.get(4));
		for(Element field:fieldElements){
			
		}
		ArrayList<Element> imageElements=DataImporter.getChildElements(rootElements.get(5));
		for(Element image:imageElements){
			images.add(new images(image));
		}*/
		/*
		Element fieldsElement = (Element)projectElement.getElementsByTagName("fields").item(0);
		NodeList fieldElements=fieldsElement.getElementsByTagName("field");
		for(int i=0;i<fieldElements.getLength(); i++){
			fields.add(new fields((Element)fieldElements.item(i)));
		}
		Element imagesElement=(Element)projectElement.getElementsByTagName("images");
		NodeList imageElements=imagesElement.getElementsByTagName("image");
		for(int i=0;i<imageElements.getLength(); i++){
			images.add(new images((Element)imageElements.item(i)));
		}*/
	}
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(ID+"\n"+title);
		return sb.toString();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRecordsperimage() {
		return recordsperimage;
	}
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}
	public int getFirstycoord() {
		return firstycoord;
	}
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}
	public int getRecordheight() {
		return recordheight;
	}
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public ArrayList<fields> getFields() {
		return fields;
	}
	public void setFields(ArrayList<fields> fields) {
		this.fields = fields;
	}
	public ArrayList<images> getImages() {
		return images;
	}
	public void setImages(ArrayList<images> images) {
		this.images = images;
	}

	
}
