package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class TopPanel extends JToolBar implements BatchController{
	private JButton zoomIn;
	private JButton zoomOut;
	private JButton invert;
	private JButton toggle;
	private JButton save;
	private JButton submit;
	TopPanel(){
		Notifier.getNotifier().addBatchController(this);
		UserState user=UserState.getUser();
		zoomIn=new JButton("Zoom In");
		zoomIn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				user.setScale(user.getScale()+.1);
				Notifier.getNotifier().notifyZoom();
			}
		});
		zoomOut=new JButton("Zoom Out");
		zoomOut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				user.setScale(user.getScale()-.1);
				Notifier.getNotifier().notifyZoom();
			}
		});
		invert=new JButton("Invert Image");
		invert.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(UserState.getUser().isInverted()){
					UserState.getUser().setInverted(false);
				}
				else{
					UserState.getUser().setInverted(true);
				}
				Notifier.getNotifier().invert();
			}
		});
		toggle=new JButton("Toggle Highlights");
		toggle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(!UserState.getUser().isHighlights()){
				UserState.getUser().setHighlights(true);
				}
				else{
					UserState.getUser().setHighlights(false);
				}
				Notifier.getNotifier().toggle();
			}
		});
		save=new JButton("Save");
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				user.saveState();
			}
		});
		submit=new JButton("Submit");
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ServerController.submitBatch(UserState.getUser().getTableEntry());
				UserState.getUser().setProjectID(0);
				Notifier.getNotifier().notifySubmitBatch();
			}
		});
		add(zoomIn);
		add(zoomOut);
		add(invert);
		add(toggle);
		add(save);
		add(submit);
		setFloatable(false);
		toggleButtons(false);
	}
	
	private void toggleButtons(boolean setButtons){
		zoomIn.setEnabled(setButtons);
		zoomOut.setEnabled(setButtons);
		invert.setEnabled(setButtons);
		toggle.setEnabled(setButtons);
		save.setEnabled(setButtons);
		submit.setEnabled(setButtons);
	}
	@Override
	public void onDownloadBatch() {
		// TODO Auto-generated method stub
		toggleButtons(true);
	}

	@Override
	public void onSubmitBatch() {
		// TODO Auto-generated method stub
		toggleButtons(false);
	}
}
