package tab2mxl_graphics;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MyFrame extends JFrame implements ActionListener {

	JButton button;
	JPanel panel;
	
	MyFrame() {
		

		button = new JButton("Select File");  //Select File button
		button.setBounds(100,100,250,100);
		button.setForeground(Color.WHITE);    //Customize button
		button.setBackground(Color.BLACK);
		button.setOpaque(true);
		button.setBorderPainted(false);
		
		button.addActionListener(this); //Button action
	//	this.add(button, new GridBagConstraints());
		
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
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button) {  //Button click
			JFileChooser fileChooser = new JFileChooser(); // Create file chooser

			int response = fileChooser.showOpenDialog(null); // Select file to open
			// fileChooser.showSaveDialog(null); //Select file to save

			if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
				
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());  //Print out path
				System.out.println("File Location: " + file + "\n");

				try {
					
					Scanner input = new Scanner(file);         //Print contents of text file to console
					
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
