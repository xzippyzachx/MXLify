package tab2mxl;

import javax.swing.*;
import java.awt.event.*;

public class MyFrame extends JFrame{

	//Screens
	JPanel mainContentPanel;
	JPanel fileUploadContentPanel;
	JPanel textInputContentPanel;
	
	MyFrame() {	
				
		///////////////////////
		//Main Screen
		///////////////////////
		mainContentPanel = new MainContentPanel();
        this.setContentPane(mainContentPanel);		
		
        //Set Frame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(852,480);
		this.setVisible(true);
			
		//Sets the FileChoosers style to the current system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Throwable ex) { }
	}
	
	
	public void FileUploadScreen()
	{
		mainContentPanel.setVisible(false);
		mainContentPanel = null;
		fileUploadContentPanel = new FileUploadContentPanel();
		this.setContentPane(fileUploadContentPanel);
	}
	
	public void TextInputScreen()
	{
		mainContentPanel.setVisible(false);
		mainContentPanel = null;
		textInputContentPanel = new TextInputContentPanel();
		this.setContentPane(textInputContentPanel);		
	}
	
}
