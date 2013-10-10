package BookMarker;








/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//import Java libraries

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
class PayFineGui extends JFrame {
	
	// private data members
	private JButton okButton,backButton;
 private BannerPanel banner;
 private JLabel invalid,fine;
 JPanel leftPanel;
 Label_TextFieldPanel memberId;
 Label_TextFieldPanel fineAmount;
 
/**
 Constructs the frame with a label,buttons and a control panel.
*/
 public PayFineGui(String memberId1){
 	
     setLayout(new BorderLayout());
     Container contentPane = getContentPane();
     banner = new BannerPanel("PAY FINE");
     contentPane.add(banner,BorderLayout.PAGE_START);
     ButtonListener bl = new ButtonListener();
     leftPanel = new JPanel();
     leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.X_AXIS));
     leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
     memberId = new Label_TextFieldPanel("Member-id",false,10);
     memberId.setText( memberId1 );
     leftPanel.add(memberId);      
     fineAmount = new Label_TextFieldPanel("Fine Amount",false,10);
     leftPanel.add(fineAmount);
     contentPane.add(leftPanel,BorderLayout.LINE_START);
     
     // Extract fine for the Member
     String m =memberId.getTextValue().trim();
		String query1 = "SELECT  fee FROM [BookMarker].[dbo].[Member] WHERE MemberCode like '" + m +"'";
     DataAccess dataAccess= new DataAccess(); 
     dataAccess.openConnection();
     ResultSet rs=dataAccess.executeSelectQuery(query1);
     float f=0;
     try {
     	while(rs.next())
     	{
			 f = rs.getFloat("fee");
     	}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
     dataAccess.closeConnection();
     fine =new JLabel(" The Fine amount is $ " +f);
     leftPanel.add(fine);
     JPanel infoPanel = new JPanel();
     infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
     // Add button Row
     JPanel buttonRow = new JPanel();
     okButton = new JButton("Pay Fine");
     backButton = new JButton("BACK");
     okButton.addActionListener(bl);
     backButton.addActionListener(bl);
     buttonRow.add(okButton);
     buttonRow.add(backButton);
     infoPanel.add(Box.createRigidArea(new Dimension(0,200)));
     infoPanel.add(buttonRow);  
     invalid =new JLabel("");
     invalid.setVisible(false);
     leftPanel.add(invalid);
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
 
 //////////////////////////////////////////////////////////////////////////////////////////////////////
 
 //Add ButtonListener eventS for OK and PayFine
 public class ButtonListener implements ActionListener
 {
 	
 	public void actionPerformed(ActionEvent e)
 	{
 		if(e.getSource()==okButton)
 		{		

 			String fineAmount1 =fineAmount.getTextValue().trim().toUpperCase();
 			String m = memberId.getTextValue().trim().toUpperCase();
 			 String query1 = "SELECT  fee FROM [BookMarker].[dbo].[Member] WHERE MemberCode like '" + m +"'";;
	             DataAccess dataAccess= new DataAccess(); 
	             dataAccess.openConnection();
	             ResultSet rs=dataAccess.executeSelectQuery(query1);
	             float f=0;
	             try {
	            	 while (rs.next())
	            	 {
					 f = rs.getFloat("fee");
	            	 }
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	             if (Double.valueOf(fineAmount1)!= f){
	            	 fine.setVisible(false);
	            	 invalid.setText("Invalid Amount" );
	            	 invalid.setVisible(true);
	            	 dataAccess.closeConnection();
		     }
	             else
	             {
	            	 Double f1=Double.valueOf(fineAmount1);
	            	 String query3 = "INSERT INTO [BookMarker].[dbo].[STransaction](memberId, fee,checkoutDate) VALUES('" + m+"','"+ f1+ "',GETDATE())";
	       			dataAccess.executeInsertQuery(query3);
	       			fine.setVisible(false);
	            	 invalid.setText("Fine paid" );
	            	 invalid.setVisible(true);
	             }
 		}
 		if (e.getSource()==backButton)
 		{
 			setVisible(false);
 			MemberGui m1=new MemberGui();
 			m1.setVisible(true);
 			m1.setSize(1000,500);
 		}
			
		}//Action Performed
 }//ButtonListener 
}// end PayFineGui