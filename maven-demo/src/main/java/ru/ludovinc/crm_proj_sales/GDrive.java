package ru.ludovinc.crm_proj_sales;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class GDrive {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-java-quickstart");

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
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE);

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
        InputStream in =
    		GDrive.class.getResourceAsStream("/client_secret.json");
    		GoogleClientSecrets clientSecrets =
    				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws Exception 
     */
    public static Drive getDriveService() throws Exception {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static String createFolder(String parentFolderId, String projId) throws Exception {
        // Создаем папку и возвращаем ее ID
    	
    	// Build a new authorized API client service.
        Drive service = getDriveService();
        // Print the names and IDs for up to 10 files.
        /////////////////////////////////////////
        File fileMetadata = new File();
        
        fileMetadata.setName(projId); // присваиваем имя папке соотв номру КП
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        
        fileMetadata.setParents(Collections.singletonList(parentFolderId)); //add
        
        File file = service.files().create(fileMetadata)
        	.setFields("id, parents")//.setFields("id")
            .execute();
        return file.getId();
    }
    
//    public static void insertFiles(String folderId) throws Exception {
//    
//    }
        
    
    
    public static String insertFile(String fileName, String folderId, java.io.File filePath) throws Exception {
        
    	Drive service = getDriveService();  
        File fileMetadata1 = new File();
        fileMetadata1.setName(fileName);
        fileMetadata1.setParents(Collections.singletonList(folderId));  
       // FileContent mediaContent = new FileContent("image/jpeg", filePath);
        FileContent mediaContent = new FileContent(null, filePath);
        File file1 = service.files().create(fileMetadata1, mediaContent)
            .setFields("id, parents")
            .execute();
        System.out.println("File ID: " + file1.getId());
		
        return folderId;
        
    }

    

/*
        /*
        
        //////////////////////////////////////////
        /*
        
        
        Drive service = getDriveService(); 
    	String folderId = "1kvRtgsAF_OHDbrSe-hNWidUbJkV5A7SL";
        
        File fileMetadata1 = new File();
        fileMetadata1.setName("Landau.jpg");
        fileMetadata1.setParents(Collections.singletonList(folderId));
        
        java.io.File filePath = new java.io.File("Landau.jpg");
        
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        
        File file1 = service.files().create(fileMetadata1, mediaContent)
            .setFields("id, parents")
            .execute();
        System.out.println("File ID: " + file1.getId());
		
        return folderId;
        
        
   */     
        
//    public static void main(String [] args){
//    	String folderId = "1Jagfw4SKQqGUXlzsqQ3xGrW79XweDF3I";
//    	try {
//			File file = GetSertainFileInFolder(folderId, null, "1laiLDm0HzKXG2owiglG9Gn3ij15lDful"); //(1aN3uCZ1Ek1cgg0O_IoeE3J1DFJEju8Qw)
//			
//			System.out.println(file.getId());
//			
//    	} catch (Exception e) {
//			e.printStackTrace();
//		} 
//    }
    
    
  
	public static File GetSertainFileInFolder(String folderId, String fileName, String fileId) throws Exception {
    	//search by name and id one of them or both of them.
    	List<File> files = GetFilesInFolder(folderId);
		if (files == null || files.size() == 0) {
			return null;
	    } else {
	        for (File file : files) {
	        	if (fileId == null && fileName.equals(file.getName())){
	        		return file;
	        		}
	        	if (fileName == null && fileId.equals(file.getId())){
	        		return file;
	        		}	
	        }
	    }
    	return null;
    }    
	public static List<File> GetFilesInFolder(String folderId) throws Exception {
	        
		Drive service = getDriveService();
		FileList result = service.files().list()				
			    .setQ("'"+folderId+"' in parents")
				.setPageSize(1000)
				.setFields("nextPageToken, files(id, name)")
	            .execute();
		List<File> files = result.getFiles();
	return files; 
	}
}
  