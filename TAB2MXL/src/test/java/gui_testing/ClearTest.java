package gui_testing;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.UndoRedoTextArea;
import gui_panels.MyFrame;
import gui_popups.ClearPopUp;
import tab2mxl.Main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class ClearTest {

	static MyFrame app;
	static UndoRedoTextArea textArea;
	static JLabel errorText;
	static JButton clearButton;
	
	static ClearPopUp popup;
	static JButton yesClear;
	static JButton noClear;
	static Robot r;
	
	@BeforeAll
	static void setUp() throws Exception {
		r = new Robot();
		
		new Main();
		Main.main(null);
		app = Main.myFrame;
		
		textArea = (UndoRedoTextArea) app.textInputContentPanel.textField;
		
		errorText = app.textInputContentPanel.errorText;
		clearButton = app.textInputContentPanel.clearButton;
	}
	
	@BeforeEach
	void ResetMouse() {
		r.mouseMove(0, 0);
	}


	@Test
	void testClearAccept() throws AWTException {
		
		textArea.focusGained(null);
		textArea.setText("--------");
		
		assertEquals(clearButton.getBackground(), new Color(33,150,243));
		
		delay();
		
		click(clearButton, 1000);
		assertEquals(clearButton.getBackground(), new Color(224,224,224));
		
		delay();
		
		getPopUpInfo();
		
		click(yesClear, 1000);
		
		delay();
		
		assertEquals(textArea.getText(), "");
	}
	
	@Test
	void testClearDeny() throws AWTException {
		delay();
		
		textArea.focusGained(null);
		textArea.setText("--------");
		
		assertEquals(clearButton.getBackground(), new Color(33,150,243));
		
		delay();
		
		click(clearButton, 1000);
		
		assertEquals(clearButton.getBackground(), new Color(224,224,224));
		
		delay();
		
		getPopUpInfo();
		
		click(noClear, 1000);
		
		delay();
		
		assertEquals(textArea.getText(), "--------");
	}
	
	@Test
	void testClearEmpty() throws AWTException {
		textArea.focusGained(null);
		
		assertEquals(clearButton.getBackground(), new Color(33,150,243));
		
		delay();
		
		click(clearButton, 1000);
		
		assertEquals(clearButton.getBackground(), new Color(224,224,224));
		
		delay();
		
		getPopUpInfo();
		
		assertNull(popup);
	}
	
	public static void click(JComponent button, int millis) throws AWTException
	{
	    Point p = button.getLocationOnScreen();
	    Robot r = new Robot();
	    r.mouseMove(p.x + button.getWidth() / 2, p.y + button.getHeight() / 2);
	    r.mousePress(InputEvent.BUTTON1_MASK);
	    try { Thread.sleep(millis); } catch (Exception e) {}
	    r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void getPopUpInfo() {
		popup = app.textInputContentPanel.clearPopUp;
		if (popup == null) return;
		
		yesClear = popup.yesButton;
		noClear = popup.noButton;
	}
	
	public void delay() {
		try { Thread.sleep(500); } catch (Exception e) {}
	}
	
}
