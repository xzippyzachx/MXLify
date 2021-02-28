package tab2mxl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import tab2mxl.TextPrompt.Show;

public class TextInputContentPanel extends JPanel implements ActionListener {
	public JTextArea textField;
	public static boolean scoremake = false;
	JPanel titlePanel;
	JLabel titleLabel;
	
	JPanel clearPanel;
	JButton clearButton;
	
	JScrollPane scroll;
	
	JPanel inputpanel;	
	JButton convertButton;
	
	String[] tabTypes = {"Guitar"/*, "Bass", "Drums"*/};
	JPanel detailsPanel;
	JComboBox tabList;
	JTextField timeSignature;
	JTextField songName;
	SteelCheckBox sheetMusicToggle;
	
	JPanel errorPanel;
	public JLabel errorText;
	
	static String tabType;
	static String title;
	static String timeSig;
	
	
	TextInputContentPanel(){		
	
		// creates main content panel, lets layout to vertical, adds padding and sets it as Content Pane
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 10);
		this.setBorder(padding);
		
        // creates Title Container and adds label to the Content panel
        titlePanel = new JPanel();        
        
        titlePanel.setLayout(new GridLayout(0, 3));
        
        clearPanel = new JPanel();
        clearPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(33,150,243));
        clearButton.setForeground(new Color(224,224,224));
        clearButton.setFocusable(false);
        clearButton.addActionListener(this);
        clearPanel.add(clearButton);
        titlePanel.add(clearPanel);
        
        titleLabel = new JLabel("Paste Your Tablature Here");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);
        
        this.add(titlePanel);
        
        // generates the text field, sets size,font, and scrollability
        textField = new JTextArea();
        
        textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.add(textField);
        scroll = new JScrollPane (textField);
        scroll.setPreferredSize(new Dimension(800, 500));
        scroll.setMinimumSize(new Dimension(480, 300));
        scroll.setSize(getPreferredSize());;
        this.add(scroll);
        
        detailsPanel = new JPanel();        
        detailsPanel.setLayout(new GridLayout(1,3));
                        
        JPanel tabListPanel = new JPanel();
        tabListPanel.setLayout(new GridLayout(0, 1));
        tabListPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        tabList = new JComboBox(tabTypes);
        tabList.setSelectedIndex(0);
        tabListPanel.add(tabList);
        
        JPanel songNamePanel = new JPanel();
        songNamePanel.setLayout(new GridLayout(0, 1));
        songNamePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        songName = new JTextField();
        songName.setFont(songName.getFont().deriveFont(16f));
        //songName.setHorizontalAlignment(JTextField.CENTER);
        songName.setText("Title");
        TextPrompt songNamePrompt = new TextPrompt("Song Name", songName,Show.FOCUS_LOST);
        songNamePrompt.setHorizontalAlignment(JTextField.CENTER);
        songNamePrompt.changeAlpha(0.8f);
        songNamePanel.add(songName);
        
        JPanel timeSignaturePanel = new JPanel();
        timeSignaturePanel.setLayout(new GridLayout(0, 1));
        timeSignaturePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        timeSignature = new JTextField();
        timeSignature.setFont(timeSignature.getFont().deriveFont(16f));
        //timeSignature.setHorizontalAlignment(JTextField.CENTER);
        timeSignature.setText("4/4");
        TextPrompt timeSignaturePrompt = new TextPrompt("Time Signature", timeSignature,Show.FOCUS_LOST);
        timeSignaturePrompt.setHorizontalAlignment(JTextField.CENTER);
        timeSignaturePrompt.changeAlpha(0.8f);
        timeSignaturePanel.add(timeSignature);
        
        Border detailsPadding = BorderFactory.createEmptyBorder(20, 0, 20, 0);
        detailsPanel.setBorder(detailsPadding);
        
        
        detailsPanel.add(tabListPanel);
//        detailsPanel.add(new JPanel());
        detailsPanel.add(songNamePanel);
