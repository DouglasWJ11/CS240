package servertester.controllers;

import java.util.*;

import client.clientcommunicator.ClientCommunicator;
import servertester.views.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		//getView().setResponse(comm.validateUser(params[0], params[1]));
	}
	
	private void getProjects() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		if(!params[0].isEmpty()&&!params[1].isEmpty()){
			//getView().setResponse(comm.getProjectInfo(params[0], params[1]));
		}
		else getView().setResponse("FAILED\n");
	}
	
	private void getSampleImage() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		if(!params[2].isEmpty()){
			getView().setResponse(comm.getSampleImage(params[0], params[1], Integer.parseInt(params[2])));
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		if(!params[2].isEmpty()){
			//getView().setResponse(comm.DownloadBatch(params[0], params[1], Integer.parseInt(params[2])));
		}
		else getView().setResponse("FAILED\n");
	}
	
	private void getFields() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		if(!params[2].isEmpty()){
			getView().setResponse(comm.getFields(params[0], params[1], Integer.parseInt(params[2])));
		}
		else{
			getView().setResponse(comm.getFields(params[0], params[1],0));			
		}
	}
	
	private void submitBatch() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		getView().setResponse(comm.SubmitBatch(params[0], params[1], Integer.parseInt(params[2]), params[3]));
	}
	
	private void search() {
		ClientCommunicator comm=new ClientCommunicator(getView().getHost(),Integer.parseInt(getView().getPort()));
		String[] params= getView().getParameterValues();
		getView().setResponse(comm.Search(params[0], params[1], params[2], params[3]));
	}

}

