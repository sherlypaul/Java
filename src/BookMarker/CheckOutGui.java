package BookMarker;

import java.awt.*;
import javax.swing.*;
import java.util.Calendar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;



//A common class which creates Title banner for all the classes
class BannerPanel extends JPanel
{    
	private String t;
    public BannerPanel(String title ){
        this.t=title;
        setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.red));
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(500,0)));
        JLabel bannerText = new JLabel(t);
        add(bannerText);

    }
    
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
class CheckOutGui extends JFrame {
	
	private JButton okButton,backButton,addToCart;
	private JTextArea result;
	private JScrollPane scrollPane;
    private BannerPanel banner;
    JPanel leftPanel;
    Label_TextFieldPanel memberId;
    Label_TextFieldPanel itemNo;
private JLabel checkOutError;
private JLabel checkout;
    //private ButtonListener;
String textarea = " Title" + "\t" + "Member Id" + "\t"   +" Checkout Date "  + "\t" + "Due Date" + "\n  " ;
/////////////////////////////////////////////////////////////////////////////////////////////////////////    



    public CheckOutGui(String memberId1){
        setLayout(new BorderLayout());
        Container contentPane = getContentPane();
        banner = new BannerPanel("ITEM CHECKOUT");
        contentPane.add(banner,BorderLayout.PAGE_START);
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.yellow);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        memberId = new Label_TextFieldPanel("Member-id",false,10);
        memberId.setText( memberId1 );
        leftPanel.add(memberId);      
        itemNo = new Label_TextFieldPanel("Item No",false,10);
        ButtonListener bl = new ButtonListener();
        addToCart = new JButton("ADD TO CART");
        leftPanel.add(itemNo);
        leftPanel.add(addToCart);
        contentPane.add(leftPanel,BorderLayout.LINE_START);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        JPanel buttonRow = new JPanel();
        okButton = new JButton("CHECKOUT");
        backButton = new JButton("BACK");    
        buttonRow.add(okButton);
        buttonRow.add(backButton);
        okButton.addActionListener(bl);
        backButton.addActionListener(bl);
        addToCart.addActionListener(bl);
        result =new JTextArea();
		scrollPane = new JScrollPane( result );
		infoPanel.add( scrollPane); 
        infoPanel.add(buttonRow);
        checkOutError = new JLabel("You have exceeded the permissable checkout limit");
        infoPanel.add(checkOutError);
        checkOutError.setVisible(false);
        checkout = new JLabel("The above items have checked out");
        infoPanel.add(checkout);
        checkout.setVisible(false);
        JPanel middlePanel  = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
        middlePanel.add(infoPanel);
        contentPane.add(middlePanel,BorderLayout.CENTER);
        JPanel rightPanel = new JPanel();     
        rightPanel.setLayout(new FlowLayout());
        contentPane.add(rightPanel,BorderLayout.LINE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();    
            setSize(1000,500);
        setVisible(true);
    }
    
 

    class ButtonListener implements ActionListener{
    	
    	int total=0, totalBookF = 0, totalVideo = 0,  totalBookNF = 0  ;

