package client;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import shared.model.Project;

public class TableEntryPanel extends JTable implements TableController,BatchController{
	TableEntryPanel(){
		
		Project p=UserState.getUser().getProject();
		String[] columns=new String[p.getFields().size()+1];
		columns[0]="Record Number";
		for(int i=0;i<p.getFields().size();i++){
			columns[i+1]=p.getFields().get(i).getTitle();
		}
		
		BatchTableModel model=new BatchTableModel(p.getRecordsperimage(), columns);
		model.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e){
				int column=e.getColumn();
				int row=e.getFirstRow();
				Object data=model.getValueAt(row,column);
				model.setValueAt(data, row, column);
			}
		});
		
		setPreferredSize(new Dimension(550,250));
		Notifier.getNotifier().addBatchController(this);
		Notifier.getNotifier().addtableController(this);
		this.setModel(model);
		setVisible(true);
		
//		final ListSelectionModel selCol=getColumnModel().getSelectionModel();
//		selCol.addListSelectionListener(new ListSelectionListener(){
//			@Override
//			public void valueChanged(ListSelectionEvent e){
//				if(selCol.isSelectedIndex(0)){
//					selCol.setSelectionInterval(1,1);
//					UserState.getUser().setySelectedCell(0);
//				}else{
//					UserState.getUser().setySelectedCell(getSelectedColumn()-1);
//				}
//			}
//		});
//		
//		final ListSelectionModel selRow=getSelectionModel();
//		selRow.addListSelectionListener(new ListSelectionListener(){
//			@Override
//			public void valueChanged(ListSelectionEvent e){
//				UserState.getUser().setySelectedCell(getSelectedRow());
//			}
//		});
	}
	
	@Override
	public void onDownloadBatch() {
		// TODO Auto-generated method stub
		/*Project p=UserState.getUser().getProject();
		String[] columns=new String[p.getFields().size()+1];
		DefaultTableModel m=(DefaultTableModel) getModel();
		Vector<Integer> columnData=new Vector<Integer>();
		for(int i=1;i<=p.getRecordsperimage();i++){
			columnData.add(i);
		}
		m.addColumn("Record Number", columnData);
		for(String s:columns){
			m.addColumn(s);
		}*/
	}

	@Override
	public void onSubmitBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cellChange() {
		// TODO Auto-generated method stub
		if(this!=null){
			this.changeSelection(UserState.getUser().getxSelectedCell(), UserState.getUser().getySelectedCell()+1, false, false);
		}
	}

	@Override
	public void valueChange(int row, int col) {
		// TODO Auto-generated method stub
		
	}
}
