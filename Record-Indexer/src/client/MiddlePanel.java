package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import shared.model.Project;
import shared.model.fields;

public class MiddlePanel extends JComponent implements ImageController, BatchController, TableController, SaveController{
	private BufferedImage recordImage;
	private RescaleOp rop;
	private UserState user;
	private int xPos,yPos;
	
	MiddlePanel(){
		Notifier.getNotifier().addImageController(this);
		Notifier.getNotifier().addBatchController(this);
		Notifier.getNotifier().addtableController(this);
		rop=new RescaleOp(-1.0f,255f,null);
		addMouseListener(imageMover);
		addMouseMotionListener(imageDragger);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int xPos=e.getX();
				int yPos=e.getY();
				
				Point2D screen = new Point2D.Double(xPos,yPos);
				
				Point2D world=new Point2D.Double();
				
				AffineTransform trans=new AffineTransform();
				
				trans.translate(getWidth()/2,getHeight()/2);
				trans.scale(user.getScale(), user.getScale());
				trans.translate(-(getWidth()/2), -(getHeight()/2));
				
				try{
					trans.inverseTransform(screen, world);
					int xImage=(int)world.getX();
					int yImage=(int)world.getY();
					xImage-=user.getxImagePos();
					yImage-=user.getyImagePos();
					List<fields> fields=user.getProject().getFields();
					Project project=user.getProject();	
					
					if(xImage>fields.get(0).getXcoord()&& xImage<fields.get(fields.size()-1).getXcoord()+fields.get(fields.size()-1).getWidth()){
						if(yImage>project.getFirstycoord()&&yImage<project.getFirstycoord()+project.getRecordsperimage()*project.getRecordheight()){
							for(int i=0;i<fields.size();i++){
								if(xImage>fields.get(i).getXcoord()&& xImage<fields.get(i).getXcoord()+fields.get(i).getWidth()){
									user.setySelectedCell(i);
									break;
								}
							}
							for(int j=0;j<project.getRecordsperimage();j++){
								if(yImage>project.getFirstycoord()+(j)*project.getRecordheight()&&yImage<project.getFirstycoord()+(j+1)*project.getRecordheight()){
									user.setxSelectedCell(j);
									break;
								}
							}
							Notifier.getNotifier().notifyCellChange();
						}
					}
				} catch(NoninvertibleTransformException e1){
					e1.printStackTrace();
				}
			}
		});
	//	setPreferredSize(new Dimension(1100,429));
	//	setMinimumSize(new Dimension(600,400));
	}
		
	@Override
	protected void paintComponent(Graphics g){

		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		if(recordImage!=null){
			if(user.getxImagePos()==-10000){
				user.setxImagePos((getWidth()-recordImage.getWidth())/2);
			}
			if(user.getyImagePos()==-10000){
				user.setyImagePos((getHeight()-recordImage.getHeight())/2);
			}
			
			g2.translate(getWidth()/2, getHeight()/2);;
			g2.scale(user.getScale(), user.getScale());
			g2.translate(-(getWidth()/2), -(getHeight()/2));
			
			if(user.isInverted()){
				g2.drawImage(recordImage, rop, user.getxImagePos(), user.getyImagePos());
			} else if(!user.isInverted()){
				g2.drawImage(recordImage, user.getxImagePos(), user.getyImagePos(), null);
			}
			
			if(user.isHighlights()){
				int row=user.getxSelectedCell();
				int height=user.getProject().getRecordheight();
				int width=user.getProject().getFields().get(user.getySelectedCell()).getWidth();
				
				g2.setColor(new Color(0,128,0,128));
				
				int x=user.getProject().getFields().get(user.getySelectedCell()).getXcoord()+user.getxImagePos();
				int y=row*height+user.getProject().getFirstycoord()+user.getyImagePos();
				g2.fillRect(x, y, width, height);
			}
		}
		g2.dispose();
	}
	
	
	@Override
	public void onDownloadBatch() {
		// TODO Auto-generated method stub
		user=UserState.getUser();
		try {
			URL url=new URL(user.getUrl());
			recordImage=ImageIO.read(url);
			repaint();
			
			addMouseWheelListener(new MouseWheelListener(){
				public void mouseWheelMoved(MouseWheelEvent e){
					if(e.getWheelRotation()<0){
						user.setScale(user.getScale()+0.05);
						Notifier.getNotifier().notifyZoom();
					}
					else{
						user.setScale(user.getScale()-0.05);
						Notifier.getNotifier().notifyZoom();
					}
				
				}
			});
			
			
		} catch(MalformedURLException e){
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}	
	}
	
	private MouseListener imageMover=new MouseAdapter(){
		@Override
		public void mousePressed(MouseEvent e){
			xPos=e.getX();
			yPos=e.getY();
		}
	};
	
	private MouseMotionListener imageDragger=new MouseMotionAdapter(){
		
		@Override
		public void mouseDragged(MouseEvent e){
			double y=(e.getY()-yPos)/user.getScale();
			double x=(e.getX()-xPos)/user.getScale();
			user.setyImagePos(user.getyImagePos()+(int)y);
			user.setxImagePos(user.getxImagePos()+(int)x);
			repaint();
			yPos=e.getY();
			xPos=e.getX();
		}
	};
	@Override
	public void onSubmitBatch() {
		// TODO Auto-generated method stub
		recordImage=null;
		repaint();
	}
	
	@Override
	public void zoom() {
		// TODO Auto-generated method stub
		repaint();
	}
	@Override
	public void invert() {
		// TODO Auto-generated method stub
		repaint();
	}
	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void cellChange() {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void valueChange(int row, int col) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		
	}
}
