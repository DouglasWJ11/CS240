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
import shared.model.SearchTuple;
import shared.model.fields;

public class accessFields {

	private static Logger logger;
	static{
		logger=Logger.getLogger("accessFields");
	}
	private ProjectsDB db;
	
	public accessFields(ProjectsDB projectsDB){
		this.db=projectsDB;
	}
	public void addField(fields field) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet keyRS=null;
		try{
			String query="insert into fields (title, xcoord, width, helphtml, knowndata, projectID, fieldnumber) values (?, ?, ?, ?, ?, ?, ?)";
			stmt=db.getConnection().prepareStatement(query);
			//stmt.setInt(1,field.getID());
			stmt.setString(1,field.getTitle());
			stmt.setInt(2,field.getXcoord());
			stmt.setInt(3,field.getWidth());
			stmt.setString(4,field.getHelphtml());
			stmt.setString(5,field.getKnownData());
			stmt.setInt(6,field.getProjectID());
			stmt.setInt(7, field.getFieldNumber());
			if(stmt.executeUpdate()==1){
				Statement keyStmt =db.getConnection().createStatement();
				keyRS=keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				field.setID(id);
			}
			else{
				throw new DatabaseException("Could not insert field");
			}
		}
		catch(SQLException e){
			System.out.print(e.getMessage());
			throw new DatabaseException("Could not insert field");
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(keyRS);
		}
	}
	
	public List<SearchTuple> searchFields(int i,String s) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List<SearchTuple> results=new ArrayList<SearchTuple>();
		try{
			String query ="select indexedData.imageID, image.file, indexedData.row, fields.id from indexedData "
					+ "join image on indexedData.imageID = image.id join fields on fields.fieldnumber=indexedData.vertical and "
					+ "fields.projectID=indexedData.projectID where fields.id=? and indexedData.data=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, i);
			stmt.setString(2, s);
			rs=stmt.executeQuery();
			while(rs.next()){
				int imageID=rs.getInt(1);
				String url=rs.getString(2);
				int recordnum=rs.getInt(3);
				int fieldID=rs.getInt(4);
				results.add(new SearchTuple(recordnum,url, imageID, fieldID));
			}
		} catch(SQLException e){
			DatabaseException serverEx=new DatabaseException(e.getMessage(),e);
			
			logger.throwing("server.database.fields","search" , serverEx);
			
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(rs);
		}
		return results;
	}
	public List<fields> getAll() throws DatabaseException{
		logger.entering("server.database.fields","getAll" );
		ArrayList<fields> result=new ArrayList<fields>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try{
			String query="select id, title, xcoord, width, helphtml, knowndata, projectID, fieldnumber from fields";
			stmt=db.getConnection().prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next()){
				int id=rs.getInt(1);
				String title=rs.getString(2);
				int xcoord=rs.getInt(3);
				int width=rs.getInt(4);
				String help=rs.getString(5);
				String known=rs.getString(6);
				int projectID=rs.getInt(7);
				int fieldnum=rs.getInt(8);
				result.add(new fields(id,title,xcoord,width,help,known,projectID,fieldnum));
			}
		}
		catch(SQLException e){
			DatabaseException serverEx=new DatabaseException(e.getMessage(),e);
			
			logger.throwing("server.database.fields","getALL" , serverEx);
			
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(rs);
			ProjectsDB.safeClose(stmt);
		}
		logger.exiting("server.database.fields","getALL");
		return result;
	}
	
	public void delete(fields field)throws DatabaseException{
		PreparedStatement stmt=null;
		try{
			String query="delete from fields where id=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, field.getID());
			if(stmt.executeUpdate()!=1){
				throw new DatabaseException("Could not delete field");
			}
		}
		catch(SQLException e){
			throw new DatabaseException("Could not delete field");
		}
		finally{
			ProjectsDB.safeClose(stmt);
		}
	}

	public void update(fields field)throws DatabaseException {
		PreparedStatement stmt=null;
		try{
			String query="update field set id=?,title=?,xcoord=?,width=?,helphtml=?,knowndata=?,projectID=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, field.getID());
			stmt.setString(2, field.getTitle());
			stmt.setInt(3, field.getXcoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelphtml());
			stmt.setString(6, field.getKnownData());
			stmt.setInt(7, field.getProjectID());
			if(stmt.executeUpdate()!=1){
				throw new DatabaseException("Could not update field");
			}
		}
		catch(SQLException e){
			throw new DatabaseException("Could not update field");
		}
		finally{
			ProjectsDB.safeClose(stmt);
		}
	}
}
