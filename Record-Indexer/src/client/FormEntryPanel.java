package client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.model.Project;
import shared.model.fields;

public class FormEntryPanel extends JPanel implements TableController{
	private JList<String> recordNumbers;
	private String[] numbers;
	private GridBagLayout myLayout;
	
	FormEntryPanel(){
		Notifier.getNotifier().addtableController(this);
		myLayout=new GridBagLayout();
		setLayout(myLayout);
		setMinimumSize(new Dimension(500,250));
		Project p=UserState.getUser().getProject();
		GridBagConstraints c=new GridBagConstraints();
		numbers=new String[p.getRecordsperimage()];
		for(int i=0;i<numbers.length;i++){
			numbers[i]=Integer.toString(i+1);
		}
		recordNumbers=new JList<String>(numbers);
		recordNumbers.setSelectedIndex(UserState.getUser().getySelectedCell());
		recordNumbers.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0){
				if(arg0.getValueIsAdjusting()) return;
				if(recordNumbers.getSelectedIndex()>=0){
					UserState.getUser().setxSelectedCell(recordNumbers.getSelectedIndex());
					Notifier.getNotifier().notifyCellChange();
				}
			}
		});
		JScrollPane rNum=new JScrollPane(recordNumbers);
		rNum.setVisible(true);
		rNum.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints d=new GridBagConstraints();
		c.weighty=.5;
		c.gridy=0;
		d.gridheight=1;
		d.weightx=.3;
		for(fields f:p.getFields()){
			c.weightx=.2;
			c.gridx=1;
			JLabel field=new JLabel(f.getTitle());
			add(field,c);
			c.gridy++;
			d.gridheight++;
		}
		c.gridy=0;
		for(fields f:p.getFields()){
			c.gridx=2;
			c.weightx=.5;
			JTextField textField=new JTextField(33);
			textField.setFocusable(true);
			textField.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					UserState.getUser().setySelectedCell(myLayout.getConstraints(e.getComponent()).gridy);
					Notifier.getNotifier().notifyCellChange();			
				}

				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			textField.getDocument().addDocumentListener(new DocumentListener() {
				  public void changedUpdate(DocumentEvent e) {
				    setText(e);
				  }
				  public void removeUpdate(DocumentEvent e) {
				    setText(e);
				  }
				  public void insertUpdate(DocumentEvent e) {
				    setText(e);
				  }

				  public void setText(DocumentEvent e) {
					int row=recordNumbers.getSelectedIndex();
					int column=myLayout.getConstraints((Component)textField).gridy+1;
					UserState.getUser().setValueAt(row, column, textField.getText());
					Notifier.getNotifier().notifyValueChange(row, column);
				  }
				});
			add(textField,c);
			c.gridy++;
		}
		d.gridx=0;
		d.gridy=0;
		add(rNum,d);
	}
	@Override
	public void cellChange() {
		// TODO Auto-generated method stub
		recordNumbers.setSelectedIndex(UserState.getUser().getxSelectedCell());
		for(Component c:this.getComponents()){
			if(myLayout.getConstraints(c).gridy==UserState.getUser().getySelectedCell()&&myLayout.getConstraints(c).gridx==2){
				c.requestFocusInWindow();
				break;
			}
		}
		Object[][] table=UserState.getUser().getTableEntry();
		for(int i=0;i<table[UserState.getUser().getxSelectedCell()].length;i++){
			for(Component c:this.getComponents()){
				if(myLayout.getConstraints(c).gridy==i-1 && myLayout.getConstraints(c).gridx==2){
					((JTextField) c).setText((String) table[UserState.getUser().getxSelectedCell()][i]);
				}
			}
		}
	}
	@Override
	public void valueChange(int row, int col) {
		// TODO Auto-generated method stub
	/*	if (recordNumbers.getSelectedIndex()==row){
			for(Component c:this.getComponents()){
				if(myLayout.getConstraints(c).gridy==col-1&&myLayout.getConstraints(c).gridx==2){
					((JTextField) c).setText((String) UserState.getUser().getValueAt(row, col));
				}
			}
		}*/
	}
}
