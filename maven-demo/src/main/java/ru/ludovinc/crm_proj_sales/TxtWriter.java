package ru.ludovinc.crm_proj_sales;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TxtWriter {
		 
    public static void writeOfferComment(String text) {
        if (text == null|| text.equals("")) return;
        try(FileWriter writer = new FileWriter(Main.absolut.getAbsolutePath()+"//dep_gateway//Комментарии_к_предложению.txt", false))
        {       	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YY");
    		String sCertDate = dateFormat.format(new Date());
            writer.write(text +" "+ sCertDate);
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        } 
    }	
}
