package listem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

//testing
public class LineCounter extends Search implements ILineCounter {

	@Override
	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, boolean recursive) {
		// TODO Auto-generated method stub
		Map<File,Integer> numbers=new HashMap<File, Integer>();
		File[] files=directory(directory,recursive,fileSelectionPattern);
		for(File f:files){
			try {
				//BufferedReader reader = new BufferedReader(new FileReader(f));
				Scanner s =new Scanner(f);
				s.useDelimiter("\n");
				int lines = 0;
				while(s.hasNextLine()){
					try{
						s.next();
						lines++;
					}
					catch(NoSuchElementException e){
						lines++;
					}
				}
				s.close();
				//while (reader.readLine() != null) lines++;
				//reader.close();
				numbers.put(f,lines);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return numbers;
	}

}
