package tab2mxl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class MainContentPanel extends JPanel implements ActionListener {
	
	JPanel titlePanel;
	JLabel titleLabel;
	
	JPanel inputpanel;
	JButton fileUploadButton;
	JButton textInputButton;
	
	MainContentPanel(){	
	
	// creates main content panel, lets layout to vertical, adds padding and sets it as Content Pane
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.setBorder(padding);
        
        // creates Title Container and adds label to the Content panel
        titlePanel = new JPanel();
        titleLabel = new JLabel("Text Tablature to MusicXML");
        titlePanel.add(titleLabel);
        this.add(titlePanel);  
        
     // creates the container for the button, generates the button and sets an action on click
        inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        
        fileUploadButton = new JButton("Upload Text File");
        fileUploadButton.setFocusable(false);
        fileUploadButton.addActionListener(this);
        inputpanel.add(fileUploadButton);
        
        textInputButton = new JButton("Input Text");
        textInputButton.setFocusable(false);
        textInputButton.addActionListener(this);
        inputpanel.add(textInputButton);
        
        Border buttonPadding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        inputpanel.setBorder(buttonPadding);
        this.add(inputpanel);
        
        this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fileUploadButton) {  //Button click			
			Main.myFrame.FileUploadScreen();			
		}
		else if (e.getSource() == textInputButton) {  //Button click			
			Main.myFrame.TextInputScreen();			
		}
	}
	
}