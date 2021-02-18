package tab2mxl;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class MyFrame extends JFrame{

	//Screens
	JPanel mainContentPanel;
	FileUploadContentPanel fileUploadContentPanel;
	public TextInputContentPanel textInputContentPanel;
	
	MyFrame() {
		
		///////////////////////
		//Main Screen
		///////////////////////
		
		mainContentPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		mainContentPanel.setLayout(new GridBagLayout ());
		
		fileUploadContentPanel = new FileUploadContentPanel();
		c.gridx = 1;
		c.gridx = 1;

		mainContentPanel.add(fileUploadContentPanel, c);

		textInputContentPanel = new TextInputContentPanel();
		c.gridx = 2;
		c.gridx = 2;
		mainContentPanel.add(textInputContentPanel, c);
		
		//Sets the FileChoosers style to the current system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Throwable ex) { }
		
        this.setContentPane(mainContentPanel);
        
        //Set Frame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280,720);
		this.setVisible(true);	
	}
	

// NO LONGER USED
//	public void FileUploadScreen()
//	{
//		mainContentPanel.setVisible(false);
//		mainContentPanel = null;
//		fileUploadContentPanel = new FileUploadContentPanel();
//		this.setContentPane(fileUploadContentPanel);
//	}
//	
//	public void TextInputScreen()
//	{
//		mainContentPanel.setVisible(false);
//		mainContentPanel = null;
//		textInputContentPanel = new TextInputContentPanel();
//		this.setContentPane(textInputContentPanel);		
//	}
//	
//	public void MainContentScreenFromText()
//	{
//		textInputContentPanel = null;
//		mainContentPanel = new MainContentPanel();
//		this.setContentPane(mainContentPanel);
//	}
//	
//	public void MainContentScreenFromUpload()
//	{
//		fileUploadContentPanel = null;
//		mainContentPanel = new MainContentPanel();
//		this.setContentPane(mainContentPanel);
//	}
//	
}
