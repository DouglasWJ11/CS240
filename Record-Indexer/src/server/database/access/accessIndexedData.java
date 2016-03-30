package server.database.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.database.ProjectsDB;
import shared.model.Value;
import shared.model.indexedData;

public class accessIndexedData {
	
	private static Logger logger;
	static{
		logger=Logger.getLogger("accessIndexedData");
	}
	private ProjectsDB db;
	
	public accessIndexedData(ProjectsDB projectsDB){
		this.db=projectsDB;
	}
	
	public void addIndexedData(indexedData id,int imageID,int projectID) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet keyRS=null;
		try{
			for(Value v:id.getValues()){
				String query="insert into indexedData (data, row, vertical, imageID, projectID) values(?,?,?,?,?)";
				stmt = db.getConnection().prepareStatement(query);
				stmt.setString(1,v.getValue());
				stmt.setInt(2, v.getRow());
				stmt.setInt(3, v.getColumn());
				stmt.setInt(4, imageID);
				stmt.setInt(5, projectID);
				if(stmt.executeUpdate()==1){
					Statement keyStmt=db.getConnection().createStatement();
					keyRS = keyStmt.executeQuery("select last_insert_rowid()");
					keyRS.next();
					int recordID = keyRS.getInt(1);
					v.setRecordID(recordID);
				}
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			throw new DatabaseException("Could not insert record into table");
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(keyRS);
		}
	}
	
	public List<Value> getRecord(indexedData ID){
		return ID.getValues();
	}

	public ArrayList<Value> getAll() throws DatabaseException {
		// TODO Auto-generated method stub
logger.entering("server.database.IndexedData", "getAll");
		
		ArrayList<Value> result = new ArrayList<Value>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select recordID, data, row, vertical, imageID from indexedData";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id=rs.getInt(1);
				String value=rs.getString(2);
				int row=rs.getInt(3);
				int column=rs.getInt(4);
				int imageID=rs.getInt(5);
				result.add(new Value(value,row,column,id,imageID));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.indexedData", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			ProjectsDB.safeClose(rs);
			ProjectsDB.safeClose(stmt);
		}

		logger.exiting("server.database.indexedData", "getAll");
		
		return result;	
	}
	public void delete(Value value) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from indexedData where data = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, value.getValue());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete contact");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete contact", e);
		}
		finally {
			ProjectsDB.safeClose(stmt);
		}
	}
}
