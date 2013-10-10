package BookMarker;
import java.sql.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.sql.*;
import javax.swing.*;
import javax.swing.JFrame;
public class BookMarker {
	
	//Data Members
	

	    private DataAccess dataAccess;
	    
	    
	    /**
	     * @param args the command line arguments
	     */

	    static String url = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=BookMarker;user=sherly;password=sherly";
	    
	    public static void main(String[] args) {
	    	
	    	JFrame frame = new Home();
   	        frame.setTitle( "Bookmarker's Library Manager" );
   	        frame.setSize( 1000, 500 );
   	        frame.setVisible(true);  
	    	DataAccess dataAccess= new DataAccess();
	    	
	    }
}
	  	  	
	  	  		
	  	  	
	



