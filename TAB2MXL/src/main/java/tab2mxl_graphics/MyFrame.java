package tab2mxl_graphics;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.prefs.Preferences;


public class MyFrame extends JFrame implements ActionListener {

	JButton button;
	JPanel panel;
	
	Preferences prefs = Preferences.userRoot().node(getClass().getName());
	String LAST_USED_FOLDER = "";
	
	MyFrame() {
		
		button = new JButton("Select File");  //Select File button
		button.setBounds(100,100,250,100);
		button.setForeground(Color.WHITE);    //Customize button
		button.setBackground(Color.BLACK);
		button.setOpaque(true);
		button.setBorderPainted(false);
		
		button.addActionListener(this); //Button action
		
		JLabel label = new JLabel("Upload Tablature to Convert to MusicXML:");
		label.setBounds(100, -100, 300, 300);
		label.setVisible(true);
		this.add(label);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setSize(500,300);
		this.setVisible(true);
		this.add(label);
		this.add(button);
			
		//Sets the FileChoosers style to the current system look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Throwable ex) { }  
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button) {  //Button click
			JFileChooser fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath())); // Create file chooser

			int response = fileChooser.showOpenDialog(null); // Select file to open
			// fileChooser.showSaveDialog(null); //Select file to save

			if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
				
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());  //Print out path
				System.out.println("File Location: " + file + "\n");
				
				prefs.put(LAST_USED_FOLDER, fileChooser.getSelectedFile().getParent()); //Save file path

				try {
					
					Scanner input = new Scanner(file); //Print contents of text file to console
					
					while (input.hasNextLine()) {
						System.out.println(input.nextLine());
					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
