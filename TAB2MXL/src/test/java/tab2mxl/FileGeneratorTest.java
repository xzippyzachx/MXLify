package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileGeneratorTest {

	static FileGenerator fileGen;
	
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
		String fileContent = this.readFile();
		
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
		//assertTrue(fileContent.equals(expected));
	}

	@Test
	void testOpenPart() {
		fail("Not yet implemented");
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
		System.out.println(fileContent);
		return fileContent;
	}
	

}
