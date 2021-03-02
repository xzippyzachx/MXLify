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
	public String LAST_USED_FOLDER_SAVE = "";
	
	
	boolean failed = false;
	static int measureNum = 0;
	File saveFile;
	FileWriter myWriter;
	static String filepath;
	
	String currentIndent = "";
	boolean measureOpen = false;
	boolean partOpen = false;
	
	FileGenerator (String path) {	
		JFileChooser fileChooser = null;
		int response = 0;
		if(path == "")
		{
			fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_SAVE, new File(".").getAbsolutePath())); // Create file chooser
			fileChooser.setFileFilter(new FileNameExtensionFilter("music xml","musicxml","mxl"));
			response = fileChooser.showSaveDialog(null); //Select file to save
		}
		
		
		if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
			
			prefs.put(LAST_USED_FOLDER_SAVE, fileChooser.getSelectedFile().getParent()); // Save file path
			
			if(path == "") {				
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
	
	/**
	 * Adds the initial info to the beginning of the MusicXML
	 * @param title
	 */
	public void addInfo(String title)
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
			myWriter.write(currentIndent + "<part-name>Guitar</part-name>");
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
	

	
	/**
	 * Adds a new part opening to the MusicXML
	 * @param partNumber
	 */
	public void openPart(int partNumber)
	{
		try {
			myWriter.write(currentIndent + "<part id=\"P" + partNumber + "\">");
			currentIndent += "  ";
			newLine();
			partOpen = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a part closing to the MusicXML
	 */
	public void closePart()
	{
		try {
			tabBack();
			myWriter.write(currentIndent + "</part>");
			newLine();
			partOpen = false;
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
	public void attributes(int division, int keySignature, int beat, int beatType, String clef, String[] tune, int[] tuneOctave) {
		try {
			myWriter.write(currentIndent + "<attributes>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<divisions>"+division+"</divisions>");
			newLine();
			myWriter.write(currentIndent + "<key>");
			currentIndent += "  ";	
			newLine();
			myWriter.write(currentIndent + "<fifths>"+0+"</fifths>");
			newLine();
			myWriter.write(currentIndent + "<mode>"+"major"+"</mode>");			
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</key>");
			newLine();
			myWriter.write(currentIndent + "<time>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<beats>"+beat+"</beats>");
			newLine();
			myWriter.write(currentIndent + "<beat-type>"+beatType+"</beat-type>");			
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</time>");
			newLine();
			//myWriter.write(currentIndent + "<staves>" + 2 + "</staves>");
			//newLine();
			myWriter.write(currentIndent + "<clef>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<sign>"+clef+"</sign>");
			newLine();
			myWriter.write(currentIndent + "<line>"+2+"</line>");
			newLine();
			myWriter.write(currentIndent + "<clef-octave-change>"+(-1)+"</clef-octave-change>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</clef>");			
			newLine();
			myWriter.write(currentIndent + "<staff-details>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<staff-lines>" + tune.length + "</staff-lines>");
			newLine();
			
			for(int i = 0; i < tune.length; i++)
			{
				myWriter.write(currentIndent + "<staff-tuning line=\"" + (i + 1) + "\">");
				currentIndent += "  ";
				newLine();
				myWriter.write(currentIndent + "<tuning-step>" + tune[tune.length - i - 1].toUpperCase() + "</tuning-step>");
				newLine();
				myWriter.write(currentIndent + "<tuning-octave>" + tuneOctave[tuneOctave.length - i - 1] + "</tuning-octave>");
				newLine();
				tabBack();
				myWriter.write(currentIndent + "</staff-tuning>");
				newLine();
			}
			
			tabBack();
			myWriter.write(currentIndent + "</staff-details>");
			newLine();
			
			tabBack();
			myWriter.write(currentIndent + "</attributes>");
			newLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds a note to the MusicXML
	 * @param string
	 * @param fret
	 * @param note
	 */
	public void addNote(int string, int fret, char note, String noteType, int duration, int octave, int dot,boolean alter)
	{
		//if(measureNum > 0) {
		try {
			myWriter.write(currentIndent + "<note>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<pitch>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<step>" + note + "</step>");
			newLine();
			if (alter) {
				myWriter.write(currentIndent + "<alter>" + 1 + "</alter>");
				newLine();
			}
			myWriter.write(currentIndent + "<octave>" + octave + "</octave>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</pitch>"); 
			newLine();
			myWriter.write(currentIndent + "<duration>" + duration + "</duration>"); 
			newLine();
			myWriter.write(currentIndent + "<type>" + noteType +"</type>"); 
			newLine();
			for(int i  = 0; i < dot; i++) {
				myWriter.write(currentIndent + "<dot/>");
				newLine();
			}
			myWriter.write(currentIndent + "<notations>");
			newLine();
			currentIndent += "  ";
			myWriter.write(currentIndent + "<technical>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<string>" + string + "</string>");
			newLine();
			myWriter.write(currentIndent + "<fret>" + fret + "</fret>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</technical>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</notations>");
			newLine();
			myWriter.write(currentIndent + "<stem>down</stem>"); 
			newLine();
			myWriter.write(currentIndent + "<staff>1</staff>");
			newLine();
			tabBack();
			myWriter.write(currentIndent + "</note>");		
			newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//}
	}
	
	/**
	 * Adds a chord to the MusicXML
	 * @param chord
	 * @param noteType
	 */

	public void addChord(char[] chord, String noteType, int duration, int[] octaves,int[] string, int[] fret, int[] dot,boolean[] alter) {
		//if(measureNum >= 1) {
		boolean firstDone = false;
		for (int i = chord.length-1; i>=0;i--) {
			if(string[i] != 0) {
				try {	
					myWriter.write(currentIndent + "<note>");
					currentIndent += "  ";
					newLine();
					if(firstDone) {
						myWriter.write(currentIndent + "<chord/>");
						newLine();
					}
					myWriter.write(currentIndent + "<pitch>");
					currentIndent += "  ";
					newLine();
					myWriter.write(currentIndent + "<step>" + chord[i] + "</step>");
					newLine();
					if (alter[i]) {
						myWriter.write(currentIndent + "<alter>" + 1 + "</alter>");
						newLine();
					}
					myWriter.write(currentIndent + "<octave>" + octaves[i]+ "</octave>");
					newLine();
					myWriter.write(currentIndent + "</pitch>"); 
					newLine();
					tabBack();
					myWriter.write(currentIndent + "<duration>" + duration + "</duration>"); 
					newLine();
					myWriter.write(currentIndent + "<type>" + noteType +"</type>"); 
					newLine();
					for(int j  = 0; j < dot[i]; j++) {
						myWriter.write(currentIndent + "<dot/>");
						newLine();
					}
					myWriter.write(currentIndent + "<notations>");
					newLine();
					currentIndent += "  ";
					myWriter.write(currentIndent + "<technical>");
					currentIndent += "  ";
					newLine();
					myWriter.write(currentIndent + "<string>" + string[i] + "</string>");
					newLine();
					myWriter.write(currentIndent + "<fret>" + fret[i] + "</fret>");
					newLine();
					tabBack();
					myWriter.write(currentIndent + "</technical>");
					newLine();
					tabBack();
					myWriter.write(currentIndent + "</notations>");
					newLine();
					myWriter.write(currentIndent + "<stem>down</stem>"); 
					newLine();
					myWriter.write(currentIndent + "<staff>1</staff>");
					newLine();
					tabBack();
					myWriter.write(currentIndent + "</note>");		
					newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				firstDone = true;
			}
		}
		//}
	}
	
	/**
	 * Adds a new measure opening to the MusicXML
	 * @param measureNumber
	 */
	public void openMeasure(int measureNumber)
	{
		try {
			myWriter.write(currentIndent + "<measure number=\"" + measureNumber + "\">");
			currentIndent += "  ";
			newLine();
			measureOpen = true;
			//measureNum = measureNumber;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a measure closing to the MusicXML
	 */
	public void closeMeasure()
	{
		try {
			tabBack();
			myWriter.write(currentIndent + "</measure>");
			newLine();
			measureOpen = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the closing lines to the MusicXML
	 */
	public void end()
	{
		try {
			myWriter.write("</score-partwise>");			
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
