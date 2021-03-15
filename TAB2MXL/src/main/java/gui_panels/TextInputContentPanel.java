package gui_panels;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import gui.ColorDef;
import gui.SteelCheckBox;
import gui.TextPrompt;
import gui.TextPrompt.Show;
import gui_popups.ClearPopUp;
import gui.UndoRedoTextArea;
import tab2mxl.CreateScore;
import tab2mxl.InstrumentDetection;
import tab2mxl.LoadManager;
import tab2mxl.Main;
import tab2mxl.SaveManager;

public class TextInputContentPanel extends JPanel implements ActionListener {
	public JTextArea textField;
	public static boolean scoremake = false;
	JPanel titlePanel;
	JLabel titleLabel;
	
	JPanel clearPanel;
	JButton clearButton;
	
	JPanel savePanel;
	JButton saveButton;
	
	JScrollPane scroll;
	
	JPanel inputpanel;	
	JButton convertButton;
	
	String[] tabTypes = {"Guitar", "Bass", "Drums"};
	JPanel detailsPanel;
	JComboBox tabList;
	JTextField timeSignature;
	JTextField songName;
	SteelCheckBox sheetMusicToggle;
	
	JPanel errorPanel;
	public JLabel errorText;
	
	private static String tabType;
	private static String title;
	private static String timeSig;
		
