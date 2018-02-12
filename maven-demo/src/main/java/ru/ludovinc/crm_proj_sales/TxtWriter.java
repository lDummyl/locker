package ru.ludovinc.crm_proj_sales;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TxtWriter {
		 
    public static void writeOfferComment(String text) {
        
        try(FileWriter writer = new FileWriter(Main.absolut.getAbsolutePath()+"//dep_gateway//Комментарии_к_предложению.txt", false))
        {       	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YY");
    		String sCertDate = dateFormat.format(new Date());
        	// запись всей строки
            writer.write(text +" "+ sCertDate);
//            // запись по символам
//            writer.append('\n');
//            writer.append('E');
             
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    }	
}
