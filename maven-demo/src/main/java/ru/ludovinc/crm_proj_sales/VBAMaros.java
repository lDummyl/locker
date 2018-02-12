package ru.ludovinc.crm_proj_sales;
import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

 
public class VBAMaros {

     public static void callExcelMacro(File file, String macroName[]) {
        ComThread.InitSTA(true);
        final ActiveXComponent excel = new ActiveXComponent("Excel.Application");
        try{
            excel.setProperty("EnableEvents", new Variant(false));
            Dispatch workbooks = excel.getProperty("Workbooks")
                    .toDispatch();
            Dispatch workBook = null;
            try {
            	workBook = Dispatch.call(workbooks, "Open",
	                    file.getAbsolutePath()).toDispatch();
            }catch(Exception e){System.out.println(e);}
           
            // Calls the macro
            
            for (String element: macroName) {
	            Variant V1 = new Variant( file.getName() + element);
	            @SuppressWarnings("unused")
				Variant result = Dispatch.call(excel, "Run", V1);
	            }
            
            // Saves and closes
            Dispatch.call(workBook, "Save");

            com.jacob.com.Variant f = new com.jacob.com.Variant(true);
            Dispatch.call(workBook, "Close", f);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            excel.invoke("Quit", new Variant[0]);
            ComThread.Release();
           System.out.println("done");
        }
    }
}