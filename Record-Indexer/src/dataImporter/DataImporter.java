package dataImporter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.database.DatabaseException;
import server.database.ProjectsDB;
import server.database.access.accessFields;
import server.database.access.accessImage;
import server.database.access.accessIndexedData;
import server.database.access.accessProject;
import server.database.access.accessUser;
import shared.model.Project;
import shared.model.User;
import shared.model.fields;
import shared.model.images;
import shared.model.indexedData;

public class DataImporter {
	public static void main(String[] args){ //throws ServerException, DatabaseException, ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory docF=DocumentBuilderFactory.newInstance();
		DocumentBuilder docB;
		ProjectsDB DB=new ProjectsDB();
		accessProject projectDAO=DB.getProjectDAO();
		accessUser userDAO=DB.getUserDAO();
		accessFields fieldDAO=DB.getFieldsDAO();
		accessImage imageDAO=DB.getImageDAO();
		accessIndexedData indexedDAO=DB.getIndexedDataDAO();
		File xmlFile = new File(args[0]);
		File dest = new File("Records");
		File emptydb = new File("database" + File.separator + "indexer_server_empty.sqlite");
		File currentdb = new File("database" + File.separator + "Users.sqlite");
		try {
			ProjectsDB.initialize();
		} catch (DatabaseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try
		{
			//	We make sure that the directory we are copying is not the the destination
			//	directory.  Otherwise, we delete the directories we are about to copy.
			if(!xmlFile.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
				FileUtils.deleteDirectory(dest);
				
			//	Copy the directories (recursively) from our source to our destination.
			FileUtils.copyDirectory(xmlFile.getParentFile(), dest);
			
			//	Overwrite my existing *.sqlite database with an empty one.  Now, my
			//	database is completelty empty and ready to load with data.
			FileUtils.copyFile(emptydb, currentdb);
		}
		catch (IOException e)
		{
			//logger.log(Level.SEVERE, "Unable to deal with the IOException thrown", e);
		}
			
		try {
			docB = docF.newDocumentBuilder();File f=new File(args[0]);
			Document doc;
			try {
				doc = docB.parse(f);
				doc.getDocumentElement().normalize();
				Element root=doc.getDocumentElement();
				dbData importer=new dbData(root);
				DB.startTransaction();
				for(User u:importer.getUsers()){
					try {
						userDAO.addUser(u);
					} catch (DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						DB.endTransaction(false);
					} 
				}
				DB.endTransaction(true);
				DB.startTransaction();
				for(Project p:importer.getProjects()){
					try {
						projectDAO.addProject(p);
					} catch (DatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						DB.endTransaction(false);
					}
				}
				DB.endTransaction(true);
				DB.startTransaction();
				for(Project p:importer.getProjects()){
					for(fields field:p.getFields()){
						try {
							fieldDAO.addField(field);
						} catch (DatabaseException e){
							e.printStackTrace();
							DB.endTransaction(false);
						}
					}
				}
				DB.endTransaction(true);
				DB.startTransaction();
				for(Project p:importer.getProjects()){
					for(images i:p.getImages()){
						try{
							imageDAO.addImage(i);
						} catch (DatabaseException e){
							e.printStackTrace();
							DB.endTransaction(false);
						}
					}
				}
				DB.endTransaction(true);
				DB.startTransaction();
				for(Project p:importer.getProjects()){
					for(images i:p.getImages()){
						for(indexedData id:i.getRecords()){
							try{
								indexedDAO.addIndexedData(id,i.getID(),p.getID());
							} catch (DatabaseException e){
								e.printStackTrace();
								DB.endTransaction(false);
							}
						}
					}
				}
				DB.endTransaction(true);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{
				DB.endTransaction(false);
				DB.endTransaction(false);
			}
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			
		}
	}
	
	
	public static String getValue(Element element) {
		String result = "";
		Node child = element.getFirstChild();
		result = child.getNodeValue();
		return result;
	}
	public static ArrayList<Element> getChildElements(Node node) {
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
		Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE){
			result.add((Element)child);
			}
		}
		return result;
		}
}
