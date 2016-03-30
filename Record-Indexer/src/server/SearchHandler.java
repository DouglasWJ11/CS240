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
import shared.communication.Search_Params;
import shared.communication.Search_Results;

public class SearchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("Record-Indexer"); 

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Search_Params db=null;
		Search_Results result=null;
		try{
			db=(Search_Params)xmlStream.fromXML(exchange.getRequestBody());
			if(ServerFacade.validateUser(db.getUsername(), db.getPassword()).isValidated()){
			result=ServerFacade.Search(db);
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
