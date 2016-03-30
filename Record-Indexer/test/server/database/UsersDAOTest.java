package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.access.accessUser;
import shared.communication.ValidateUser_Result;
import shared.model.User;

public class UsersDAOTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ProjectsDB.initialize();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		return;
	}
	private ProjectsDB db;
	private accessUser userDAO;
	@Before
	public void setUp() throws Exception{
		db=new ProjectsDB();
		db.startTransaction();
		List<User> contacts = db.getUserDAO().getAll();
		
		for (User c : contacts) {
			db.getUserDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new ProjectsDB();
		db.startTransaction();
		userDAO=db.getUserDAO();
	}
	@After
	public void tearDown() throws Exception{
		db.endTransaction(false);
		db=null;
		userDAO=null;
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		User jim=new User(-1,"jimmyb","Jim","Bob","SuperPassword","jimmybob@jimmy.com",0,0);
		User phil=new User(-1,"philly","Phillip","Johnson","LessGood","p@p.com",0,0);
		userDAO.addUser(jim);
		userDAO.addUser(phil);
		
		List<User> all=userDAO.getAll();
		assertEquals(2,all.size());
		boolean foundJim=false;
		boolean foundPhil=false;
		
		for(User u:all){
			assertFalse(u.getID()==-1);
				if(!foundJim){
					foundJim=areEqual(u,jim,false);
				}
				if(!foundPhil){
					foundPhil=areEqual(u,phil,false);
				}
		}
		assertTrue(foundJim&&foundPhil);
	}
	
	@Test
	public void testGetAll() throws DatabaseException{
		List<User> all=userDAO.getAll();
		assertEquals(0,all.size());
	}
	
	@Test
	public void testDelete() throws DatabaseException{
		User jim=new User(-1,"jimmyb","Jim","Bob","SuperPassword","jimmybob@jimmy.com",0,0);
		User phil=new User(-1,"philly","Phillip","Johnson","LessGood","p@p.com",0,0);
		
		userDAO.addUser(jim);
		//userDAO.addUser(phil);
		try{
		userDAO.delete(jim);
		userDAO.delete(phil);
		}
		catch(DatabaseException e){
			//System.out.println("Cannot delete");
		}
	}
	
	@Test
	public void testUpdate() throws DatabaseException{
		User jim=new User(-1,"jimmyb","Jim","Bob","SuperPassword","jimmybob@jimmy.com",0,0);
		User phil=new User(-1,"philly","Phillip","Johnson","LessGood","p@p.com",0,0);
		
		userDAO.addUser(jim);
		userDAO.addUser(phil);
		
		jim.setFirstName("Greg");
		jim.setLastName("John");
		phil.setEmailAddress("frank@gmail.com");
		phil.setRecordsIndexed(phil.getRecordsIndexed()+20);
		userDAO.update(jim);
		userDAO.update(phil);
		List<User> all=userDAO.getAll();
		assertEquals(2,all.size());
	}
	
	//@Test
	/*public void testValidateUser() throws DatabaseException{
		User jim=new User(-1,"jimmyb","Jim","Bob","SuperPassword","jimmybob@jimmy.com",0,0);
		User phil=new User(-1,"philly","Phillip","Johnson","LessGood","p@p.com",0,0);
		
		userDAO.addUser(jim);
		userDAO.addUser(phil);
//		boolean f=userDAO.validateUser("sup","joe");
		ValidateUser_Result u=userDAO.validateUser("philly","LessGood");
		ValidateUser_Result two=userDAO.validateUser("James", "fake");
		assertEquals("Phillip",u.getFirstName());
		assertNotEquals("Jim",u.getFirstName());
		assertNotEquals("James",two.getFirstName());
		assertEquals(false,two.isValidated());
		//assertEquals(true,userDAO.validateUser("jimmyb","SuperPassword"));
	}*/
	@Test
	public void updateBatch()throws DatabaseException{
		User jim=new User(-1,"jimmyb","Jim","Bob","SuperPassword","jimmybob@jimmy.com",0,0);
		User phil=new User(-1,"philly","Phillip","Johnson","LessGood","p@p.com",0,0);
		
		userDAO.addUser(jim);
		userDAO.addUser(phil);
		userDAO.setBatchID(1, "jimmyb");
		List<User> all=userDAO.getAll();
		for(User u:all){
			System.out.println(u.getCurrentBatchID());
		}
	}
	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getID() != b.getID()) {
				return false;
			}
		}	
		return (safeEquals(a.getUsername(), b.getUsername()) &&
				safeEquals(a.getFirstName(), b.getFirstName()) &&
				safeEquals(a.getLastName(), b.getLastName()) &&
				safeEquals(a.getEmailAddress(), b.getEmailAddress()) &&
				safeEquals(a.getPassword(), b.getPassword()));
	}
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}
}
