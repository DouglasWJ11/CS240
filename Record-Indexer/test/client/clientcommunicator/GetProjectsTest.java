package client.clientcommunicator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataImporter.DataImporter;
import server.Server;
import shared.communication.GetProjects_Result;

public class GetProjectsTest {
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
	public void Successtest() {
		GetProjects_Result output=comm.getProjectInfo("test1", "test1");
		//assertEquals("1\n1890 Census\n2\n1900 Census\n3\nDraft Records\n",output);
	}
	@Test
	public void failedUserValidation(){
		GetProjects_Result output=comm.getProjectInfo("test", "test");
		//assertEquals("FAILED\n",output);
	}

}
