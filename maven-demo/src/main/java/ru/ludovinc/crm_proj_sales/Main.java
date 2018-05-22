package ru.ludovinc.crm_proj_sales;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main {
	protected static MainWindow window;
	private static BufferedImage image;
	public static Client client = new Client();
	public static File absolut = new File("");
	public static JFrame introFrame;
	public static void main (String [] Args){
		
		try {
			GeetingsImage();
			Sql_communication.createBaseTables();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Сервер не подключен!");
		} 
		introFrame.dispose();
		setWindow(new MainWindow(640,480));	
	}
										
    public static void GeetingsImage() throws IOException, InterruptedException {
        String path = Main.absolut.getAbsolutePath()+"\\Graphics\\Intro\\logo_01.png";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        JLabel label = new JLabel(new ImageIcon(image));
        introFrame = new JFrame();
        introFrame.setUndecorated(true);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.getContentPane().add(label);
        introFrame.pack();
        introFrame.setLocation(200,200);
        introFrame.setLocationRelativeTo(null);
        introFrame.setVisible(true);   
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
			e.printStackTrace();
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
