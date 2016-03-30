package client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import client.clientcommunicator.ClientCommunicator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ServerHost=args[0];
		int Server_Port=Integer.parseInt(args[1]);
		ClientCommunicator comm=new  ClientCommunicator(ServerHost,Server_Port);
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				LoginInterface login=new LoginInterface();
				login.display(comm);
			}
		});
	}
}
