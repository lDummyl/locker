package ru.ludovinc.crm_proj_sales;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.InsertDimensionRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class SpreadSheets {

	/** Application name. */
	private static final String APPLICATION_NAME =
			"Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY =
			JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials
	 * at ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES =
			Arrays.asList(SheetsScopes.SPREADSHEETS,SheetsScopes.DRIVE);

	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/2/sheets.googleapis.com-java-quickstart.json");
	
	private static Credential SpreadSheetCredential;

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Creates an authorized Credential object.
	 * @return an authorized Credential object.
	 * @throws Exception 
	 */

	public static Credential authorize() throws Exception {
		// Load client secrets.
		if (SpreadSheetCredential != null) return SpreadSheetCredential; 
		
		InputStream in = new FileInputStream(Main.absolut.getAbsolutePath()+"\\src\\main\\resources\\client_secret.json");  
			
		GoogleClientSecrets clientSecrets =				
				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline")
				.build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println(
				"Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		SpreadSheetCredential = credential;
		return credential;
	}

	/*
	 * Build and return an authorized Sheets API client service.
	 * @return an authorized Sheets API client service
	 * @throws Exception 
	 */
	public static Sheets getSheetsService() throws Exception {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	public static Drive getDriveService() throws Exception {
		Credential credential = authorize();
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	public static void journalInsert(OfferGeneral offerGeneral, String projectName) throws Exception {

		String sCertDate = new SimpleDateFormat("dd.MM.YY").format(new Date());
		String range = "Коммерческий отдел!A2"; // Лист
		//TOdo: если первая строка пустая то он наебывается 
		int serNum = Integer.parseInt(read(range))+1; // Запрашиваем последний порядковый номер и прибавляем +1.
		String folderLink = GDrive.createFolder(Journal.getArchiveId(), String.valueOf(serNum));// создаем папку с проектом и получаем ее id чтобы записать в журнал.
		String folderAddress = "\"https://drive.google.com/drive/folders/" + folderLink + "\"";
		String link = "=HYPERLINK(" + folderAddress + ";"+(serNum)+")"; // Пристегиваем гиперссылку на папку с данными.
		String contractNumber = null;
		if (offerGeneral.customerName.equals("Вентерм"))
			{
			contractNumber = "98/17-2017";
			}else{
			contractNumber = "---";
			}
		List<Object> offerLine = new ArrayList<Object>();
		{
			offerLine.add(link); //serial number including Hyperlink to the folder on GDrive, which contains the general data: offer, request, etc.   
			offerLine.add(offerGeneral.requestName); //id запроса.
			offerLine.add(""); //Счет
			offerLine.add(contractNumber);//Договор
			offerLine.add((int)(offerGeneral.Summ/1000)); //сумма офера, оругляем до тыс.
//			offerLine.add("Вентерм"); //Контрагент
			offerLine.add(offerGeneral.customerName); //Контрагент
			offerLine.add(projectName); //Проект
			offerLine.add("Лудов Д.Ю.(auto)"); //Исполнитель
			offerLine.add(sCertDate);//Дата
			offerLine.add(""); //Примечание
		}
		
		insertRow(1); //inseption of blank row, first	    
		write(offerLine); // write to it

		java.io.File filePath = new java.io.File(Main.absolut.getAbsolutePath()+"\\dep_gateway\\offer.xls");
		filePath.renameTo(new java.io.File(Main.absolut.getAbsolutePath()+"\\dep_gateway\\КП №"+ serNum + " для " + offerGeneral.requestName+ " от " + sCertDate + ".xls"));  
		java.io.File [] fList;        
		java.io.File F = new java.io.File(Main.absolut.getAbsolutePath()+"\\dep_gateway\\");   
		fList = F.listFiles();                 
		
		for(int i=0; i<fList.length; i++)           
		{
			//Нужны только папки в место isFile() пишем isDirectory()
			if(fList[i].isFile())
				GDrive.insertFile(fList[i].getName(), folderLink, fList[i].getAbsoluteFile());
			//сгружаем все что есть в папке на гуглдиск
		}
	}


	public static void journalInsertChange(OfferGeneral offerGeneral, String serNum) throws Exception {
		//передаем комплект в готовый ранее проект.
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YY");
		String sCertDate = dateFormat.format(new Date());

		com.google.api.services.drive.model.File projFolder = GDrive.GetSertainFileInFolder(Journal.getArchiveId(), serNum, null); 
		int j;
		for (j = 1; j<30; j++){
			com.google.api.services.drive.model.File addFolder = GDrive.GetSertainFileInFolder(projFolder.getId(), "Изм "+j, null);
			if (addFolder == null){break;}
		}
		String folderLink = GDrive.createFolder(projFolder.getId(), "Изм "+j);// создаем папку с проектом и получаем ее id.

		java.io.File filePath = new java.io.File(Main.absolut.getAbsolutePath()+"\\dep_gateway\\offer.xls");
		filePath.renameTo(new java.io.File(Main.absolut.getAbsolutePath()+"\\dep_gateway\\КП №"+ serNum + " для " + offerGeneral.requestName+ " от " + sCertDate + ".xls"));  

		java.io.File []fList;        
		java.io.File F = new java.io.File(Main.absolut.getAbsolutePath()+"\\dep_gateway\\");
		fList = F.listFiles();                 
		for(int i=0; i<fList.length; i++)           
		{
			//Нужны только папки в место isFile() пишим isDirectory()
			if(fList[i].isFile())
				GDrive.insertFile(fList[i].getName(), folderLink, fList[i].getAbsoluteFile());
			//сгружаем все что есть в папке на гуглдиск
		}
	}


	public static void write(List<Object> offerLine) throws Exception {

		String range = "Коммерческий отдел!A2:J2"; 
		String valueInputOption = "USER_ENTERED"; 
		ValueRange requestBody = new ValueRange();

		Sheets sheetsService = getSheetsService();
		Sheets.Spreadsheets.Values.Update request =
				sheetsService.spreadsheets().values().update(Journal.getjournalId(), range, requestBody);
		request.setValueInputOption(valueInputOption);
		@SuppressWarnings("unused")
		UpdateValuesResponse response = request.execute();

		List<List<Object>> values = Arrays.asList(offerLine);
		ValueRange body = new ValueRange().setValues(values);
		UpdateValuesResponse result =
				sheetsService.spreadsheets().values().update(Journal.getjournalId(), range, body)
				.setValueInputOption(valueInputOption)
				.execute();
		System.out.printf("%d cells updated.", result.getUpdatedCells());

	}


	public static void insert(String line) throws Exception {

		// The A1 notation of a range to search for a logical table of data.
		// Values will be appended after the last row of the table.
		String range = "Лист!A1"; // TODO: Update placeholder value.

		// How the input data should be interpreted.
		String valueInputOption = "USER_ENTERED"; // TODO: Update placeholder value.

		// How the input data should be inserted.
		String insertDataOption = "OVERWRITE"; // TODO: Update placeholder value.

		// TODO: Assign values to desired fields of `requestBody`:
		ValueRange requestBody = new ValueRange();

		Sheets sheetsService = getSheetsService();
		Sheets.Spreadsheets.Values.Append request =
				sheetsService.spreadsheets().values().append(Journal.getjournalId(), range, requestBody);
		request.setValueInputOption(valueInputOption);
		request.setInsertDataOption(insertDataOption);

		// TODO: Change code below to process the `response` object:

		Object obj = line; // Здесь передаем аргумент функции
		List<List<Object>> values = Arrays.asList(
				Arrays.asList(obj)
				);
		ValueRange body = new ValueRange()
				.setValues(values);
		UpdateValuesResponse result =
				sheetsService.spreadsheets().values().update(Journal.getjournalId(), range, body)
				.setValueInputOption(valueInputOption)
				.execute();
		System.out.printf("%d cells updated.", result.getUpdatedCells());
		AppendValuesResponse response = request.execute();
		// TODO: Change code below to process the `response` object:
		System.out.println(response);
	}


	public static void insertRow(int index) throws Exception {

		// sheetGid is located in the tail of journal's URL.

		// The spreadsheet to apply the updates to.
		//String Journal.getjournalId() = "1569DTiMnVkdFzTW_Rint8juaqVySuRoBsQ0kD5aty04"; // TODO: Update placeholder value.
		// A list of updates to apply to the spreadsheet.
		// Requests will be applied in the order they are specified.
		// If any request is not valid, no requests will be applied.


		InsertDimensionRequest insertDimensionRequest = new InsertDimensionRequest();
		DimensionRange dimRange = new DimensionRange();
		dimRange.setStartIndex(index);
		dimRange.setEndIndex(index+1);
		dimRange.setSheetId(Integer.parseInt(Journal.getJournalGid()));

		dimRange.setDimension("ROWS");
		insertDimensionRequest.setRange(dimRange);

		List<Request> requests = new ArrayList<Request>(); // TODO: Update placeholder value.
		requests.add( new Request().setInsertDimension(insertDimensionRequest));

		// TODO: Assign values to desired fields of `requestBody`:

		BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest().setRequests(Arrays.asList(
				new Request().setInsertDimension(insertDimensionRequest)));

		requestBody.setRequests(requests);
		Sheets sheetsService = getSheetsService();
		Sheets.Spreadsheets.BatchUpdate request =
				sheetsService.spreadsheets().batchUpdate(Journal.getjournalId(), requestBody);

		System.out.println(Journal.getjournalId());

		BatchUpdateSpreadsheetResponse response = request.execute();
		// TODO: Change code below to process the `response` object:
		System.out.println(response);

	}


	public static String read(String range) throws Exception {	        
		// Build a new authorized API client service.

		Sheets service = getSheetsService();
		ValueRange response = service.spreadsheets().values()
				.get(Journal.getjournalId(), range)
				.execute();
		List<List<Object>> values = response.getValues();

		if (values == null || values.size() == 0) {
			return "No data found.";
		} else {
			for (@SuppressWarnings("rawtypes") List row : values) {
				return (String) row.get(0);
			}  
		}
		return "";
	}	

}