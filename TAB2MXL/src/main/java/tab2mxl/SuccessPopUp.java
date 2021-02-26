package tab2mxl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;

public class SuccessPopUp extends PopupFactory implements ActionListener{

	Popup popup;
	
	JButton okButton;
	JButton openButton;
	
	MyFrame myFrame;
	String path;
	
	SuccessPopUp (Component owner, String path){
				
		myFrame = (MyFrame) owner;
		this.path = path;
		
        JPanel panel = new JPanel(); 
        panel.setPreferredSize(new Dimension(300, 100));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        
        JPanel titlepanel = new JPanel();
        titlepanel.setLayout(new FlowLayout());
        JLabel title = new JLabel("Conversion Was Successful");
        title.setForeground(new Color(110,199,56));
        title.setFont(new Font(title.getName(), Font.BOLD, 14));
        Border titlePadding = BorderFactory.createEmptyBorder(15, 10, 0, 10);
        title.setBorder(titlePadding);
        titlepanel.add(title);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        //Ok button
        okButton = new JButton("Ok");        
        okButton.setBorderPainted(false);
        okButton.setBackground(new Color(33,150,243));
        okButton.setForeground(new Color(224,224,224));
        okButton.setFocusable(false);
        okButton.addActionListener(this);
        
        //Button hover effects
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	okButton.setBackground(new Color(60,160,243));
    	    	okButton.setForeground(Color.black);
    	    }

    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	okButton.setBackground(new Color(33,150,243));
    	    	okButton.setForeground(new Color(224,224,224));
    	    }
    	});   
        inputPanel.add(okButton);
        
        //Open button
        openButton = new JButton("Open");        
        openButton.setBorderPainted(false);
        openButton.setBackground(new Color(33,150,243));
        openButton.setForeground(new Color(224,224,224));
        openButton.setFocusable(false);
        openButton.addActionListener(this);
        
        //Button hover effects
        openButton.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseEntered(java.awt.event.MouseEvent evt) {
    	    	openButton.setBackground(new Color(60,160,243));
    	    	openButton.setForeground(Color.black);
    	    }

    	    public void mouseExited(java.awt.event.MouseEvent evt) {
    	    	openButton.setBackground(new Color(33,150,243));
    	    	openButton.setForeground(new Color(224,224,224));
    	    }
    	});   
        inputPanel.add(openButton);
        
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
		if(e.getSource() == okButton)
		{
			popup.hide();
			Main.isInPopUp = false;
		}
		else if(e.getSource() == openButton)
		{
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().open(new File(path).getParentFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			popup.hide();
			Main.isInPopUp = false;
		}
	}	
}
