package SandCourt.main33;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ClientModel implements TableModel {

	
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		Object ret_object = null;
		if (col == 0){
			switch(row){
			case 0: 
			ret_object = "Company name";
			break;
			case 1:
			ret_object = "Address";	
			break;
			}
		}
		if (col == 1){
			switch(row){
			case 0: 
			ret_object = Main.client.getNaming();
			break;
			case 1:
			ret_object = Main.client.getAddress();	
			break;
			}
		}
		return ret_object;
	}

	@Override
	public boolean isCellEditable(int row, int count) {
		return true;
	}
	@Override
	public void removeTableModelListener(TableModelListener arg0) {

	}
	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {

		
	}

}
