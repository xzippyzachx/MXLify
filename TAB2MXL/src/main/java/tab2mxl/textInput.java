package tab2mxl;

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

public class textInput extends JFrame implements ActionListener {
	private  JPanel ContentPanel;
	private static  JTextArea textfield;
	private JPanel titlePanel;
	private JLabel titleLabel;
	private JScrollPane scroll;
	private JPanel inputpanel;
	private JButton button;
	
	textInput(){
	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Throwable ex) { }  
	
	// creates main content panel, lets layout to vertical, adds padding and sets it as Content Pane
		ContentPanel = new JPanel();
		ContentPanel.setLayout(new BoxLayout(ContentPanel, BoxLayout.Y_AXIS));
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ContentPanel.setBorder(padding);
        this.setContentPane(ContentPanel);
        
        // creates Title Container and adds label to the Content panel
        titlePanel = new JPanel();
        titleLabel = new JLabel("Paste Your Tab Here");
        titlePanel.add(titleLabel);
        ContentPanel.add(titlePanel);
        
        // generates the text field, sets size,font, and scrollability
        textfield = new JTextArea(25,60);
        textfield.setFont(Font.getFont(Font.MONOSPACED));
        ContentPanel.add(textfield);
        scroll = new JScrollPane (textfield, 
     		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ContentPanel.add(scroll);
        
        // creates the container for the button, generates the button and sets an action on click
        inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        button = new JButton("Upload Tabulature!");
        button.addActionListener(this);
        inputpanel.add(button);
        Border buttonPadding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        inputpanel.setBorder(buttonPadding);
        
        //adds the button container to the contentpanel
        ContentPanel.add(inputpanel);
        
        this.pack();
        this.setVisible(true);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(textfield.getText());
		
	}
	
}