//        detailsPanel.add(new JPanel());
        detailsPanel.add(timeSignaturePanel);
        
        // creates the container for the button, generates the button and sets an action on click
        inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        convertButton = new JButton("Convert To MusicXML");
        convertButton.setBackground(new Color(33,150,243));
        convertButton.setForeground(new Color(224,224,224));
        convertButton.setFocusable(false);
        convertButton.addActionListener(this);
        JPanel togglepanel = new JPanel();

        sheetMusicToggle = new SteelCheckBox();
        sheetMusicToggle.setRised(false);
		sheetMusicToggle.setSelectedColor(ColorDef.YELLOW);
        sheetMusicToggle.setText("Sheet Music");
        sheetMusicToggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID() == ActionEvent.ACTION_PERFORMED){
					TextInputContentPanel.scoremake = !TextInputContentPanel.scoremake;
				}
//				TextInputContentPanel.scoremake = e.getStateChange() == ItemEvent.SELECTED;
				System.out.println(TextInputContentPanel.scoremake);
				if( TextInputContentPanel.scoremake && !CreateScore.isWindows()){
					JOptionPane.showMessageDialog(null,"We've detected you're running this application on MacOS, please read the User Manuel before using this feature to prevent any errors");
//
//
				}
			}
		});
        togglepanel.add(sheetMusicToggle);
		Border togglePadding = BorderFactory.createEmptyBorder(0, 40, 0, 0);
		togglepanel.setBorder(togglePadding);
        inputpanel.add(convertButton);
        inputpanel.add(togglepanel);
        Border buttonPadding = BorderFactory.createEmptyBorder(10, 10, 0, 10);
        inputpanel.setBorder(buttonPadding);
        
        errorPanel = new JPanel();
        errorPanel.setLayout(new FlowLayout());
        errorText = new JLabel("");        
        errorText.setForeground(Color.red);
        errorPanel.add(errorText);
        errorPanel.setPreferredSize(new Dimension(100, 30));
        
        //adds the button container to the content panel
        this.add(detailsPanel);
        this.add(inputpanel);
        this.add(errorPanel);
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == convertButton)
		{
			if(Main.isInPopUp)
				return;
	
			//Detect if the text area is empty
			if(textField.getText().isEmpty())
			{
				errorText.setText("Text Area Empty");
				return;
			}
			else if (!textField.getText().contains("|"))
			{
				errorText.setText("Wrong Formatting");
				return;
			}
			
			String[] inputText = textField.getText().split("\n");
			
			ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
			
			for (String line : inputText) {	
				line = cleanTextContent(line); //Removes redundant spaces
				String[] lineInput = line.substring(line.indexOf('|')).split("");
				ArrayList<String> lineInputList = new ArrayList<String>();
				String tunePlusOctave = line.substring(0, line.indexOf('|')).trim();
				String tune = "";
				String octave = "";
				if(tunePlusOctave.length() > 0) {
					try {
						octave = "" + Integer.parseInt(tunePlusOctave.substring(tunePlusOctave.length()-1));
						tune = tunePlusOctave.substring(0, tunePlusOctave.length()-1);
					}catch(NumberFormatException e1){
						octave = "";
						tune = tunePlusOctave;
					}
				}
				lineInputList.add(tune.trim());
				lineInputList.add(octave.trim());
				//lineInputList.add(tune);
				for(String character : lineInput) {
					lineInputList.add(character);
			    }
				input.add(lineInputList);
			}
			tabType = tabList.getSelectedItem().toString();
			title = songName.getText();
			timeSig = timeSignature.getText();
					
			errorText.setText("");
								
			//Detect if the field is empty
			if(tabType.equals("") || title.equals("") || timeSig.equals(""))
			{
				errorText.setText("Field Empty");
			}
			else {
				int lineLength = input.get(0).size();
				for (ArrayList<String> line : input) {
					if(line.size() != lineLength)
					{
						errorText.setText("Wrong Formatting");
						break;
					}
				}
			}
			
			if (errorText.getText() == "")
			{			
				Main.Convert(input);
			}	
		}
		else if(e.getSource() == clearButton)
		{
			if(Main.isInPopUp)
				return;
			
			if(!Main.myFrame.textInputContentPanel.textField.getText().isEmpty())
				new ClearPopUp(Main.myFrame, "", "Clear Current Tablature");
		}
	}
	
	private static String cleanTextContent(String text) 
	{
	    // strips off all non-ASCII characters
	    text = text.replaceAll("[^\\x00-\\x7F]", "");
	 
	    // erases all the ASCII control characters
	    text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
	     
	    // removes non-printable characters from Unicode
	    text = text.replaceAll("\\p{C}", "");
	 
	    return text.trim();
	}
		
}
