package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.clientcommunicator.ClientCommunicator;
import shared.communication.ValidateUser_Result;

public class LoginInterface {
	private JFrame frame;
	public LoginInterface(){
	}
	public void display(ClientCommunicator comm){
		UserState.getUser().setComm(comm);
		frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JTextField username=new JTextField(40);
		JPanel left=new JPanel();
		JPanel right=new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
		right.setLayout(new BoxLayout(right,BoxLayout.PAGE_AXIS));
		frame.add(left,BorderLayout.LINE_START);
		frame.add(right,BorderLayout.LINE_END);
		right.add(username);
		JLabel label=new JLabel("Username");
		JLabel pword=new JLabel("Password");
		pword.setVerticalAlignment(SwingConstants.BOTTOM);
		left.add(label);
		left.add(pword);
		JPasswordField passwordfield=new JPasswordField(40);
		right.add(passwordfield);
		JButton login=new JButton("Login");
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ValidateUser_Result result=ServerController.validateUser(username.getText(),passwordfield.getPassword());
				if(!result.isValidated()){
					JFrame failed=new JFrame("Login Failed");
					JLabel message=new JLabel("Invalid username and/or password");
					JButton tryAgain=new JButton("Try Again");
					tryAgain.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							failed.setVisible(false);
							failed.dispose();
						}
						
					});
					failed.add(message,BorderLayout.CENTER);
					failed.add(tryAgain, BorderLayout.SOUTH);
					failed.setSize(300, 100);
					failed.setVisible(true);
					passwordfield.setText("");
				}
				else if(result.isValidated()){
					ServerController.setUsername(username.getText());
					ServerController.setPassword(passwordfield.getPassword());
					comm.setUsername(username.getText());
					JOptionPane.showMessageDialog(frame.getContentPane(), "Welcome "+result.getFirstName()+" "+result.getLastName()
					+" You have indexed "+result.getRecordsIndexed()+" records");
					frame.setVisible(false);
					UserState.getUser().loadState();
					frame.dispose();
				}
				//IndexingWindow IW=new IndexingWindow();
			}
		});
		JButton exit=new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
					System.exit(0);
			}
		});
		JPanel bottom=new JPanel();
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS));
		bottom.add(Box.createHorizontalGlue());
		bottom.add(login);
		bottom.add(Box.createRigidArea(new Dimension(25,0)));
		bottom.add(exit);
		bottom.add(Box.createHorizontalGlue());
		frame.add(bottom,BorderLayout.PAGE_END);
		//frame.setSize(500, 500);
		frame.pack();
		frame.setVisible(true);
	}
}
