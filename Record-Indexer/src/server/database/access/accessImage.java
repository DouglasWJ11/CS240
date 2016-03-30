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
import shared.model.images;

/*
 * private String file;9orecordList;
	private int projectID;
 */
public class accessImage {
	/*
	 * 
	 */
	private static Logger logger;
	
	static{
		logger=Logger.getLogger("accessImage");
	}
	private ProjectsDB db;
	public accessImage(ProjectsDB projectsDB){
		this.db =projectsDB;
	}
	public void addImage(images image)throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet keyRS=null;
		try{
			String query="insert into image (file, projectID, checkedout, hasrecords) values (?,?,?,?)";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setString(1, image.getFile());
			stmt.setInt(2, image.getProjectID());
			stmt.setInt(3, image.getCheckedOut());
			stmt.setInt(4, image.getHasrecords());
			if(stmt.executeUpdate()==1){
				Statement keyStmt=db.getConnection().createStatement();
				keyRS=keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id=keyRS.getInt(1);
				image.setID(id);
			}
			else{
				throw new DatabaseException("Could not insert image");
			}
		}
		catch(SQLException e){
			throw new DatabaseException("Could not insert image");
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(keyRS);
		}
		/*IndexedDataDB idb=new IndexedDataDB();
		accessIndexedData indexedDataDAO=idb.getIndexedDataDAO();
		for(indexedData d:image.getRecords()){
			indexedDataDAO.addIndexedData(d);
		}*/
	}
	public int getProjectID(int batchID) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try{
			String query=("select projectID from image where id=?");
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, batchID);
			rs=stmt.executeQuery();
			return rs.getInt(1);
		} catch(SQLException e){
			throw new DatabaseException(e.getMessage(),e);
		}
	}
	public images getSampleImage(int projectID) throws DatabaseException{
		String sampleURL=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		images myImage=null;
		try{
			String query=("select id, file, projectID from image where projectID=?");
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			rs=stmt.executeQuery();
			int id=rs.getInt(1);
			sampleURL=rs.getString(2);
			int projectid=rs.getInt(3);
			myImage=new images(id,sampleURL,projectid,0,0);
		}
		catch(SQLException e){
			DatabaseException serverEx=new DatabaseException(e.getMessage(),e);
			logger.throwing("server.database.image", "getSample", serverEx);
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(rs);
		}
		return myImage;
	}
	public images getImage(int projectID) throws DatabaseException{
		String sampleURL=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		images myImage=null;
		try{
			String query=("select id, file, projectID from image where projectID=? AND checkedout=0 AND hasrecords=0");
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			rs=stmt.executeQuery();
			int id=rs.getInt(1);
			sampleURL=rs.getString(2);
			int projectid=rs.getInt(3);
			myImage=new images(id,sampleURL,projectid,0,0);
		}
		catch(SQLException e){
			DatabaseException serverEx=new DatabaseException(e.getMessage(),e);
			logger.throwing("server.database.image", "getSample", serverEx);
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(rs);
		}
		return myImage;
	}
	
	public void updateRecords(int batchID)throws DatabaseException{
		PreparedStatement stmt=null;
		try{
			String query=("update image set hasrecords=? where id=?");
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1,1);
			stmt.setInt(2, batchID);
			if(stmt.executeUpdate()!=1){
				throw new DatabaseException("Could not update image");
			}
		} catch (SQLException e){
			throw new DatabaseException("Could not update image",e);
		} finally{
			ProjectsDB.safeClose(stmt);
		}
	}
	
	public List<images> getALL() throws DatabaseException{
		logger.entering("server.database.image", "getALL");
		ArrayList<images> result=new ArrayList<images>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try{
			String query="select id, file, projectID,checkedout from image";
			stmt=db.getConnection().prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next()){
				int id=rs.getInt(1);
				String file=rs.getString(2);
				int projectID=rs.getInt(3);
				int checkedOut=rs.getInt(4);
				int records=rs.getInt(5);
				result.add(new images(id,file,projectID,checkedOut,records));
			}
		}
		catch(SQLException e){
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.image", "getAll", serverEx);
			
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(rs);
		}
		logger.exiting("server.database.image", "getALL");
		return result;
	}
	
	public void delete(images image){
		
	}
	/**
	 * 
	 * @param Record
	 */
	
}
