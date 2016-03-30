package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.facade.ServerFacade;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;

public class GetSampleImageHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("Record-Indexer"); 

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetSampleImage_Params db=null;
		GetSampleImage_Result result=null;
		try{
			db=(GetSampleImage_Params)xmlStream.fromXML(exchange.getRequestBody());
			ArrayList<Integer> projects=ServerFacade.getProjectIDs();
			if(projects.contains(db.getProjectID())){
				if(ServerFacade.validateUser(db.getUsername(), db.getPassword()).isValidated()){
				result=ServerFacade.GetSampleImage(db.getProjectID());
				}
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
