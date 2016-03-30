package shared.model;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class indexedData {
	private int imageID;
	private ArrayList<Value> values=new ArrayList<Value>();
	public indexedData(Element recordElement,int row){
		Element valuesElement=(Element)recordElement.getElementsByTagName("values").item(0);
		NodeList valueElements=valuesElement.getElementsByTagName("value");
		for(int i=0;i<valueElements.getLength();i++){
			values.add(new Value((Element)valueElements.item(i),row,i+1));
		}
	}
	
	public indexedData(ArrayList<Value> values){
		this.values=values;
	}
	public ArrayList<Value> getValues(){
		return values;
	}
}
