package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;

class FileGeneratorTest {

	static FileGenerator fileGen;
	String fileContent;
	String expected;
	
	@BeforeAll
	static void setUp() {
		fileGen = new FileGenerator();
	}
	
	@Test
	void testFileGenerator() {
		assertNotNull(fileGen.myWriter);
		assertNotNull(fileGen.saveFile);
	}

	@Test
	void testAddInfo1() {
		fileGen.addInfo("Example Title");
		fileGen.end();
		
		fileContent = this.readFile();
		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<score-partwise version=\"3.1\">\n"
				+ "  <work>\n"
				+ "    <work-title>Example Title</work-title>\n"
				+ "  </work>\n"
				+ "  <part-list>\n"
				+ "    <score-part id=\"P1\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "  </part-list>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testOpenPart() {
		this.openWriter();
		fileGen.openPart(3);
		fileGen.end();
		
		fileContent = this.readFile();
		expected = "  <part id=\"P3\">\n"
				        + "</score-partwise>";
		assertEquals(expected, fileContent); 
	}

	@Test
	void testClosePart() {
		fail("Not yet implemented");
	}

	@Test
	void testAttributes() {
		fail("Not yet implemented");
	}

	@Test
	void testAddNote() {
		fail("Not yet implemented");
	}

	@Test
	void testAddChord() {
		fail("Not yet implemented");
	}

	@Test
	void testOpenMeasure() {
		fail("Not yet implemented");
	}

	@Test
	void testCloseMeasure() {
		fail("Not yet implemented");
	}

	@Test
	void testEnd() {
		fail("Not yet implemented");
	}
	
	@SuppressWarnings("resource")
	String readFile() {
		String fileContent = null;
		try {
			fileContent = new Scanner(new File("output.musicxml")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(fileContent + "\n\n--------------------------------------\n");
		return fileContent;
	}
	
	void openWriter(){
		try {
			fileGen.myWriter = new FileWriter(fileGen.saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
