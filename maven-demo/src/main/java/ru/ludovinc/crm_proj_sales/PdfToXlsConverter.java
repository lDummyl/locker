package ru.ludovinc.crm_proj_sales;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.util.IOUtils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class PdfToXlsConverter {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pdfLocation = "C:\\Users\\805268\\Desktop\\Intelligence\\Pdf conversion\\VT17-010212-01_AHU.pdf";
		PdfReader reader = new PdfReader(pdfLocation);
        PdfReaderContentParser parser = new PdfReaderContentParser(
                reader);
        // PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        TextExtractionStrategy strategy;
        String line = null;
        ArrayList<String> liners = new ArrayList<String>();
    	
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            
        	strategy = parser.processContent(i,
                    new SimpleTextExtractionStrategy());
        	line = strategy.getResultantText();    
        	liners.add(line);
        }
        reader.close();

        
        
        // using apache poi text to excel converter

        @SuppressWarnings("resource")
		org.apache.poi.ss.usermodel.Workbook wb = new HSSFWorkbook();
        CreationHelper helper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");
        
        //System.out.println("link------->" + line);
       
        //List<String> lines = IOUtils.readLines(new StringReader(line));
        int q=0;
        for (int i = 0; i < liners.size(); i++) {       
        	String str[] = liners.get(i).split("\n");
        	for (int j = 0; j < str.length; j++) {
        		Row row = sheet.createRow((short) q);
        		row.createCell(0).setCellValue(
                		helper.createRichTextString(str[j]));
        		q++;
            }
        }
        FileOutputStream fileOut = new FileOutputStream(
                "C:\\Users\\805268\\Desktop\\Intelligence\\Pdf conversion\\VT17-010212-01_AHU_Converted.xls");
        wb.write(fileOut);
        fileOut.close();
	}

}





/*
for (int i = 0; i < liners.size(); i++) {
    
	String str[] = liners.get(i).split("\n");
    
	
	
	Row row = sheet.createRow((short) i);
    
    for (int j = 0; j < str.length; j++) {
        row.createCell(j).setCellValue(
                helper.createRichTextString(str[j]));

*/