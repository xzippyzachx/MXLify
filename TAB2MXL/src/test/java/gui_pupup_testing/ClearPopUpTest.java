package gui_pupup_testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui_popups.ClearPopUp;
import tab2mxl.Main;
import gui_testing.ClearTest;

class ClearPopUpTest {

	static ClearPopUp popup;
	
	@BeforeAll
	static void setUp() throws Exception {
		new Main();
		Main.main(null);
		try { Thread.sleep(2000); } catch (Exception e) {}
		popup = new ClearPopUp(Main.myFrame, "", "Clear Current Tablature");
		try { Thread.sleep(2000); } catch (Exception e) {}
	}

	@Test
	void test() throws AWTException {
		
		assertNotNull(popup);
		assertNotNull(popup.noButton);
		assertNotNull(popup.noButton);
		try { Thread.sleep(1000); } catch (Exception e) {}
		assertTrue(Main.isInPopUp);
		
		assertEquals(popup.noButton.getBackground(), new Color(33,150,243));
		
		ClearTest.click(popup.noButton, 1000);
		try { Thread.sleep(1000); } catch (Exception e) {}
		
		
		assertFalse(Main.isInPopUp);
		
	}
	
	

}
