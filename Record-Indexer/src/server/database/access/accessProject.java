package server.database.access;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.*;

import server.database.DatabaseException;
import server.database.ProjectsDB;
import shared.model.Project;
public class accessProject {
	
	private static Logger logger;
	
	static {
		logger = Logger.getLogger("ProjectsDB");
	}
	
	private ProjectsDB db;
	public accessProject(ProjectsDB db){
		this.db=db;
	}
	public void copyEmptyDatabase(){
		File emptydb = new File("database" + File.separator + "indexer_server_empty.sqlite");
		File currentdb = new File("database" + File.separator + "Users.sqlite");
		try {
			FileUtils.copyFile(emptydb, currentdb);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the name and id of each project
	 * @param userName
	 * @param Password
	 * @return
	 * @throws DatabaseException 
	 */
	public Project getProject(int projectID) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Project found=null;
		try{
			String query ="select id, title, records_per_image, firstycoord, recordheight from projects where id=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			rs=stmt.executeQuery();
			int id=rs.getInt(1);
			String title=rs.getString(2);
			int records=rs.getInt(3);
			int firsty=rs.getInt(4);
			int recordheight=rs.getInt(5);
			found=new Project(id,title,records,firsty,recordheight);
		} catch(SQLException e){
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.projects", "getAll", serverEx);
			
			throw serverEx;
		}
		return found;
	}
	public List<Project> getProjectInfo() throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ArrayList<Project> result=new ArrayList<Project>();
		try{
			String query ="select id, title, records_per_image, firstycoord, recordheight from projects";
			stmt=db.getConnection().prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next()){
				int id=rs.getInt(1);
				String title=rs.getString(2);
				int records=rs.getInt(3);
				int firsty=rs.getInt(4);
				int height=rs.getInt(5);
				result.add(new Project(id,title,records,firsty,height));
			}
		}
		catch(SQLException e){
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.projects", "getAll", serverEx);
			
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(rs);
			ProjectsDB.safeClose(stmt);
		}
		return result;
	}
	
	public void addProject(Project p) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;	
		try{
			String query = "insert into projects(title,records_per_image,firstycoord,recordheight) values(?,?,?,?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1,p.getTitle());
			stmt.setInt(2, p.getRecordsperimage());
			stmt.setInt(3, p.getFirstycoord());
			stmt.setInt(4, p.getRecordheight());
			if(stmt.executeUpdate()==1){
				Statement keyStmt=db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				p.setID(id);
			}
			else{
				throw new DatabaseException("Could not insert project");
			}
		}
		catch(SQLException e){
			throw new DatabaseException("Could not insert project");
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			ProjectsDB.safeClose(keyRS);
			ProjectsDB.safeClose(stmt);
		}
	}
	
	public void delete(Project project) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from projects where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, project.getID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete project", e);
		}
		finally {
			ProjectsDB.safeClose(stmt);
		}
	}
}
