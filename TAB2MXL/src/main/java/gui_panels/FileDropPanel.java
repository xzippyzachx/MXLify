package gui_panels;

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

import gui.DragDropListener;

@SuppressWarnings("serial")
public class FileDropPanel extends JPanel {
	
	public static String dropFilePath = "";
	JPanel dropPanel;
	JLabel dropLabel;
	JLabel dropLoc;

	public FileDropPanel(){

		// Main Panel for Drop Elements
		dropPanel = new JPanel();
		dropPanel.setLayout(new BoxLayout(dropPanel, BoxLayout.Y_AXIS));// sets layout to vertical
		Border DropPadding = BorderFactory.createEmptyBorder(0, 0, 0, 10);
		dropPanel.setBorder(DropPadding);
		
		//Title Label -----------------------------------------------------------
		dropLabel = new JLabel("<html><body style='text-align: center'>Drop Tablature Text File");				
		
		dropLabel.setHorizontalAlignment(JLabel.CENTER);
		
		dropLabel.setHorizontalTextPosition(JLabel.CENTER);
		dropLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		Border padding = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		dropLabel.setBorder(padding);
		dropLabel.setForeground(new Color(224,224,224));
		dropLabel.setBackground(new Color(33,150,243));
		dropLabel.setOpaque(true);
					
	    dropPanel.add(dropLabel);
		
	    //Drop Image Label ------------------------------------------------------
		dropLoc = new JLabel();
		
		ImageIcon imageIcon = new ImageIcon("Images/DropFile.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newImage = image.getScaledInstance(140, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newImage);  // transform it back
		dropLoc.setIcon(imageIcon);
		
		dropLoc.setMinimumSize(new Dimension(300, 120));
		dropLoc.setPreferredSize(new Dimension(300, 120));
		dropLoc.setMaximumSize(new Dimension(300, 120));
		
		dropLoc.setHorizontalAlignment(JLabel.CENTER);
		dropLoc.setHorizontalTextPosition(JLabel.CENTER);
		dropLoc.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		dropLoc.setBackground(new Color(224,224,224));
		
		//dropLoc hover effects
		dropLoc.addMouseListener(new MouseAdapter() {
    	    public void mouseEntered(MouseEvent evt) {
    	    	dropLoc.setBackground(new Color(207,207,207));
    	    }
    	    

    	    public void mouseExited(MouseEvent evt) {
    	    	dropLoc.setBackground(new Color(224,224,224));
    	    }
		});
		
		dropLoc.setOpaque(true);
		
		DragDropListener dragDropListener = new DragDropListener();
		new DropTarget(dropLoc, dragDropListener); // Connect the label with a drag and drop listener

		// Add the label to the content
		dropPanel.setOpaque(false);
		dropPanel.add(dropLoc);
		//dropPanel.add(dropLabel);
				

    	 
		
				
		/*
		//Bottom Label ------------------------------------------------------------
		JLabel dropText = new JLabel("Drop File in Above Area");
		
		dropText.setBackground(Color.PINK);
		//dropText.setFont(new Font(,Font.PLAIN, 16));
		
		dropText.setMinimumSize(new Dimension(300, 25));
		dropText.setPreferredSize(new Dimension(300, 25));
		dropText.setMaximumSize(new Dimension(300, 25));
		
		dropText.setHorizontalAlignment(JLabel.CENTER);
		dropText.setHorizontalTextPosition(JLabel.CENTER);
		dropText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		dropText.setBackground(Color.LIGHT_GRAY);
		dropText.setOpaque(true);
		
		dropPanel.add(dropText);
		*/
		this.setOpaque(false);
		this.add(dropPanel);//Add Drop Panel to File Drop Panel
		this.setVisible(true); //Set File Drop Panel to Visible
	}
	
	public static String getDropFilePath() {
		return dropFilePath;
	}

	public JPanel getDropPanel() {
		return dropPanel;
	}

	public JLabel getDropLabel() {
		return dropLabel;
	}

	public JLabel getDropLoc() {
		return dropLoc;
	}
}
