package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StringTuningTest {

	static StringTuning tunning;
	static String[] tune = {"e", "B", "G", "D", "A", "E"};; 
	
	@BeforeAll
    static void setUp() {
		//String[] tune = {"e", "B", "G", "D", "A", "E"};
		int[] oct = new int[]{1, 2, 3, 4, 5, 6};
	     tunning = new StringTuning(tune, 6, oct);
	}
	
	@Test
	void testDefaultTuning() {
		//String[] empty = {};
		String[] tuneA = {"B", "G", "D", "A", "E"};
		String[] tuneB = {"e", "B", "G", "D", "A", "E"};
		String[] tuneC = {"e", "B", "G", "D", "A", "E", "B"};
		String[] tuneD = {"e", "B", "G", "D", "A", "E", "B", "F#"};
		String[] tuneE = {"e", "B", "G", "D", "A", "E", "B", "F#", "C#"};
		
		assertArrayEquals(StringTuning.getDefaultTuning(5), tuneA);
		assertArrayEquals(StringTuning.getDefaultTuning(6), tuneB);
		assertArrayEquals(StringTuning.getDefaultTuning(7), tuneC);
		assertArrayEquals(StringTuning.getDefaultTuning(8), tuneD);
		assertArrayEquals(StringTuning.getDefaultTuning(9), tuneE);
	}
	
	void testStringNotesHashMap() {
		fail("Not yet implemented");
	}
	
	@Test
	void testGetNote() {
	
		// GUITAR NOTES
		assertEquals("G", tunning.getNote("E", 3, 1));     //Testing notes from different positions
		assertEquals("F", tunning.getNote("E", 1, 6));
		assertEquals("C#", tunning.getNote("E", 21, 1));
		assertEquals("C#", tunning.getNote("D", 11, 4));
		assertEquals("B", tunning.getNote("G", 4, 3));
		
	}

}
