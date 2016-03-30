package client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import shared.communication.DownloadBatch_Result;
import shared.model.Project;
import shared.model.images;

public class DownloadBatch extends JDialog {
	DownloadBatch(){
		setTitle("Download Batch");
		setModal(true);
		this.setResizable(false);
		Object[] projects=ServerController.getProjects();
		JLabel projec=new JLabel("Projects");
		projec.setMinimumSize(new Dimension(100,10));
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=.3;
		c.weighty=.5;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=2;
		//c.fill=GridBagConstraints.HORIZONTAL;
		getContentPane().add(projec,c);
		c.weightx=.6;
		//String s=(String)JOptionPane.showInputDialog(download, "Project:", "Download Batch", JOptionPane.PLAIN_MESSAGE,new ImageIcon(),projects,projects[0]);
		JComboBox<Object> options=new JComboBox<Object>(projects);
		JButton sample=new JButton("View Sample");
		sample.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int project=options.getSelectedIndex()+1;
				String path=ServerController.getSampleImage(project);
				URL url;
				BufferedImage image;
				setVisible(false);
				try {
					url = new URL(path);
					image = ImageIO.read(url);
					Image i=image.getScaledInstance(526, 340, Image.SCALE_SMOOTH);
					JDialog f=new JDialog();
					f.setModal(true);
				    f.setLayout(new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS));
				    f.setResizable(false);
				    f.setTitle("Sample Image From "+(String)options.getSelectedItem());
				    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    f.pack();
				    f.setSize(700, 430);
				    JLabel t=new JLabel(new ImageIcon(i));
				    t.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				    f.add(t);
				    f.add(Box.createVerticalGlue());
				    JButton close=new JButton("Close");
				    close.setSize(100, 40);
				    close.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				    close.addActionListener(new ActionListener(){
				    	@Override
				    	public void actionPerformed(ActionEvent arg0){
				    		f.setVisible(false);
				    		f.dispose();
				    		setVisible(true);
				    	}
				    });
				    f.add(close);
				    f.setVisible(true);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally{
				}
			}
		});
		c.gridx=2;
		getContentPane().add(options,c);
		c.gridx=4;
		c.gridwidth=1;
		c.weightx=.3;
		getContentPane().add(sample,c);
		JButton ok=new JButton("Download");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				savefromDB(ServerController.downloadBatch(options.getSelectedIndex()+1));
				Notifier.getNotifier().notifyDownloadBatch();
				//start the engine
				dispose();
			}
		});
		JButton cancel=new JButton("Cancel");
		cancel.setSize(150, 15);
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		c.gridy=2;
		c.gridx=0;
		c.ipady=10;
		c.gridwidth=2;
		c.fill=GridBagConstraints.HORIZONTAL;
		getContentPane().add(cancel,c);
		c.gridx=2;
		getContentPane().add(ok,c);
		setPreferredSize(new Dimension(500,500));
		setMinimumSize(new Dimension(500,150));
		setVisible(true);
	}
	
	public void savefromDB(DownloadBatch_Result db){
		if(db==null){
			return;
		}
		Project p=db.getProject();
		images b=db.getBatch();
		UserState user=UserState.getUser();
		user.setProjectID(p.getID());
		user.setBatchID(b.getId());
		user.setTableEntry(new Object[p.getRecordsperimage()][p.getFields().size()+1]);
		user.setProject(p);
		user.setUrl(b.getFile());
	}
}
