package gui_panel_testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import javax.swing.JLabel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui_panels.FileDropPanel;

public class FileDropPanelTest {
	
	static FileDropPanel testPanel;
	Color labelBackground = new Color(33,150,243);

	@BeforeAll
	static void setUp() throws Exception {
		testPanel = new FileDropPanel();
	}

	@Test
	void testElements() {
		assertNotNull(FileDropPanel.getDropFilePath());
		assertNotNull(testPanel.getDropPanel());
		assertNotNull(testPanel.getDropLoc());
		assertNotNull(testPanel.getDropLabel());
	}
	
	@Test
	void testDropLabel() {
		assertTrue(testPanel.getDropLabel() instanceof JLabel);
		
		JLabel dropLabel = testPanel.getDropLabel();
		assertEquals(dropLabel.getHorizontalAlignment(), JLabel.CENTER);
		assertEquals(dropLabel.getHorizontalTextPosition(), JLabel.CENTER);
		assertEquals(dropLabel.getAlignmentX(), JLabel.CENTER_ALIGNMENT);
		
		assertTrue(dropLabel.isOpaque());
		assertTrue(dropLabel.isVisible());
		
		assertTrue(dropLabel.getText().endsWith("Drop Tablature Text File"));
		assertEquals(dropLabel.getBackground(), labelBackground);
	}
	
	@Test
	void testDropLoc() {
		assertTrue(testPanel.getDropLoc() instanceof JLabel);
		
		JLabel dropLoc = testPanel.getDropLoc();
		
	//	assertEquals(dropLoc.getWidth(), 120);
	//	assertEquals(dropLoc.getHeight(), 300);
		
		assertNotNull(dropLoc.getIcon());
		assertEquals(dropLoc.getIcon().getIconHeight(),120);
		assertEquals(dropLoc.getIcon().getIconWidth(),140);
		
		assertEquals(dropLoc.getBackground(), Color.LIGHT_GRAY);
		
	}
	
	

}
