package ru.ludovinc.crm_proj_sales;

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
		//ГРУБЫЙ ПОИСК 	 
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
	
	public static int post() throws Exception{
		//вводим в базу клиента и возвращаем его номер в базе
		
		try{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM `clients` WHERE `naming` LIKE '"+Main.client.getNaming()+"'");
			ResultSet result = statement.executeQuery();
			
			if (SearchClient(Main.client.getNaming())){ // if company is already written in base we output the base data to panel
				JOptionPane.showMessageDialog(null, "Company is already in the base.");
				Main.client.setNaming(result.getString("naming"));
				Main.client.setAddress(result.getString("address"));
				return 0;
			}
				PreparedStatement posted = conn.prepareStatement("INSERT INTO clients(naming, address, region, phone, email) VALUES ('" + Main.client.getNaming() + "', '"+ Main.client.getAddress() +"', '"+ Main.client.getRegion() +"', '"+ Main.client.getPhone() +"', '"+ Main.client.geteMail()+"')");
				posted.executeUpdate();
				posted = conn.prepareStatement("SELECT * FROM `clients` WHERE `naming` LIKE '"+Main.client.getNaming()+"'");
				result = posted.executeQuery();
				result.next();
				return result.getInt("id");
		}catch (Exception e){System.out.println(e);};
		return 0;
	}
	
	
	public static void createBaseTables () throws Exception{
		// создаем таблицы баз данных сначала клиенты потом работники, работники с клиентами
		// связываются через id.
		try{
		
			Connection conn = getConnection();
			PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS clients(id int NOT NULL AUTO_INCREMENT, naming varchar(255), address varchar(255), PRIMARY KEY(id))");
			create.executeUpdate();
			create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS employers(id int NOT NULL AUTO_INCREMENT, name varchar(255), position varchar(255), phone varchar(255), email varchar(255), companyid int(11), PRIMARY KEY(id))");
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