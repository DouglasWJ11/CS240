package dataImporter;

import java.util.ArrayList;

import org.w3c.dom.Element;

import shared.model.Project;
import shared.model.User;

public class dbData {
	private ArrayList<User> users=new ArrayList<User>();
	private ArrayList<Project> projects=new ArrayList<Project>();
	public dbData(Element root) {
		ArrayList<Element> rootElements = DataImporter.getChildElements(root);
		ArrayList<Element> userElements =DataImporter.getChildElements(rootElements.get(0));
		for(Element userElement : userElements) {
			users.add(new User(userElement));
		}
		ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
		int i=1;
		for(Element projectElement : projectElements) {
			projects.add(new Project(projectElement,i));
			i++;
		}
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public ArrayList<Project> getProjects() {
		return projects;
	}
}
