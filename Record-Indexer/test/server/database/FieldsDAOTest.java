package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.access.accessFields;
import shared.model.fields;

public class FieldsDAOTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ProjectsDB.initialize();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		return;
	}
	private ProjectsDB db;
	private accessFields FieldsDAO;
	@Before
	public void setUp() throws Exception{
		db=new ProjectsDB();
		db.startTransaction();
		List<fields> fields = db.getFieldsDAO().getAll();
		
		for (fields f : fields) {
			db.getFieldsDAO().delete(f);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new ProjectsDB();
		db.startTransaction();
		FieldsDAO=db.getFieldsDAO();
	}
	@After
	public void tearDown() throws Exception{
		db.endTransaction(false);
		db=null;
		FieldsDAO=null;
	}

	@Test
	public void testAdd() throws DatabaseException {
		fields frank=new fields(-1,"name",100,200,"helpdata","knowndata",1,1);
		fields age=new fields(1,"age",725,120,"fieldhelp","knowndata",1,1);
		FieldsDAO.addField(frank);
		FieldsDAO.addField(age);
		
		List<fields> all=FieldsDAO.getAll();
		assertEquals(2,all.size());
		for(fields f:all){
			assertFalse(f.getID()==-1);
		}
	}

}
