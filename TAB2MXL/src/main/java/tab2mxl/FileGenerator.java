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
	
	FileGenerator (String path) {		
		stringBuilder = new StringBuilder();
		failed = false;
		
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
			stringBuilder.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void tabBack()
	{
		try {
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
		} catch (StringIndexOutOfBoundsException e) {
			//e.printStackTrace();
		}
	}
	
	private void write(String line)
	{
		try {
			myWriter.write(line);
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
	public void addInfo(String title, String instrument)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	public void addInfoDrums(String title)  {
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
			newLine();

			//Add
			myWriter.write(currentIndent + "<part-name>Drumset</part-name>");
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I36\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Bass Drum 1</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I37\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Bass Drum 2</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I38\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Side Stick</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I39\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Snare</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I42\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low Floor Tom</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I43\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Closed Hi-Hat</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I44\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>High Floor Tom</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I45\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Pedal Hi-Hat</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I46\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low Tom</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I47\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Open Hi-Hat</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I48\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low-Mid Tom</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I49\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Hi-Mid Tom</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I50\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Crash Cymbal 1</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I51\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>High Tom</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I52\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Ride Cymbal 1</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I53\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Chinese Cymbal</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I54\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Ride Bell</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I55\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Tambourine</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I56\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Splash Cymbal</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I57\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Cowbell</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I58\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Crash Cymbal 2</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I60\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Ride Cymbal 2</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I64\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Open Hi Conga</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "<score-instrument id=\"P1-I65\">");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent +  "<instrument-name>Low Conga</instrument-name>");
			newLine();
			myWriter.write(currentIndent +  "</score-instrument>");
			tabBack();
			newLine();

			myWriter.write(currentIndent +  "</score-part>");
			tabBack();
			newLine();
			myWriter.write(currentIndent +  "</part-list>");
			newLine();
			tabBack();
			myWriter.write(currentIndent +  "<part id=\"P1\">");
			newLine();
			tabBack();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openDrumMeasure(int measureNumber){
		try {
			myWriter.write(currentIndent + "<measure number=\"" + measureNumber + "\">");
			currentIndent += "  ";
			newLine();
			measureOpen = true;
		}
		catch(IOException e){

		}

	}

	public void attributesDrum(int division, int beat, int beatType) {
		try {
			myWriter.write(currentIndent + "<attributes>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<divisions>" + division + "</divisions>");
			newLine();
			myWriter.write(currentIndent + "<key>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<fifths>" + 0 + "</fifths>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</key>");
			newLine();
			myWriter.write(currentIndent + "<time>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<beats>" + beat + "</beats>");
			newLine();
			myWriter.write(currentIndent + "<beat-type>" + beatType + "</beat-type>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</time>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "<clef>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<sign>percussion</sign>");
			newLine();
			myWriter.write(currentIndent + "<line>" + 2 + "</line>");
			newLine();
			myWriter.write(currentIndent + "</clef>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</attributes>");
			newLine();
		}
		catch(IOException e){

		}
	}

	public void addDrumNote(String noteHead, int duration, String displayStep, int displayOctave, String instrumentID, String noteType, int voice){

		try {
			myWriter.write(currentIndent + "<note>");
			newLine();
			currentIndent += "  ";
			myWriter.write(currentIndent + "<unpitched>");
			newLine();
			currentIndent += "  ";

			myWriter.write(currentIndent + "<display-step>" + displayStep + "</display-step>");
			newLine();
			myWriter.write(currentIndent + "<display-octave>" + displayOctave + "</display-octave>");
			newLine();
			myWriter.write(currentIndent + "</unpitched>");
			newLine();
			tabBack();

			myWriter.write(currentIndent + "<duration>" + duration + "</duration>");
			newLine();
			myWriter.write(currentIndent + "<instrument id=\"" + instrumentID + "\"/>");
			newLine();
			myWriter.write(currentIndent + "<voice>" + voice + "</voice>" );
			newLine();
			myWriter.write(currentIndent + "<type>" + noteType + "</type>");
			newLine();
			if(noteHead.equals("x")){
				myWriter.write(currentIndent + "<notehead>" + noteHead + "</notehead>");
				newLine();
			}
			myWriter.write(currentIndent + "</note>");
			newLine();
			tabBack();

		} catch (IOException e){
			e.printStackTrace();
		}

	}
	public void Backup(int totalDuration){

		try {
			myWriter.write(currentIndent + "<backup>");
			newLine();
			currentIndent += "  ";

			myWriter.write(currentIndent + "<duration>" + totalDuration + "</duration>");
			newLine();
			myWriter.write(currentIndent + "</backup>");
			newLine();
			tabBack();

		} catch (IOException e){
			e.printStackTrace();
		}


	}

	public void openDrumPart(int partNumber)
	{
		try {
		myWriter.write(currentIndent + "<part id=\"P" + partNumber + "\">");
		currentIndent += "  ";
		newLine();
		partOpen = true;
		}
		catch(IOException e){

		}
	}

	public void closeDrumPart()
	{
		try {
		tabBack();
		myWriter.write(currentIndent + "</part>");
		newLine();
		partOpen = false;
		}
		catch(IOException e){

		}
	}

	public void closeDrumMeasure(){
		try {
			tabBack();
			myWriter.write(currentIndent + "</measure>");
			newLine();
			measureOpen = false;
		}
		catch(IOException e){

		}
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
	 * Adds attributes to the MusicXML
	 * @param division
	 * @param keySignature
	 * @param beat
	 * @param beatType
	 * @param clef
	 * @param tune
	 */
	public void attributes(int division, int keySignature, int beat, int beatType, String clef, String[] tune, int[] tuneOctave) {
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
	
	/**
	 * Adds a note to the MusicXML
	 * @param string
	 * @param fret
	 * @param note
	 */
	public void addNote(int string, int fret, char note, String noteType, int duration, int octave, int dot,boolean alter,boolean hammerStart, boolean hammerContinue, boolean hammerDone,boolean harmonic)
	{
		write(currentIndent + "<note>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<pitch>");
		currentIndent += "  ";
		newLine();
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
		write(currentIndent + "<duration>" + duration + "</duration>");
		newLine();
		write(currentIndent + "<type>" + noteType +"</type>");
		newLine();
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
	public void addChord(char[] chord, String noteType, int duration, int[] octaves,int[] string, int[] fret, int[] dot,boolean[] alter, int HamLocation, boolean hammerStart, boolean hammerContinue, boolean hammerDone,boolean[] harmonic) {
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
	
	public void addRest(int duration, String noteType) {
		write(currentIndent + "<note>");
		currentIndent += "  ";
		newLine();
		write(currentIndent + "<rest/>");
		newLine();
		write(currentIndent + "<duration>" + duration + "</duration>");
		newLine();
		write(currentIndent + "<type>" + noteType + "</type>");
		newLine();
		tabBack();
		write(currentIndent + "</note>");
		newLine();
	}
	
	/**
	 * Adds a new measure opening to the MusicXML
	 * @param measureNumber
	 */
	public void openMeasure(int measureNumber, boolean isRepeat, int repeatAmount)
	{
		System.out.println("OPEN MEASURE METHOD");
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
	 * Adds the closing lines to the MusicXML
	 */
	public void end()
	{
		try {
			myWriter.write(stringBuilder.toString().replaceAll("(?m)^[ \t]*\r?\n", ""));
			myWriter.write("</score-partwise>");
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
