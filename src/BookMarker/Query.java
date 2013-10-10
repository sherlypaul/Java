package BookMarker;


//Imports
//Imports
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



class Query extends JFrame
{
	// Instance attributes used in this example
	private	JPanel		topPanel;
	private JPanel      advPanel;
	private	JTable		table;
	private	JScrollPane scrollPane;
	private JButton back;
	
	 
	/*public void buildData( )
	 {
		 try {
			 int i=0;
			while(rs.next())
			 {
				int j=0;
						String title = rs.getString("title");
						dataValues[i][j] = title;
						j++;
					String name = rs.getString("author");
					dataValues[i][j] = name;
					j++;
					String stage = rs.getString("stage") ; 
					dataValues[i][j] = stage;
					j++;
					String location = rs.getString("location");
					dataValues[i][j] = location; 
					
					i++;
					
			 }
			/*for(i=0;i<500;i++)
			{
				for (j=0;j<4;j++)
				{
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }*/

	// Constructor of main frame
	public void QuerySearch(String[][] dataValues)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set the frame characteristics
		setTitle( "Bookmarker's Library Manager" );
		//setSize( 1000, 500 );
		setBackground( Color.gray );

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );
		
		// Create columns names
		String columnNames[] = { "Title", "Author", "Type" ,"Location", "Code"};

		// Create some data
		/*String dataValues[][] =
		{
			{ "12", "234", "67" ,""},
			{ "-123", "43", "853","" },
			{ "93", "89.2", "109","" },
			{ "279", "9033", "3092","" },
		
		};*/
		 

		// Create a new table instance
		table = new JTable( dataValues, columnNames );
		back=new JButton("Home");
		ButtonListener b = new ButtonListener();
   back.addActionListener(b);
		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		topPanel.add( scrollPane, BorderLayout.CENTER );
		topPanel.add(back,BorderLayout.SOUTH);
		setVisible( true );
	}
	
	public class ButtonListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==back )
			{		
				
				
		    	 setVisible( false );
		    	 Home a = new Home();
		   	     a.setTitle( "Bookmarker's Library Manager" );
			     a.setSize( 1000, 500 );
			}
		}
	}
	}
