package tab2mxl;

import java.awt.*;
import java.awt.dnd.DropTarget;

import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class FileDropPanel extends JPanel{
	
	public static String dropFilePath = "";
	
	FileDropPanel(){
		
				// Main Panel for Drop Elements
				JPanel dropPanel = new JPanel();
				dropPanel.setLayout(new BoxLayout(dropPanel, BoxLayout.Y_AXIS));// sets layout to vertical
				Border DropPadding = BorderFactory.createEmptyBorder(0, 10, 10, 10);
				dropPanel.setBorder(DropPadding);
				
				//Title Label -----------------------------------------------------------
				JLabel dropLabel = new JLabel("<html><body style='text-align: center'>Drop Tablature Text File");
				//JLabel dropLabel = new JLabel("Drop Tablature to Convert to MusicXML");
				dropLabel.setBackground(Color.PINK);
				//dropLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
				
				dropLabel.setHorizontalAlignment(JLabel.CENTER);
				dropLabel.setHorizontalTextPosition(JLabel.CENTER);
				dropLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				
				dropLabel.setBackground(new Color(51,153,255));
				dropLabel.setOpaque(true);
				
				Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
				dropLabel.setBorder(border);
				
			    dropPanel.add(dropLabel);
				
			    //Drop Image Label ------------------------------------------------------
				JLabel dropLoc = new JLabel();
				
				ImageIcon imageIcon = new ImageIcon("DropImage.png"); // load the image to a imageIcon
				Image image = imageIcon.getImage(); // transform it 
				Image newImage = image.getScaledInstance(120, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imageIcon = new ImageIcon(newImage);  // transform it back
				dropLoc.setIcon(imageIcon);
				
				dropLoc.setMinimumSize(new Dimension(300, 100));
				dropLoc.setPreferredSize(new Dimension(300, 100));
				dropLoc.setMaximumSize(new Dimension(300, 100));
				
				dropLoc.setHorizontalAlignment(JLabel.CENTER);
				dropLoc.setHorizontalTextPosition(JLabel.CENTER);
				dropLoc.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				
				dropLoc.setBackground(Color.LIGHT_GRAY);
				dropLoc.setOpaque(true);
				
				DragDropListener dragDropListener = new DragDropListener();
				new DropTarget(dropLoc, dragDropListener); // Connect the label with a drag and drop listener

				// Add the label to the content
				dropPanel.add(dropLoc);
				
				
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
				
				this.add(dropPanel);//Add Drop Panel to File Drop Panel
				this.setVisible(true); //Set File Drop Panel to Visible
    
	}
	
}


