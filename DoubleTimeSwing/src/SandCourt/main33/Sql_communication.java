package SandCourt.main33;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Sql_communication {

	public static ArrayList<String> get () throws Exception{
		
		try{
			Connection conn = getConnection(); 
			PreparedStatement statement = conn.prepareStatement("SELECT naming, address from clients");
			ResultSet result = statement.executeQuery();
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()){
				System.out.println(result.getString("naming"));
				System.out.println("  ");
				System.out.println(result.getString("address"));
				
				array.add(result.getString("address"));
			}		
			System.out.println("All records have been shown.");
			return array;
		}catch (Exception e) {System.out.println(e);}
		return null;
	}
	
	public static boolean SearchClient(String pattern) throws Exception{
		//for now we search only by name;
		try{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM `clients` WHERE `naming` LIKE '"+pattern+"'");
			ResultSet result = statement.executeQuery();			
			if (result.next()){ 
				GetClientDataFromBase(result);
				return true;
			}
		}catch (Exception e){System.out.println(e);};
		return false;
	}
	
	public static String roughSearch(String pattern) throws Exception{
		//√–”Ѕџ… ѕќ»—  	 
		try{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM `clients` WHERE `naming` LIKE '"+pattern+"%'");
			ResultSet result = statement.executeQuery();			
			if (result.next()){ 
				
				GetClientDataFromBase(result);
				return result.getString("naming");
			}
		}catch (Exception e){System.out.println(e);};
		return pattern;
	}
	
	
	public static void GetClientDataFromBase(ResultSet result) throws Exception{
	// выводим в табло данные по клиенту
			Main.client.setNaming(result.getString("naming"));
			Main.client.setAddress(result.getString("address"));
	
	}
	
	public static void post(String naming, String address) throws Exception{
		try{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM `clients` WHERE `naming` LIKE '"+naming+"'");
			ResultSet result = statement.executeQuery();
			
			if (SearchClient(naming)){ // if company is already written in base we output the base data to panel
				JOptionPane.showMessageDialog(null, "Company is already in the base.");
				Main.client.setNaming(result.getString("naming"));
				Main.client.setAddress(result.getString("address"));
				return;
			}
			PreparedStatement posted = conn.prepareStatement("INSERT INTO clients(naming, address) VALUES ('"+naming+"', '"+address+"')");
			posted.executeUpdate();
		}catch (Exception e){System.out.println(e);};
	}
	public static void createTable () throws Exception{
		try{
		
			Connection conn = getConnection();
			PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS clients(id int NOT NULL AUTO_INCREMENT, naming varchar(255), address varchar(255), PRIMARY KEY(id))");
			create.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
	}
	
	public static Connection getConnection() throws Exception{
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mySql://localhost:3306/company_base";
			String username = "root";
			String password = "r98vb3v]v[pv442vfDEWDludov";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,username,password);
			return conn;
		}catch (Exception e) {System.out.println(e);}
		return null;
	}
}