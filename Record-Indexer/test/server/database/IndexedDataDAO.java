package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.access.accessIndexedData;
import shared.model.Value;
import shared.model.indexedData;

public class IndexedDataDAO {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ProjectsDB.initialize();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		return;
	}
	private ProjectsDB db;
	private accessIndexedData indexedDataDAO;
	@Before
	public void setUp() throws Exception{
		db=new ProjectsDB();
		db.startTransaction();
		List<Value> records = db.getIndexedDataDAO().getAll();
		
		for (Value i : records) {
			db.getIndexedDataDAO().delete(i);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new ProjectsDB();
		db.startTransaction();
		indexedDataDAO=db.getIndexedDataDAO();
	}
	@After
	public void tearDown() throws Exception{
		db.endTransaction(false);
		db=null;
		indexedDataDAO=null;
	}
	@Test
	public void test() throws DatabaseException {
		ArrayList<Value> values=new ArrayList<Value>();
		Value firstname=new Value("Doug",1,1,-1,20);
		Value lastname=new Value("Johnson",1,2,-1,20);
		values.add(firstname);
		values.add(lastname);
		indexedData id=new indexedData(values);
		indexedDataDAO.addIndexedData(id,1,1);
		ArrayList<Value> ar= indexedDataDAO.getAll();
		assertEquals(2,ar.size());
	}

}
