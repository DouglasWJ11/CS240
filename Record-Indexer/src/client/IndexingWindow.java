package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import client.clientcommunicator.ClientCommunicator;

public class IndexingWindow extends JFrame implements SaveController, BatchController{
	private JSplitPane verticalSplit;
	private JMenuItem downloadbatch;
	private BottomPanel bot;
	
	IndexingWindow(){
		Notifier.getNotifier().addBatchController(this);
		Notifier.getNotifier().addSaveController(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run(){
				Notifier.getNotifier().notifySave();
			}
		}));
		setMinimumSize(new Dimension(300,250));
		setLayout(new BorderLayout());
		setMenuBar(this);
		MiddlePanel mid=new MiddlePanel();
		
		bot=new BottomPanel();
		add(new TopPanel(), BorderLayout.NORTH);
		verticalSplit=new JSplitPane(JSplitPane.VERTICAL_SPLIT,mid,bot);
		verticalSplit.setContinuousLayout(true);
		verticalSplit.setDividerLocation(UserState.getUser().getySplit());
		verticalSplit.setResizeWeight(.70);
		add(verticalSplit);
		setSize(UserState.getUser().getWindowWidth(), UserState.getUser().getWindowHeight());
		this.setLocation(UserState.getUser().getWindowlocation());
		setVisible(true);
	}
	
	private void setMenuBar(IndexingWindow window){
		JMenuBar menus=new JMenuBar();
		JMenu file=new JMenu("file");
		JMenuItem logout=new JMenuItem("Logout");
		logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Notifier.getNotifier().notifySave();
				LoginInterface li=new LoginInterface();
				ClientCommunicator comm=UserState.getUser().getComm();
				UserState.getUser().logout();
				li.display(comm);
				window.dispose();
			}
		});
		downloadbatch=new JMenuItem("Download Batch");
		downloadbatch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				DownloadBatch db=new DownloadBatch();
			}
		});
		JMenuItem exit=new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Notifier.getNotifier().notifySave();
					System.exit(0);
			}
		});
		file.add(logout);
		file.add(downloadbatch);
		file.add(exit);
		menus.add(file);
		setJMenuBar(menus);
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		UserState.getUser().setySplit(verticalSplit.getDividerLocation());
		Rectangle r = this.getBounds();
		UserState.getUser().setWindowHeight(r.height);
		UserState.getUser().setWindowWidth(r.width);
		UserState.getUser().setySplit(verticalSplit.getDividerLocation());
		UserState.getUser().setWindowlocation(this.getLocation());
	}

	@Override
	public void onDownloadBatch() {
		// TODO Auto-generated method stub
		downloadbatch.setEnabled(false);
	}

	@Override
	public void onSubmitBatch() {
		// TODO Auto-generated method stub
		//bot=new BottomPanel();
		//add(bot,BorderLayout.SOUTH);
		//repaint();
		downloadbatch.setEnabled(true);
	}
}
