package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DrumTuningTest {
	
	static DrumTuning tune;
	static String[] drums = {"BD","FT","SN","T2","HT","Rd","HH","C"};
	
	@BeforeAll
	static void setUp() {
		tune = new DrumTuning(drums, drums.length);
	}
	
	@Test
	void testDrumSupport() {
		assertEquals(true, DrumTuning.drumSupportCheck("C"));
		assertEquals(true, DrumTuning.drumSupportCheck("HH"));
		assertEquals(true, DrumTuning.drumSupportCheck("BD"));
		assertEquals(false, DrumTuning.drumSupportCheck("X"));
		assertEquals(false, DrumTuning.drumSupportCheck("Hf"));
		assertEquals(false, DrumTuning.drumSupportCheck("C2"));	
	}
	
	@Test
	void testGetNote() {
		assertEquals("F", tune.getNote("BD"));
		assertEquals("A", tune.getNote("FT"));
		assertEquals("C", tune.getNote("SN"));
		assertEquals("D", tune.getNote("T2"));
		assertEquals("E", tune.getNote("HT"));
		assertEquals("F", tune.getNote("Rd"));
		assertEquals("G", tune.getNote("HH"));
		assertEquals("A", tune.getNote("C"));
		assertEquals(null, tune.getNote("C2"));
		assertEquals(null, tune.getNote("Hf"));
		assertEquals(null, tune.getNote("K"));
	}
	
	@Test
	void testGetOctave() {
		assertEquals(4, tune.getOctave("BD"));
		assertEquals(4, tune.getOctave("FT"));
		assertEquals(5, tune.getOctave("SN"));
		assertEquals(5, tune.getOctave("T2"));
		assertEquals(5, tune.getOctave("HT"));
		assertEquals(5, tune.getOctave("Rd"));
		assertEquals(5, tune.getOctave("HH"));
		assertEquals(5, tune.getOctave("C"));
		assertEquals(-1, tune.getOctave("C2"));
		assertEquals(-1, tune.getOctave("Hf"));
		assertEquals(-1, tune.getOctave("K"));
	}
	
	@Test
	void testGetID() {
		assertEquals("P1-I36", tune.getID("BD", "o"));
		assertEquals("P1-I42", tune.getID("FT", "o"));
		assertEquals("P1-I39", tune.getID("SN", "o"));
		assertEquals("P1-I46", tune.getID("T2", "o"));
		assertEquals("P1-I48", tune.getID("HT", "o"));
		assertEquals("P1-I47", tune.getID("HH", "o"));
		assertEquals("P1-I43", tune.getID("HH", "x"));
		assertEquals("P1-I52", tune.getID("Rd", "x"));
		assertEquals("P1-I50", tune.getID("C", "x"));
		assertEquals(null, tune.getID("K", "o"));
		assertEquals(null, tune.getID("Hf", "x"));
		assertEquals(null, tune.getID("C2", "x"));
	}
	
	@Test
	void testGetVoice() {
		assertEquals(2, DrumTuning.getVoice("BD"));
		assertEquals(1, DrumTuning.getVoice("FT"));
		assertEquals(1, DrumTuning.getVoice("SN"));
		assertEquals(1, DrumTuning.getVoice("T2"));
		assertEquals(1, DrumTuning.getVoice("HT"));
		assertEquals(1, DrumTuning.getVoice("HH"));
		assertEquals(1, DrumTuning.getVoice("Rd"));
		assertEquals(1, DrumTuning.getVoice("C"));
		assertEquals(-1, DrumTuning.getVoice("K"));
		assertEquals(-1, DrumTuning.getVoice("Hf"));
		assertEquals(-1, DrumTuning.getVoice("C2"));
	}	
}
