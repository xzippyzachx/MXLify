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
	
	File saveFile;
	FileWriter myWriter;
	
	String currentIndent = "";
	boolean measureOpen = false;
	boolean partOpen = false;
	
	FileGenerator () {		
		JFileChooser fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_SAVE, new File(".").getAbsolutePath())); // Create file chooser
		fileChooser.setFileFilter(new FileNameExtensionFilter("musicxml file","musicxml"));
		int response = fileChooser.showSaveDialog(null); //Select file to save
		
		if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
			
			saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath());  //Print out path
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
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
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
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</score-part>");			
			newLine();
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
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
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
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
	public void attributes(int division, int keySignature, int beat, int beatType, String clef, String[] tune) {
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
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</key>");
			newLine();
			myWriter.write(currentIndent + "<time>");
			currentIndent += "  ";
			
			newLine();
			myWriter.write(currentIndent + "<beats>"+beat+"</beats>");
			newLine();
			myWriter.write(currentIndent + "<beat-type>"+beatType+"</beat-type>");			
			newLine();
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</time>");
			newLine();
			myWriter.write(currentIndent + "<staves>" + 2 + "</staves>");
			newLine();
			myWriter.write(currentIndent + "<clef>");
			currentIndent += "  ";
			
			newLine();
			myWriter.write(currentIndent + "<sign>"+clef+"</sign>");
			newLine();
			myWriter.write(currentIndent + "<line>"+2+"</line>");
			newLine();
			myWriter.write(currentIndent + "<clef-octave-change>"+(-1)+"</clef-octave-change>");
			newLine();
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</clef>");			
			newLine();
			myWriter.write(currentIndent + "<staff-details number = \"" + 2 + "\">");
			currentIndent += "  ";
			
			newLine();
			myWriter.write(currentIndent + "<staff-lines>" + tune.length + "</staff-lines>");
			newLine();
			
			for(int i = 0; i < tune.length; i++)
			{
				myWriter.write(currentIndent + "<staff-tuning line=\"" + (i + 1) + "\">");
				currentIndent += "  ";
				newLine();
				myWriter.write(currentIndent + "<tuning-step>" + tune[tune.length - i - 1] + "</tuning-step>");
				newLine();
				myWriter.write(currentIndent + "<tuning-octave>" + 2 + "</tuning-octave>");
				newLine();
				currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
				myWriter.write(currentIndent + "</staff-tuning>");
				newLine();
			}
			
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</staff-details>");
			newLine();
			
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
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
	public void addNote(int string, int fret, String note, String noteType, int duration)
	{
		try {
			myWriter.write(currentIndent + "<note>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<pitch>");
			currentIndent += "  ";
			newLine();
			myWriter.write(currentIndent + "<step>" + note + "</step>");
			newLine();
			myWriter.write(currentIndent + "<octave>4</octave>");
			newLine();
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</pitch>"); 
			newLine();
			myWriter.write(currentIndent + "<duration>" + duration + "</duration>"); 
			newLine();
			myWriter.write(currentIndent + "<type>" + noteType +"</type>"); 
			newLine();
			myWriter.write(currentIndent + "<stem>down</stem>"); 
			newLine();
			myWriter.write(currentIndent + "<staff>1</staff>");
			newLine();
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</note>");		
			newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a chord to the MusicXML
	 * @param chord
	 * @param noteType
	 */
	public void addChord(char[] chord, String noteType) {
		for (int i = 0; i<chord.length;i++) {
			try {	
				myWriter.write(currentIndent + "<note>");
				currentIndent += "  ";
				newLine();			
				if(i>0) {
					myWriter.write(currentIndent + "<chord/>");
					newLine();
				}
				myWriter.write(currentIndent + "<pitch>");
				currentIndent += "  ";
				newLine();
				myWriter.write(currentIndent + "<step>" + chord[i] + "</step>");
				newLine();
				myWriter.write(currentIndent + "<octave>4</octave>");
				newLine();
				currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
				myWriter.write(currentIndent + "</pitch>"); 
				newLine();
				myWriter.write(currentIndent + "<duration>1</duration>"); 
				newLine();
				myWriter.write(currentIndent + "<type>" + noteType +"</type>"); 
				newLine();
				myWriter.write(currentIndent + "<stem>down</stem>"); 
				newLine();
				myWriter.write(currentIndent + "<staff>1</staff>");
				newLine();
				currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
				myWriter.write(currentIndent + "</note>");		
				newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
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
