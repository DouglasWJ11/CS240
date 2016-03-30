package client.clientcommunicator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.ClientException;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Results;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.SearchTuple;

public class ClientCommunicator {
	
	private String SERVER_HOST = "localhost";
	private int SERVER_PORT = 8080;
	private String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private String HTTP_GET = "GET";
	private String HTTP_POST = "POST";
	private String username;
	private String password;
	private XStream xmlStream;

	public String getServerHost(){
		return SERVER_HOST;
	}
	
	public int getServerPort(){
		return SERVER_PORT;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ClientCommunicator(String host, int port) {
		xmlStream = new XStream(new DomDriver());
		SERVER_HOST=host;
		SERVER_PORT=port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	public byte[] download(String URL) throws ClientException, IOException{
		URL url=new URL(URL);
		HttpURLConnection connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod(HTTP_GET);
		connection.connect();
		byte[] b = null;
		if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
			 connection.getInputStream().read(b);
		}
		return b;
	}
	
	private Object doPost(String urlPath, Object postData) throws ClientException {
		Object result=null;
		try {
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
				result=xmlStream.fromXML(connection.getInputStream());
			}
			else if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
		return result;
	}
	/**
	 * returns a download output object which contains all of the information relating to the downloaded image. 
	 * Fails if username and password don't validate, if projectID doesn't exist or if the user already has a batch assigned to them.
	 * @param username
	 * @param password
	 * @param projectID
	 * @return
	 * @throws ClientException 
	 */
	@SuppressWarnings("finally")
	public DownloadBatch_Result DownloadBatch(String username, String password, int projectID) {
		GetSampleImage_Params params=new GetSampleImage_Params(username,password,projectID);
		DownloadBatch_Result result=null;
			try {
				result=(DownloadBatch_Result)doPost("/DownloadBatch",params);
				return result;
			} catch (ClientException e) {
				// TODO Auto-generated catch block
			} finally{
				return result;
			}
	}
	
	public String SubmitBatch(String username, String password, int batchID, String fieldValues) {
		SubmitBatch_Params params = new SubmitBatch_Params(username,password,batchID,fieldValues);
		SubmitBatch_Result result;
		try {
			result = (SubmitBatch_Result)doPost("/SubmitBatch",params);
			if(result.success()){
				return "TRUE\n";
			}
			else return "FAILED\n";
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED\n";
		}
		
	}
	
	/**
	 * returns ID's and titles for all available projects
	 * @param username
	 * @param password
	 * @return project ID \n project Title \n
	 * @throws ClientException 
	 */
	/*public String getProjectInfo(String username, String password) {
		ValidateUser_Params params=new ValidateUser_Params (username,password);
		GetProjects_Result result=null;
		try {
			result=(GetProjects_Result)doPost("/GetProjects",params);
			if(result!=null){
				return result.toString();
			}
			else{
				return "FAILED\n";
			}
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED\n";
		}

	}*/
	
	public GetProjects_Result getProjectInfo(String username,String password){
		ValidateUser_Params params=new ValidateUser_Params(username,password);
		GetProjects_Result result=null;
		try{
			result=(GetProjects_Result)doPost("/GetProjects",params);
		} catch (ClientException e){
			e.printStackTrace();
		} finally{
			return result;
		}
	}
	/**
	 * If operation succeeds returns a url for an image in the project. If it fails return fail
	 * @param username
	 * @param password
	 * @param projectID
	 * @return a url string.
	 * @throws ClientException 
	 */
	public String getSampleImage(String username, String password, int projectID) {
		GetSampleImage_Params params=new GetSampleImage_Params(username,password,projectID);
		GetSampleImage_Result result;
		try {
			result = (GetSampleImage_Result)doPost("/GetSampleImage",params);
			if(result!=null){
				return URL_PREFIX+"/"+result.getURL();
			}
			else return "FAILED\n";
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED\n";
		}
		
	}
	
	/**checks if the parameters are valid together
	 * 
	 * @param username 
	 * @param password
	 * @return true if they match 
	 * @throws ClientException 
	 */
	public ValidateUser_Result validateUser(String username, String password) {
		ValidateUser_Params params=new ValidateUser_Params(username,password);
		ValidateUser_Result result=null;
		try {
			result = (ValidateUser_Result)doPost("/ValidateUser",params);
			return result;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		}
		
	}
	/**
	 * Returns a string containing all of the fields in a project.
	 * Fails if fails to validate user or if projectID doesn't exist
	 * @param username
	 * @param password
	 * @param projectID
	 * @return String of field info or FAILURE!
	 * @throws ClientException 
	 */
	public String getFields(String username, String password, int projectID) {
		GetFields_Params params=new GetFields_Params(username,password,projectID);
		GetFields_Result result;
		if(projectID!=0){
			try {
				result = (GetFields_Result)doPost("/GetFields",params);
				if(result!=null){
				return result.toString();
				} 
				else return("FAILED\n");
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "FAILED\n";
			}
		}
		else{
			try{
				result=(GetFields_Result)doPost("/GetFields",params);
				return result.toString();
			} catch(ClientException e){
				e.printStackTrace();
				return "FAILED\n";
			}
		}
	}
	/**
	 * returns a searchresult object if the parameters are matched. Fails if no validation no fields 
	 * @param username
	 * @param password
	 * @param fieldsparams
	 * @param searchParams
	 * @return a searchresult object or null
	 * @throws ClientException 
	 */
	public String Search(String username, String password, String fieldsparams,String searchParams) {
		Search_Params params=new Search_Params(username,password,fieldsparams,searchParams);
		Search_Results result;
		try {
			result = (Search_Results)doPost("/Search",params);
			StringBuilder sb=new StringBuilder();
			for(SearchTuple st:result.getResults()){
				sb.append(st.getBatchID()+"\n"+URL_PREFIX+"/"+st.getImageURL()+"\n"+st.getRecordNumber()+"\n"+st.getFieldID()+"\n");
			}
			return sb.toString();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED\n";
		}
		
	}
}
