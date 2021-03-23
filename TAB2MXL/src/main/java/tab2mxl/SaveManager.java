package tab2mxl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveManager {

	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER_SAVE = "";
	public boolean failed = false;
	
	File saveFile;
	FileWriter myWriter;
	static String filepath;
	
	public SaveManager (String path, int instrument, String title, String timesig, String textArea)
	{
		if(textArea.isEmpty())
			return;
		
		failed = false;
		JFileChooser fileChooser = null;
		int response = 0;
		
		if(path == "")
		{
			fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_SAVE, new File(".").getAbsolutePath())); // Create file chooser
			fileChooser.setFileFilter(new FileNameExtensionFilter("mxlify","mxlify"));
			response = fileChooser.showSaveDialog(null); //Select file to save
		}
				
		if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen
						
			if(path == "") {
				prefs.put(LAST_USED_FOLDER_SAVE, fileChooser.getSelectedFile().getParent()); // Save file path
				filepath = fileChooser.getSelectedFile().getAbsolutePath();
				if (!filepath.substring(filepath.lastIndexOf(".")+1).equals("mxlify"))     
					filepath += ".mxlify"; //Add extension to file if not already added
				saveFile = new File(filepath);
			}
			else {
				filepath = path;
				saveFile = new File(filepath);
			}
			
			try {
		      myWriter = new FileWriter(saveFile);
		      
		      myWriter.write("instrument " + instrument);
		      myWriter.write("\n");
		      myWriter.write("title " + title);
		      myWriter.write("\n");
		      myWriter.write("timesig " + timesig);
		      myWriter.write("\n");
		      myWriter.write(textArea);
		      
		      myWriter.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		}
		else // Canceled
		{
			failed = true;
		}
	}
	
}
