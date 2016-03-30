package server.database;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import server.database.access.accessFields;
import server.database.access.accessImage;
import server.database.access.accessIndexedData;
import server.database.access.accessProject;
import server.database.access.accessUser;

public class ProjectsDB {

		
		private static final String DATABASE_DIRECTORY = "database";
		private static final String DATABASE_FILE = "Users.sqlite";
		private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
													File.separator + DATABASE_FILE;
		private static Logger logger;
		
		static {
			logger = Logger.getLogger("UsersDB");
		}

		public static void initialize() throws DatabaseException {
			try {
				final String driver = "org.sqlite.JDBC";
				Class.forName(driver);
			}
			catch(ClassNotFoundException e) {
				
				DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
				
				logger.throwing("server.database.database", "initialize", serverEx);

				throw serverEx; 
			}
		}

		private accessProject ProjectDAO;
		private accessUser UserDAO;
		private accessFields FieldsDAO;
		public accessUser getUserDAO() {
			return UserDAO;
		}

		public accessFields getFieldsDAO() {
			return FieldsDAO;
		}

		public accessImage getImageDAO() {
			return ImageDAO;
		}

		public accessIndexedData getIndexedDataDAO() {
			return IndexedDataDAO;
		}

		private accessImage ImageDAO;
		private accessIndexedData IndexedDataDAO;
		private Connection connection;
		
		public ProjectsDB() {
			ProjectDAO = new accessProject(this);
			UserDAO=new accessUser(this);
			FieldsDAO=new accessFields(this);
			ImageDAO=new accessImage(this);
			IndexedDataDAO=new accessIndexedData(this);
			connection = null;
		}
		
		public accessProject getProjectDAO() {
			return ProjectDAO;
		}
		
		public Connection getConnection() {
			return connection;
		}

		public void startTransaction() throws DatabaseException {
			try {
				assert (connection == null);	
				connection = DriverManager.getConnection(DATABASE_URL);
				connection.setAutoCommit(false);
			}
			catch (SQLException e) {
				throw new DatabaseException("Could not connect to database. Make sure " + 
					DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
			}
		}
		
		public void endTransaction(boolean commit) {
			if (connection != null) {		
				try {
					if (commit) {
						connection.commit();
					}
					else {
						connection.rollback();
					}
				}
				catch (SQLException e) {
					System.out.println("Could not end transaction");
					e.printStackTrace();
				}
				finally {
					safeClose(connection);
					connection = null;
				}
			}
		}
		
		public static void safeClose(Connection conn) {
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException e) {
					// ...
				}
			}
		}
		
		public static void safeClose(Statement stmt) {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException e) {
					// ...
				}
			}
		}
		
		public static void safeClose(PreparedStatement stmt) {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException e) {
					// ...
				}
			}
		}
		
		public static void safeClose(ResultSet rs) {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException e) {
					// ...
				}
			}
		}
}
