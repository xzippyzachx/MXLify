package gui_panel_testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gui_panels.MyFrame;

public class MyFrameTest {
	
	static MyFrame testFrame;
	
	@BeforeAll
	static void setUp() {
		testFrame = new MyFrame();
	}

	@Test
	void testPanels() {
		assertNotNull(testFrame.mainContentPanel);
		assertNotNull(testFrame.textInputContentPanel);
		assertNotNull(testFrame.fileUploadContentPanel);
	}
	
	@Test
	void testTitle() {
		assertEquals(testFrame.getTitle(), "MXLify");
	}
	
	@Test
	void testSize() {
		assertEquals(testFrame.getWidth(), 1280);
		assertEquals(testFrame.getHeight(), 720);
	}
}

