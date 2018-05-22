package ru.ludovinc.crm_proj_sales;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ClientModel implements TableModel {

	private String [] properties = {
			"Название",
			"Адрес",
			"Регион",
			"E-mail",
			"Телефон",
			"Осн. направление"};
	
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub

	}

	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Main.client.getTableVal()[0].length;
	}

	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		//return properties.length;
		return Main.client.getTableVal().length;
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		Object ret_object = null;
		ret_object = Main.client.getTableVal()[row][col];
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
