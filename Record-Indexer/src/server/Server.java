package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.*;

import server.facade.ServerFacade;

public class Server {
	private static  int SERVER_PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("contactmanager"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	
	private HttpServer server;
	
	private Server() {
		return;
	}
	
	private void run() {
		
		logger.info("Initializing Model");
		
		try {
			ServerFacade.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		server.createContext("/DownloadBatch",DownloadBatchHandler);
		server.createContext("/",DownloadFileHandler);
		server.createContext("/GetFields",GetFieldsHandler);
		server.createContext("/GetProjects",GetProjectsHandler);
		server.createContext("/Search",SearchHandler);
		server.createContext("/SubmitBatch",SubmitBatchHandler);
		server.createContext("/ValidateUser",ValidateUserHandler);
		server.createContext("/GetSampleImage",GetSampleImageHandler);
		logger.info("Starting HTTP Server");

		server.start();
		
	}
	
	private HttpHandler DownloadBatchHandler=new DownloadBatchHandler();
	private HttpHandler DownloadFileHandler=new DownloadFileHandler();
	private HttpHandler GetFieldsHandler=new GetFieldsHandler();
	private HttpHandler GetProjectsHandler=new GetProjectsHandler();
	private HttpHandler SearchHandler=new SearchHandler();
	private HttpHandler SubmitBatchHandler=new SubmitBatchHandler();
	private HttpHandler ValidateUserHandler=new ValidateUserHandler();
	private HttpHandler GetSampleImageHandler=new GetSampleImageHandler();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		if(args.length>0){
			SERVER_PORT_NUMBER=Integer.parseInt(args[0]);
		}
		new Server().run();
	}

}
