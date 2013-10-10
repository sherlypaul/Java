package BookMarker;




//Import the libraries
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;


class AdminGui extends JFrame
{
	
	//Private data members

 private Container pane;
 private JLabel wLabel,lLabel,eLabel,pLabel,dLabel;
 private JTextField userName;
 private JPasswordField password;
 private JPanel loginPanel ,advtPanel ,operationPanel,buttonPanel,artPanel,datesInnerPanel;
 Label_TextFieldPanel from ,to; 
 private JRadioButton mCount;
 private JRadioButton iCategoryCount;
 private JRadioButton iCOut;
 private JRadioButton fCount;
 private JRadioButton iMostCOut;
 private JButton ok;
 private JButton cancel;
 private Calendar startDate = Calendar.getInstance();
 private JTextArea result;
	private JScrollPane scrollPane;
 String textarea = " Checkout Count" + "\t" + "ItemNo" + "\t"   +" Title "  +  "\n  " ;
 /**
    Constructs the frame with a label and a control panel.
 */
 public AdminGui()
 {  

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ButtonListener bl = new ButtonListener();
    pane=getContentPane();
    advtPanel = new JPanel();
    buttonPanel=new JPanel();
    artPanel = new JPanel(new BorderLayout());
    ok = new JButton("Submit");
    cancel = new JButton("Cancel");
    // Sets the action commands 
    ok.addActionListener(bl);
    cancel.addActionListener(bl);
    buttonPanel.add(ok);
    buttonPanel.add(cancel);
    artPanel.add(buttonPanel, BorderLayout.SOUTH );
    result =new JTextArea();
	scrollPane = new JScrollPane( result );
	artPanel.add( scrollPane,BorderLayout.CENTER);
	  loginPanel = new JPanel();
	  loginPanel.setBackground(Color.yellow);
	  eLabel = new JLabel("Wrong Username/Password");
	  eLabel.setForeground(Color.RED);
	  wLabel = new JLabel("Select an option");
	  wLabel.setForeground(Color.RED);
	  lLabel = new JLabel("Please enter your login details: User Name -");
	  lLabel.setBackground(Color.yellow);
	  dLabel=new JLabel("        Enter Date in YYYY-MM-DD Format",SwingConstants.CENTER);
	  dLabel.setFont( new Font( "Courier", Font.BOLD, 12 ) );
	  dLabel.setForeground(Color.BLUE);
	  from = new Label_TextFieldPanel("From",false,10);
	  to = new Label_TextFieldPanel("To",false,10);
	  loginPanel.add(lLabel);
	  loginPanel.add(Box.createRigidArea(new Dimension(0,10)));
	  userName = new JTextField(15);
      loginPanel.add(userName);  
	  pLabel = new JLabel(" Password -");
	  pLabel.setBackground(Color.yellow);
	  loginPanel.add(pLabel);
	  loginPanel.add(Box.createRigidArea(new Dimension(0,10)));
	  password = new JPasswordField(15);
    loginPanel.add(password);   
    
    // Dates panel
    datesInnerPanel = new JPanel(new FlowLayout());
    
    datesInnerPanel.add(from);
    datesInnerPanel.add(to);
    init();
    setVisible( true );
 }
 
 //////////////////////////////////////////////////////////////////////////////////

