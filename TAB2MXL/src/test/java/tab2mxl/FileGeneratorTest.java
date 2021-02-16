package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class FileGeneratorTest {

	static FileGenerator fileGen;
	String fileContent;
	String expected;
	
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
		setUp();
		fileGen.openPart(3);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<part id=\"P" + 3 + "\">\n"
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
		int[] oct = new int[] {1, 2, 3, 4, 5, 6};
		fileGen.attributes(2,4,4,4, "G", tune, oct);
		fileGen.end();
		String fileContent = this.readFile();
		System.out.println(fileContent);
		String expected = "<attributes>\n"
				+ "  <divisions>2</divisions>\n"
				+ "  <key>\n"
				+ "    <fifths>0</fifths>\n"
				+ "    <mode>major</mode>\n"
				+ "  </key>\n"
				+ "  <time>\n"
				+ "    <beats>4</beats>\n"
				+ "    <beat-type>4</beat-type>\n"
				+ "  </time>\n"
				+ "  <clef>\n"
				+ "    <sign>G</sign>\n"
				+ "    <line>2</line>\n"
				+ "    <clef-octave-change>-1</clef-octave-change>\n"
				+ "  </clef>\n"
				+ "  <staff-details number = \"2\">\n"
				+ "    <staff-lines>6</staff-lines>\n"
				+ "    <staff-tuning line=\"1\">\n"
				+ "      <tuning-step>E</tuning-step>\n"
				+ "      <tuning-octave>6</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"2\">\n"
				+ "      <tuning-step>A</tuning-step>\n"
				+ "      <tuning-octave>5</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"3\">\n"
				+ "      <tuning-step>D</tuning-step>\n"
				+ "      <tuning-octave>4</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"4\">\n"
				+ "      <tuning-step>G</tuning-step>\n"
				+ "      <tuning-octave>3</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"5\">\n"
				+ "      <tuning-step>B</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"6\">\n"
				+ "      <tuning-step>E</tuning-step>\n"
				+ "      <tuning-octave>1</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "  </staff-details>\n"
				+ "</attributes>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testAddNote() {
		setUp();
		fileGen.addNote(1,1,"E", "half", 1, 3);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<note>\n"
				+ "  <pitch>\n"
				+ "    <step>E</step>\n"
				+ "    <octave>3</octave>\n"
				+ "  </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>half</type>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>1</string>\n"
				+ "      <fret>1</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "  <stem>down</stem>\n"
				+ "  <staff>1</staff>\n"
				+ "</note>\n"
				+ "</score-partwise>";
		assertEquals(expected, fileContent); 
	}

	@Test
	void testAddChord() {
		setUp();		
		char[] notes = new char[]{'E','B'};		
		int[] frets = new int[] {1,2};
		int[] lines = new int[] {1,2};
		int[] oct = new int[] {1,2,3};
		fileGen.addChord(notes, "half", 1,oct,frets,lines);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = 
				"<note>\n"
				+ "  <pitch>\n"
				+ "    <step>B</step>\n"
				+ "    <octave>2</octave>\n"
				+ "    </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>half</type>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>2</string>\n"
				+ "      <fret>2</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "  <stem>down</stem>\n"
				+ "  <staff>1</staff>\n"
				+ "</note>\n"
				+ "<note>\n"
				+ "  <chord/>\n"
				+ "  <pitch>\n"
				+ "    <step>E</step>\n"
				+ "    <octave>1</octave>\n"
				+ "    </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>half</type>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>1</string>\n"
				+ "      <fret>1</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "  <stem>down</stem>\n"
				+ "  <staff>1</staff>\n"
				+ "</note>\n"
				+ "</score-partwise>";
				
				
				
		assertEquals(expected, fileContent); 
	    }

	@Test
	void testOpenMeasure() {
		setUp();
		fileGen.openMeasure(4);
		fileGen.end();
		String fileContent = this.readFile();
		String expected3 = "<measure number=\"" + 4 + "\">\n" + "</score-partwise>";
		assertEquals(expected3, fileContent); 
	}

	@Test
	void testCloseMeasure() {
		setUp();
		fileGen.closeMeasure();
		fileGen.end();
		String fileContent = this.readFile();
		String expected1 =  "</measure>\n" 
				 + "</score-partwise>";
		assertEquals(expected1, fileContent); 
	}

	@Test
	void testEnd() {
		setUp();
		fileGen.end();
		String fileContent = this.readFile();
		String expected2 = "</score-partwise>";
		assertEquals(expected2, fileContent); 
	}
	
	@SuppressWarnings("resource")
	String readFile() {
		String fileContent = null;
		try {
			fileContent = new Scanner(new File("tester.musicxml")).useDelimiter("\\Z").next();
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
