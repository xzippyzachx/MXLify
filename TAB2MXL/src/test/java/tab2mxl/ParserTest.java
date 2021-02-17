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
		assertEquals("whole", Parser.noteType(1.0));
		assertEquals("half", Parser.noteType(1.0/2.0));
		
		assertEquals("quarter", Parser.noteType(1.0/4.0));
		assertEquals("eighth", Parser.noteType(1.0/8.0));
		assertEquals("semiquaver", Parser.noteType(1.0/16.0));
		assertEquals("demisemiquaver", Parser.noteType(1.0/32.0));
		assertEquals("hemidemisemiquaver", Parser.noteType(1.0/64.0));
		assertEquals("semihemidemisemiquaver", Parser.noteType(1.0/128));
		assertEquals("demisemihemidemisemiquaver", Parser.noteType(1.0/256));
		assertEquals("hemidemisemihemidemisemiquaver", Parser.noteType(1.0/512));
		assertEquals("semihemidemisemihemidemisemiquaver", Parser.noteType(1.0/1024));
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
