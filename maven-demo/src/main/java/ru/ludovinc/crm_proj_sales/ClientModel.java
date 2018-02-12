package ru.ludovinc.crm_proj_sales;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ClientModel implements TableModel {

	
	
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub

	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		Object ret_object = null;
		if (col == 0){
			switch(row){
			case 0: 
				ret_object = "Название";
				break;
			case 1:
				ret_object = "Адрес";	
				break;
			case 2:
				ret_object = "Регион";	
				break;
			case 3:
				ret_object = "E-mail";	
				break;
			case 4:
				ret_object = "Телефон";	
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
			case 2:
				ret_object = Main.client.getRegion();	
				break;
			case 3:
				ret_object = Main.client.geteMail();	
				break;
			case 4:
				ret_object = Main.client.getPhone();
				break;
			}
		}
		return ret_object;
	}

	public boolean isCellEditable(int row, int count) {
		return true;
	}
	
	public void removeTableModelListener(TableModelListener arg0) {

	}
	
	public void setValueAt(Object arg0, int arg1, int arg2) {

		
	}

}
