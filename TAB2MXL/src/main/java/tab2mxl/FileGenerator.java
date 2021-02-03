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
	 * Adds a note to the MusicXML
	 * @param string
	 * @param fret
	 * @param note
	 */
	public void addNote(int string, int fret, String note, String noteType)
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
