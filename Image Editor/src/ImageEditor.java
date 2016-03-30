import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
//printf("%s\n",string) 
//static methods can only call other static methods
public class ImageEditor {
	Image i;
	ImageEditor(){}
	private void uploadImage(String file) {
		try {
			FileReader fr=new FileReader(file);
			Scanner s = new Scanner(fr); 
			s.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
			s.next();
			int width=Integer.parseInt(s.next());
			int height=Integer.parseInt(s.next());
			this.i=new Image(width,height);
			s.next();
			for(int y=0;y<height;y++){
				for (int x=0;x<width;x++){
					int r=Integer.parseInt(s.next());
					int g=Integer.parseInt(s.next());
					int b=Integer.parseInt(s.next());
					this.i.addPixel(x,y,r,g,b);
				}
			}
		s.close();
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	private void save(String file){
		try {
			
			FileWriter fw =new FileWriter(file);
			BufferedWriter bw=new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			StringBuilder sb = new StringBuilder();
			sb.append("P3\n");
			sb.append(this.i.getW());
			sb.append("\n");
			sb.append(this.i.getH());
			sb.append("\n");
			sb.append("255");
			pw.println(sb.toString());
			pw.print(this.i.output());
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageEditor ie = new ImageEditor();
		ie.uploadImage(args[0]);
		if(args[2].equals("invert")){
			ie.i.invert();
		}
		else if(args[2].equals("greyscale")){
			ie.i.greyscale();
		}
		else if (args[2].equals("emboss")){
			ie.i.emboss();
		}
		else if(args[2].equals("motionblur")){
			if(Integer.parseInt(args[3])>1){
				ie.i.motionblur(Integer.parseInt(args[3]));
			}
		}
		ie.save(args[1]);
	}
}
