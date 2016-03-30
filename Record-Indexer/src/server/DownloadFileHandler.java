package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.*;

public class DownloadFileHandler implements HttpHandler{
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{
		try{
			File file=new File("./Records" +File.separator+exchange.getRequestURI());
			exchange.sendResponseHeaders(200,0);
			Files.copy(file.toPath(), exchange.getResponseBody());
			exchange.getResponseBody().close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	/*public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		String filepath="/Records";
		String s=filepath+exchange.getRequestURI().toString();
		System.out.println(s);
		Path p=Paths.get(s);
		byte[] b=Files.readAllBytes(p);
		exchange.sendResponseHeaders(200, b.length);
		exchange.getResponseBody().write(b);
		exchange.close();
		
	}*/

}
