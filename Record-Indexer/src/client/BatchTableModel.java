package client;

import javax.swing.table.AbstractTableModel;

public class BatchTableModel extends AbstractTableModel {
	private int rows;
	private String[] columnNames;
	
	BatchTableModel(int rows,String[] columnNames){
		this.rows=rows;
		this.columnNames=columnNames;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rows;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col){
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		if(col==0) return row+1;
		return UserState.getUser().getValueAt(row,col);
	}
	
	public void setValueAt(Object value, int row,int col){
		UserState.getUser().setValueAt(row, col, value);
		Notifier.getNotifier().notifyValueChange(row, col);
	}

	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return columnIndex!=0;
	}
}


