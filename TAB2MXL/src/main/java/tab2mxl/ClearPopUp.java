package tab2mxl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;

public class ClearPopUp extends PopupFactory implements ActionListener{

	Popup popup;
	
	JButton yesButton;
	JButton noButton;
	
	String input;
	MyFrame myFrame;
	
		
	ClearPopUp (Component owner, String input){
				
		
		
		this.input = input;
		myFrame = (MyFrame) owner;
		
        JPanel panel = new JPanel(); 
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        
        JPanel titlepanel = new JPanel();
        titlepanel.setLayout(new FlowLayout());
        JLabel title = new JLabel("Override Current Tablature");
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
        
        //Button hover effects
        yesButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	yesButton.setBackground(new Color(60,160,243));
    	    	yesButton.setForeground(Color.black);
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
        
        //Button hover effects
        noButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	noButton.setBackground(new Color(60,160,243));
    	    	noButton.setForeground(Color.black);
    	    }

    	    public void mouseExited(java.awt.event.MouseEvent evt) {
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
