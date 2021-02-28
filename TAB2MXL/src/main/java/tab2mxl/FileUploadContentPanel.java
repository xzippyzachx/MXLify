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
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUploadContentPanel extends JPanel implements ActionListener {

	JButton selectButton;
	JButton backButton;

	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER = "";
	public static String dropFilePath = "";

	FileUploadContentPanel() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // sets layout to be vertical
		Border padding = BorderFactory.createEmptyBorder(10, 0, 10, 10);
		this.setBorder(padding); // adds a basic border around the frame

		// creates panel to place the main body elements
		JPanel OptionsPanel = new JPanel(new GridLayout(2,1)); // 
		Border OptionsPadding = BorderFactory.createEmptyBorder(100, 00, 50, 0);
		OptionsPanel.setBorder(OptionsPadding);
		OptionsPanel.setOpaque(false);

		// creates panel for the upload label and button to go into
		JPanel UploadPanel = new JPanel();
		UploadPanel.setLayout(new GridLayout(1,1)); // sets layout of this panel to be vertical

		UploadPanel.setOpaque(false);
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
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		Border ButtonPadding = BorderFactory.createEmptyBorder(10, 0, 0, 0);
		ButtonPanel.setBorder(ButtonPadding);


		ButtonPanel.setOpaque(false);


		selectButton = new JButton("Select File"); // Select File button
		selectButton.setBackground(new Color(33,150,243));
		selectButton.setForeground(new Color(224,224,224));

		selectButton.setFocusable(false);
		selectButton.addActionListener(this); // Button action
		ButtonPanel.add(selectButton);
		
		UploadPanel.add(ButtonPanel); // adds button panel to the upload panel

		FileDropPanel DropPanel = new FileDropPanel();
		Border DropPadding = BorderFactory.createEmptyBorder(20, 0, 0, 0);
		DropPanel.setBorder(DropPadding);
		
		OptionsPanel.add(DropPanel);
		OptionsPanel.add(UploadPanel);
		
		this.add(OptionsPanel);
		this.setOpaque(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == selectButton && !Main.isInPopUp) { // Button click
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");				
			JFileChooser fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath())); // Create file chooser
			fileChooser.setFileFilter(filter); // Only allow .txt files
			
			ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();

			int response = fileChooser.showOpenDialog(null); // Select file to open

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