	TextInputContentPanel(){
	
		// creates main content panel, lets layout to vertical, adds padding and sets it as Content Pane
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border padding = BorderFactory.createEmptyBorder(4, 10,0, 10);
		this.setBorder(padding);
		
        // creates Title Container and adds label to the Content panel
        titlePanel = new JPanel();        
        
        titlePanel.setLayout(new GridLayout(0, 3));
        titlePanel.setOpaque(false);
       // titlePanel.setBackground(Color.black);
        padding = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		titlePanel.setBorder(padding);
		
		savePanel = new JPanel();
		savePanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
		savePanel.setOpaque(false);
        
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(33,150,243));
        saveButton.setForeground(new Color(224,224,224));
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);
        //Button hover effects
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	saveButton.setBackground(new Color(224,224,224));
    	    	saveButton.setForeground(new Color(33,150,243));
    	    }
    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	saveButton.setBackground(new Color(33,150,243));
    	    	saveButton.setForeground(new Color(224,224,224));
    	    }
    	});  
        savePanel.add(saveButton);              
        titlePanel.add(savePanel);
        
        titleLabel = new JLabel("Paste Your Tablature Here");
     //   titleLabel.setPreferredSize(new Dimension(100,25));
      //  titleLabel.setMinimumSize(new Dimension(200,100));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setBackground(new Color(33,150,243));
        titleLabel.setForeground(Color.white);
        titleLabel.setSize(new Dimension(100,20));
        titlePanel.add(titleLabel);
        //titlePanel.setPreferredSize(new Dimension(30,40));
       // titlePanel.setOpaque(true);
        //titlePanel.setBackground(new Color(33,150,243));
        
        clearPanel = new JPanel();
        clearPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20,0));
        clearPanel.setOpaque(false);
        
        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(33,150,243));
        clearButton.setForeground(new Color(224,224,224));
        clearButton.setFocusable(false);
        clearButton.addActionListener(this);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        //Button hover effects
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	clearButton.setBackground(new Color(224,224,224));
    	    	clearButton.setForeground(new Color(33,150,243));
    	    }
    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	clearButton.setBackground(new Color(33,150,243));
    	    	clearButton.setForeground(new Color(224,224,224));
    	    }
    	}); 
        clearPanel.add(clearButton);
        titlePanel.add(clearPanel);
        
        this.add(titlePanel);
        
        // generates the text field, sets size,font, and scrollability
        textField = new UndoRedoTextArea(); //Create empty text area (Undoable Text Area)
        textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textField.getDocument().addDocumentListener(new DocumentListener() { // Detect changes to the text area
            @Override
            public void changedUpdate(DocumentEvent arg0) { }
            
			@Override
			public void insertUpdate(DocumentEvent e) {
				String[] inputText = textField.getText().split("\n");
				tabList.setSelectedIndex(InstrumentDetection.detectInstrument(GetInput(inputText)));
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				String[] inputText = textField.getText().split("\n");
				tabList.setSelectedIndex(InstrumentDetection.detectInstrument(GetInput(inputText)));
			}
        });
        
        //textField.setFont(f);
        this.add(textField);
        scroll = new JScrollPane (textField);
        scroll.setPreferredSize(new Dimension(800, 500));
        scroll.setMinimumSize(new Dimension(480, 300));
        scroll.setSize(getPreferredSize());;
        this.add(scroll);
        
        detailsPanel = new JPanel();        
        detailsPanel.setLayout(new GridLayout(1,3));
        detailsPanel.setOpaque(false);
                        
        JPanel tabListPanel = new JPanel();
        tabListPanel.setLayout(new GridLayout(0, 1));
        tabListPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        tabListPanel.setOpaque(false);
        tabList = new JComboBox<Object>(tabTypes);
        tabList.setSelectedIndex(0);
        tabListPanel.add(tabList);
        
        JPanel songNamePanel = new JPanel();
        songNamePanel.setLayout(new GridLayout(0, 1));
        songNamePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        songNamePanel.setOpaque(false);
        songName = new JTextField();
        songName.setFont(songName.getFont().deriveFont(16f));
        //songName.setHorizontalAlignment(JTextField.CENTER);
        TextPrompt songNamePrompt = new TextPrompt("Song Name", songName,Show.FOCUS_LOST);
        songNamePrompt.setHorizontalAlignment(JTextField.CENTER);
        songNamePrompt.changeAlpha(0.8f);
        songNamePanel.add(songName);
        
        JPanel timeSignaturePanel = new JPanel();
        timeSignaturePanel.setLayout(new GridLayout(0, 1));
        timeSignaturePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        timeSignaturePanel.setOpaque(false);
        timeSignature = new JTextField();
        timeSignature.setFont(timeSignature.getFont().deriveFont(16f));
        //timeSignature.setHorizontalAlignment(JTextField.CENTER);
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
        convertButton.setOpaque(true);
        convertButton.setBorderPainted(false);
        //Button hover effects
        convertButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	convertButton.setBackground(new Color(224,224,224));
    	    	convertButton.setForeground(new Color(33,150,243));
    	    }
    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	convertButton.setBackground(new Color(33,150,243));
    	    	convertButton.setForeground(new Color(224,224,224));
    	    }
    	});
        
        JPanel togglepanel = new JPanel();
        togglepanel.setOpaque(false);
        sheetMusicToggle = new SteelCheckBox();
        
        sheetMusicToggle.setColored(true);
        sheetMusicToggle.setSelectedColor(ColorDef.CUSTOM_BLUE);
        sheetMusicToggle.setRised(false);
        sheetMusicToggle.setText("Sheet Music");
        sheetMusicToggle.setForeground(Color.white);
        sheetMusicToggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getID() == ActionEvent.ACTION_PERFORMED){
					TextInputContentPanel.scoremake = !TextInputContentPanel.scoremake;
				}
				//TextInputContentPanel.scoremake = e.getStateChange() == ItemEvent.SELECTED;
				//System.out.println(TextInputContentPanel.scoremake);
				if( TextInputContentPanel.scoremake && !CreateScore.isWindows()){
					JOptionPane.showMessageDialog(null,"We've detected you're running this application on MacOS, please read Section 6.5 of the User Manual before using this feature to prevent any errors");
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
        inputpanel.setOpaque(false);
        
        errorPanel = new JPanel();
        errorPanel.setLayout(new FlowLayout());
        errorText = new JLabel("");        
        errorText.setForeground(Color.red);
        errorPanel.add(errorText);
        errorPanel.setPreferredSize(new Dimension(100, 30));
        errorPanel.setOpaque(false);
        
        //adds the button container to the content panel
        this.add(detailsPanel);
        this.add(inputpanel);
        this.add(errorPanel);

        this.setOpaque(false);
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == convertButton && !Main.isInPopUp)
		{	
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
			
			input = GetInput(inputText); // Convert to double String ArrayList
			
			setTabType(tabList.getSelectedItem().toString());
			setTitle(songName.getText());
			setTimeSig(timeSignature.getText());
					
			errorText.setText("");
								
			//Detect if the text area is empty
			int lineLength = input.get(0).size();
			for (ArrayList<String> line : input) {
				if(line.size() != lineLength && line.size() != 0)
				{
					errorText.setText("Wrong Formatting");
					break;
				}
			}
						
			/*
			for (ArrayList<String> line : input) {
				for (String chr : line) {
					System.out.print(chr);
				}
				System.out.println("");
			}
			*/
			
			if (errorText.getText() == "")
			{
				Main.Convert(input, tabList.getSelectedIndex());
			}
		}
		else if(e.getSource() == clearButton && !Main.isInPopUp)
		{
			if(!Main.myFrame.textInputContentPanel.textField.getText().isEmpty())
				new ClearPopUp(Main.myFrame, "", "Clear Current Tablature");
		}
		else if(e.getSource() == saveButton && !Main.isInPopUp)
		{
			new SaveManager("", tabList.getSelectedIndex(), songName.getText(), timeSignature.getText(), textField.getText());
		}
		
	}
	
	private ArrayList<ArrayList<String>> GetInput (String[] textInput)
	{
		if(textField.getText().isEmpty())
		{
			return null;
		}
		else if (!textField.getText().contains("|"))
		{
			return null;
		}
		
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		
		for (String line : textInput) {
			if(line.length() > 1)
			{
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
			else
			{
				input.add(new ArrayList<String>());
			}
		}
		
		return input;
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

	public static String getTitle() {
		if(title.isEmpty())
			return "Title";
		return title;
	}

	public static void setTitle(String title) {
		TextInputContentPanel.title = title;
	}

	public static String getTabType() {
		return tabType;
	}

	public static void setTabType(String tabType) {
		TextInputContentPanel.tabType = tabType;
	}

	public static String getTimeSig() {
		if(timeSig.isEmpty())
			return "4/4";
		return timeSig;
	}

	public static void setTimeSig(String timeSig) {
		TextInputContentPanel.timeSig = timeSig;
	}
		
}
