package ru.ludovinc.crm_proj_sales;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Main {
	private static MainWindow window;
	private static BufferedImage image;
	public static Client client = new Client();
	public static File absolut = new File("");
	
	public static void main (String [] Args){
		
//		
//		try {
//			Sql_communication.createBaseTables();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//	
		JOptionPane.showMessageDialog(null, "Hello!");
		setWindow(new MainWindow(640,480));	
	
	
	}
	
	public static void setImage(URL url){
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void setImage(File file){
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public static void saveImage (File file, String format){
		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Наебываемся");
			//e.printStackTrace();
		}
	}
	public static MainWindow getWindow() {
		return window;
	}
	public static void setWindow(MainWindow window) {
		Main.window = window;
	}
	public static BufferedImage getImage() {
		return image;
	}
	public static void setImage(BufferedImage image) {
		Main.image = image;
	}
}
