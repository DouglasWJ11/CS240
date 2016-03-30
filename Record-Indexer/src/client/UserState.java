package client;

import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.clientcommunicator.ClientCommunicator;
import shared.model.Project;

public class UserState implements Serializable {
	private static UserState user=null;
	private ClientCommunicator comm;
	private boolean highlights, inverted;
	private int winXPosition, winYPosition, windowSize, xSplit, ySplit, zoomLevel, projectID, batchID, xSelectedCell, ySelectedCell, xImagePos, yImagePos, windowWidth, windowHeight;
	private double scale, xStart, yStart, xEnd, yEnd;
	private Object[][] tableEntry;
	private Project project;
	private String url;
	private Point windowlocation;
	
	private UserState(){
		projectID=0;
		batchID=0;
		xSelectedCell=0;
		ySelectedCell=0;
		xImagePos=-10000;
		yImagePos=-10000;
		xSplit=545;
		ySplit=422;
		winXPosition=-10000;
		winYPosition=-10000;
		windowWidth=900;
		windowHeight=700;
		scale=.7;
		windowlocation=new Point(0,0);
		highlights=true;
		inverted=false;
		comm=null;
		tableEntry=null;
	}
	
	public Point getWindowlocation() {
		return windowlocation;
	}
	public void setWindowlocation(Point windowlocation) {
		this.windowlocation = windowlocation;
	}
	
	public void logout(){
		user=null;
	}
	public void loadState(){
		File f=new File("data/users/" +comm.getUsername()+".state");
		Notifier.getNotifier().clearControllers();
		
		IndexingWindow indexer;
		
		if(!f.exists()){
			indexer=new IndexingWindow();
			return;
		}
		
		FileInputStream fs=null;
		try{
			XStream xStream =new XStream(new DomDriver());
			fs=new FileInputStream(f);
			UserState.setUser((UserState)xStream.fromXML(fs));
			fs.close();
			indexer=new IndexingWindow();
			if(UserState.getUser().getProjectID()!=0){
				Notifier.getNotifier().notifyDownloadBatch();
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(fs!=null){
				try {
					fs.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveState(){
		OutputStream outFile=null;
		FileOutputStream fs=null;
		try{
			XStream xStream =new XStream(new DomDriver());
			fs=new FileOutputStream(new File("data/users/" +comm.getUsername()+".state"));
			outFile=new BufferedOutputStream(fs);
			xStream.toXML(UserState.getUser(),outFile);
			fs.close();
			outFile.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally{
			if(outFile!=null){
				try{
					fs.close();
					outFile.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getHelpHtml(){
		StringBuilder sb=new StringBuilder();
		sb.append("http://" + comm.getServerHost() + ":" + comm.getServerPort()+"/");
		sb.append(project.getFields().get(this.ySelectedCell).getHelphtml());
		return sb.toString();
	}
	
	public Object getValueAt(int row, int col){
		return tableEntry[row][col];
	}
	
	public void setValueAt(int row,int column,Object value){
		tableEntry[row][column]=value;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = "http://" + comm.getServerHost() + ":" + comm.getServerPort()+"/"+url;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Object[][] getTableEntry() {
		return tableEntry;
	}

	public void setTableEntry(Object[][] tableEntry) {
		this.tableEntry = tableEntry;
	}
	
	public ClientCommunicator getComm(){
		return comm;
	}
	
	public void setComm(ClientCommunicator comm) {
		this.comm = comm;
	}
	
	public static UserState getUser() {
		if(user==null){
			user=new UserState();
		}
		return user;
	}

	public static void setUser(UserState user) {
		UserState.user = user;
	}

	public boolean isHighlights() {
		return highlights;
	}

	public void setHighlights(boolean highlights) {
		this.highlights = highlights;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	public int getxSplit() {
		return xSplit;
	}

	public void setxSplit(int xSplit) {
		this.xSplit = xSplit;
	}

	public int getySplit() {
		return ySplit;
	}

	public void setySplit(int ySplit) {
		this.ySplit = ySplit;
	}

	public int getWinXPosition() {
		return winXPosition;
	}

	public void setWinXPosition(int winXPosition) {
		this.winXPosition = winXPosition;
	}

	public int getWinYPosition() {
		return winYPosition;
	}

	public void setWinYPosition(int winYPosition) {
		this.winYPosition = winYPosition;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public int getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getBatchID() {
		return batchID;
	}

	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

	public int getxSelectedCell() {
		return xSelectedCell;
	}

	public void setxSelectedCell(int xSelectedCell) {
		this.xSelectedCell = xSelectedCell;
	}

	public void setSelectedCell(int row,int column){
		this.xSelectedCell=row;
		this.ySelectedCell=column;
	}
	
	public int getySelectedCell() {
		return ySelectedCell;
	}

	public void setySelectedCell(int ySelectedCell) {
		this.ySelectedCell = ySelectedCell;
	}

	public int getxImagePos() {
		return xImagePos;
	}

	public void setxImagePos(int xImagePos) {
		this.xImagePos = xImagePos;
	}

	public int getyImagePos() {
		return yImagePos;
	}

	public void setyImagePos(int yImagePos) {
		this.yImagePos = yImagePos;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		if(scale<.3){
			this.scale=.3;
		}
		if(scale>3){
			this.scale=3;
		}
	}

	public double getxStart() {
		return xStart;
	}

	public void setxStart(double xStart) {
		this.xStart = xStart;
	}

	public double getyStart() {
		return yStart;
	}

	public void setyStart(double yStart) {
		this.yStart = yStart;
	}

	public double getxEnd() {
		return xEnd;
	}

	public void setxEnd(double xEnd) {
		this.xEnd = xEnd;
	}

	public double getyEnd() {
		return yEnd;
	}

	public void setyEnd(double yEnd) {
		this.yEnd = yEnd;
	}
}
