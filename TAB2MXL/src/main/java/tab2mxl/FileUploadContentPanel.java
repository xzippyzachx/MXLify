package tab2mxl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.io.File;
import java.io.FileNotFoundException;
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
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.setBorder(padding); // adds a basic border around the frame

		// creates back button containder adds button to the top left of the content
		// panel
		JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		backPanel.add(backButton);
		this.add(backPanel);

		// creates panel to place the main body elements
		JPanel OptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // sets layout of this panel to be horizontal
		Border OptionsPadding = BorderFactory.createEmptyBorder(0, 50, 55, 0);
		OptionsPanel.setBorder(OptionsPadding);

		// creates panel for the upload label and button to go into
		JPanel UploadPanel = new JPanel();
		UploadPanel.setLayout(new BoxLayout(UploadPanel, BoxLayout.Y_AXIS)); // sets layout of this panel to be vertical

		JLabel label = new JLabel("Upload Tablature to Convert to MusicXML"); // creates label
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16)); // sets font for the label
		UploadPanel.add(label);

		JPanel ButtonPanel = new JPanel(); // creates panel for button
		ButtonPanel.setLayout(new FlowLayout()); // centers the button in that panel

		selectButton = new JButton("Select File"); // Select File button
//		selectButton.setBounds(100,100,250,100);
		selectButton.setForeground(Color.WHITE); // Customize button
		selectButton.setBackground(Color.BLACK);
		selectButton.setOpaque(true);
		selectButton.setBorderPainted(false);
		selectButton.addActionListener(this); // Button action
		ButtonPanel.add(selectButton);

		UploadPanel.add(ButtonPanel); // adds button panel to the upload panel

		// creates a panel for the drop label and drop box to go into
		JPanel DropPanel = new JPanel();
		DropPanel.setLayout(new BoxLayout(DropPanel, BoxLayout.Y_AXIS));// sets layout to vertical
		Border DropPadding = BorderFactory.createEmptyBorder(0, 45, 15, 0);
		DropPanel.setBorder(DropPadding);
		JLabel DropLabel = new JLabel("Drop Tablature to Convert to MusicXML");
		DropLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		DropPanel.add(DropLabel);
		/**
		 * to do: create the drop box for dropping in a text file
		 */
		
		JLabel dropLoc = new JLabel("Drag File Here!",SwingConstants.CENTER);
		dropLoc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		dropLoc.setForeground(Color.red);
		 MyDragDropListener myDragDropListener = new MyDragDropListener();

	    // Connect the label with a drag and drop listener
	    new DropTarget(dropLoc, myDragDropListener);

	    // Add the label to the content
	    DropPanel.add(dropLoc);
		OptionsPanel.add(UploadPanel);
		OptionsPanel.add(DropPanel);

		// JLabel label = new JLabel("Upload Tablature to Convert to MusicXML:");
		// label.setBounds(100, -100, 300, 300);

		// this.add(label);
		this.add(OptionsPanel);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			this.setVisible(false);
			Main.myFrame.MainContentScreenFromUpload();
		} else {
			if (e.getSource() == selectButton) { // Button click
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

						Scanner scannerInput = new Scanner(file); // Print contents of text file to console

						while (scannerInput.hasNextLine()) {
							String[] lineInput = scannerInput.nextLine().split("");
							ArrayList<String> lineInputList = new ArrayList<String>();

							for (String character : lineInput) {
								lineInputList.add(character);
							}
							input.add(lineInputList);
						}

						scannerInput.close();

						// Call FileUploaded() method in Main_GUI
						Main.FileUploaded(input);

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

}

class MyDragDropListener implements DropTargetListener {

	@Override
	public void drop(DropTargetDropEvent event) {

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