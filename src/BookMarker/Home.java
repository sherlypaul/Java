package BookMarker;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

//import BookMarker.MemberGui.ButtonListener;



//import GUI.event;


/**
   This is the GUI for an application that helps customers find a house of their choice.
   This frame represents a window to enter a user's
   choices for the location, area and energy options for a house.
*/

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

/**
   This is the Start form which gives selections to the users
*/
class Home extends JFrame
{
 
   private Container pane;
   private JLabel label,label1,advLabel ;
   private JPanel menuPanel ,advtPanel ,operationPanel;
   private JButton ok;
   String[][] dataValue;
 
   private Label_TextFieldPanel itemNo,author,title;

    String selected = "Selected ";
   /**
      Constructs the frame with a labels, menubars and a control panel.
   */
   public Home()
   {  
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pane=getContentPane();
      menuPanel = new JPanel( new GridLayout(0, 1,0,2) );
      advtPanel = new JPanel();
      operationPanel= new JPanel( new FlowLayout() );
      advLabel = new JLabel( "  New additions to the Library are:A Brief History of Time: Stephen Hawking ,The God Delusion :Richard Dawkins    ");
	  advLabel.setFont( new Font( "Courier", Font.BOLD, 19 ) );
	  advLabel.setForeground(Color.blue);
	  advtPanel.add(advLabel);
      ok = new JButton("Search");
      title = new Label_TextFieldPanel("Title",false,10);
      author =new Label_TextFieldPanel("Author/Director",false,10);
      itemNo =new Label_TextFieldPanel("Item No",false,10);
      label1 = new JLabel("Search Item:");
      ButtonListener bl = new ButtonListener();
      ok.addActionListener(bl);
      init();
      setVisible( true );
   }

   //////////////////////////////////////////////////////////////////////////////////
 
   private void init() {
	      Font f = null;
	      getContentPane().setLayout( new BorderLayout() );
	      title.setFont( f = new Font( "Dialog", Font.BOLD, 12 ) );
	      author.setFont( f );
	      itemNo.setFont( f );
	      operationPanel.add( label1 );
	      operationPanel.add( itemNo ); operationPanel.add( author );
	      operationPanel.add( title );operationPanel.add(ok);
	      menuPanel.setFont( f );
	  	  JMenuBar menuBar = new JMenuBar();	
	      menuBar.setBackground( Color.RED );
	      // Create a menu
	      JMenu menu1 = new JMenu("User");
	      menu1.setBackground(Color.PINK);
	      menuBar.add(menu1);
	      // Create a menu item
	      JMenuItem item1 = new JMenuItem("Admin");
	      item1.addActionListener(new MenuActionListener());
	      menu1.add(item1);
	      JMenuItem item2 = new JMenuItem("Member");
	      item2.addActionListener(new MenuActionListener());
	      menu1.add(item2);
	      JMenuItem item3 = new JMenuItem("New User?");
	      item3.addActionListener(new MenuActionListener());
	      menu1.add(item3);
	      label = new JLabel("Welcome to BookMarker's Library",SwingConstants.CENTER);
	      label.setForeground(Color.BLUE);
	      Font labelFont = new Font("Serif", Font.BOLD, 26);
	      label.setFont(labelFont);
	      menuPanel.add(label);
	  	  setJMenuBar(menuBar); 	
	  	  menuPanel.add(menuBar);
	      pane.add( menuPanel, BorderLayout.NORTH );
	      pane.add( advtPanel, BorderLayout.SOUTH );
	      pane.add( operationPanel, BorderLayout.CENTER );
	      
	      ActionListener listener = new ActionListener(){
		 	  public void actionPerformed(ActionEvent event){
		 	    String oldText = advLabel.getText();
		 	    String newText = oldText.substring(1) + oldText.substring(0, 1);
		 	     advLabel.setText( newText );
		 	  }
		   }; 
		   javax.swing.Timer timer = new javax.swing.Timer(200, listener);
		   timer.start();
	      pack();
	   }




   public class ButtonListener implements ActionListener
   {
   	
