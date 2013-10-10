package BookMarker;
//import BannerPanel;

import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;




/////////////////////////////////////////////////////////////////////////////////////////////////////////////
class ReturnGui extends JFrame  {
	private JButton okButton,backButton;
	private JTable table;
	private JScrollPane scrollPane;
    private BannerPanel banner;
    private JPanel leftPanel;
    private Label_TextFieldPanel memberId,itemNo;
    private String dataValue[][];
    private String memberEmail[];
    private JLabel return1;
    public ReturnGui(String memberId1){

    setLayout(new BorderLayout());
    Container contentPane = getContentPane();
    banner = new BannerPanel("RETURN AN ITEM");
    contentPane.add(banner,BorderLayout.PAGE_START);
    leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
    leftPanel.setBackground(Color.yellow);
    leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
    memberId = new Label_TextFieldPanel("MemberId",false,10);;
    memberId.setText( memberId1 );
    leftPanel.add(memberId);      
    itemNo = new Label_TextFieldPanel("Item No",false,10);
    leftPanel.add(itemNo);
    contentPane.add(leftPanel,BorderLayout.LINE_START);
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
    return1 = new JLabel("The  items has been returned ");
    return1.setVisible(false);
    JPanel buttonRow = new JPanel();
    ButtonListener bl = new ButtonListener(); 
    okButton = new JButton("RETURN");
    backButton = new JButton("BACK");
    buttonRow.add(okButton);
    buttonRow.add(backButton);
    okButton.addActionListener(bl);
    backButton.addActionListener(bl);

		
		// Create columns names
		String columnNames[] = { "Title", "Member Id", "Item No", "Due Date"};
		String query1 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[Book] where checkedoutby =  '" + memberId1 +"' and ( stage ='BORROWED'or stage = 'RESERVED')";
		String query2 ="SELECT * FROM [BookMarker].[dbo].[Book] where  checkedoutby =  '" + memberId1 +"' and ( stage ='BORROWED'or stage = 'RESERVED')";
        String query3 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[VIDEO] where  checkedoutby =  '" + memberId1 +"' and ( stage ='BORROWED'or stage = 'RESERVED')";
		String query4 ="SELECT * FROM [BookMarker].[dbo].[VIDEO] where  checkedoutby =  '" + memberId1 +"'and ( stage ='BORROWED'or stage = 'RESERVED')";
			DataAccess dataAccess= new DataAccess();
 			dataAccess.openConnection();
 			 ResultSet rs=dataAccess.executeSelectQuery(query1);
 			 int i=0;
 			try {
				while(rs.next())
					 {
					     i=rs.getInt("noOfRows");
						
						
						
					 }
				rs=dataAccess.executeSelectQuery(query3);
	   			int j = 0;
	   			while(rs.next())
				 {
				     j=rs.getInt("noOfRows");
				 }
	   			 dataValue= new String[i+j][4];
	   			 memberEmail =new String[i+j];
				rs  =dataAccess.executeSelectQuery(query2);
	   			i =0;
	   			while(rs.next())
	   				{
	   				 j=0;
	   				dataValue[i][j] = rs.getString("title");
					
					j++;
					dataValue[i][j] = rs.getString("checkedoutby");
				
				j++;
				dataValue[i][j] = rs.getString("itemCode") ;
				j++;
				dataValue[i][j] = rs.getString("duedate") ;
				memberEmail[i] = rs.getString("reservedby");
				i++;
				
	   				}
				rs  =dataAccess.executeSelectQuery(query4);
	   			
	   			while(rs.next())
	   				{
	   				j=0;
	   				dataValue[i][j] = rs.getString("title");
					
					j++;
					dataValue[i][j] = rs.getString("checkedoutby");
				
				j++;
				dataValue[i][j] = rs.getString("itemCode") ;
				j++;
				dataValue[i][j] = rs.getString("duedate") ;
				memberEmail[i] = rs.getString("reservedby");
				i++;
	   				}
	   			
 			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 			dataAccess.closeConnection();
	   				
		// Create a new table instance
		table = new JTable( dataValue, columnNames );
		scrollPane = new JScrollPane( table );
		infoPanel.add( scrollPane);
		infoPanel.add(return1);
    infoPanel.add(buttonRow);  
    JPanel middlePanel  = new JPanel();
    middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
    middlePanel.add(infoPanel);
    contentPane.add(middlePanel,BorderLayout.CENTER);
    JPanel rightPanel = new JPanel();     
    rightPanel.setLayout(new FlowLayout());
    contentPane.add(rightPanel,BorderLayout.LINE_END);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();    
        
    setVisible(true);
}
class ButtonListener implements ActionListener{
public void actionPerformed( ActionEvent e )
{
	
		  if(e.getSource()==backButton)
   	  	  {
   	  		  setVisible( false );
   	  	    	 MemberGui a = new MemberGui();
   	  	   	     a.setTitle( "Bookmarker's Library Manager" );
   	  		     a.setSize( 1000, 500 );
   	  	  }
		  if(!itemNo.getTextValue().trim().equals(""))
		  {
   	  	if(e.getSource()==okButton)
   		  {
   			 
   	  	    String item =itemNo.getTextValue().trim().toUpperCase();
	  		String member = memberId.getTextValue().trim();
   	  	    String query1 ="SELECT * FROM [BookMarker].[dbo].[Book] where itemCode = '" + item + "'" ;
			String query2 ="SELECT * FROM [BookMarker].[dbo].[Video] where itemCode = '" + item + "'" ;
			DataAccess dataAccess= new DataAccess();
  			dataAccess.openConnection();
  			ResultSet rs;
  			int day,month,year,noOfDay=0;
  			String checkoutDate,returnDate,itemCode,stage;
  			float amount;
  			Date duedate;
  			Date date1 = new Date();
  			try 
  			{
  					
  			rs  =dataAccess.executeSelectQuery(query1);
  			
  			while(rs.next())
				 { 
  			Calendar now = Calendar.getInstance();
  			year = now.get(Calendar.YEAR);
  			day =now.get(Calendar.DATE );
  			month = now.get(Calendar.MONTH );
  			returnDate = Integer.toString(year) + "-"+  Integer.toString(month) + "-"+ Integer.toString(day); 
  			itemCode = rs.getString("itemCode");
  			stage = rs.getString("stage");
  			duedate = rs.getDate("duedate");
  			
  			if (duedate.before(date1) )
  			{
  				long startTime = duedate.getTime();
  				long endTime = date1.getTime();
  				long diffTime = endTime - startTime;
  				 noOfDay = (int) (diffTime / (1000 * 60 * 60 * 24));
  			}
  			if(itemCode.contains("NF") &&  !(stage.contains("Available") ))
  			{
  			 amount = (float) (noOfDay * 0.30);
  				 String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId,itemNo,returnDate,noOfDays) VALUES('" + member+"','"+ itemCode+ "','" + returnDate +"'," + noOfDay + ")";
  			dataAccess.executeInsertQuery(query3);
  		    String query4 = "Update [BookMarker].[dbo].[Book] set stage ='Available', checkedoutby = '"+ member +"', returnDate = GETDATE()  where itemCode = '" + itemCode +"'"; 
			dataAccess.executeUpdateQuery(query4);
			String query7 = "Update [BookMarker].[dbo].[Member] set fee = '" + amount+ "'  where MemberCode = '" + member +"'"; 
			dataAccess.executeUpdateQuery(query4);
			String query8 = "Select emailId from [BookMarker].[dbo].[Member] where MemberCode ='"  + member+"'";
			rs = dataAccess.executeSelectQuery(query8);
			while(rs.next())
			{
				String email1 = rs.getString("emailId");
				Mail m = new Mail(email1);
			}
  			}
  			else if( !(stage.contains("Available") ) ){
  				amount = (float) (noOfDay * 0.15);
  			String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId,itemNo,returnDate,noOfDays) VALUES('" + member+"','"+ itemCode+ "','" + returnDate +"','" + noOfDay + "')";
  			dataAccess.executeInsertQuery(query3);
  		    String query4 = "Update [BookMarker].[dbo].[Book] set stage ='Available', checkedoutby = '"+ member +"', returnDate = GETDATE()  where itemCode = '" + itemCode +"'"; 
			dataAccess.executeUpdateQuery(query4);
			String query7 = "Update [BookMarker].[dbo].[Member] set fee = '" + amount+ "'  where MemberCode = '" + member +"'"; 
			dataAccess.executeUpdateQuery(query7);
			String query8 = "Select emailId from [BookMarker].[dbo].[Member] where MemberCode ='"  + member+"'";
			rs = dataAccess.executeSelectQuery(query8);
			while(rs.next())
			{
				String email1 = rs.getString("emailId");
				Mail m = new Mail(email1);
			}
  			}
  	
  			return1.setVisible(true);
				 }
  			rs  =dataAccess.executeSelectQuery(query2);
  			while(rs.next())
			 { 
  			Calendar now = Calendar.getInstance();
  			year = now.get(Calendar.YEAR);
  		    now.add(Calendar.WEEK_OF_YEAR,1);
  			day =now.get(Calendar.DATE );
  			month = now.get(Calendar.MONTH ) +1;
  			returnDate = Integer.toString(year) + "-"+  Integer.toString(month) + "-"+ Integer.toString(day); 
  			itemCode = rs.getString("itemCode");
  			stage = rs.getString("stage");
  			duedate = rs.getDate("duedate");
  			if (duedate.before(date1) )
  			{
  				long startTime = duedate.getTime();
  				long endTime = date1.getTime();
  				long diffTime = endTime - startTime;
  				 noOfDay = (int) (diffTime / (1000 * 60 * 60 * 24));
  			}
  			if(itemCode.contains("V")  && !(stage.contains("Available") ))
  			{
  				amount = (float) (noOfDay * 0.50);
  				 String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId,itemNo,returnDate,noOfDays) VALUES('" + member+"','"+ itemCode+ "','" + returnDate +"','" + noOfDay + "')";
  			dataAccess.executeInsertQuery(query3);
  		    String query4 = "Update [BookMarker].[dbo].[Video] set stage ='Available', checkedoutby = '"+ member +"', returnDate = GETDATE()  where itemCode = '" + itemCode +"'"; 
			dataAccess.executeUpdateQuery(query4);
			String query7 = "Update [BookMarker].[dbo].[Member] set fee = '" + amount+ "'  where MemberCode = '" + member +"'"; 
			dataAccess.executeUpdateQuery(query7);
			String query8 = "Select emailId from [BookMarker].[dbo].[Member] where MemberCode ='"  + member+"'";
			rs = dataAccess.executeSelectQuery(query8);
			while(rs.next())
			{
				String email1 = rs.getString("emailId");
				Mail m = new Mail(email1);
			}
			return1.setVisible(true);
  			}
  	   	
			 }
  				
  				
  			}
  			 catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  			dataAccess.closeConnection();
   		  }
	  
}
}
}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////