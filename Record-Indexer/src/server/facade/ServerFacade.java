package server.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.ServerException;
import server.database.DatabaseException;
import server.database.ProjectsDB;
import server.database.access.accessFields;
import server.database.access.accessImage;
import server.database.access.accessIndexedData;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleImage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Results;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.communication.ValidateUser_Result;
import shared.model.Project;
import shared.model.SearchTuple;
import shared.model.Value;
import shared.model.fields;
import shared.model.images;
import shared.model.indexedData;

public class ServerFacade {
	public static void initialize() throws ServerException {		
		try {
			ProjectsDB.initialize();
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static ValidateUser_Result validateUser(String username, String password) throws ServerException{
		ProjectsDB udb=new ProjectsDB();
		try{
			udb.startTransaction();
			ValidateUser_Result user=udb.getUserDAO().validateUser(username, password);
			udb.endTransaction(true);
			return user;
		}
		catch(DatabaseException e){
			udb.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
		
	}
	public static ArrayList<Integer> getProjectIDs() throws ServerException{
		ArrayList<Integer> projectIDs=new ArrayList<Integer>();
		ProjectsDB db=new ProjectsDB();
		GetProjects_Result projects=null;
		try{
			db.startTransaction();
			projects=new GetProjects_Result(db.getProjectDAO().getProjectInfo());
			db.endTransaction(true);
			List<Project> projectlist=projects.getProjects();
			for(Project P:projectlist){
				projectIDs.add(P.getID());
			}
			return projectIDs;
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
	}
	
	public static GetProjects_Result getProjects() throws ServerException{
		ProjectsDB db=new ProjectsDB();
		GetProjects_Result projects=null;
		try{
			db.startTransaction();
			projects=new GetProjects_Result(db.getProjectDAO().getProjectInfo());
			db.endTransaction(true);
			return projects;
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
	}
	
	public static GetSampleImage_Result GetSampleImage(int projectID) throws ServerException{
		ProjectsDB db=new ProjectsDB();
		GetSampleImage_Result sample=null;
		try{
			db.startTransaction();
			sample=new GetSampleImage_Result(db.getImageDAO().getSampleImage(projectID).getFile());
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
		return sample;
	}
	
	public static DownloadBatch_Result DownloadBatch(int projectID,String username) throws ServerException{
		ProjectsDB db=new ProjectsDB();
		DownloadBatch_Result dbr=null;
		images image=null;
		Project found=null;
		List<fields> allFields;
		try{
			db.startTransaction();
			image=db.getImageDAO().getImage(projectID);
			db.endTransaction(true);
		} catch(DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
		try{
			db.startTransaction();
			found=db.getProjectDAO().getProject(projectID);
			db.endTransaction(true);
		} catch(DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
		try{
			db.startTransaction();
			allFields=db.getFieldsDAO().getAll();
			db.endTransaction(true);
		} catch (DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
		ArrayList<fields> matched=new ArrayList<fields>();
		for(fields f:allFields){
			if(f.getProjectID()==projectID){
				matched.add(f);
			}
		}
		found.setFields(matched);
		try{
			db.startTransaction();
			db.getUserDAO().setBatchID(image.getID(), username);
			db.endTransaction(true);
		} catch(DatabaseException e){
			db.endTransaction(false);
			throw new ServerException(e.getMessage(),e);
		}
		dbr=new DownloadBatch_Result(found,image);
		return dbr;
	}
	public static SubmitBatch_Result SubmitBatch(SubmitBatch_Params params){
		ArrayList<Value> values=new ArrayList<Value>();
		HashMap<Integer,String> records=params.getRecordValues();
		for(int i=0;i<records.size();i++){
			String  s=records.get(i);
			String delim="[,]";
			String[] v=s.split(delim);
			for(int j=0;j<v.length;j++){
				values.add(new Value(v[j],i+1,j+1,-1,params.getBatchID()));
			}
		}
		indexedData id=new indexedData(values);
		ProjectsDB db=new ProjectsDB();
		accessIndexedData indexedDAO=db.getIndexedDataDAO();
		accessImage imageDAO=db.getImageDAO();
		try{
			db.startTransaction();
			db.getUserDAO().setBatchID(0, params.getUsername());
			int recordsperimage=db.getProjectDAO().getProject(imageDAO.getProjectID(params.getBatchID())).getRecordsperimage();
			db.getUserDAO().updateRecordsIndexed(params.getUsername(), recordsperimage);
			db.endTransaction(true);
		} catch(DatabaseException e){
			db.endTransaction(false);
			e.printStackTrace();
			return new SubmitBatch_Result(false);
		}
		try{
			db.startTransaction();
			imageDAO.updateRecords(params.getBatchID());
			db.endTransaction(true);
		} catch(DatabaseException e){
			db.endTransaction(false);
			e.printStackTrace();
			return new SubmitBatch_Result(false);
		}
		try {
			db.startTransaction();
			int projectID=imageDAO.getProjectID(params.getBatchID());
			indexedDAO.addIndexedData(id,params.getBatchID(),projectID);
			db.endTransaction(true);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);
			e.printStackTrace();
			return new SubmitBatch_Result(false);
		}
		return new SubmitBatch_Result(true);
	}
	public static GetFields_Result GetFields(GetFields_Params param) throws ServerException{
		ProjectsDB db=new ProjectsDB();
		accessFields fieldsDAO=db.getFieldsDAO();
		List<fields> allFields=null;
		ArrayList<fields> chosen=new ArrayList<fields>();
		try{
			db.startTransaction();
			allFields=fieldsDAO.getAll();
			db.endTransaction(true);
		} catch(DatabaseException e){
			db.endTransaction(false);
			e.printStackTrace();
			throw new ServerException(e.getMessage(),e);
		}
		if(param.getProjectID()>0){
			for(fields f:allFields){
				if(f.getProjectID()==param.getProjectID()){
					chosen.add(f);
				}
			}
			return new GetFields_Result(chosen);
		}
		else return new GetFields_Result(allFields);
	}
	public static Search_Results Search(Search_Params param) throws ServerException {
		ProjectsDB fDB=new ProjectsDB();
		accessFields fieldsDAO=fDB.getFieldsDAO();
		ArrayList<SearchTuple> searchResults=new ArrayList<SearchTuple>();
		try{
			fDB.startTransaction();
			for(int i:param.getSearchFields()){
				for(String s:param.getSearchStrings()){
					searchResults.addAll(fieldsDAO.searchFields(i, s));
				}
			}
			fDB.endTransaction(true);
		} catch(DatabaseException e){
			fDB.endTransaction(false);
			e.printStackTrace();
			throw new ServerException(e.getMessage(),e);
		}
			return new Search_Results(searchResults);
	}
}
