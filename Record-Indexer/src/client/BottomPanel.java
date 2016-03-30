package client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class BottomPanel extends JPanel implements BatchController, SaveController {
	private TableEntryPanel table;
	private JScrollPane Scrolltable;
	private FieldHelp fieldHelpPanel;
	private JScrollPane Scrollform;
	private JTabbedPane left,right;
	private JSplitPane botSplit;
	private GridBagConstraints c;
	BottomPanel(){
		Notifier.getNotifier().addBatchController(this);
		Notifier.getNotifier().addSaveController(this);
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(1100,250));
		setMaximumSize(new Dimension(2000,350));
		left=new JTabbedPane();
		Scrolltable=new JScrollPane();
		Scrolltable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Scrolltable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//table=new TableEntryPanel();
		//Scrolltable.add(new TableEntryPanel());
		Scrolltable.setSize(new Dimension(550,250));
		Scrollform=new JScrollPane();
		Scrollform.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Scrollform.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		left.add("Table Entry",Scrolltable);
		left.add("Form Entry", Scrollform);
		left.setSize(new Dimension(550, 250));
		setVisible(true);
		c=new GridBagConstraints();
		c.gridx=0;
		c.weightx=.5;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		right=new JTabbedPane();
		fieldHelpPanel=new FieldHelp();
		right.add("Field Help", fieldHelpPanel);
		right.setPreferredSize(new Dimension(550, 250));
		botSplit=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,left,right);
		botSplit.setSize(new Dimension(1100,221));
//		botSplit.setDividerLocation((double).5);
		botSplit.setDividerLocation(UserState.getUser().getxSplit());
		botSplit.setContinuousLayout(true);
		botSplit.setPreferredSize(new Dimension(1100, 250));
		botSplit.setResizeWeight(.5);
		add(botSplit,c);
	}

	@Override
	public void onDownloadBatch() {
		// TODO Auto-generated method stub
		TableEntryPanel table=new TableEntryPanel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent event){
				int row =table.rowAtPoint(event.getPoint());
				int col =table.columnAtPoint(event.getPoint());
				if(col>0){
					UserState.getUser().setSelectedCell(row,col-1);
				}
				Notifier.getNotifier().notifyCellChange();
			}
		});
		final ListSelectionModel selCol=table.getColumnModel().getSelectionModel();
		selCol.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				if(selCol.isSelectedIndex(0)){
					selCol.setSelectionInterval(1,1);
					UserState.getUser().setySelectedCell(0);
				}else{
					UserState.getUser().setySelectedCell(table.getSelectedColumn()-1);
				}
			}
		});
		
		final ListSelectionModel selRow=table.getSelectionModel();
		selRow.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				UserState.getUser().setxSelectedCell(table.getSelectedRow());
			}
		});
		Scrolltable=new JScrollPane(table);
		//Scrolltable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//Scrolltable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//Scrolltable.setMinimumSize(new Dimension(550,250));
		left.remove(0);
		left.remove(0);
		left.add("Table Entry", Scrolltable);
		Scrollform=new JScrollPane(new FormEntryPanel());
		left.add("Form Entry", Scrollform);
	}

	@Override
	public void onSubmitBatch() {
		// TODO Auto-generated method stub
		left=new JTabbedPane();
		left.add("Table Entry", new JScrollPane());
		left.add("Form Entry",new JScrollPane());
		right=new JTabbedPane();
		right.add("Field Help", new JScrollPane());
		botSplit.setLeftComponent(left);
		botSplit.setRightComponent(right);
		botSplit.setDividerLocation(545);
	//	botSplit=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,left,right);
		repaint();
	}

	@Override
	public void Save() {
		// TODO Auto-generated method stub
		UserState.getUser().setxSplit(botSplit.getDividerLocation());
	}
}
