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
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.communication.ValidateUser_Result;

public class SubmitBatchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("Record-Indexer"); 

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		SubmitBatch_Params db=null;
		SubmitBatch_Result result=null;
		try{
			db=(SubmitBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
			ValidateUser_Result validated=ServerFacade.validateUser(db.getUsername(), db.getPassword());
			if(validated.isValidated()&&validated.getBatchID()==db.getBatchID()){
				result=ServerFacade.SubmitBatch(db);
			}
			else result=new SubmitBatch_Result(false);
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
