package tab2mxl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class FileUploadContentPanel extends JPanel implements ActionListener {

	JButton selectButton;
	JButton backButton;

	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER = "";
	public static String dropFilePath = "";

	FileUploadContentPanel() {

		//////////////////////
		// File Upload Screen
		//////////////////////

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // sets layout to be vertical
		Border padding = BorderFactory.createEmptyBorder(10, 0, 10, 10);
		this.setBorder(padding); // adds a basic border around the frame

//		BACK BUTTON REMOVED
//		// creates back button container adds button to the top left of the content
//		// panel
//		JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		backButton = new JButton("Back");
//		backButton.addActionListener(this);
//		backPanel.add(backButton);
//		this.add(backPanel);

		// creates panel to place the main body elements
		JPanel OptionsPanel = new JPanel(new GridLayout(2,1)); // 
		Border OptionsPadding = BorderFactory.createEmptyBorder(100, 00, 55, 0);
		OptionsPanel.setBorder(OptionsPadding);

		// creates panel for the upload label and button to go into
		JPanel UploadPanel = new JPanel();
		UploadPanel.setLayout(new GridLayout(1,1)); // sets layout of this panel to be vertical
//		JPanel SpacePanelTop = new JPanel(); // makes the top quarter of the UploadPanel empty space
//		JPanel SpacePanelBottom = new JPanel();  // makes the bottom quarter of the UploadPanel empty space
		//JPanel LabelPanel = new JPanel(new GridBagLayout()); // allows us centering of the label
		//JLabel label = new JLabel("Upload Tablature Text File"); // creates label
		
//		label.setMinimumSize(new Dimension(300, 50));
//		label.setPreferredSize(new Dimension(300, 50));
//		label.setMaximumSize(new Dimension(300, 50));
//		
//		label.setHorizontalAlignment(JLabel.CENTER);
//		label.setHorizontalTextPosition(JLabel.CENTER);
//		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
//		
//		label.setBackground(new Color(51,153,255));
//		label.setOpaque(true);
//		
//		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
//		label.setBorder(border);
	//	label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16)); // sets font for the label
		
//		LabelPanel.add(label);
//		UploadPanel.add(SpacePanelTop);
//		UploadPanel.add(LabelPanel);

		JPanel ButtonPanel = new JPanel(); // creates panel for button
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
		Border ButtonPadding = BorderFactory.createEmptyBorder(20, 65, 0, 0);
		ButtonPanel.setBorder(ButtonPadding);
//		ButtonPanel.setLayout(new GridBagLayout()); // centers the button in that panel

		selectButton = new JButton("Select Tablature Text File"); // Select File button
		selectButton.setBackground(new Color(33,150,243));
		selectButton.setForeground(new Color(224,224,224));
//		selectButton.setBounds(100,100,250,100);
		//selectButton.setForeground(Color.WHITE); // Customize button
		//selectButton.setBackground(Color.BLACK);
		//selectButton.setOpaque(true);
		//selectButton.setBorderPainted(false);
		selectButton.setFocusable(false);
		selectButton.addActionListener(this); // Button action
		ButtonPanel.add(selectButton);
		
		UploadPanel.add(ButtonPanel); // adds button panel to the upload panel
//		UploadPanel.add(SpacePanelBottom); 
		
		// creates a panel for the drop label and drop box to go into
		FileDropPanel DropPanel = new FileDropPanel();
		Border DropPadding = BorderFactory.createEmptyBorder(20, 0, 0, 0);
		DropPanel.setBorder(DropPadding);
//		DropPanel.setLayout(new BoxLayout(DropPanel, BoxLayout.Y_AXIS));// sets layout to vertical
//		Border DropPadding = BorderFactory.createEmptyBorder(0, 45, 15, 0);
//		DropPanel.setBorder(DropPadding);
//		JLabel DropLabel = new JLabel("Drop Tablature to Convert to MusicXML");
//		
//		DropLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
//		DropPanel.add(DropLabel);
//		/**
//		 * to do: create the drop box for dropping in a text file
//		 */
//		
//		JLabel dropLoc = new JLabel("Drag File Here!",SwingConstants.CENTER);
//		dropLoc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
//		dropLoc.setForeground(Color.red);
//		 MyDragDropListener myDragDropListener = new MyDragDropListener();
//
//	    // Connect the label with a drag and drop listener
//	    new DropTarget(dropLoc, myDragDropListener);
//
//	    // Add the label to the content
//	    DropPanel.add(dropLoc);
		
		OptionsPanel.add(DropPanel);
		OptionsPanel.add(UploadPanel);
		

		// JLabel label = new JLabel("Upload Tablature to Convert to MusicXML:");
		// label.setBounds(100, -100, 300, 300);

		// this.add(label);
		
		this.add(OptionsPanel);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
			if (e.getSource() == selectButton && !Main.isInPopUp) { // Button click
				JFileChooser fileChooser = new JFileChooser(
						prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath())); // Create file chooser
				ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();

				int response = fileChooser.showOpenDialog(null); // Select file to open
				// fileChooser.showSaveDialog(null); //Select file to save

				if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen

					File file = new File(fileChooser.getSelectedFile().getAbsolutePath()); // Print out path
					System.out.println("File Location: " + file + "\n");

					prefs.put(LAST_USED_FOLDER, fileChooser.getSelectedFile().getParent()); // Save file path

					try {
						BufferedReader reader = new BufferedReader(new FileReader (file));
					    String         line = null;
					    StringBuilder  stringBuilder = new StringBuilder();
					    String         ls = System.getProperty("line.separator");
				    
				        while((line = reader.readLine()) != null) {
				            stringBuilder.append(line);
				            stringBuilder.append(ls);
				        }

				        Main.FileUploaded(stringBuilder.toString());
				        
				        reader.close();
				    } catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		
	}

}

class MyDragDropListener implements DropTargetListener {

	@Override
	public void drop(DropTargetDropEvent event) {
		
		if(Main.isInPopUp)
			return;

		event.acceptDrop(DnDConstants.ACTION_COPY); // Accept copy drops

		Transferable transferable = event.getTransferable(); // Get the transfer which can provide the dropped item data

		DataFlavor[] flavors = transferable.getTransferDataFlavors(); // Get the data formats of the dropped item

		for (DataFlavor flavor : flavors) { // Loop through the flavors

			try {

				// If the drop items are files
				if (flavor.isFlavorJavaFileListType()) {

					// Get all of the dropped files
					List files = (List) transferable.getTransferData(flavor);

					// Loop them through
					for (Object file : files) {
						FileUploadContentPanel.dropFilePath = ((File) (file)).getPath();

						// Print out the file path
						System.out.println("File path is '" + ((File) (file)).getPath() + "'.");

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		event.dropComplete(true); // Inform that the drop is complete
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}