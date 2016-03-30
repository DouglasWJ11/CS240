package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.access.accessProject;
import shared.model.Project;

public class ProjectDAOTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ProjectsDB.initialize();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		return;
	}
	private ProjectsDB db;
	private accessProject ProjectDAO;
	@Before
	public void setUp() throws Exception{
		/*db=new ProjectsDB();
		db.startTransaction();
		List<Project> contacts = db.getProjectDAO().getProjectInfo();
		
		for (Project p : contacts) {
			db.getProjectDAO().delete(p);
		}
		
		db.endTransaction(true);*/

		// Prepare database for test case	
		db = new ProjectsDB();
		db.startTransaction();
		ProjectDAO=db.getProjectDAO();
	}
	@After
	public void tearDown() throws Exception{
		db.endTransaction(true);
		db=null;
		ProjectDAO=null;
	}
	@Test 
	public void Databasesetup()throws DatabaseException{
		ProjectDAO.copyEmptyDatabase();
	}
	/*@Test
	public void testAddProject() throws DatabaseException {
		Project one=new Project(1,"1910 Census",20,11,5);
		Project two=new Project(2,"1920 Census",20,11,5);
		ProjectDAO.addProject(one);
		ProjectDAO.addProject(two);
		List<Project> projects=ProjectDAO.getProjectInfo();
		for(Project p:projects){
			System.out.println(p.toString());
		}
		assertEquals(5,projects.size());
		//ProjectDAO.delete(one);
		//ProjectDAO.delete(two);
	}*/
	/*@Test
	public void testGetProjectInfo() throws DatabaseException{
		List<Project> projects=ProjectDAO.getProjectInfo();
		for(Project p:projects){
			System.out.println(p.toString());
		}
		assertEquals(3,projects.size());
	}*/


}
