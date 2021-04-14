package gui_popups;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;

import gui_panels.MyFrame;
import tab2mxl.Main;

public class LoadPopUp extends PopupFactory implements ActionListener{

	Popup popup;
	
	public JButton yesButton;
	public JButton noButton;
	
	String input;
	String instrument;
	String songTitle;
	String timeSig;
	String custom;
	MyFrame myFrame;
		
	public LoadPopUp (Component owner, String input, String instrument, String songTitle, String timeSig, String custom, String message){
		
		this.input = input;
		this.instrument = instrument;
		this.songTitle = songTitle;
		this.timeSig = timeSig;
		this.custom = custom;
		myFrame = (MyFrame) owner;
		
        JPanel panel = new JPanel(); 
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        
        JPanel titlepanel = new JPanel();
        titlepanel.setLayout(new FlowLayout());
        JLabel title = new JLabel(message);
        title.setForeground(new Color(232, 32, 21));
        title.setFont(new Font(title.getName(), Font.BOLD, 14));
        Border titlePadding = BorderFactory.createEmptyBorder(15, 10, 0, 10);
        title.setBorder(titlePadding);
        titlepanel.add(title);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());     
        
        yesButton = new JButton("Yes");
        yesButton.setBorderPainted(false);
        yesButton.setBackground(new Color(33,150,243));
        yesButton.setForeground(new Color(224,224,224));
        yesButton.setFocusable(false);
        yesButton.addActionListener(this);
        yesButton.setOpaque(true);
        yesButton.setBorderPainted(false);
        
        //Button hover effects
        yesButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	yesButton.setBackground(new Color(224,224,224));
    	    	yesButton.setForeground(new Color(33,150,243));
    	    }

    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	yesButton.setBackground(new Color(33,150,243));
    	    	yesButton.setForeground(new Color(224,224,224));
    	    }
    	});        
        inputPanel.add(yesButton);        
        
        noButton = new JButton("No");
        noButton.setBorderPainted(false);
        noButton.setBackground(new Color(33,150,243));
        noButton.setForeground(new Color(224,224,224));
        noButton.setFocusable(false);
        noButton.addActionListener(this);
        noButton.setOpaque(true);
        noButton.setBorderPainted(false);
        
        //Button hover effects
        noButton.addMouseListener(new MouseAdapter() {
    	    public void mouseEntered(MouseEvent evt) {
    	    	noButton.setBackground(new Color(224,224,224));
    	    	noButton.setForeground(new Color(33,150,243));
    	    }

    	    public void mouseExited(MouseEvent evt) {
    	    	noButton.setBackground(new Color(33,150,243));
    	    	noButton.setForeground(new Color(224,224,224));
    	    }
    	});        
        inputPanel.add(noButton);
        
        Border buttonPadding = BorderFactory.createEmptyBorder(0, 10, 0, 10);
        inputPanel.setBorder(buttonPadding);
        
        panel.add(titlepanel);
        panel.add(inputPanel);
        
        Point pt = myFrame.mainContentPanel.getLocationOnScreen();
        Dimension frameSize = myFrame.getBounds().getSize();
        
        popup = this.getPopup(myFrame, panel, pt.x + frameSize.width/2 - 150, pt.y + frameSize.height/2 - 50);
        popup.show();
        Main.isInPopUp = true;
}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == yesButton)
		{			
			myFrame.textInputContentPanel.instrumentList.setSelectedIndex(Integer.parseInt(instrument));
			myFrame.textInputContentPanel.songName.setText(songTitle);
			myFrame.textInputContentPanel.timeSignature.setText(timeSig);
			myFrame.fileUploadContentPanel.customTextArea.focusGained(null);
			myFrame.fileUploadContentPanel.customTextArea.setText(custom);
			myFrame.textInputContentPanel.textField.focusGained(null);
			myFrame.textInputContentPanel.textField.setText(input);
			popup.hide();
			Main.isInPopUp = false;
		}
		else
		{
			popup.hide();
			Main.isInPopUp = false;
		}
	}	
	
}
