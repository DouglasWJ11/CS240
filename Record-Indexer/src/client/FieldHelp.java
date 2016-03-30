package client;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FieldHelp extends JPanel implements TableController{
	JEditorPane pane;
	JScrollPane scrollPane;
	FieldHelp(){
		pane=new JEditorPane();
		pane.setEditable(false);
		Notifier.getNotifier().addtableController(this);
		pane.setContentType("text/html");
		scrollPane=new JScrollPane(pane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(new BorderLayout());
		add(scrollPane,BorderLayout.CENTER);
	}
	
	@Override
	public void cellChange() {
		// TODO Auto-generated method stub
		String path=UserState.getUser().getHelpHtml();
		try{
			pane.setPage(new URL(path));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void valueChange(int row, int col) {
		// TODO Auto-generated method stub
		
	}
}
