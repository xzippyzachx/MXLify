package tab2mxl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class FileUploadContentPanel extends JPanel implements ActionListener {
	
	JButton selectButton;
	
	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER = "";
	
	FileUploadContentPanel(){
	
		//////////////////////
		//File Upload Screen
		//////////////////////
		this.setLayout(null);
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.setBorder(padding);
		
		selectButton = new JButton("Select File");  //Select File button
		selectButton.setBounds(100,100,250,100);
		selectButton.setForeground(Color.WHITE);    //Customize button
		selectButton.setBackground(Color.BLACK);
		selectButton.setOpaque(true);
		selectButton.setBorderPainted(false);
		
		selectButton.addActionListener(this); //Button action
		
		JLabel label = new JLabel("Upload Tablature to Convert to MusicXML:");
		label.setBounds(100, -100, 300, 300);	
		
		this.add(label);
		this.add(selectButton);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == selectButton) {  //Button click
			JFileChooser fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath())); // Create file chooser
			ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
			
			int response = fileChooser.showOpenDialog(null); // Select file to open
			// fileChooser.showSaveDialog(null); //Select file to save

			if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
				
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());  //Print out path
				System.out.println("File Location: " + file + "\n");
				
				prefs.put(LAST_USED_FOLDER, fileChooser.getSelectedFile().getParent()); //Save file path

				try {
					
					Scanner scannerInput = new Scanner(file); //Print contents of text file to console
					
					while (scannerInput.hasNextLine()) {	
						String[] lineInput = scannerInput.nextLine().split("");						
						ArrayList<String> lineInputList = new ArrayList<String>();
						
						for(String character : lineInput) {
							lineInputList.add(character);
					    }			
						input.add(lineInputList);
					}
					
					scannerInput.close();										
					
					//Call FileUploaded() method in Main_GUI
					Main.FileUploaded(input);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
}