package tab2mxl;

import java.awt.*;
import java.awt.dnd.DropTarget;

import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class FileDropPanel extends JPanel{
	
	public static String dropFilePath = "";
	 private JPanel jp2;
	    private JButton b2;
	FileDropPanel(){
		
		
				JPanel DropPanel = new JPanel();
				DropPanel.setLayout(new BoxLayout(DropPanel, BoxLayout.Y_AXIS));// sets layout to vertical
				Border DropPadding = BorderFactory.createEmptyBorder(0, 10, 10, 10);
				DropPanel.setBorder(DropPadding);
				
				
				JLabel DropLabel = new JLabel("<html><body style='text-align: center'>Drop Tablature to<br>Convert to MusicXML");
				DropLabel.setBackground(Color.PINK);
				DropLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
				//DropLabel.setMinimumSize(new Dimension(300, 75));
				//DropLabel.setPreferredSize(new Dimension(300, 75));
				//DropLabel.setMaximumSize(new Dimension(300, 75));
				DropLabel.setHorizontalAlignment(JLabel.CENTER);
				DropLabel.setHorizontalTextPosition(JLabel.CENTER);
				DropLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				DropLabel.setBackground(Color.PINK);
				DropLabel.setOpaque(true);
				Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
				 
			    // set the border of this component
			    DropLabel.setBorder(border);
				DropPanel.add(DropLabel);
				
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

				// Connect the label with a drag and drop listener
				new DropTarget(dropLoc, dragDropListener);

				// Add the label to the content
				DropPanel.add(dropLoc);
				
				
				
				JLabel DropText = new JLabel("Drop File in Above Area");
				DropText.setBackground(Color.PINK);
				DropText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
				DropText.setMinimumSize(new Dimension(300, 25));
				DropText.setPreferredSize(new Dimension(300, 25));
				DropText.setMaximumSize(new Dimension(300, 25));
				DropText.setHorizontalAlignment(JLabel.CENTER);
				DropText.setHorizontalTextPosition(JLabel.CENTER);
				DropText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
				DropText.setBackground(Color.LIGHT_GRAY);
				DropText.setOpaque(true);
				
				DropPanel.add(DropText);
				
				this.add(DropPanel);

				this.setVisible(true);
    
	}
	
}


