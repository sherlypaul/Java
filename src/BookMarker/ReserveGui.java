package BookMarker;
//import BannerPanel;

import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


class Label_TextFieldPanel extends JPanel
{
protected Label_TextFieldPanel(String labelTxt,boolean editableProperty,int width)
{
    setLayout(new FlowLayout());
    setBackground(Color.YELLOW);
    add (new JLabel(labelTxt));
    txtField = new JTextField(width);
    add (txtField);
}
public void setText(String text)
{
    txtField.setText(text);
}
public String getTextValue()
{
    return txtField.getText();
}    
private JTextField txtField;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
class ReserveGuiWin extends JFrame  {
	private JButton okButton,backButton;
	private JTable table;
	private JScrollPane scrollPane;
	private BannerPanel banner;
    private JPanel leftPanel, labelPanel, mergePanel;
    private JLabel result;
    private Label_TextFieldPanel memberId, itemNo;
    private String dataValue[][] ;
    
    
public ReserveGuiWin(String memberId1){

    setLayout(new BorderLayout());
    Container contentPane = getContentPane();
    banner = new BannerPanel("RESERVE AN ITEM");
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
    JPanel buttonRow = new JPanel();
    labelPanel = new JPanel();
    JPanel mergePanel = new JPanel(new GridLayout(0,1));
    result = new JLabel("Item reserved");
    labelPanel.add(result); 
    result.setVisible(false);
    ButtonListener bl = new ButtonListener(); 
    okButton = new JButton("RESERVE");
    backButton = new JButton("BACK");
    okButton.addActionListener(bl);
    backButton.addActionListener(bl);
    buttonRow.add(okButton);
    buttonRow.add(backButton);
    mergePanel.add(buttonRow);
    mergePanel.add(labelPanel);
		
		// Create columns names
		String columnNames[] = { "Item", "Status", "Member Name"};
		String query1 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[Book] where stage ='RESERVED'  ";
		String query2 ="SELECT * FROM [BookMarker].[dbo].[Book] where stage = 'RESERVED' ";
        String query3 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[VIDEO] where stage ='RESERVED' ";
	    String query4 ="SELECT * FROM [BookMarker].[dbo].[VIDEO] where stage = 'RESERVED' ";
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
	   			 dataValue= new String[i+j][3];
				rs  =dataAccess.executeSelectQuery(query2);
	   			i =0;
	   			while(rs.next())
	   				{
	   				 j=0;
	   				dataValue[i][j] = rs.getString("title");
					
					j++;
					dataValue[i][j] = rs.getString("stage");
				
				j++;
				dataValue[i][j] = rs.getString("reservedby") ;
				i++;
	   				}
				rs  =dataAccess.executeSelectQuery(query4);
	   			while(rs.next())
	   				{
	   				 j=0;
	   				dataValue[i][j] = rs.getString("title");
					
					j++;
					dataValue[i][j] = rs.getString("stage");
				
				j++;
				dataValue[i][j] = rs.getString("reservedby") ;
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
    infoPanel.add(mergePanel); 
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
public class ButtonListener implements ActionListener
{
public void actionPerformed( ActionEvent e )
{
	  if(!itemNo.getTextValue().trim().equals(""))
	  {
		  
		 String item =itemNo.getTextValue().trim();
		 String member = memberId.getTextValue().trim();
	  if(e.getSource()==okButton)
	  {
		  String query1;
		  if(!(item.contains("V")))
		  {
			  query1 = "Update [BookMarker].[dbo].[Book] set stage ='RESERVED', reservedby = '"+ member +"', reservedate = GETDATE() where itemCode = '" + item +"'"; //and stage ne 'Available'" ; 
		  }
		  else
		  {
			  query1 = "Update [BookMarker].[dbo].[Video] set stage ='RESERVED', reservedby = '"+ member +"', reservedate = GETDATE() where itemCode = '" + item +"'";//and stage ne 'Available'" ; 
		  }
		   
		  DataAccess dataAccess= new DataAccess();
		  dataAccess.openConnection();
			dataAccess.executeUpdateQuery(query1);
			dataAccess.closeConnection();
			result.setVisible(true);
	  }
	  
	  }
	  if(e.getSource()==backButton)
	  {
		  setVisible( false );
	    	 MemberGui a = new MemberGui();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
		  
	  }
	  
}
}

}



public class ReserveGui {

 public static void createAndShowGUI(String memberId) {
  
    ReserveGuiWin frame = new ReserveGuiWin(memberId);
    frame.setVisible(true);
    frame.setSize(1000,500);
} 
 }