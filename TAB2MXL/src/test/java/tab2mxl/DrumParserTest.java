package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DrumParserTest {
	
	String fileContent;
	String expected;
	
	@BeforeAll
	static void setUp() throws Exception {
		new Main();
		Main.main(null);
	}

	@Test
	void test() {
	
	
	String path = "ParserTests/tester.musicxml";
	
	String textField = "C |x---------------|--------x-------|x---------------|--------x-------|\n"
			+ "HH|--x-x-x-x-x-x-x-|----------------|--x-x-x-x-x-x-x-|----------------|\n"
			+ "SD|----o-------o---|oooo------------|----o-------o---|oooo------------|\n"
			+ "HT|----------------|----oo----------|----------------|----oo----------|\n"
			+ "T |----------------|------oo--------|----------------|----oo----------|\n"
			+ "T2|o-------o-------|o-------o-------|----------------|----oo----------|\n";
	

	String[] inputText = textField.split("\n");
	ArrayList<ArrayList<String>> input = GetInput(inputText);
	DrumParser.path = path;
	new DrumParser(input);
	
	
	fileContent = this.readFile(path);
	expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<score-partwise version=\"3.1\">\n"
			+ "  <work>\n"
			+ "    <work-title>Title</work-title>\n"
			+ "  </work>\n"
			+ "  <part-list>\n"
			+ "    <score-part id=\"P1\">\n"
			+ "      <part-name>Drumset</part-name>\n"
			+ "      <score-instrument id=\"P1-I36\">\n"
			+ "        <instrument-name>Bass Drum 1</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I37\">\n"
			+ "        <instrument-name>Bass Drum 2</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I38\">\n"
			+ "        <instrument-name>Side Stick</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I39\">\n"
			+ "        <instrument-name>Snare</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I42\">\n"
			+ "        <instrument-name>Low Floor Tom</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I43\">\n"
			+ "        <instrument-name>Closed Hi-Hat</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I44\">\n"
			+ "        <instrument-name>High Floor Tom</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I45\">\n"
			+ "        <instrument-name>Pedal Hi-Hat</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I46\">\n"
			+ "        <instrument-name>Low Tom</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I47\">\n"
			+ "        <instrument-name>Open Hi-Hat</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I48\">\n"
			+ "        <instrument-name>Low-Mid Tom</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I49\">\n"
			+ "        <instrument-name>Hi-Mid Tom</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I50\">\n"
			+ "        <instrument-name>Crash Cymbal 1</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I51\">\n"
			+ "        <instrument-name>High Tom</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I52\">\n"
			+ "        <instrument-name>Ride Cymbal 1</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I53\">\n"
			+ "        <instrument-name>Chinese Cymbal</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I54\">\n"
			+ "        <instrument-name>Ride Bell</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I55\">\n"
			+ "        <instrument-name>Tambourine</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I56\">\n"
			+ "        <instrument-name>Splash Cymbal</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I57\">\n"
			+ "        <instrument-name>Cowbell</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I58\">\n"
			+ "        <instrument-name>Crash Cymbal 2</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I60\">\n"
			+ "        <instrument-name>Ride Cymbal 2</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I64\">\n"
			+ "        <instrument-name>Open Hi Conga</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "      <score-instrument id=\"P1-I65\">\n"
			+ "        <instrument-name>Low Conga</instrument-name>\n"
			+ "      </score-instrument>\n"
			+ "    </score-part>\n"
			+ "  </part-list>\n"
			+ "  <part id=\"P1\">\n"
			+ "    <measure number=\"1\">\n"
			+ "      <attributes>\n"
			+ "        <divisions>4</divisions>\n"
			+ "        <key>\n"
			+ "          <fifths>0</fifths>\n"
			+ "        </key>\n"
			+ "        <time>\n"
			+ "          <beats>4</beats>\n"
			+ "          <beat-type>4</beat-type>\n"
			+ "        </time>\n"
			+ "        <clef>\n"
			+ "          <sign>percussion</sign>\n"
			+ "          <line>2</line>\n"
			+ "        </clef>\n"
			+ "      </attributes>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>D</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I46\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>A</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I50\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>D</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I46\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <backup>\n"
			+ "        <duration>16</duration>\n"
			+ "      </backup>\n"
			+ "      <barline location=\"right\">\n"
			+ "        <bar-style>light-heavy</bar-style>\n"
			+ "        <repeat direction=\"backward\" times=\"0\"/>\n"
			+ "      </barline>\n"
			+ "    </measure>\n"
			+ "    <measure number=\"2\">\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>D</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I46\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>D</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>8</duration>\n"
			+ "        <instrument id=\"P1-I46\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>half</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>A</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>8</duration>\n"
			+ "        <instrument id=\"P1-I50\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>half</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <backup>\n"
			+ "        <duration>16</duration>\n"
			+ "      </backup>\n"
			+ "      <barline location=\"right\">\n"
			+ "        <bar-style>light-heavy</bar-style>\n"
			+ "        <repeat direction=\"backward\" times=\"0\"/>\n"
			+ "      </barline>\n"
			+ "    </measure>\n"
			+ "    <measure number=\"3\">\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>A</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I50\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>G</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>2</duration>\n"
			+ "        <instrument id=\"P1-I43\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <backup>\n"
			+ "        <duration>16</duration>\n"
			+ "      </backup>\n"
			+ "      <barline location=\"right\">\n"
			+ "        <bar-style>light-heavy</bar-style>\n"
			+ "        <repeat direction=\"backward\" times=\"0\"/>\n"
			+ "      </barline>\n"
			+ "    </measure>\n"
			+ "    <measure number=\"4\">\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>C</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I39\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>D</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I46\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>1</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>16th</type>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>D</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>3</duration>\n"
			+ "        <instrument id=\"P1-I46\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <dot/>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>3</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <dot/>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <chord/>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>E</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>3</duration>\n"
			+ "        <instrument id=\"P1-I48\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>eighth</type>\n"
			+ "        <dot/>\n"
			+ "      </note>\n"
			+ "      <note>\n"
			+ "        <unpitched>\n"
			+ "          <display-step>A</display-step>\n"
			+ "          <display-octave>5</display-octave>\n"
			+ "          </unpitched>\n"
			+ "        <duration>8</duration>\n"
			+ "        <instrument id=\"P1-I50\"/>\n"
			+ "        <voice>1</voice>\n"
			+ "        <type>half</type>\n"
			+ "        <notehead>x</notehead>\n"
			+ "      </note>\n"
			+ "      <backup>\n"
			+ "        <duration>16</duration>\n"
			+ "      </backup>\n"
			+ "      <barline location=\"right\">\n"
			+ "        <bar-style>light-heavy</bar-style>\n"
			+ "        <repeat direction=\"backward\" times=\"0\"/>\n"
			+ "      </barline>\n"
			+ "    </measure>\n"
			+ "  </part>\n"
			+ "</score-partwise>";
	
	assertEquals(expected, fileContent);
		
	}
	
	private ArrayList<ArrayList<String>> GetInput (String[] textInput)
	{		
		
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		
		for (String line : textInput) {
			if(line.length() > 1)
			{
				line = cleanTextContent(line); //Removes redundant spaces
								
				String[] lineInput = line.substring(line.indexOf('|')).split("");
				ArrayList<String> lineInputList = new ArrayList<String>();
				String tunePlusOctave = line.substring(0, line.indexOf('|')).trim();
				String tune = "";
				String octave = "";
				if(tunePlusOctave.length() > 0) {
					try {
						octave = "" + Integer.parseInt(tunePlusOctave.substring(tunePlusOctave.length()-1));
						tune = tunePlusOctave.substring(0, tunePlusOctave.length()-1);
					}catch(NumberFormatException e1){
						octave = "";
						tune = tunePlusOctave;
					}
				}
				lineInputList.add(tune.trim());
				lineInputList.add(octave.trim());
				//lineInputList.add(tune);
				for(String character : lineInput) {
					lineInputList.add(character);
			    }
				
				input.add(lineInputList);
			}
			else
			{
				input.add(new ArrayList<String>());
			}
		}
		
		return input;
	}
	
	private static String cleanTextContent(String text) 
	{
	    // strips off all non-ASCII characters
	    text = text.replaceAll("[^\\x00-\\x7F]", "");
	 
	    // erases all the ASCII control characters
	    text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
	    // removes non-printable characters from Unicode
	    text = text.replaceAll("\\p{C}", "");
	    return text.trim();
	}
	
	@SuppressWarnings("resource")
	String readFile(String path) {
		String fileContent = null;
		try {
			fileContent = new Scanner(new File(path)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

}
