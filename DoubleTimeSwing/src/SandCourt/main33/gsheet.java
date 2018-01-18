package SandCourt.main33;
import com.google.gdata.*;
import com.google.gdata.client.AuthTokenFactory;
import com.google.gdata.client.GoogleService;
import com.google.gdata.util.ServiceException;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

import java.io.IOException;
import java.net.URL;

import com.google.gdata.*;

public class gsheet 
{
    public static final String GOOGLE_ACCOUNT_USERNAME = "ludovdim@gmail.com";
    public static final String GOOGLE_ACCOUNT_PASSWORD = "xxxxxxxx"; 
    public static final String SPREADSHEET_URL = "https://docs.google.com/spreadsheets/d/1SNFVxn4sICavhUIkwqIPP-3eg8z8BJWJC5OZU3ZFaBY/edit#gid=763456541";


    public static void main(String[] args) throws IOException, ServiceException{
    	
    	SpreadsheetService service = new SpreadsheetService("JAVA");
    	
    	service.setProtocolVersion( SpreadsheetService.Versions.V1 );
    	service.setUserCredentials(GOOGLE_ACCOUNT_USERNAME, GOOGLE_ACCOUNT_PASSWORD);
        
    	URL metafeedUrl = new URL(SPREADSHEET_URL);
        
        SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
        URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();
        ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
        for(ListEntry entry : feed.getEntries()){
        	System.out.println("new row");
        	for(String tag : entry.getCustomElements().getTags()){
        		System.out.println("     "+tag + ": " + entry.getCustomElements().getValue(tag));
        	}

        }

    }
}