package shared.model;

import org.w3c.dom.Element;

import dataImporter.DataImporter;

public class User {
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String emailAddress;
	private int ID;
	int recordsIndexed;
	int currentBatchID;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * creates a user object based on a user in the database
	 */
	public User(Element UserElement){
		username=DataImporter.getValue((Element)UserElement.getElementsByTagName("username").item(0));
		firstName=DataImporter.getValue((Element)UserElement.getElementsByTagName("firstname").item(0));
		lastName=DataImporter.getValue((Element)UserElement.getElementsByTagName("lastname").item(0));
		password=DataImporter.getValue((Element)UserElement.getElementsByTagName("password").item(0));
		emailAddress=DataImporter.getValue((Element)UserElement.getElementsByTagName("email").item(0));
		//ID=Integer.parseInt(DataImporter.getValue((Element)UserElement.getElementsByTagName("id").item(0)));
		recordsIndexed=Integer.parseInt(DataImporter.getValue((Element)UserElement.getElementsByTagName("indexedrecords").item(0)));
		//currentBatchID=Integer.parseInt(DataImporter.getValue((Element)UserElement.getElementsByTagName("current_batchID").item(0)));
	}
	public User(int ID,String uName,String fName, String lName, String pWord,String eAdd, int recs, int batchID){
		this.ID=ID;
		username=uName;
		firstName=fName;
		lastName=lName;
		password=pWord;
		emailAddress=eAdd;
		recordsIndexed=recs;
		currentBatchID=batchID;
	}
	public String toString(){
		return username+" "+firstName+" "+lastName+" "+emailAddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getRecordsIndexed() {
		return recordsIndexed;
	}
	public void setRecordsIndexed(int recordsIndexed) {
		this.recordsIndexed = recordsIndexed;
	}
	public int getCurrentBatchID() {
		return currentBatchID;
	}
	public void setCurrentBatchID(int currentBatchID) {
		this.currentBatchID = currentBatchID;
	}


}
