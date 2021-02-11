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
		fileGen.attributes(2,4,4,4, "G", tune);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<attributes>\n"
				+ "  <divisions>"+ 2 +"</divisions>\n"
				+ "  <key>\n"
				+ "    <fifths>0</fifths>\n"
				+ "    <mode>major</mode>\n"
				+ "  </key>\n"
				+ "  <time>\n"
				+ "    <beats>"+4+"</beats>\n"
				+ "    <beat-type>"+4+"</beat-type>\n"
				+ "  </time>\n"
				+ "  <staves>2</staves>\n"
				+ "  <clef>\n"
				+ "    <sign>"+"G"+"</sign>\n"
				+ "    <line>2</line>\n"
				+ "    <clef-octave-change>-1</clef-octave-change>\n"
				+ "  </clef>\n"
				+ "  <staff-details number = \"2\">\n"
				+ "    <staff-lines>" + tune.length + "</staff-lines>\n"
				+ "    <staff-tuning line=\"1\">\n"
				+ "      <tuning-step>" + tune[5] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"2\">\n"
				+ "      <tuning-step>" + tune[4] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"3\">\n"
				+ "      <tuning-step>" + tune[3] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"4\">\n"
				+ "      <tuning-step>" + tune[2] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"5\">\n"
				+ "      <tuning-step>" + tune[1] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"6\">\n"
				+ "      <tuning-step>" + tune[0] + "</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "  </staff-details>\n"
				+ "</attributes>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testAddNote() {
		setUp();
		fileGen.addNote(1,1,"E", "half", 1);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<note>\n"
				+ "  <pitch>\n"
				+ "    <step>" + "E" + "</step>\n"
				+ "    <octave>4</octave>\n"
				+ "  </pitch>\n"
				+ "  <duration>" + 1 + "</duration>\n"
				+ "  <type>" + "half" +"</type>\n"
				+ "  <stem>down</stem>\n"
				+ "  <staff>1</staff>\n"
				+ "</note>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testAddChord() {
		setUp();		
		char[] notes = new char[]{'E', 'B', 'G'};		
		fileGen.addChord(notes, "half", 1);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<note>\n"
				+ "  <pitch>\n"
				+ "    <step>" + "E" + "</step>\n"
				+ "    <octave>4</octave>\n"
				+ "  </pitch>\n"
				+ "  <duration>" + 1 + "</duration>\n"
				+ "  <type>" + "half" +"</type>\n"
				+ "  <stem>down</stem>\n"
				+ "  <staff>1</staff>\n"
				+ "</note>\n"
				+ "<note>\n"
				+ "  <chord/>\n"
				+ "  <pitch>\n"
				+ "    <step>" + "B" + "</step>\n"
				+ "    <octave>4</octave>\n"
				+ "  </pitch>\n"				
				+ "  <duration>" + 1 + "</duration>\n"
				+ "  <type>" + "half" +"</type>\n"
				+ "  <stem>down</stem>\n"
				+ "  <staff>1</staff>\n"
				+ "</note>\n"
				+ "<note>\n"
				+ "  <chord/>\n"
				+ "  <pitch>\n"
				+ "    <step>" + "G" + "</step>\n"
				+ "    <octave>4</octave>\n"
				+ "  </pitch>\n"				
				+ "  <duration>" + 1 + "</duration>\n"
				+ "  <type>" + "half" +"</type>\n"
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
