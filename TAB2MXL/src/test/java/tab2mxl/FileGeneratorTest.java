package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileGeneratorTest {

	static FileGenerator fileGen;
	
	static void setUp() {
		fileGen = new FileGenerator("tester.musicxml");
	}
	
	@Test
	void testFileGenerator() {
		setUp();
		assertNotNull(fileGen.myWriter);
		assertNotNull(fileGen.saveFile);
	}

	@Test
	void testAddInfo1() {
		setUp();
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
		setUp();
		fileGen.openPart(1);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<part id=\"P" + 1 + "\">\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testClosePart() {
		setUp();
		fileGen.closePart();
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "</part>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testAttributes() {
		setUp();
		
		String[] tune = new String[]{"E", "B", "G", "D", "A", "E"};
		
		fileGen.attributes(2,4,4,4, "G", tune);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<attributes>\n"
				+ "  <divisions>"+ 2 +"</divisions>\n"
				+ "  <key>\n"
				+ "    <fifths>+0</fifths>\n"
				+ "    <mode>major</mode>\n"
				+ "  </key>\n"
				+ "  <time>\n"
				+ "    <beats>"+4+"</beats>\n"
				+ "    <beat-type>"+4+"</beat-type>\n"
				+ "  </time>\n"
				+ "  <staves>2</staves>\n"
				+ "  <clef>\n"
				+ "    <sign>"+"G"+"</sign>\n"
				+ "    <line>+2</line>\n"
				+ "    <clef-octave-change>-1</clef-octave-change>\n"
				+ "  </clef>\n"
				+ "  <staff-details number = \"2\">\n"
				+ "    <staff-lines>" + tune.length + "</staff-lines>\n"
				+ "    <staff-tuning line=\"1\">\n"
				+ "      <tuning-step>" + tune[0] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"2\">\n"
				+ "      <tuning-step>" + tune[1] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"3\">\n"
				+ "      <tuning-step>" + tune[2] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"4\">\n"
				+ "      <tuning-step>" + tune[3] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"5\">\n"
				+ "      <tuning-step>" + tune[4] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"6\">\n"
				+ "      <tuning-step>" + tune[5] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "  </staff-details>\n"
				+ "</attributes>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
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
			fileContent = new Scanner(new File("tester.musicxml")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(fileContent);
		return fileContent;
	}	

}
