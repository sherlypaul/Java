package BookMarker;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.awt.Color;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
class TransactionHistoryGui extends JFrame {
	
	private JButton okButton,backButton;
	private JTable table;
	private JScrollPane scrollPane;
    private BannerPanel banner;
    JPanel leftPanel,datesPanel;
    Label_TextFieldPanel memberId,from ,to;
    Label_TextFieldPanel month; 
    private JComboBox startYear, startMonth;
    private Calendar startDate = Calendar.getInstance();
    private ButtonListener bl ;
    private JLabel dLabel;
    private JTextArea result;
    String textArea = "Item Borrowed" + "\t" +"Borrow-Date"+"\t" + "Return Date" +"\t" +  "Fine Paid" + "\n"; 
/////////////////////////////////////////////////////////////////////////////////////////////////////////    



    public TransactionHistoryGui(String  memberId1){
        setLayout(new BorderLayout());
        Container contentPane = getContentPane();
        bl = new ButtonListener();
        banner = new BannerPanel("Transaction History");
        contentPane.add(banner,BorderLayout.PAGE_START);
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.yellow);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        memberId = new Label_TextFieldPanel("Member-id",false,10);
        memberId.setText( memberId1 );
        leftPanel.add(memberId);      
        contentPane.add(leftPanel,BorderLayout.LINE_START);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        JPanel buttonRow = new JPanel();
        okButton = new JButton("VIEW HISTORY");
        backButton = new JButton("BACK");    
        buttonRow.add(okButton);
        buttonRow.add(backButton);
        okButton.addActionListener(bl);
        backButton.addActionListener(bl);
        JPanel datesPanel = new JPanel();
        JPanel datesInnerPanel = new JPanel(new FlowLayout());
    	from = new Label_TextFieldPanel("From",false,10);
      	to = new Label_TextFieldPanel("To",false,10);
        datesInnerPanel.add(from);
        datesInnerPanel.add(to);
        dLabel=new JLabel("        Enter Date in YYYY-MM-DD Format",SwingConstants.CENTER);
    	dLabel.setFont( new Font( "Courier", Font.BOLD, 12 ) );
    	 dLabel.setForeground(Color.BLUE);
    	 datesPanel.add(dLabel, BorderLayout.NORTH);
        datesPanel.add(datesInnerPanel, BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(datesPanel);
		result =new JTextArea();
		scrollPane = new JScrollPane( result );
		infoPanel.add(topPanel); 
		infoPanel.add( scrollPane); 
        infoPanel.add(buttonRow);  
        JPanel middlePanel  = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
        middlePanel.add(infoPanel);
        contentPane.add(middlePanel,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();    
            
        setVisible(true);
    }
   
    public class ButtonListener implements ActionListener
    {
    	
    	public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==okButton )
    		{	
    			
    			 String from1 = from.getTextValue().trim();
       	  		 String to1 = to.getTextValue().trim();
       	  	String member = memberId.getTextValue().trim();
       	  	
       		String query2 = "SELECT * FROM [BookMarker].[dbo].[STransaction] where memberId = '" + member +"' and (checkoutDate > '" + from1 + "' and checkoutDate < '" + to1 +"' or returnDate > '"+ from1 + "' and returnDate <  '" + to1 +"')";
       			DataAccess dataAccess= new DataAccess();
       				dataAccess.openConnection();
       				 ResultSet rs=dataAccess.executeSelectQuery(query2);
       				 try
       				 {
       					 
       				while(rs.next())
       	   	   	   	{	
       					String a =  rs.getString("itemNo");
  					 if(a== null)
  					 {
  						 a=" ";
  					 }
  					 String b=rs.getString("checkoutDate");
  					 if(b==null)
  					 {
  						 b =" ";
  					 }
  					 String c=rs.getString("returnDate");
  					 if(c==null)
  					 {
  						  c = " ";
  					 }
  					String d= rs.getString("fee");
  					if(d==null)
  					{
  					 d=" ";
  					}
       					textArea = textArea+ a + "\t" +  b+ "\t"  + c+ "\t" + d + "\n" ;
       	   	   	   	}
       				
       				dataAccess.closeConnection();
       				
       				 }
       				catch (SQLException e1) {
       					// TODO Auto-generated catch block
       					e1.printStackTrace();
       				}
       				result.setText(textArea);
    		}
    		else if (e.getSource()==backButton ){
    			setVisible(false);
    			MemberGui m =new MemberGui();
    			m.setSize(1000,500);
    			
    		}
    	}
    	
    }

     
   }