package tab2mxl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileGenerator {

	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER_CONVERT = "";
		
	public boolean failed = false;
	static int measureNum = 0;
	File saveFile;
	FileWriter myWriter;
	static String filepath;
	
	String currentIndent = "";
	boolean measureOpen = false;
	boolean partOpen = false;
	
	StringBuilder stringBuilder;
	boolean doublePart;
	
	FileGenerator (String path, boolean doublePart) {		
		stringBuilder = new StringBuilder();
		failed = false;
		this.doublePart = doublePart;
		
		JFileChooser fileChooser = null;
		int response = 0;
		if(path == "")
		{
			fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_CONVERT, new File(".").getAbsolutePath())); // Create file chooser
			fileChooser.setFileFilter(new FileNameExtensionFilter("music xml","musicxml","mxl"));
			response = fileChooser.showSaveDialog(null); //Select file to save
		}		
		
		if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
						
			if(path == "") {
				prefs.put(LAST_USED_FOLDER_CONVERT, fileChooser.getSelectedFile().getParent()); // Save file path
				filepath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filepath.substring(filepath.lastIndexOf(".")+1).equals("musicxml") && !filepath.substring(filepath.lastIndexOf(".")+1).equals("mxl"))     
					filepath += ".musicxml"; //Add extension to file if not already added
				saveFile = new File(filepath);
			}
			else {
				filepath= path;
				saveFile = new File(filepath);
			}
			
			try {
		      myWriter = new FileWriter(saveFile);
		      
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		}
		else // Canceled
		{
			failed = true;
		}
	}
	
	/**
	 * Goes to the next line in the MusicXML
	 */
	private void newLine()
	{
		try {
			myWriter.write("\n");
			if(doublePart)
				stringBuilder.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void tabBack()
	{
		try {
			if(currentIndent.length() > 1)
				currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	private void write(String line)
	{
		try {
			myWriter.write(line);
			if(doublePart)
				appendString(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendString(String line)
	{		
		if(line.equals("  <part id=\"P1\">"))
			stringBuilder.append("  <part id=\"P2\">");
		else if(line.equals("          <sign>G</sign>"))
			stringBuilder.append("          <sign>TAB</sign>");
		else
			stringBuilder.append(line);
	}
	
	/**
	 * Adds the initial info to the beginning of the MusicXML
	 * @param title
	 */
	public void addStringInfo(String title, String instrument)
	{
		try {
			myWriter.write(currentIndent + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");		
			newLine();
			myWriter.write(currentIndent + "<score-partwise version=\"3.1\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<work>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<work-title>" + title + "</work-title>");
			newLine();	
			tabBack();
			myWriter.write(currentIndent + "</work>");			
			newLine();			
			myWriter.write(currentIndent + "<part-list>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<score-part id=\"P1\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<part-name>" + instrument + "</part-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</score-part>");		
			newLine();
			myWriter.write(currentIndent + "<score-part id=\"P2\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<part-name>" + instrument + "</part-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</score-part>");	
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</part-list>");			
			newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	public void addDrumInfo(String title)  {
		try {
			myWriter.write(currentIndent + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			newLine();
			myWriter.write(currentIndent + "<score-partwise version=\"3.1\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<work>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<work-title>" + title + "</work-title>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</work>");
			newLine();
			myWriter.write(currentIndent + "<part-list>");
			currentIndent += "  ";
			newLine();
			
			myWriter.write(currentIndent + "<score-part id=\"P1\">");
			currentIndent += "  ";
			newLine();

			//Add			
			myWriter.write(currentIndent + "<part-name>Drumset</part-name>");
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I36\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Bass Drum 1</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I37\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Bass Drum 2</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I38\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Side Stick</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I39\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Snare</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I42\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low Floor Tom</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I43\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Closed Hi-Hat</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I44\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>High Floor Tom</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I45\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Pedal Hi-Hat</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I46\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low Tom</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I47\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Open Hi-Hat</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I48\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low-Mid Tom</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I49\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Hi-Mid Tom</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I50\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Crash Cymbal 1</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I51\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>High Tom</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I52\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Ride Cymbal 1</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I53\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Chinese Cymbal</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I54\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Ride Bell</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I55\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Tambourine</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I56\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Splash Cymbal</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I57\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Cowbell</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I58\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Crash Cymbal 2</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I60\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Ride Cymbal 2</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I64\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Open Hi Conga</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I65\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low Conga</instrument-name>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "</score-instrument>");			
			newLine();

			tabBack();
			myWriter.write(currentIndent +  "</score-part>");			
			newLine();
			
			tabBack();
			myWriter.write(currentIndent +  "</part-list>");
			newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds attributes to the MusicXML
	 * @param division
	 * @param keySignature
	 * @param beat
	 * @param beatType
	 * @param clef
	 * @param tune
	 */
	public void stringAttributes(int division, int keySignature, int beat, int beatType, String clef, String[] tune, int[] tuneOctave) {
		write(currentIndent + "<attributes>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<divisions>"+division+"</divisions>");
		newLine();
		write(currentIndent + "<key>");
		currentIndent += "  ";	
		newLine();
		write(currentIndent + "<fifths>"+0+"</fifths>");
		newLine();
		write(currentIndent + "<mode>"+"major"+"</mode>");
		newLine();
		tabBack();
		write(currentIndent + "</key>");
		newLine();
		write(currentIndent + "<time>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<beats>"+beat+"</beats>");
		newLine();
		write(currentIndent + "<beat-type>"+beatType+"</beat-type>");
		newLine();
		tabBack();
		write(currentIndent + "</time>");
		newLine();
		//write(currentIndent + "<staves>" + 2 + "</staves>");
		//newLine();
		write(currentIndent + "<clef>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<sign>"+clef+"</sign>");
		newLine();
		write(currentIndent + "<line>"+2+"</line>");
		newLine();
		write(currentIndent + "<clef-octave-change>"+(-1)+"</clef-octave-change>");
		newLine();
		tabBack();
		write(currentIndent + "</clef>");
		newLine();
		write(currentIndent + "<staff-details>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<staff-lines>" + tune.length + "</staff-lines>");
		newLine();
		
		for(int i = 0; i < tune.length; i++)
		{
			write(currentIndent + "<staff-tuning line=\"" + (i + 1) + "\">");
			currentIndent += "  ";
			newLine();
			write(currentIndent + "<tuning-step>" + tune[tune.length - i - 1].toUpperCase() + "</tuning-step>");
			newLine();
			write(currentIndent + "<tuning-octave>" + tuneOctave[tuneOctave.length - i - 1] + "</tuning-octave>");
			newLine();
			tabBack();
			write(currentIndent + "</staff-tuning>");
			newLine();
		}
		
		tabBack();
		write(currentIndent + "</staff-details>");
		newLine();		
		tabBack();
		write(currentIndent + "</attributes>");
		newLine();		
	}
	
	public void drumAttributes(int division, int beat, int beatType) {
		write(currentIndent + "<attributes>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<divisions>" + division + "</divisions>");
		newLine();
		write(currentIndent + "<key>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<fifths>" + 0 + "</fifths>");
		newLine();
		tabBack();
		write(currentIndent + "</key>");
		newLine();
		write(currentIndent + "<time>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<beats>" + beat + "</beats>");
		newLine();
		write(currentIndent + "<beat-type>" + beatType + "</beat-type>");
		newLine();
		tabBack();
		write(currentIndent + "</time>");
		newLine();
		write(currentIndent + "<clef>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<sign>percussion</sign>");
		newLine();
		write(currentIndent + "<line>" + 2 + "</line>");
		newLine();
		tabBack();
		write(currentIndent + "</clef>");
		newLine();
		tabBack();
		write(currentIndent + "</attributes>");
		newLine();
	}
	
	/**
	 * Adds a new part opening to the MusicXML
	 * @param partNumber
	 */
	public void openPart(int partNumber)
	{
		write(currentIndent + "<part id=\"P" + partNumber + "\">");
		currentIndent += "  ";
		newLine();
		partOpen = true;
	}
	
	/**
	 * Adds a part closing to the MusicXML
	 */
	public void closePart()
	{
		tabBack();
		write(currentIndent + "</part>");
		newLine();
		partOpen = false;
	}
	
	/**
	 * Adds a new measure opening to the MusicXML
	 * @param measureNumber
	 */
	public void openMeasure(int measureNumber, boolean isRepeat, int repeatAmount)
	{
		write(currentIndent + "<measure number=\"" + measureNumber + "\">");
		currentIndent += "  ";
		newLine();
		
		if(isRepeat)
		{
			write(currentIndent + "<barline location=\"left\">");
			currentIndent += "  ";
			newLine();
			write(currentIndent + "<bar-style>heavy-light</bar-style>");
			newLine();
			write(currentIndent + "<repeat direction=\"forward\" times=\"" + repeatAmount + "\"/>");
			tabBack();
			newLine();
			write(currentIndent + "</barline>");
			newLine();
			write(currentIndent + "<direction placement=\"above\">");
			currentIndent += "  ";
			newLine();
			write(currentIndent + "<direction-type>");
			currentIndent += "  ";
			newLine();
			write(currentIndent + "<words default-x=\"15\" default-y=\"15\" font-size=\"9\" font-style=\"italic\">Repeat " + repeatAmount + "x</words>");
			tabBack();
			newLine();
			write(currentIndent + "</direction-type>");
			tabBack();
			newLine();
	        write(currentIndent + "</direction>");
	        newLine();
		}
        
		measureOpen = true;
	}
	
	/**
	 * Adds a measure closing to the MusicXML
	 */
	public void closeMeasure(boolean isRepeat, int repeatAmount)
	{
		if(isRepeat)
		{
			write(currentIndent + "<barline location=\"right\">");
			currentIndent += "  ";
			newLine();
			write(currentIndent + "<bar-style>light-heavy</bar-style>");
			newLine();
			write(currentIndent + "<repeat direction=\"backward\" times=\"" + repeatAmount + "\"/>");
			tabBack();
			newLine();
			write(currentIndent + "</barline>");
			newLine();
		}
		
		tabBack();
		write(currentIndent + "</measure>");
		newLine();
		
		measureOpen = false;
	}
		
	/**
	 * Adds a note to the MusicXML
	 * @param string
	 * @param fret
	 * @param note
	 */
	public void addStringNote(int string, int fret, char note, String noteType, int duration, int octave, int dot,boolean alter,boolean hammerStart, boolean hammerContinue, boolean hammerDone,boolean harmonic,boolean grace)
	{
		write(currentIndent + "<note>");
		currentIndent += "  ";
		newLine();
		if (grace) {
			 write(currentIndent + "<grace/>");
			 newLine();
		}
		write(currentIndent + "<pitch>");
		newLine();
		currentIndent += "  ";
		write(currentIndent + "<step>" + note + "</step>");
		newLine();
		if (alter) {
			write(currentIndent + "<alter>" + 1 + "</alter>");
			newLine();
		}
		write(currentIndent + "<octave>" + octave + "</octave>");
		newLine();
		tabBack();
		write(currentIndent + "</pitch>");
		newLine();
		if(!grace) {
			write(currentIndent + "<duration>" + duration + "</duration>");
			newLine();
		}
		if(!grace) {
			write(currentIndent + "<type>" + noteType +"</type>");
			newLine();
		}
		for(int i  = 0; i < dot; i++) {
			write(currentIndent + "<dot/>");
			newLine();
		}
		if(alter){
			write(currentIndent + "<accidental>sharp</accidental>");
			newLine();
		}
		write(currentIndent + "<notations>");
		newLine();
		currentIndent += "  ";
		write(currentIndent + "<technical>");
		currentIndent += "  ";
		newLine();
		if(harmonic) {
			write(currentIndent + "<harmonic default-x=\"3\" default-y=\"24\" placement=\"above\" print-object=\"yes\"/>");
			newLine();
		}
		if(hammerStart){
			write(currentIndent + "<hammer-on number=\"1\" type=\"start\">H</hammer-on>");
			newLine();
		}
		else if(hammerContinue){
			write(currentIndent + "<hammer-on number=\"1\" type=\"stop\"/>");
			newLine();
			write(currentIndent + "<hammer-on number=\"1\" type=\"start\">H</hammer-on>");
			newLine();
		}
		else if(hammerDone){
			write(currentIndent + "<hammer-on number=\"1\" type=\"stop\"/>");
			newLine();
		}
		write(currentIndent + "<string>" + string + "</string>");
		newLine();
		write(currentIndent + "<fret>" + fret + "</fret>");
		newLine();
		tabBack();
		write(currentIndent + "</technical>");
		newLine();
		if(hammerStart){
			write(currentIndent + "<slur number=\"1\" placement=\"above\" type=\"start\"/>");
			newLine();
		}
		else if(hammerDone){
			write(currentIndent + "<slur number=\"1\" type=\"stop\"/>");
			newLine();
		}
		tabBack();
		write(currentIndent + "</notations>");
		newLine();
//		write(currentIndent + "<stem>down</stem>"); 
//		newLine();
//		write(currentIndent + "<staff>1</staff>");
//		newLine();
		tabBack();
		write(currentIndent + "</note>");
		newLine();
	}
	
	/**
	 * Adds a chord to the MusicXML
	 * @param chord
	 * @param noteType
	 */
	public void addStringChord(char[] chord, String noteType, int duration, int[] octaves,int[] string, int[] fret, int[] dot,boolean[] alter, int HamLocation, boolean hammerStart, boolean hammerContinue, boolean hammerDone,boolean[] harmonic) {
		boolean firstDone = false;
		for (int i = chord.length-1; i>=0;i--) {
			if(string[i] != 0) {
				write(currentIndent + "<note>");
				currentIndent += "  ";
				newLine();
				if(firstDone) {
					write(currentIndent + "<chord/>");
					newLine();
				}
				write(currentIndent + "<pitch>");
				currentIndent += "  ";
				newLine();
				write(currentIndent + "<step>" + chord[i] + "</step>");
				newLine();
				if (alter[i]) {
					write(currentIndent + "<alter>" + 1 + "</alter>");
					newLine();
				}
				write(currentIndent + "<octave>" + octaves[i]+ "</octave>");
				newLine();
				write(currentIndent + "</pitch>");
				newLine();
				tabBack();
				write(currentIndent + "<duration>" + duration + "</duration>");
				newLine();
				write(currentIndent + "<type>" + noteType +"</type>");
				newLine();
				for(int j  = 0; j < dot[i]; j++) {
					write(currentIndent + "<dot/>");
					newLine();
				}
				if(alter[i]){
					write(currentIndent + "<accidental>sharp</accidental>");
					newLine();
				}
				write(currentIndent + "<notations>");
				newLine();
				currentIndent += "  ";
				write(currentIndent + "<technical>");
				currentIndent += "  ";
				newLine();
				if(harmonic[i]) {
					write(currentIndent + "<harmonic default-x=\"3\" default-y=\"24\" placement=\"above\" print-object=\"yes\"/>");
					newLine();
				}
				if(i == HamLocation &&hammerStart){
					write(currentIndent + "<hammer-on number=\"1\" type=\"start\">H</hammer-on>");
					newLine();
				}
				else if(i == HamLocation && hammerContinue){
					write(currentIndent + "<hammer-on number=\"1\" type=\"stop\"/>");
					newLine();
					write(currentIndent + "<hammer-on number=\"1\" type=\"start\">H</hammer-on>");
					newLine();
				}
				else if(i == HamLocation && hammerDone){
					write(currentIndent + "<hammer-on number=\"1\" type=\"stop\"/>");
					newLine();
				}
				write(currentIndent + "<string>" + string[i] + "</string>");
				newLine();
				write(currentIndent + "<fret>" + fret[i] + "</fret>");
				newLine();
				tabBack();
				write(currentIndent + "</technical>");
				newLine();
				tabBack();
				write(currentIndent + "</notations>");
				newLine();
				write(currentIndent + "<stem>down</stem>");
				newLine();
				write(currentIndent + "<staff>1</staff>");
				newLine();
				tabBack();
				write(currentIndent + "</note>");
				newLine();
				firstDone = true;
			}
		}		
	}
		
	public void addDrumNote(String noteHead, int duration, String displayStep, int displayOctave, String instrumentID, String noteType, int voice, int dot){

		write(currentIndent + "<note>");
		newLine();
		currentIndent += "  ";
		write(currentIndent + "<unpitched>");
		newLine();
		currentIndent += "  ";

		write(currentIndent + "<display-step>" + displayStep + "</display-step>");
		newLine();
		write(currentIndent + "<display-octave>" + displayOctave + "</display-octave>");
		newLine();
		write(currentIndent + "</unpitched>");
		newLine();
		tabBack();

		write(currentIndent + "<duration>" + duration + "</duration>");
		newLine();
		write(currentIndent + "<instrument id=\"" + instrumentID + "\"/>");
		newLine();
		write(currentIndent + "<voice>" + voice + "</voice>" );
		newLine();
		write(currentIndent + "<type>" + noteType + "</type>");
		newLine();
		for(int d  = 0; d < dot; d++) {
			write(currentIndent + "<dot/>");
			newLine();
		}
		if(noteHead.equals("x")){
			write(currentIndent + "<notehead>" + noteHead + "</notehead>");
			newLine();
		}
		tabBack();
		write(currentIndent + "</note>");
		newLine();
			
	}
	
	public void addDrumChord(ArrayList<String>chords, int duration, ArrayList<String>chordNotes, ArrayList<Integer>chordOctaves, ArrayList<String>chordIDs, ArrayList<String>chordSymbols, int chordDot, String chordType, int voice) {

		boolean first = false;
		for(int i = chords.size()-1; i >= 0; i--) {
			write(currentIndent + "<note>");
			newLine();
			currentIndent += "  ";
			if(first) {
				write(currentIndent + "<chord/>");
				newLine();	
			}
			write(currentIndent + "<unpitched>");
			newLine();
			currentIndent += "  ";

			write(currentIndent + "<display-step>" + chordNotes.get(i) + "</display-step>");
			newLine();
			write(currentIndent + "<display-octave>" + chordOctaves.get(i) + "</display-octave>");
			newLine();
			write(currentIndent + "</unpitched>");
			newLine();
			tabBack();

			write(currentIndent + "<duration>" + duration + "</duration>");
			newLine();
			write(currentIndent + "<instrument id=\"" + chordIDs.get(i) + "\"/>");
			newLine();
			write(currentIndent + "<voice>" + voice + "</voice>" );
			newLine();
			write(currentIndent + "<type>" + chordType + "</type>");
			newLine();
			for(int j  = 0; j < chordDot; j++) {
				write(currentIndent + "<dot/>");
				newLine();
			}
			if(chordSymbols.get(i).equals("x")){
				write(currentIndent + "<notehead>" + chordSymbols.get(i) + "</notehead>");
				newLine();
			}
			tabBack();
			write(currentIndent + "</note>");
			newLine();
			
			first = true;
		}
	}
		
	public void addRest(int duration, String noteType, int v) {
		
		if(duration > 0) {
			write(currentIndent + "<note>");
			currentIndent += "  ";
			newLine();
			write(currentIndent + "<rest/>");
			newLine();
			write(currentIndent + "<duration>" + duration + "</duration>");
			newLine();
			if(v != -1) {
				write(currentIndent + "<voice>" + v + "</voice>" );
				newLine();
			}
			write(currentIndent + "<type>" + noteType + "</type>");
			newLine();
			tabBack();
			write(currentIndent + "</note>");
			newLine();
		}
	}
		
	public void backup(int totalDuration){

		write(currentIndent + "<backup>");
		newLine();
		currentIndent += "  ";

		write(currentIndent + "<duration>" + totalDuration + "</duration>");
		newLine();
		tabBack();
		write(currentIndent + "</backup>");
		newLine();

	}
	/**
	 * Adds the closing lines to the MusicXML
	 */
	public void end()
	{
		try {
			if(doublePart)
				myWriter.write(stringBuilder.toString().replaceAll("(?m)^[ \t]*\r?\n", ""));
			
			myWriter.write("</score-partwise>");
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
