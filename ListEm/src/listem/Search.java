package listem;
//testing
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/*if recursive=true loop asking if it is a directory then recall the function on the directory else search the names and make a list of matching names */
public class Search {
	public File[] directory(File directory,boolean recursive,String pattern){
		File[] names=directory.listFiles();
		ArrayList<File> found=new ArrayList<File>();
		if(names!=null){
			for (File f:names){
				if(recursive==true){
					if(f.isDirectory()){
						File[] second=directory(f,true,pattern);
						for(File g:second){
							found.add(g);
						}
					}
				}
				Pattern p=Pattern.compile(pattern);
				Matcher m=p.matcher(f.getName());
				if(m.matches()){
					found.add(f);
				}
			}
		}
		File[] find;
		find= new File[found.size()];
		return found.toArray(find);
	}
} 