   	public void actionPerformed(ActionEvent e)
   	{
   		
   			if(e.getSource()==ok)
   		{	
   				String bookTitle;
   				String name;
   				String item;
   				Boolean orFlag = false;
   				String query1 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[Book] ";		
   				String query2 ="SELECT * FROM [BookMarker].[dbo].[Book] ";
   				String query3 = "SELECT count(*) as noOfRows FROM [BookMarker].[dbo].[Video] ";
   				String query4 ="SELECT * FROM [BookMarker].[dbo].[Video] ";
   				
   				if(!title.getTextValue().trim().equals("")||!author.getTextValue().trim().equals("")||!itemNo.getTextValue().trim().equals(""))
   				{
   					query1 += " where ";
   					query2 += " where ";
   					query3 += " where ";
   					query4 += " where ";
   					
   				}
   				
   		if(!title.getTextValue().trim().equals("")){
   			bookTitle=title.getTextValue().trim();
   			System.out.println(bookTitle);
   			bookTitle = "%"+ bookTitle + "%";
   			System.out.println(bookTitle);
   			 query1+= " title like '" + bookTitle +"' ";
   			query2+= " title like '" + bookTitle +"' ";
   			query3+= " title like '" + bookTitle +"' ";
   			query4+= " title like '" + bookTitle +"' ";
   			orFlag=true;
   					 
   		}
   		if(!author.getTextValue().trim().equals("")){
   			 name =author.getTextValue().trim();
   			name = "%"+name +"%";
   			if (orFlag)
   			{
   				query1 += " or ";
   				query2 += " or ";
   				query3 += " or ";
   				query4 += " or ";
   				
   			}
   			query1 +=" author like '" + name + "' "; 
   			query2 +=" author like '" + name + "' "; 
   			query3 +=" director like '" + name + "' "; 
   			query4 +=" director like '" + name + "' "; 
   			orFlag =true;
   			}
   		if(!itemNo.getTextValue().trim().equals("")){
   			item =itemNo.getTextValue().trim();
   			item = "%"+item +"%";
  			if (orFlag)
  			{
  				query1 += " or ";
  				query2 += " or ";
  				query3 += " or ";
  				query4 += " or ";
  				
  			}
  			query1 +=" itemCode like '" + item + "'";
  			query2 +=" itemCode like '" + item + "'"; 
  			query3 +=" itemCode like '" + item + "'";
  			query4 +=" itemCode like '" + item + "'"; 
  			}
   			 
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
   			 dataValue= new String[i+j][5];
   			rs  =dataAccess.executeSelectQuery(query2);
   			i=0;
   			while(rs.next())
				 {
				   
				j=0;
				dataValue[i][j] = rs.getString("title");
				
				j++;
				dataValue[i][j] = rs.getString("author");
			
			j++;
			dataValue[i][j] = rs.getString("category") ; 

			j++;
			dataValue[i][j] = rs.getString("location");
 
			j++;
			dataValue[i][j] = rs.getString("itemCode");
			i++;
			
				 }
   			rs  =dataAccess.executeSelectQuery(query4);
   			while(rs.next())
			 {		
			j=0;
			dataValue[i][j] = rs.getString("title");
			
			j++;
			dataValue[i][j] = rs.getString("director");
		
		j++;
		dataValue[i][j] = rs.getString("category") ; 
		
		j++;
		dataValue[i][j] = rs.getString("location");
		 
		j++;
		dataValue[i][j] = rs.getString("itemCode");
		
		i++;		
			 }
   			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   			dataAccess.closeConnection();
   			setVisible( false );
	    	 Query searchBook = new Query();
	    	 searchBook.QuerySearch(dataValue);
	   	     searchBook.setTitle( "Bookmarker's Library Manager" );
		     searchBook.setSize( 1000, 500 );
   		}
   	}
   }
   


   
   class MenuActionListener implements ActionListener {
	   public void actionPerformed(ActionEvent e) {
	     selected = e.getActionCommand();
	     if (selected.equals( "Admin" )) {
	    	 setVisible( false );
	    	 AdminGui a = new AdminGui();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
	       }
	     else if (selected.equals( "Member" )) {
	    	 setVisible( false );
	    	 MemberGui a = new MemberGui();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
	   }
	     else if (selected.equals( "New User?" )) {
	    	 setVisible( false );
	    	 NewUserGui a = new NewUserGui();
	   	     a.setTitle( "Bookmarker's Library Manager" );
		     a.setSize( 1000, 500 );
	    	
		   }
	   }
   }
   

}