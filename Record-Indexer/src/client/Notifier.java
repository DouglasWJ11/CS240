package client;

import java.util.concurrent.CopyOnWriteArrayList;

public class Notifier {
	private static Notifier n;
	
	private CopyOnWriteArrayList<BatchController> batchControllers;
	private CopyOnWriteArrayList<ImageController> imageControllers;
	private CopyOnWriteArrayList<TableController> tableControllers;
	private CopyOnWriteArrayList<SaveController> saveControllers;
	
	private Notifier(){
		batchControllers=new CopyOnWriteArrayList<BatchController>();
		imageControllers=new CopyOnWriteArrayList<ImageController>();
		tableControllers=new CopyOnWriteArrayList<TableController>();
		saveControllers=new CopyOnWriteArrayList<SaveController>();
	}
	
	public static Notifier getNotifier(){
		if(n==null){
			n=new Notifier();
		}
		return n;
	}
	public void clearControllers(){
		batchControllers=new CopyOnWriteArrayList<BatchController>();
		imageControllers=new CopyOnWriteArrayList<ImageController>();
		tableControllers=new CopyOnWriteArrayList<TableController>();
		saveControllers=new CopyOnWriteArrayList<SaveController>();
	}
	
	public void addBatchController(BatchController c){batchControllers.add(c);}
	public void addImageController(ImageController c){imageControllers.add(c);}
	public void addtableController(TableController c){tableControllers.add(c);}
	public void addSaveController(SaveController c){saveControllers.add(c);}
	
	public void notifyDownloadBatch(){
		for(BatchController c: batchControllers){
			c.onDownloadBatch();
		}
	}
	
	public void notifySubmitBatch(){
		for(BatchController c:batchControllers){
			c.onSubmitBatch();
		}
	}
	
	public void notifyCellChange(){
		for(TableController t:tableControllers){
			t.cellChange();
		}
	}
	
	public void notifyValueChange(int row, int col){
		for(TableController t:tableControllers){
			t.valueChange(row, col);
		}
	}
	
	public void toggle(){
		for(ImageController i:imageControllers){
			i.toggle();
		}
	}

	public void invert() {
		// TODO Auto-generated method stub
		for(ImageController i:imageControllers){
			i.invert();
		}
	}
	
	public void notifyZoom(){
		for(ImageController i:imageControllers){
			i.zoom();
		}
	}
	
	public void notifySave(){
		for(SaveController s:saveControllers){
			s.Save();
			UserState.getUser().saveState();
		}
	}
}
