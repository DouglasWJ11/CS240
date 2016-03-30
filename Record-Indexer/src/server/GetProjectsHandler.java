package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.facade.ServerFacade;
import shared.communication.GetProjects_Result;
import shared.communication.ValidateUser_Params;

public class GetProjectsHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("Record-Indexer"); 

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		ValidateUser_Params db=null;
		GetProjects_Result result=null;
		try{
			db=(ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
			if(ServerFacade.validateUser(db.getUserName(), db.getPassword()).isValidated()){
				result=ServerFacade.getProjects();
			}
		} catch(ServerException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
