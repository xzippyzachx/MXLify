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
	
	FileGenerator (ArrayList<char[]> output) {		
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
	private void NewLine()
	{
		try {
			myWriter.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the initial info to the beginning of the MusicXML
	 */
	public void AddInfo()
	{
		try {
			myWriter.write("<score-partwise version=\"3.1\">");
			currentIndent += "  ";
			NewLine();
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
	public void AddNote(int string, int fret, String note)
	{
		try {
			myWriter.write(currentIndent + "<note>");
			currentIndent += "  ";
			NewLine();
			
			//do note stuff here
			
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</note>");		
			NewLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a new measure opening to the MusicXML
	 * @param measureNumber
	 */
	public void OpenMeasure(int measureNumber)
	{
		try {
			myWriter.write(currentIndent + "<measure number=" + measureNumber + ">");
			currentIndent += "  ";
			NewLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a measure closing to the MusicXML
	 */
	public void CloseMeasure()
	{
		try {
			currentIndent = currentIndent.substring(0,currentIndent.length() - 2);
			myWriter.write(currentIndent + "</measure>");
			NewLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the closing lines to the MusicXML
	 */
	public void End()
	{
		try {
			myWriter.write("</score-partwise>");			
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
