package BookMarker;





// Java Libraries

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


class MemberGui extends JFrame
{

	// Data-members
	
   private Container pane;
   private JLabel wLabel;
   private JLabel lLabel;
   private JLabel eLabel;
   private JTextField memberId;
   private JPanel idPanel ,advtPanel ,operationPanel,buttonPanel,aPanel;
   private JRadioButton rvBook;
   private JRadioButton coBook;
   private JRadioButton rtnBook;
   private JRadioButton fPay;
   private JRadioButton tHist;
   private JButton ok;
   private JButton cancel;
    public String memberId1 ="";
   /**
      Constructs the frame with a label and a control panel.
   */
   public MemberGui()
   {  
	
	  setTitle( "Bookmarker's Library Manager" );
	  setSize( 1000, 500 );
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ButtonListener bl = new ButtonListener();
      pane=getContentPane();
      advtPanel = new JPanel();
      buttonPanel=new JPanel();
      aPanel=new JPanel(new BorderLayout());
      aPanel.add(buttonPanel, BorderLayout.SOUTH );
      ok = new JButton("Submit");
      cancel = new JButton("Cancel");
      ok.addActionListener(bl);
      cancel.addActionListener(bl);
      buttonPanel.add(ok);
      buttonPanel.add(cancel);
  	  idPanel = new JPanel();
  	  idPanel.setBackground(Color.yellow);
  	  eLabel = new JLabel("Wrong Member-id");
  	  eLabel.setForeground(Color.RED);
  	  wLabel = new JLabel("Select an option");
  	  wLabel.setForeground(Color.RED);
  	  lLabel = new JLabel("Please enter your Member id -");
  	  lLabel.setBackground(Color.yellow);
	  idPanel.add(lLabel);
	  idPanel.add(Box.createRigidArea(new Dimension(0,10)));
  	  memberId = new JTextField(15);
      idPanel.add(memberId);     
      init();
      setVisible( true );
      
   }

   //////////////////////////////////////////////////////////////////////////////////
 
   private void init() {
	      getContentPane().setLayout( new BorderLayout() );
	      operationPanel = createRadioButtons();
	      pane.add( idPanel, BorderLayout.NORTH );
	      pane.add( advtPanel, BorderLayout.SOUTH );
	      pane.add( operationPanel, BorderLayout.WEST );
	      pane.add( aPanel, BorderLayout.CENTER );
	      
	      pack();
	   }
   public JPanel createRadioButtons()
   {
	  rvBook = new JRadioButton("Reserve an item  ");
	  coBook = new JRadioButton("Check out an item");
	  rtnBook = new JRadioButton("Return an item");
	  fPay = new JRadioButton("Pay the fines");
	  tHist  = new JRadioButton("Query for the Transaction history:");
      ButtonGroup group = new ButtonGroup();
      group.add(rvBook);
      group.add(coBook);
      group.add(rtnBook);
      group.add(fPay);
      group.add(tHist);
      JPanel panel = new JPanel();
      panel.add( rvBook ); 
      panel.add( coBook );
      panel.add( rtnBook );
      panel.add( fPay );
      panel.add(tHist);
      panel.add(eLabel);
      panel.add(wLabel);
      eLabel.setVisible(false);
      wLabel.setVisible(false);
      panel.setBorder(new TitledBorder(new EtchedBorder(), "Perform one of the following actions : "));
      panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
      return panel;
   }
 
   public boolean verifyId(){
	   String uName = memberId.getText().trim().toUpperCase();
	   DataAccess dataAccess= new DataAccess();
	   dataAccess.openConnection();
	   String query1="SELECT MemberCode from [BookMarker].[dbo].[Member] where MemberCode= '" + uName +"' ";
			ResultSet rs=dataAccess.executeSelectQuery(query1);
			int i=0;
			try {
			while(rs.next())
				 {
				 memberId1=rs.getString("MemberCode");
				 i=1;
				 }
			} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   if(i==1){
            return true;
	   }
	   else{
		   eLabel.setVisible(true);
	   }
	   return false;
   }
   

   
public class ButtonListener implements ActionListener
{
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==ok && verifyId()==true)
		{		
			
			if(rvBook.isSelected() && verifyId()==true )
			{
				setVisible(false);
				System.out.println("rvBook selected");
				ReserveGui member = new ReserveGui();
				member.createAndShowGUI(memberId1);
	        }
			else if(coBook.isSelected() && verifyId()==true)
			{
	
				setVisible(false);
				CheckOutGui member = new CheckOutGui(memberId1);
				member.setVisible(true);
				member.setSize(1000,500);
	
				

	        }
			else if(rtnBook.isSelected() && verifyId()==true)
			{
				setVisible(false);
				System.out.println("rtnBook selected");
				ReturnGui member = new ReturnGui(memberId1);
				member.setVisible(true);
				member.setSize(1000,500);

	        }
			else if(fPay.isSelected() && verifyId()==true)
			{
				setVisible(false);
				System.out.println(" fPay selected");
				PayFineGui member = new PayFineGui(memberId1);
                member.setSize(1000,500);
	        }
			else if(tHist.isSelected() && verifyId()==true)
			{
				setVisible(false);
				System.out.println("tHist selected");
				TransactionHistoryGui member = new TransactionHistoryGui(memberId1);
                member.setSize(1000,500);

	        }
			else {
				wLabel.setVisible(true);
			}
		}
		else if(e.getSource()==cancel){
	    	 setVisible( false );
	    	 Home a = new Home();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
		}
	}
}
}