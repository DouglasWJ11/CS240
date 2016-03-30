package server;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.facade.ServerFacade;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.ValidateUser_Result;

public class DownloadBatchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("Record-Indexer"); 

	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetSampleImage_Params db=null;
		DownloadBatch_Result result=null;
		try{
			db=(GetSampleImage_Params)xmlStream.fromXML(exchange.getRequestBody());
			ArrayList<Integer> projects=ServerFacade.getProjectIDs();
			if(projects.contains(db.getProjectID())){
				ValidateUser_Result validated=ServerFacade.validateUser(db.getUsername(), db.getPassword());
				if(validated.isValidated()&&validated.getBatchID()==0){
					result=ServerFacade.DownloadBatch(db.getProjectID(),db.getUsername());
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
