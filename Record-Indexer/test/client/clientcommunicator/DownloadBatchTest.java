package client.clientcommunicator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataImporter.DataImporter;
import server.Server;

public class DownloadBatchTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

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
		String[] file=new String[1];
		file[0]="record-indexer-data/Records/Records.xml";
		DataImporter.main(file);
		comm=new ClientCommunicator("localhost",8080);
	}
	@After
	public void tearDown() throws Exception{
		comm=null;
	}
	/*@Test
	public void Successtest() {
		String result=comm.DownloadBatch("test1", "test1", 1);
		//System.out.print(result);
		assertEquals("1\n1\nhttp://localhost:8080/images/1890_image0.png\n199\n0\n4\n1\nLast Name\nhttp://localhost:8080/fieldhelp/last_name.html"
				+ "\n60\n300\nhttp://localhost:8080/knowndata/1890_last_names.txt\n2\nFirst Name\nhttp://localhost:8080/fieldhelp/first_name.html"
				+ "\n360\n280\nhttp://localhost:8080/knowndata/1890_first_names.txt\n3\nGender\nhttp://localhost:8080/fieldhelp/gender.html\n640"
				+ "\n205\nhttp://localhost:8080/knowndata/genders.txt\n4\nAge\nhttp://localhost:8080/fieldhelp/age.html\n845\n120\n",result);
	}
	@Test
	public void failedtest(){
		String one=comm.DownloadBatch("test", "test", 1);
		assertEquals("FAILED\n",one);
		String two=comm.DownloadBatch("test1", "test1", 3);
		assertEquals("FAILED\n",two);
		String three=comm.DownloadBatch("test1", "test1", 4);
		assertEquals("FAILED\n",three);
	}
	@Test
	public void download2ndbatch(){
		String result =comm.DownloadBatch("test1", "test1", 1);
		assertEquals("1\n1\nhttp://localhost:8080/images/1890_image0.png\n199\n0\n4\n1\nLast Name\nhttp://localhost:8080/fieldhelp/last_name.html"
				+ "\n60\n300\nhttp://localhost:8080/knowndata/1890_last_names.txt\n2\nFirst Name\nhttp://localhost:8080/fieldhelp/first_name.html"
				+ "\n360\n280\nhttp://localhost:8080/knowndata/1890_first_names.txt\n3\nGender\nhttp://localhost:8080/fieldhelp/gender.html\n640"
				+ "\n205\nhttp://localhost:8080/knowndata/genders.txt\n4\nAge\nhttp://localhost:8080/fieldhelp/age.html\n845\n120\n",result);
		String failed=comm.DownloadBatch("test1", "test1", 1);
		assertEquals("FAILED\n",failed);
	}*/
}
