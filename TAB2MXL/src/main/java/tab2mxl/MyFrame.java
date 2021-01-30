package tab2mxl;

import javax.swing.*;

import java.awt.GridLayout;
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
		mainContentPanel = new JPanel();
		mainContentPanel.setLayout(new GridLayout(1, 2));
		
		JPanel leftPanel = new TextInputContentPanel();
		JPanel RightPanel = new  FileUploadContentPanel();
		
		mainContentPanel.add(RightPanel);
		mainContentPanel.add(leftPanel);
		
		//Sets the FileChoosers style to the current system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Throwable ex) { }
		
        this.setContentPane(mainContentPanel);		
		
        //Set Frame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(852,480);
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