   	 public void actionPerformed( ActionEvent e )
   	 { 
   		 
   		
   		 if(!itemNo.getTextValue().trim().equals(""))
   	  	  {
   	  
   	  		 String item =itemNo.getTextValue().trim();
   	  		 String member = memberId.getTextValue().trim();
   	  		 
   	  	  if(e.getSource()==addToCart  && total !=7 && totalVideo !=2 && totalBookF != 3 &&  totalBookNF != 2)
   	  	  {
   	  		  
   	  		 
   				String query1 ="SELECT * FROM [BookMarker].[dbo].[Book] where itemCode = '" + item + "'" ;
   				String query2 ="SELECT * FROM [BookMarker].[dbo].[Video] where itemCode = '" + item + "'" ;
   				DataAccess dataAccess= new DataAccess();
   	   			dataAccess.openConnection();
   	   			 ResultSet rs;
   	   			int day,month,year;
   	   			String dueDate,itemCode,stage;
   	   			try 
   	   			{
   	   					total++;
   	   			rs  =dataAccess.executeSelectQuery(query1);
   	   			
   	   			while(rs.next())
   					 { 
   	   			Calendar now = Calendar.getInstance();
   	   			year = now.get(Calendar.YEAR);
   	   		    now.add(Calendar.WEEK_OF_YEAR,2);
   	   			day =now.get(Calendar.DATE );
   	   			month = now.get(Calendar.MONTH ) + 1;
   	   			dueDate = Integer.toString(year) + "-"+  Integer.toString(month) + "-"+ Integer.toString(day); 
   	   			itemCode = rs.getString("itemCode");
   	   			stage = rs.getString("stage");
   	   			if(itemCode.contains("NF") &&  !(stage.contains("BORROWED") ))
   	   			{
   	   			totalBookNF++;
   	   				 String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId,itemNo,dueDate,checkoutDate) VALUES('" + member+"','"+ itemCode+ "','" + dueDate +"', GETDATE())";
   	   			dataAccess.executeInsertQuery(query3);
   	   		    String query4 = "Update [BookMarker].[dbo].[Book] set stage ='BORROWED', checkedoutby = '"+ member +"', checkoutdate = GETDATE()  where itemCode = '" + itemCode +"'"; 
	   			dataAccess.executeUpdateQuery(query4);
   	   			}
   	   			else if( !(stage.contains("BORROWED") ) ){
   	   			totalBookF++;
   	   			String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId,itemNo,dueDate,checkoutDate) VALUES('" + member+"','"+ itemCode+ "','" + dueDate +"', GETDATE())";
   	   			dataAccess.executeInsertQuery(query3);
   	   		    String query4 = "Update [BookMarker].[dbo].[Book] set stage ='BORROWED', checkedoutby = '"+ member +"', checkoutdate = GETDATE()  where itemCode = '" + itemCode +"'"; 
	   			dataAccess.executeUpdateQuery(query4);
   	   			}
   	   		String query5 ="SELECT * FROM [BookMarker].[dbo].[Book] where itemCode = '" + item + "'" ;
   	   	rs  = dataAccess.executeSelectQuery(query5);
   	   	while(rs.next())
   	   	{
   	   	textarea = textarea+ rs.getString("title") +"\t" + member + "\t" + rs.getString("checkoutdate")+ "\t"  +rs.getString("duedate") + "\n" ;
   	   	}
   	   			
   					 }
   	   			rs  =dataAccess.executeSelectQuery(query2);
   	   			while(rs.next())
   				 { 
   	   			Calendar now = Calendar.getInstance();
   	   			year = now.get(Calendar.YEAR);
   	   		    now.add(Calendar.WEEK_OF_YEAR,1);
   	   			day =now.get(Calendar.DATE );
   	   			month = now.get(Calendar.MONTH );
   	   			dueDate = Integer.toString(year) + "-"+  Integer.toString(month) + "-"+ Integer.toString(day); 
   	   			itemCode = rs.getString("itemCode");
   	   			stage = rs.getString("stage");
   	   			 
   	   			if(itemCode.contains("V")  && !(stage.contains("BORROWED") ))
   	   			{
   	   			totalVideo++;
   	   				 String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId,itemNo,dueDate) VALUES('" + member+"','"+ itemCode+ "','" + dueDate +"')";
   	   			dataAccess.executeInsertQuery(query3);
   	   		    String query4 = "Update [BookMarker].[dbo].[Video] set stage ='BORROWED', checkedoutby = '"+ member +"', checkoutdate = GETDATE()  where itemCode = '" + itemCode +"'"; 
	   			dataAccess.executeUpdateQuery(query4);
   	   			}
   	   		String query6 ="SELECT * FROM [BookMarker].[dbo].[Video] where itemCode = '" + item + "'" ;
   	   	   	rs  = dataAccess.executeSelectQuery(query6);
   	   	   	while(rs.next())
   	   	   	{
   	   	   	textarea = textarea+ rs.getString("title") +"\t" + member + "\t" + rs.getString("checkoutdate")+ "\t"  +rs.getString("duedate") + "\n" ;
   	   	   	}
   				 }
   	   				
   	   				
   	   			}
   	   			 catch (SQLException e1) {
   					// TODO Auto-generated catch block
   					e1.printStackTrace();
   				}
   	   		    result.setText(textarea);
   	   			dataAccess.closeConnection();
   	  	  }
   	  	else
		{
		checkOutError.setVisible(true);
		}
   	  	  }
   		
   	  	  if(e.getSource()==backButton)
   	  	  {
   	  		     setVisible( false );
   	  	    	 MemberGui a = new MemberGui();
   	  	   	     a.setTitle( "Bookmarker's Library Manager" );
   	  		     a.setSize( 1000, 500 );
   	  	  }
   	  	if(e.getSource()==okButton)
   		  {
   	  	    checkout.setVisible(true);
   		  }
   	    }

   		 
   	 }
   }