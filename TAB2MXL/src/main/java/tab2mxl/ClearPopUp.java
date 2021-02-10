package tab2mxl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
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
        title.setForeground(Color.red);
        title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
        Border titlePadding = BorderFactory.createEmptyBorder(10, 10, 0, 10);
        title.setBorder(titlePadding);
        titlepanel.add(title);
        
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());
        
        yesButton = new JButton("Yes");
        //yesButton.setBackground(new Color(33,150,243));
        //yesButton.setForeground(new Color(224,224,224));
        yesButton.setFocusable(false);
        yesButton.addActionListener(this);
        inputpanel.add(yesButton);
        
        noButton = new JButton("No");
        //noButton.setBackground(new Color(33,150,243));
        //noButton.setForeground(new Color(224,224,224));
        noButton.setFocusable(false);
        noButton.addActionListener(this);
        inputpanel.add(noButton);
        
        panel.add(titlepanel);
        panel.add(inputpanel);
        
        Dimension frameSize = myFrame.getBounds().getSize();
        Dimension panelSize = panel.getBounds().getSize();
        
        popup = this.getPopup(myFrame, panel, frameSize.width/2 - panelSize.width/2, frameSize.height/2 - panelSize.height/2);
        popup.show();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == yesButton)
		{
			myFrame.textInputContentPanel.textField.setText(input);
			popup.hide();
		}
		else
		{
			popup.hide();
		}
	}
	
}
