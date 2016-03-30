
public class Pixel {
	
	private int red, green, blue;
		
	public Pixel(int r,int g,int b) {
		this.red=r;
		this.green=g;
		this.blue=b;
	}
	public void invert() {
		this.red=255-red;
		this.green=255-green;
		this.blue=255-blue;
	}
	public void greyscale(){
		int average=(red+blue+green)/3;
		this.red=average;
		this.green=average;
		this.blue=average;
	}
	public int getR(){
		return red;
	}
	public int getG(){
		return green;
	}
	public int getB(){
		return blue;
	}
	public void setRGB(int r,int g,int b){
		this.red=r;
		this.green=g;
		this.blue=b;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(red);
		sb.append(" ");
		sb.append(green);
		sb.append(" ");
		sb.append(blue);
		sb.append("\n");
		return sb.toString();
	}
}
