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
	
	FileGenerator (ArrayList<char[]> output) {
		
		JFileChooser fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_SAVE, new File(".").getAbsolutePath())); // Create file chooser
		fileChooser.setFileFilter(new FileNameExtensionFilter("musicxml file","musicxml"));
		int response = fileChooser.showSaveDialog(null); //Select file to save
		
		if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
			
			saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath());  //Print out path
			try {
		      FileWriter myWriter = new FileWriter(saveFile);
		      
		      //Just printing the output for now
		      for(char[] col : output)
				{
					for(char character : col)
					{
						myWriter.write(character);
					}
					myWriter.write("\n");
				}
		      
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		}
	}
	
}
