package client.clientcommunicator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataImporter.DataImporter;
import server.Server;
import shared.communication.ValidateUser_Result;

public class ValidateTest {
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
	public void Validateonetest() {
		ValidateUser_Result first=comm.validateUser("test1", "test2");
		//assertEquals("FALSE",first);
		ValidateUser_Result second=comm.validateUser("sheila", "parker");
		//assertEquals("TRUE\nSheila\nParker\n"+0,second);
	}

}
