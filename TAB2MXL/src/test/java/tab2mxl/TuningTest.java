package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TuningTest {

	static Tuning tunning;
	static String[] tune = {"e", "B", "G", "D", "A", "E"};; 
	
	@BeforeAll
    static void setUp() {
		//String[] tune = {"e", "B", "G", "D", "A", "E"};
	     tunning = new Tuning(tune, 6);
	}
	
	@Test
	void testDefaultTuning() {
		//String[] empty = {};
		String[] tuneA = {"E", "B", "G", "D", "A", "E"};
		String[] tuneB = {"E", "B", "G", "D", "A", "E", "B"};
		String[] tuneC = {"E", "B", "G", "D", "A", "E", "B", "F#"};
		String[] tuneD = {"E", "B", "G", "D", "A", "E", "B", "F#", "C#"};
		
		assertEquals(Tuning.getDefaultTuning(6), tuneA);
		assertEquals(Tuning.getDefaultTuning(7), tuneB);
		assertEquals(Tuning.getDefaultTuning(8), tuneC);
		assertEquals(Tuning.getDefaultTuning(9), tuneD);
	}
	
	@Test
	void testGetNote() {
	
		// GUITAR NOTES
		assertEquals("G", tunning.getNote("E", 3));     //Testing notes from different positions
		assertEquals("F", tunning.getNote("E", 1));
		assertEquals("C#", tunning.getNote("E", 21));
		
		assertEquals("C#", tunning.getNote("D", 11));
		assertEquals("B", tunning.getNote("G", 4));
		
	}

}
