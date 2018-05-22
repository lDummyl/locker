package ru.ludovinc.crm_proj_sales;

public class Client {
	
	private String currentCompany;
	public String naming;
	private String address;
	private String region;
	private String eMail;
	private String phone;
	private String direction;
	
	public final  String [] properties = {
			"Название",
			"Адрес",
			"Регион",
			"E-mail",
			"Телефон",
			"Осн. направление"};
	
	public String[][] getTableVal(){
		String[][] arr = {
				{properties[0], this.naming},
				{properties[1], this.address},
				{properties[2], this.region},
				{properties[3], this.eMail},
				{properties[4], this.phone},
				{properties[5], this.direction}
				};
		return arr; 
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNaming() {
		return naming;
	}
	public void setNaming(String naming) {
		this.naming = naming;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDirection() {
		return direction;
	}
	
}
