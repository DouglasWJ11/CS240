package client.clientcommunicator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataImporter.DataImporter;
import server.Server;

public class GetSampleImageTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		String[] file=new String[1];
		file[0]="record-indexer-data/records/Records.xml";
		DataImporter.main(file);
		String[] args=new String[1];
		args[0]="8080";
		Server.main(args);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		return;
	}
	private ClientCommunicator comm;
	@Before
	public void setUp() throws Exception{
		comm=new ClientCommunicator("localhost",8080);
	}
	@After
	public void tearDown() throws Exception{
		comm=null;
	}
	@Test
	public void test() {
		String url=comm.getSampleImage("test1", "test1", 1);
		assertEquals("http://localhost:8080/images/1890_image0.png",url);
	}

}
