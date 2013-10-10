package BookMarker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
	//DataMembers
	 private static String url = "jdbc:sqlserver://localhost:1433;" +
             "databaseName=BookMarker;user=sherly;password=sherly";
	 Connection con=null;
	  	Statement stmt=null;
	  	ResultSet rs = null;
	  	String[][]  dataValues;
	  	int noOfRow =0;
	  	//Class functions
	  	public Connection openConnection(){
	  		try{
	  			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		  		con = DriverManager.getConnection(url);
	  		}
	  		catch(ClassNotFoundException e) //catch error Class.forName
	 		  {
	 		       System.err.println("Error class not found");
	 		       e.printStackTrace();
	 		       System.exit(1);
	 		  } 
	catch(SQLException e) //catch error for getConnection
	  {
	       System.err.println("Error connecting");
	       e.printStackTrace();
	       System.exit(1);
	  }
	  	return con;	
	  	}
	  	
	  	public ResultSet executeCountQuery(String query1){
	  		int i=0;
	  		try{
	  			//openConnection();
	  			stmt = con.createStatement();
	  			
	  			try{
	  				rs = stmt.executeQuery(query1);
	  				 return rs;
	  			}
	  			catch(SQLException e) //catch error for any query
			    {
				 System.err.println("Query error");
				 e.printStackTrace();
				 stmt.close();
				 con.close();
				 System.exit(1);
			    }
	  			closeConnection();
	  		}
	  		catch(SQLException e) //catch error for creating the statement
		       {
			    System.err.println("Error creating statement");
			    e.printStackTrace();
			    System.exit(1);
			   
		       }
	  		finally{
	  			
	  			try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  		}
	  		
	  		return rs;
	  		
	  	}
	  	public ResultSet executeSelectQuery( String query1){
	  		try{
	  			//openConnection();
	  			stmt = con.createStatement();
	  			
	  			try{
	  				
	  				rs = stmt.executeQuery(query1);
	  				
	  				
	  				 return rs;
	  			}
	  			catch(SQLException e) //catch error for any query
			    {
				 System.err.println("Query error");
				 e.printStackTrace();
				 stmt.close();
				 con.close();
				 System.exit(1);
			    }
	  			closeConnection();
	  		}
	  		catch(SQLException e) //catch error for creating the statement
		       {
			    System.err.println("Error creating statement");
			    e.printStackTrace();
			    System.exit(1);
			   
		       }
	  		finally{
	  			
	  		}
	  		
	  		return rs;
	  	}
	  	public void executeInsertQuery(String query1){
	  		try{
	  			//openConnection();
	  			stmt = con.createStatement();
	  			try{
	  				stmt.executeUpdate(query1);
	  				//stmt.close();
	  				 //con.close();
	  			}
	  			catch(SQLException e) //catch error for any query
			    {
				 System.err.println("Query error");
				 e.printStackTrace();
				 stmt.close();
				 con.close();
				 System.exit(1);
			    }
	  			//closeConnection();
	  		}
	  		catch(SQLException e) //catch error for creating the statement
		       {
			    System.err.println("Error creating statement");
			    e.printStackTrace();
			    System.exit(1);
			   
		       }
	  		finally{
	  		}
	  		
	  	}
public void executeDeleteQuery(String query1){
	try{
			
			stmt = con.createStatement();
			try{
				
				stmt.executeUpdate(query1);
			}
			catch(SQLException e) //catch error for any query
	    {
		 System.err.println("Query error");
		 e.printStackTrace();
		 stmt.close();
		 con.close();
		 System.exit(1);
	    }
			//closeConnection();
		}
		catch(SQLException e) //catch error for creating the statement
       {
	    System.err.println("Error creating statement");
	    e.printStackTrace();
	    System.exit(1);
	   
       }
		finally{
			
		}
	
}
public void executeUpdateQuery(String query1){
	try{
		//openConnection();
		stmt = con.createStatement();
		try{
			//stmt.executeUpdate(query1);
			stmt.executeUpdate(query1);
			//stmt.close();
			// con.close();
		}
		catch(SQLException e) //catch error for any query
    {
	 System.err.println("Query error");
	 e.printStackTrace();
	// stmt.close();
	 //con.close();
	 System.exit(1);
    }
		//closeConnection();
	}
	catch(SQLException e) //catch error for creating the statement
   {
    System.err.println("Error creating statement");
    e.printStackTrace();
    System.exit(1);
   
   }
	finally{

	}
	
}
       public void closeConnection(){
    	   if (stmt!=null)
    	   {
    		   try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   }
    	   if(con!=null)
    	   {
    	   try{
    		   con.close();
    	   }
    	   catch(SQLException e){
    		   System.err.println("Error connecting");
    	       e.printStackTrace();
    	       System.exit(1); 
    	   }
    	   }
    	   
       }
}