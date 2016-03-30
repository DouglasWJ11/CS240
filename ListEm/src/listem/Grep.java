package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends Search implements IGrep {
	@Override
	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) {
		// TODO Auto-generated method stub
		File[] fileList=directory(directory,recursive,fileSelectionPattern);
		//Map<File, List<String>> grepped=new HashMap<File, List<String>>();
		return PatternMatcher(fileList,Pattern.compile(substringSelectionPattern));
	}
	public Map<File,List<String>> PatternMatcher(File[] names,Pattern substring){
		Map<File,List<String>> grep=new HashMap<File, List<String>>();
		for(File f:names){
			try {
				Scanner s=new Scanner(f);
				List<String> found=new ArrayList<String>();
				boolean toAdd=false;
				while(s.hasNextLine()){
					String next=s.nextLine();
					Matcher m =substring.matcher(next);
					if(m.find()){
						found.add(next);
						toAdd=true;
					}
				}
				s.close();
				if(toAdd==true){
					grep.put(f, found);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		}
		return grep;
	}
}
//testing