 private void init() {
	
	      getContentPane().setLayout( new BorderLayout() );
	      operationPanel = createRadioButtons();
	      operationPanel.add(Box.createRigidArea(new Dimension(0,20)));
	      operationPanel.add(dLabel);
	      operationPanel.add(datesInnerPanel);
	      pane.add( loginPanel, BorderLayout.NORTH );
	      pane.add( advtPanel, BorderLayout.SOUTH );
	      pane.add( operationPanel, BorderLayout.WEST );
	      pane.add( artPanel, BorderLayout.CENTER );
	      pack();
	   }
 public JPanel createRadioButtons()
 {
	  mCount = new JRadioButton("The total number of members in the BookMarkers library ");
	  iCategoryCount = new JRadioButton("No of items in the library by category");
	  iCOut = new JRadioButton("No of items currently out");
	  fCount = new JRadioButton("Total amount of fines collected in a specific period of time");
	  iMostCOut  = new JRadioButton("Item checked out the most in a year");
	  ButtonGroup group = new ButtonGroup();
    group.add(mCount);
    group.add(iCategoryCount);
    group.add(iCOut);
    group.add(fCount);
    group.add(iMostCOut);
    JPanel panel = new JPanel();
    panel.add( mCount ); 
    panel.add( iCategoryCount );
    panel.add( iCOut );
    panel.add( fCount );
    panel.add(iMostCOut);
    panel.add(eLabel);
    panel.add(wLabel);
    eLabel.setVisible(false);
    wLabel.setVisible(false);
    panel.setBorder(new TitledBorder(new EtchedBorder(), "Please select one of the following actions to find: "));
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    return panel;
 }
 
 protected void buildYearsList(JComboBox yearsList) {

     int currentYear = startDate.get(Calendar.YEAR);

     for (int yearCount = currentYear - 5; yearCount <= currentYear + 5; yearCount++)
         yearsList.addItem(Integer.toString(yearCount));
 }

 /**
  * This method builds the list of months for the start
  * date and end date of the semester
  * @param monthsList The combo box containing the months
  */
 protected void buildMonthsList(JComboBox monthsList) {
        
     monthsList.removeAllItems();
     for (int monthCount = 0; monthCount < 12; monthCount++)
         monthsList.addItem(Months.values()[monthCount]);
 }
 
//Verifies login name and password
 public boolean verifyLogin(){
	   String uName = userName.getText();
	   char[] input = password.getPassword();
	   if(uName.equals("admin") && isPasswordCorrect(input)==true){
          return true;
	   }
	   else{
		   eLabel.setVisible(true);
	   }
	   return false;
 }
 
 private static boolean isPasswordCorrect(char[] input) {
	    boolean isCorrect = true;
	    char[] correctPassword = { '1', '2', '3', '4', '5', '6', '7' };

	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals (input, correctPassword);
	    }
	    Arrays.fill(correctPassword,'0');

	    return isCorrect;
	}
 
 
