package tab2mxl;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.Graphics;  
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;  

public class MyFrame extends JFrame{

	//Screens
	JPanel mainContentPanel;
	FileUploadContentPanel fileUploadContentPanel;
	public TextInputContentPanel textInputContentPanel;
	Color frameColour = new Color(0,90,108);
	
	MyFrame() {
		
		///////////////////////
		//Main Screen
		///////////////////////
		
		mainContentPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		mainContentPanel.setLayout(new GridBagLayout ());
		mainContentPanel.setOpaque(false);
		
		fileUploadContentPanel = new FileUploadContentPanel();
		c.gridx = 1;
		c.gridx = 1;

		mainContentPanel.add(fileUploadContentPanel, c);

		textInputContentPanel = new TextInputContentPanel();
		c.gridx = 2;
		c.gridx = 2;
		mainContentPanel.add(textInputContentPanel, c);
		
		ImagePanel bg = new ImagePanel(getBackgroundImage().getImage());
		bg.add(mainContentPanel);
		
		//Sets the FileChoosers style to the current system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Throwable ex) { }
		
		this.setContentPane(bg);
		 this.setLayout(new GridBagLayout());
         
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280,720);
		this.setResizable(false);
		this.setTitle("TAB2MXL");
		this.setLocationRelativeTo(null); //Open in center of screen
		this.setVisible(true);	

	}
	
	private ImageIcon getBackgroundImage() {
		ImageIcon backgroundImage = new ImageIcon("bg4.jpg");
		Image image = backgroundImage.getImage(); // transform it 
		Image newImage = image.getScaledInstance(1280, 720,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		backgroundImage = new ImageIcon(newImage);  // transform it back
		return backgroundImage;
	}
	
	class ImagePanel extends JPanel{
		private Image img;

		  public ImagePanel(String img) {
		    this(new ImageIcon(img).getImage());
		  }

		  public ImagePanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }

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
