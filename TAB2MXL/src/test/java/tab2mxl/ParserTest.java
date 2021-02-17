package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserTest {

	@BeforeAll
	static void setUp() {
		File file = new File("TestInput_Guitar1.txt");
	}
	
	//@Test
	void testParser() {
		fail("Not yet implemented");
	}

	@Test
	void testNoteType() {
		assertEquals("whole", Parser.noteType(1));
		assertEquals("half", Parser.noteType(0.5));
		
		assertEquals("quarter", Parser.noteType(0.25));
		assertEquals("eighth", Parser.noteType(0.125));
		assertEquals("32nd", Parser.noteType(0.03125));
		assertEquals("64th", Parser.noteType(0.015625));
		
		assertEquals("128th", Parser.noteType(1.0/128));
		assertEquals("256th", Parser.noteType(1.0/256));
		assertEquals("512th", Parser.noteType(1.0/512));
		assertEquals("1024th", Parser.noteType(1.0/1024));
	}
	
	
	
	@Test
	void testAddTitle() {
		Parser.misc = new HashMap<String, String>();
		Parser.addTitle("Test Title");
		
		assertEquals(Parser.misc.get("Title"), "Test Title");
	}

	@Test
	void testAddTabType() {
		Parser.misc = new HashMap<String, String>();
		Parser.addTabType("Guitar");
		
		assertEquals(Parser.misc.get("TabType"), "Guitar");
	}

	@Test
	void testAddTime() {
		Parser.misc = new HashMap<String, String>();
		Parser.addTime("4");
		
		assertEquals(Parser.misc.get("TimeSig"), "4");
	}

}
