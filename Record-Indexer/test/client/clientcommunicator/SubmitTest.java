package client.clientcommunicator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataImporter.DataImporter;
import server.Server;
import shared.communication.DownloadBatch_Result;

public class SubmitTest {
	private int batchID;
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
		DownloadBatch_Result batch=comm.DownloadBatch("test1", "test1", 1);
		//String[] response=batch.split("\n");
		//batchID=Integer.parseInt(response[0]);
	}
	@After
	public void tearDown() throws Exception{
		comm=null;
	}
	@Test
	public void test() {
		String values="Johnson,Doug,Male,25;Johnson,Heather,Female,24";
		String result=comm.SubmitBatch("test1", "test1", batchID, values);
		assertEquals(result,"TRUE\n");
	}

}
