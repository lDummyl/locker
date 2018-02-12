package ru.ludovinc.crm_proj_sales;
import java.io.File;
import java.io.IOException;

public class SideApp {

	public static void runAcrobat() {
		
		String AcrobatPath = "C:/Program Files (x86)/Adobe/Acrobat 11.0/Acrobat"; // нужно создать в настройках сохранение пути к акробату
		File acrobatDir = new File(AcrobatPath);
		String command = "cmd /c start Acrobat.exe";
		try {
			@SuppressWarnings("unused")
			Process p = Runtime.getRuntime().exec(command, null, acrobatDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void runExcel() {
		
		String AcrobatPath = "C:/Program Files/Microsoft Office/Office15"; // нужно создать в настройках сохранение пути к акробату
		String xlsPath = "C:/Users/805268/workspace/maven-demo/batch_calculator.xlsm";
		
		File acrobatDir = new File(AcrobatPath);
		String command = "cmd /c start EXCEL.exe" + " " + xlsPath + "";
		
		try {
			@SuppressWarnings("unused")
			Process p = Runtime.getRuntime().exec(command, null, acrobatDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
