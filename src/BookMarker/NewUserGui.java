package BookMarker;import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SpringLayout; 


//Import Java Libraries


import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



//  Class constructor

//Import Java Libraries


import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



//  Class constructor

class NewUserGui extends JFrame
{

// Data members
   private Container pane;
   private JLabel nameLabel,addressLabel,phLabel;
   private JLabel mLabel,eLabel,eLabel1;
   private JTextField name,address,phNo,email;
   private JPanel dPanel ,advtPanel,buttonPanel;
   private JButton ok,cancel,payFee;

   /**
      Constructs the frame with a labels,buttons and a control panel.
   */
   
   public NewUserGui()
   {  
	  
	  setTitle( "Bookmarker's Library Manager" );
	  setSize( 1000, 500 );
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ButtonListener bl = new ButtonListener();
      pane=getContentPane();
      advtPanel = new JPanel();
      buttonPanel=new JPanel();
      ok = new JButton("Submit");
      cancel = new JButton("Cancel");
      payFee = new JButton("Pay Fee");
      payFee.setEnabled(false);
      ok.addActionListener(bl);
      payFee.addActionListener(bl);
      cancel.addActionListener(bl);
      buttonPanel.add(ok);
      buttonPanel.add(cancel);
      buttonPanel.add(payFee);
  	  dPanel = new JPanel();
	  nameLabel= new JLabel("Name : ");
	  dPanel.add(nameLabel); 
  	  name = new JTextField(15);
      dPanel.add(name); 
	  addressLabel= new JLabel("Address : ");
	  dPanel.add(addressLabel); 
  	  address = new JTextField(15);
      dPanel.add(address);    
	  phLabel= new JLabel("Phone No : ");
	  dPanel.add(phLabel); 
  	  phNo = new JTextField(15);
      dPanel.add(phNo); 
	  mLabel= new JLabel("Email-id : ");
	  dPanel.add(mLabel); 
  	  email = new JTextField(15);
      dPanel.add(email);
      dPanel.setBorder(new TitledBorder(new EtchedBorder(), "Please fill in the following details : "));
      dPanel.setLayout(new GridLayout(0,2));
      eLabel= new JLabel("Please pay Registration fees $10");
      dPanel.add(eLabel);
      eLabel.setVisible(false);
      eLabel1= new JLabel("Congratulations! You have been succesfully registered ");
      dPanel.add(eLabel1);
      eLabel1.setVisible(false); 
      init();
      setVisible( true );
   }

   //////////////////////////////////////////////////////////////////////////////////
 // adds container
   private void init() {
	      getContentPane().setLayout( new BorderLayout() );
	      pane.add( dPanel, BorderLayout.WEST );
	      pane.add( advtPanel, BorderLayout.SOUTH );
	      pane.add( buttonPanel, BorderLayout.CENTER );
	      pack();
	   }
   
   
 // Address Validation
   
   public boolean verifyAdd(){
	   
	   String uName = address.getText().trim().toUpperCase();
	   if(!address.getText().trim().equals("")){
	   if(uName.equals("SPINGFIELD") ){
            return true;
	   }
	   else{
		   
		   eLabel.setVisible(true);
		   payFee.setEnabled(true);
		   return false;
	   }
	   }
	   return false;
   }
   

// Button Listener Events :
public class ButtonListener implements ActionListener
{
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==ok && verifyAdd()==true)
		{		
			DataAccess dataAccess= new DataAccess();
			String uName = name.getText().trim();
			String uPhNo= phNo.getText().trim();
			String uEmail =email.getText().trim();
			String uAddr = address.getText().trim();
			
   			String query1 = "INSERT INTO [BookMarker].[dbo].[Member](name,emailId,phoneNo,city) VALUES('" + uName+"','"+ uEmail +"','" + uPhNo +"','" +  uAddr +"')";
   			dataAccess.openConnection();
   			dataAccess.executeInsertQuery(query1);
   			query1="SELECT top 1 MemberCode from [BookMarker].[dbo].[Member] order by memberId desc";
   			ResultSet rs=dataAccess.executeSelectQuery(query1);
   			String memberId ="";
  			try {
				while(rs.next())
					 {
					 memberId=rs.getString("MemberCode");
					 }
  			} catch (SQLException e1) {
				e1.printStackTrace();
			}
   			dataAccess.closeConnection();
   			eLabel1.setText("Congratulations! You have been succesfully registered. Your MemberId is: " + memberId  );
   		    eLabel1.setVisible(true);
			}
		
			
		else if(e.getSource()==cancel )
		{		
	    	 setVisible( false );
	    	 Home a = new Home();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
		}
		
		else if (e.getSource()==payFee){
			
			DataAccess dataAccess= new DataAccess();
			String uName = name.getText().trim();
			String uPhNo= phNo.getText().trim();
			String uEmail =email.getText().trim();
			String uAddr = address.getText().trim();
			
   			String query1 = "INSERT INTO [BookMarker].[dbo].[Member](name,emailId,phoneNo,city) VALUES('" + uName+"','"+ uEmail +"','" + uPhNo +"','" +  uAddr +"')";
   			dataAccess.openConnection();
   			dataAccess.executeInsertQuery(query1);
   			query1="SELECT top 1 MemberCode from [BookMarker].[dbo].[Member] order by memberId desc";
   			ResultSet rs=dataAccess.executeSelectQuery(query1);
   			String memberId ="";
  			try {
				while(rs.next())
					 {
					 memberId=rs.getString("MemberCode");
					 }
  			} catch (SQLException e1) {
				e1.printStackTrace();
			}
   			dataAccess.closeConnection();
   			eLabel1.setText("Congratulations! You have been succesfully registered. Your MemberId is: " + memberId  );
   		    eLabel1.setVisible(true);
			
		}
		}
	}
}

