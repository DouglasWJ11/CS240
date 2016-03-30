package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.access.accessImage;
import shared.model.images;

public class ImagesDAOTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		ProjectsDB.initialize();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		return;
	}
	private ProjectsDB db;
	private accessImage ImageDAO;
	@Before
	public void setUp() throws Exception{
		db=new ProjectsDB();
		db.startTransaction();
		List<images> images = db.getImageDAO().getALL();
		
		for (images i : images) {
			db.getImageDAO().delete(i);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new ProjectsDB();
		db.startTransaction();
		ImageDAO=db.getImageDAO();
	}
	@After
	public void tearDown() throws Exception{
		db.endTransaction(false);
		db=null;
		ImageDAO=null;
	}
	
	@Test
	public void getSampleImage() throws DatabaseException{
		images first=new images(-1,"thisisapath",1,0,0);
		images second=new images(-1,"filename",2,0,0);
		images third=new images(-1,"five",1,1,0);
		images fourth=new images(-1,"four",1,1,0);
		ImageDAO.addImage(first);
		ImageDAO.addImage(second);		
		ImageDAO.addImage(third);
		ImageDAO.addImage(fourth);
		List<images> mine=ImageDAO.getALL();
		images url=ImageDAO.getImage(1);
		images secondurl=ImageDAO.getImage(2);
		assertEquals("thisisapath",url.getFile());
		assertEquals("filename",secondurl.getFile());
	}
	@Test
	public void AddImagetest() throws DatabaseException {
		images first=new images(-1,"thisisapath",1,0,0);
		images second=new images(-1,"filename",2,0,0);
		ImageDAO.addImage(first);
		ImageDAO.addImage(second);
		List<images> all=ImageDAO.getALL();
		assertEquals(2,all.size());
	}

}
