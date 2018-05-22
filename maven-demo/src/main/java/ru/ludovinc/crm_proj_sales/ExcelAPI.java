package ru.ludovinc.crm_proj_sales;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelAPI {

	public static OfferGeneral getOfferData() throws IOException {
		OfferGeneral offerGeneral = new OfferGeneral();
		//String[] pack = new String[3];
		String projPath = Main.absolut.getAbsolutePath();
		
		FileInputStream xlsOffer = new FileInputStream(projPath+"\\dep_gateway\\offer.xls");
		Workbook wb = new HSSFWorkbook(xlsOffer);
		//Sheet Offer = wb.getSheetAt(0);
		String requestName = wb.getSheetAt(0).getRow(10).getCell(1).getStringCellValue();
		String customerName = wb.getSheetAt(0).getRow(11).getCell(1).getStringCellValue();
		if (customerName == null || customerName.equals("")){
			customerName = JOptionPane.showInputDialog("Поле заказчик в КП не заполнено, ввдеите наименование в поле ниже");
		}
		System.out.println(requestName);
		offerGeneral.requestName = requestName;
		offerGeneral.Summ = getSumm(wb);
		offerGeneral.customerName = customerName; 		
		xlsOffer.close();
		return offerGeneral;
		
	}
	@SuppressWarnings("unused")
	private static String readResult (Workbook wb,int row, int col){
		String cell="";
		cell = wb.getSheetAt(0).getRow(row).getCell(col).getStringCellValue();
		return cell;
	}
	private static double getSumm(Workbook wb){
		int i = 0;
		for (i = 0;i<10000;i++){
			String anchor = wb.getSheetAt(0).getRow(i).getCell(5).toString();
			if (anchor.equals("ИТОГО:")){
				return wb.getSheetAt(0).getRow(i).getCell(6).getNumericCellValue();
			}
		}
		return 0;
	}
	public static void insertNumToOffer(int number, String name) throws IOException{
		//OfferGeneral offerGeneral = new OfferGeneral();
		String projPath = "C:\\Users\\805268\\workspace\\maven-demo";
		FileInputStream xlsOffer = new FileInputStream(projPath+"\\dep_gateway\\"+name);
		Workbook  wb = new HSSFWorkbook(xlsOffer);
		Sheet offer = wb.getSheetAt(0);
		Row row = offer.createRow(12);
		Cell cell = row.createCell(3);
		cell.setCellValue(number);
		try {
			FileOutputStream fos = new FileOutputStream(projPath+"\\dep_gateway\\"+name); // по умолчанию пришет в проект в рабочей зоне, можно передать как и нужный адрес.
			wb.write(fos);
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

