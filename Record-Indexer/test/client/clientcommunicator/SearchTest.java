package client.clientcommunicator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataImporter.DataImporter;
import server.Server;

public class SearchTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		String[] file=new String[1];
		file[0]="record-indexer-data/Records/Records.xml";
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
	public void Onefieldtest() {
		String result=comm.Search("test1", "test1", "10", "WHITE");
		assertEquals("55\nhttp://localhost:8080images/draft_image14.png\n55\n10\n",result);
	}
	@Test
	public void multifiedTest(){
		String result=comm.Search("test1","test1","10,11,12,13","FOX,RUSSELL,SIMON,PABLO");
		System.out.print(result);
	}

}