public class ButtonListener implements ActionListener
{
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==ok && verifyLogin()==true)
		{		
			
	// The total number of members in the BookMarkers library 
			if(mCount.isSelected() )
			{
				
		   				String query1 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[Member] ";
		   			
		   			 
		   			DataAccess dataAccess= new DataAccess();
		   			dataAccess.openConnection();
		   			 ResultSet rs=dataAccess.executeSelectQuery(query1);
		   			int i = 0;
		   			try {
						while(rs.next())
							 {
							      i=rs.getInt("noOfRows");
							      Font labelFont = new Font("Serif", Font.BOLD, 15);
							      result.setFont(labelFont);
							      result.setText("Total number of members in the Library are: "+i);
							    
							 }
		   			}
		   			 					
		   			 catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		   			dataAccess.closeConnection();
		   			
			     
		   		}//end of inner if
			
			
			// The total number of Items by category
			else if(iCategoryCount.isSelected() )
			{
				
					
			   		    String query1 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE category like '" + BCategory.Fiction +"'";
			   		    String query2 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE category like '" + BCategory.Arts +"'";
			   		    String query3 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE category like '" + BCategory.Engineering +"'";
			   		    String query4 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE category like '" + BCategory.Business +"'";
			   		    String query5 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE category like '" + BCategory.Politics +"'";
			   		    String query6 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE category like '" + BCategory.Science +"'";
			   		    String query7 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Video] WHERE category like '" + VCategory.Biography +"'";
			   		    String query8 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Video] WHERE category like '" + VCategory.Entertainment +"'";
			   		    String query9 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Video] WHERE category like '" + VCategory.Documentary +"'";
			   		    String query10 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Video] WHERE category like '" + VCategory.History +"'";
			   			DataAccess dataAccess= new DataAccess();
			   			dataAccess.openConnection();
			   			ResultSet r1=dataAccess.executeSelectQuery(query1);
			   			ResultSet r2=dataAccess.executeSelectQuery(query2);
			   			ResultSet r3=dataAccess.executeSelectQuery(query3);
			   			ResultSet r4=dataAccess.executeSelectQuery(query4);
			   			ResultSet r8=dataAccess.executeSelectQuery(query5);
			   			ResultSet r9=dataAccess.executeSelectQuery(query6);
			   			ResultSet v1=dataAccess.executeSelectQuery(query7);
			   			ResultSet v2=dataAccess.executeSelectQuery(query8);
			   			ResultSet v3=dataAccess.executeSelectQuery(query9);
			   			ResultSet v4=dataAccess.executeSelectQuery(query10);
			   			int cf= 0,ca= 0,ce= 0,cb= 0,cp= 0,cs = 0;
			   		    int vb=0,ve=0,vd=0,vh=0;
			   			try {
							while(r1.next())
								 {
								     cf=r1.getInt("noOfRows");
								     System.out.println(cf);
								 }
							while(r2.next())
							 {
							     ca=r2.getInt("noOfRows");
							     System.out.println(ca);
							 }
							while(r3.next())
							 {
							     ce=r3.getInt("noOfRows");
							     System.out.println(ce);
							 }
						    while(r4.next())
						     {
						         cb=r4.getInt("noOfRows");
						         System.out.println(cb);
						     }
						  
				          while(r8.next())
				             {
				                 cp=r8.getInt("noOfRows");
				                 System.out.println(cp);
				              }
				          while(r9.next())
				             {
				                 cs=r9.getInt("noOfRows");
				                 System.out.println(cs);
				              }
							while(v1.next())
							 {
							     vb=v1.getInt("noOfRows");
							     System.out.println(vb);
							 }
						    while(v2.next())
						     {
						         ve=v2.getInt("noOfRows");
						         System.out.println(ve);
						     }
						    while(v3.next())
						     {
						         vd=v3.getInt("noOfRows");
						         System.out.println(vd);
						     }
						    while(v4.next())
						     {
						         vh=v4.getInt("noOfRows");
						         System.out.println(vh);
						     }


				          JFrame f = new JFrame();
				          f.setLayout(getLayout());
				          f.setSize(400, 300);
				          double[] values = new double[10];
				          String[] names = new String[10];
				          values[0] = cf;
				          names[0] = "BOOKS - Fiction";

				          values[1] = ca;
				          names[1] = "Arts";

				          values[2] = ce;
				          names[2] = "Engineering";
				          values[3] = cb;
				          names[3] = "Business";
				          values[4] = cp;
				          names[4] = "Politics";

				          values[5] = cs;
				          names[5] = "Science";
				          
				          values[6] = vb;
				          names[6] = "VIDEOS -Biography";

				          values[7] = ve;
				          names[7] = "Entertainment";
				          
				          values[8] = vd;
				          names[8] = "Documentary";

				          values[9] = vh;
				          names[9] = "History";
				         f.getContentPane().add(new ChartPanel(values, names, "Items in each Category"));
				         f.setSize(1000,500);
				         f.setVisible(true);
			   	     }// end of try				
			   			 catch (SQLException e1) {
							e1.printStackTrace();
					}//end of catch
			   			dataAccess.closeConnection();
				}//end of else if
	        
			
			
			
	   	
			else if(fCount.isSelected() )
			{
				System.out.println(" fCount selected");
				String d1=from.getTextValue();
				String d2=to.getTextValue();
				String query1 = "Select SUM(Fee) as fee From [BookMarker].[dbo].[STransaction] Where CheckoutDate>='" + d1+ "' AND CheckOutDate<=' "+ d2 +"'";
	   			DataAccess dataAccess= new DataAccess();
	   			dataAccess.openConnection();
	   			ResultSet r1=dataAccess.executeSelectQuery(query1);
	   			float tF=0;
	   			try {
					while(r1.next())
						 {
						     tF = r1.getFloat("fee");  
						 }
					Font labelFont = new Font("Serif", Font.BOLD, 15);
					result.setFont(labelFont);
				    result.setText("  Total amount of fine in the Library is: "+tF);					
				    
				      }//end of try
	   			   catch (SQLException e1) {
					e1.printStackTrace();
				}//end of catch
			dataAccess.closeConnection();
	        }
			
			else if(iMostCOut.isSelected() )
			{
				System.out.println(" iMostCOut selected");
				String d1=from.getTextValue();
				String d2=to.getTextValue();
				String query1 =" Select Count(*) as CheckOutCount, A.itemNo, C.title from [BookMarker].[dbo].[STransaction] as A JOIN [BookMarker].[dbo].[Video] as C ON A.itemno=C.itemCode  Where A.returnDate IS NULL AND A.checkoutdate>='"+ d1 +"' AND A.checkoutdate<='"+d2+"' Group By A.itemNo, C.title order by CheckOutCount desc";
	   		    String query2 =" Select Count(*) as CheckOutCount, A.itemNo, B.title from [BookMarker].[dbo].[STransaction] as A JOIN [BookMarker].[dbo].[Book] as B ON A.itemno=B.itemCode  Where A.returnDate IS NULL AND A.checkoutdate>='"+ d1 +"' AND A.checkoutdate<='"+d2+"' Group By A.itemNo, B.title order by CheckOutCount desc";
	   		    
	   			DataAccess dataAccess= new DataAccess();
	   			dataAccess.openConnection();
	   			ResultSet r1=dataAccess.executeSelectQuery(query1);
	   			ResultSet r2=dataAccess.executeSelectQuery(query2);
	   			//end of try
			try{
				while(r1.next())
				{
					textarea = textarea + r1.getInt("CheckOutCount") +"\t"+ r1.getString("itemNo")+"\t" + r1.getString("title")+"\n";
				}
				while(r2.next())
				{
					textarea = textarea + r2.getInt("CheckOutCount") +"\t"+ r2.getString("itemNo")+"\t" + r2.getString("title")+"\n";
				}
				System.out.print(textarea);
				result.setText(textarea);
				}
			
				    catch (SQLException e1) {
						e1.printStackTrace();
					}//end of catch
			
		   			dataAccess.closeConnection();
				
			}//end of else if
			
			else if(iCOut .isSelected() )
			{
				String b="BORROWED";
				String query1 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Book] WHERE stage = '" + b +"'";
				String query2 ="SELECT  count(*)as noOfRows FROM [BookMarker].[dbo].[Video] WHERE stage ='" + b +"'";
				DataAccess dataAccess= new DataAccess();
	   			dataAccess.openConnection();
	   			ResultSet r1=dataAccess.executeSelectQuery(query1);
	   			ResultSet r2=dataAccess.executeSelectQuery(query2);
	   			int bo= 0,vi= 0;
	   			try {
					while(r1.next())
						 {
						     bo=r1.getInt("noOfRows");
						     
						     System.out.println(bo);
						 }
					while(r2.next())
					 {
					     vi=r2.getInt("noOfRows");
					     
					     System.out.println(vi);
					 }
					Font labelFont = new Font("Serif", Font.BOLD, 13);
					  result.setFont(labelFont);
				      result.setText("  Total number of checked out books & videos in the Library are: "+bo +" & "+ vi+ "respectively");
	   			}// end of try
	   		 catch (SQLException e1) {
					e1.printStackTrace();
				}//end of catch
	   			dataAccess.closeConnection();
			}// end of else if
			else 
			{
				wLabel.setVisible(true);
			}
		}//end of outer if
		else if(e.getSource()==cancel){
	    	 setVisible( false );
	    	 Home a = new Home();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
		     }	
		
		
		}//action performed
	}//buttonlistner
}//adminGui