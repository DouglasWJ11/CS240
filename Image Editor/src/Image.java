import java.util.ArrayList;
import java.util.Collections;

public class Image {
	private int width, height;
	private Pixel[][] pixelArray;
	public Image(int w,int h){
		this.width=w;
		this.height=h;
		pixelArray=new Pixel[w][h];
	}
	public void addPixel(int x,int y,int r,int g, int b){
		pixelArray[x][y] =new Pixel(r,g,b);
	}
	public void invert(){
		for (int x=0;x<width;x++){
			for (int y=0;y<height;y++){
				pixelArray[x][y].invert();
			}
		}
	}
	public void greyscale(){
		for (int x=0;x<width;x++){
			for (int y=0;y<height;y++){
				pixelArray[x][y].greyscale();
			}
		}
	}
	public void emboss(){
		for(int y=height-1;y>=0;y--){
			for (int x=width-1;x>=0;x--){
				int v=0;
				if(x-1<0||y-1<0){
					v=128;
					pixelArray[x][y].setRGB(v,v,v);
				}
				else{
					int redDiff=pixelArray[x][y].getR()-pixelArray[x-1][y-1].getR();
					int greenDiff=pixelArray[x][y].getG()-pixelArray[x-1][y-1].getG();
					int blueDiff=pixelArray[x][y].getB()-pixelArray[x-1][y-1].getB();
					ArrayList<Integer> myArray=new ArrayList<Integer>();
					myArray.add(redDiff);
					myArray.add(greenDiff);
					myArray.add(blueDiff);
					int max=Collections.max(myArray);
					int min=Collections.min(myArray);
					if(Math.abs(min)>Math.abs(max)){
						v=min+128;
					}
					else if(Math.abs(min)<Math.abs(max)){
						v=max+128;
					}
					else if(Math.abs(min)==Math.abs(max)){
						if(max==redDiff||min==redDiff){
							v=redDiff+128;
						}
						else if(max==greenDiff||min==greenDiff){
							v=greenDiff+128;
						}
						else{
							v=blueDiff+128;
						}
					}
					if(v>255)v=255;
					else if(v<0)v=0;
					pixelArray[x][y].setRGB(v, v, v);
					}
				}
			}
		}
	public void motionblur(int n) {
		//if(n<width){
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int redv=0;
				int greenv=0;
				int bluev=0;
				int num=0;

					for(int i=0;i<n;i++) {
						if(x+num<width){
						redv+=pixelArray[x+i][y].getR();
						greenv+=pixelArray[x+i][y].getG();
						bluev+=pixelArray[x+i][y].getB();
						num++;
					}
				}
				pixelArray[x][y].setRGB(redv/num,greenv/num, bluev/num);
			}
		}
		//}
	}
	public String output(){
		StringBuilder sb=new StringBuilder();
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				sb.append(pixelArray[x][y].toString());
			}
		}
		return sb.toString();
	}
	public int getH(){
		return height;
	}
	public int getW(){
		return width;
	}
}
