package gui_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.UndoRedoTextArea;
import tab2mxl.LoadManager;
import tab2mxl.Main;

public class FileUploadContentPanel extends JPanel implements ActionListener {

	JButton openButton;
	JButton backButton;
	
	JPanel OptionsPanel;
	JPanel MeasurePanel;

	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER = "";
	public static String dropFilePath = "";

	FileUploadContentPanel() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // sets layout to be vertical
		Border padding = BorderFactory.createEmptyBorder(10, 0, 10, 10);
		this.setBorder(padding); // adds a basic border around the frame

		// creates panel to place the main body elements
		OptionsPanel = new JPanel(); // 
		OptionsPanel.setLayout(new BoxLayout(OptionsPanel, BoxLayout.Y_AXIS));
		Border OptionsPadding = BorderFactory.createEmptyBorder(0, 0, 100, 0);
		OptionsPanel.setBorder(OptionsPadding);
		OptionsPanel.setOpaque(false);

		// creates panel for the upload label and button to go into
		JPanel UploadPanel = new JPanel();
		UploadPanel.setLayout(new GridLayout(1,1)); // sets layout of this panel to be vertical

		UploadPanel.setOpaque(false);

		JPanel ButtonPanel = new JPanel(); // creates panel for button
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		Border ButtonPadding = BorderFactory.createEmptyBorder(10, 0, 0, 0);
		ButtonPanel.setBorder(ButtonPadding);

		ButtonPanel.setOpaque(false);

		openButton = new JButton("Open"); // Select File button
		openButton.setBackground(new Color(33,150,243));
		openButton.setForeground(new Color(224,224,224));
		openButton.setOpaque(true);
		openButton.setBorderPainted(false);

		openButton.setFocusable(false);
		openButton.addActionListener(this); // Button action
		//Button hover effects
        openButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	openButton.setBackground(new Color(224,224,224));
    	    	openButton.setForeground(new Color(33,150,243));
    	    }
    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	openButton.setBackground(new Color(33,150,243));
    	    	openButton.setForeground(new Color(224,224,224));
    	    }
    	}); 
		
		ButtonPanel.add(openButton);
		
		UploadPanel.add(ButtonPanel); // adds button panel to the upload panel

		FileDropPanel DropPanel = new FileDropPanel();
		Border DropPadding = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		DropPanel.setBorder(DropPadding);
		
		// creates panel for the upload label and button to go into
		MeasurePanel = new JPanel();
		Border measurePanelPadding = BorderFactory.createEmptyBorder(10, 0, 125, 0);
		MeasurePanel.setLayout(new BoxLayout(MeasurePanel, BoxLayout.Y_AXIS));// sets layout to vertical
		MeasurePanel.setBorder(measurePanelPadding);
		MeasurePanel.setOpaque(false);
		
		JLabel measureLabel = new JLabel("<html><body style='text-align: center'>Edit Measure Time Signatures");				
		
		measureLabel.setHorizontalAlignment(JLabel.CENTER);
		
		measureLabel.setHorizontalTextPosition(JLabel.CENTER);
		measureLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		Border measureLabelPadding = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		measureLabel.setBorder(measureLabelPadding);
		measureLabel.setForeground(new Color(224,224,224));
		measureLabel.setBackground(new Color(33,150,243));
		measureLabel.setOpaque(true);
					
		MeasurePanel.add(measureLabel);
		
		JTextArea textField = new UndoRedoTextArea(); //Create empty text area (Undo-able Text Area)
        textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		
        MeasurePanel.add(textField);
        
        JScrollPane scroll = new JScrollPane (textField);
        scroll.setPreferredSize(new Dimension(300, 265));
        scroll.setSize(getPreferredSize());;
        MeasurePanel.add(scroll);
        
		OptionsPanel.add(DropPanel);
		OptionsPanel.add(UploadPanel);
		//OptionsPanel.add(MeasurePanel);
		
		this.add(OptionsPanel);
		this.setOpaque(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == openButton && !Main.isInPopUp) { // Button click
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", ".mxlify", "mxlify");				
			JFileChooser fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath())); // Create file chooser
			fileChooser.setFileFilter(filter); // Only allow .txt files
			
			int response = fileChooser.showOpenDialog(null); // Select file to open

			if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen

				File file = new File(fileChooser.getSelectedFile().getAbsolutePath()); // Print out path
				System.out.println("File Location: " + file + "\n");

				prefs.put(LAST_USED_FOLDER, fileChooser.getSelectedFile().getParent()); // Save file path

				try {
					String fileName = file.getName();
					String extension = "";

					int i = fileName.lastIndexOf('.');
					int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

					if (i > p)
					    extension = fileName.substring(i+1);
										
					if(extension.equals(".mxlify") || extension.equals("mxlify"))
					{
						LoadManager loadManager = new LoadManager(file.getPath());
						String[] loadedData = loadManager.GetLoadedData();
						if(!loadManager.failed)
						{
							Main.myFrame.textInputContentPanel.instrumentList.setSelectedIndex(Integer.parseInt(loadedData[0]));
							Main.myFrame.textInputContentPanel.songName.setText(loadedData[1]);
							Main.myFrame.textInputContentPanel.timeSignature.setText(loadedData[2]);
						}
						return;
					}
					
					BufferedReader reader = new BufferedReader(new FileReader (file));
				    String         line = null;
				    StringBuilder  stringBuilder = new StringBuilder();
				    String         ls = System.getProperty("line.separator");
			    
			        while((line = reader.readLine()) != null) {
			            stringBuilder.append(line);
			            stringBuilder.append(ls);
			        }

			        if(extension.equals(".txt") || extension.equals("txt"))
					{
						Main.FileUploaded(stringBuilder.toString());
					}			        
			        
			        reader.close();
			    } catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
}

