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
import shared.communication.ValidateUser_Result;
import shared.model.User;


public class accessUser {
	/**
	 * 
	 * @param userName
	 * @param Password
	 * @return
	 */
private static Logger logger;
	
	static {
		logger = Logger.getLogger("accessUser");
	}
private ProjectsDB db;
	
	public accessUser (ProjectsDB projectsDB){
		this.db = projectsDB;
	}
	public void setBatchID(int batchID,String username) throws DatabaseException{
		PreparedStatement stmt=null;
		try{
			String query="update users set current_batchID=? where username=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setInt(1, batchID);
			stmt.setString(2, username);
			if(stmt.executeUpdate()!=1){
				throw new DatabaseException("Could not update user");
			}
		} catch (SQLException e){
			throw new DatabaseException("Could not update user",e);
		} finally{
			ProjectsDB.safeClose(stmt);
		}
	}
	public void updateRecordsIndexed(String username,int recordsperimage) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try{
			String query="select records_indexed from Users where username=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			rs=stmt.executeQuery();
			int recordsIndexed=rs.getInt(1);
			recordsIndexed+=recordsperimage;
			String update="update users set records_indexed=? where username=?";
			stmt=db.getConnection().prepareStatement(update);
			stmt.setInt(1, recordsIndexed);
			stmt.setString(2, username);
			if(stmt.executeUpdate()!=1){
				throw new DatabaseException("Could not update user");
			}
		} catch(SQLException e){
			throw new DatabaseException(e.getMessage(),e);
		}
	}
	public ValidateUser_Result validateUser(String username, String password) throws DatabaseException{
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ValidateUser_Result user=null;
		try{
			//String query = "select id, username, Firstname, Lastname, emailaddress, password, records_indexed, current_batchID from Users where username =?"; //+ username; //+ "AND password =" + password;
			String query="select Firstname, Lastname, records_indexed, current_batchID from Users where username=? AND password=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs=stmt.executeQuery();
			if(rs.next()){
				String firstname=rs.getString(1);
				String lastname=rs.getString(2);
				int records=rs.getInt(3);
				int batchid=rs.getInt(4);
				user=new ValidateUser_Result(true,firstname,lastname,records,batchid);
			}
			else{
				user=new ValidateUser_Result(false,null,null,0,0);
			}
			/*while(rs.next()){
				int id = rs.getInt(1);
				String uname = rs.getString(2);
				String firstname= rs.getString(3);
				String lastname = rs.getString(4);
				String pword = rs.getString(5);
				String email = rs.getString(6);
				int recordsIndexed=rs.getInt(7);
				int batchID=rs.getInt(8);
				user=new User(id,uname,firstname,lastname,email,pword,recordsIndexed,batchID);
			}*/
		}
		catch(SQLException e){
			DatabaseException serverEx=new DatabaseException(e.getMessage(),e);
			logger.throwing("server.database.Users", "validateUser", serverEx);
			throw serverEx;
		}
		finally{
			ProjectsDB.safeClose(rs);
			ProjectsDB.safeClose(stmt);
		}
		return user;
	}
	
	public void addUser(User user) throws DatabaseException{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;	
		try{
			String query = "insert into Users (Username, Firstname, Lastname, emailaddress, password, records_indexed,current_batchID) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getEmailAddress());
			stmt.setString(5, user.getPassword());
			stmt.setInt(6, user.getRecordsIndexed());
			stmt.setInt(7, user.getCurrentBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setID(id);
			}
			else {
				throw new DatabaseException("Could not insert contact");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert contact", e);
		}
		finally {
			ProjectsDB.safeClose(stmt);
			ProjectsDB.safeClose(keyRS);
		}
	}
	
	public List<User> getAll() throws DatabaseException{
		logger.entering("server.database.Users", "getAll");
		
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, username, Firstname, Lastname, emailaddress, password, records_indexed, current_batchID from users";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String firstname= rs.getString(3);
				String lastname = rs.getString(4);
				String password = rs.getString(5);
				String email = rs.getString(6);
				int recordsIndexed=rs.getInt(7);
				int batchID=rs.getInt(8);
				result.add(new User(id,username,firstname,lastname,email,password,recordsIndexed,batchID));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Users", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			ProjectsDB.safeClose(rs);
			ProjectsDB.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "getAll");
		
		return result;	
	}
	
	public void delete(User contact) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from users where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, contact.getID());
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
	
	public void update(User user) throws DatabaseException{
		PreparedStatement stmt=null;
		try{
			String query="update Users set Username=?,Firstname=?,Lastname=?,emailaddress=?,"
					+ "password=?,records_indexed=?,current_batchID=? where id=?";
			stmt=db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getFirstName());
			stmt.setString(3, user.getLastName());
			stmt.setString(4, user.getEmailAddress());
			stmt.setString(5, user.getPassword());
			stmt.setInt(6, user.getRecordsIndexed());
			stmt.setInt(7, user.getCurrentBatchID());
			stmt.setInt(8, user.getID());
			if(stmt.executeUpdate()!=1){
				throw new DatabaseException("Could not update user");
			}
		}
		catch(SQLException e){
			throw new DatabaseException("Could not update user",e);
		}
		finally{
			ProjectsDB.safeClose(stmt);
		}
	}
}
