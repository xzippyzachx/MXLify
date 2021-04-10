package tab2mxl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui_popups.LoadPopUp;

public class LoadManager {

	public Preferences prefs = Preferences.userRoot().node(getClass().getName());
	public String LAST_USED_FOLDER_LOAD = "";
	
	private String[] loadedData = new String[3];
	private String loadedText = "";
	private String loadedCustom = "";
		
	File loadFile;
	public boolean failed = false;
	
	public LoadManager (String path)
	{
		failed = false;
		JFileChooser fileChooser = null;
		int response = 0;
		
		if(path == "")
		{
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.mxlify", "mxlify");				
			fileChooser = new JFileChooser(prefs.get(LAST_USED_FOLDER_LOAD, new File(".").getAbsolutePath())); // Create file chooser
			fileChooser.setFileFilter(filter); // Only allow .mxlify files
			response = fileChooser.showSaveDialog(null); //Select file to save
		}

		if (response == JFileChooser.APPROVE_OPTION) { // if File successively chosen

			if(path == "")
			{
				loadFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
				prefs.put(LAST_USED_FOLDER_LOAD, fileChooser.getSelectedFile().getParent()); // Save file path
			}
			else
			{
				loadFile = new File(path);
			}

			try {
		        String fileName = ((File) (loadFile)).getName();
				String extension = "";

				int i = fileName.lastIndexOf('.');
				int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

				if (i > p)
				    extension = fileName.substring(i+1);
				
				//System.out.println(extension);
				
				BufferedReader reader = new BufferedReader(new FileReader (loadFile));
			    String         line = null;
			    StringBuilder  tablatureStringBuilder = new StringBuilder();
			    StringBuilder  customStringBuilder = new StringBuilder();
			    String         ls = System.getProperty("line.separator");
				
				if(extension.equals(".mxlify") || extension.equals("mxlify"))
				{
					String section = "";
					
					while((line = reader.readLine()) != null) {
			        	
						if(section.isEmpty() && line.split(" ").length > 1)
						{
							switch (line.split(" ")[0])
							{
								case "instrument":
									loadedData[0] = (line.split(" ")[1].equals("null")) ? "" : line.split(" ")[1];
									break;
								case "title":
									loadedData[1] = (line.split(" ")[1].equals("null")) ? "" : line.split(" ")[1];
									break;
								case "timesig":
									loadedData[2] = (line.split(" ")[1].equals("null")) ? "" : line.split(" ")[1];
									break;
								default:																
						            break;
							}
							continue;
						}
						else if(line.equals("custom"))
						{
							section = "custom";
							continue;
						}
						else if(line.equals("tablature"))
						{
							section = "tablature";
							continue;
						}
						
						if (section.equals("custom") && !line.split(" ")[0].equals("title") && !line.split(" ")[0].equals("timesig"))
						{
							customStringBuilder.append(line);
							customStringBuilder.append(ls);
						}
						else if (section.equals("tablature") && !line.split(" ")[0].equals("title") && !line.split(" ")[0].equals("timesig"))
						{
							tablatureStringBuilder.append(line);
							tablatureStringBuilder.append(ls);
						}
			        }
					
					if(path.equals("") && (!Main.myFrame.textInputContentPanel.textField.getText().isEmpty() || !Main.myFrame.textInputContentPanel.songName.getText().isEmpty() || !Main.myFrame.textInputContentPanel.timeSignature.getText().isEmpty() || !Main.myFrame.fileUploadContentPanel.customTextArea.getText().isEmpty()))
					{
						new LoadPopUp(Main.myFrame, tablatureStringBuilder.toString(), loadedData[0], loadedData[1], loadedData[2], customStringBuilder.toString(), "Override Current Tablature");
					}
					else if(path.equals(""))
					{
						Main.myFrame.textInputContentPanel.instrumentList.setSelectedIndex(Integer.parseInt(loadedData[0]));
						Main.myFrame.textInputContentPanel.songName.setText(loadedData[1]);
						Main.myFrame.textInputContentPanel.timeSignature.setText(loadedData[2]);
						Main.myFrame.fileUploadContentPanel.customTextArea.focusGained(null);
						Main.myFrame.fileUploadContentPanel.customTextArea.setText(customStringBuilder.toString());
						Main.myFrame.textInputContentPanel.textField.focusGained(null);
						Main.myFrame.textInputContentPanel.textField.setText(tablatureStringBuilder.toString());
					}
					
					loadedCustom = customStringBuilder.toString();
					loadedText = tablatureStringBuilder.toString();
				}

		        reader.close();
		    } catch (IOException e1) {
				e1.printStackTrace();
			}
		} else
		{
			failed = true;
		}
	}
	
	public String[] GetLoadedData()
	{		
		return loadedData;
	}
	
	public String GetLoadedText()
	{		
		return loadedText;
	}
	
	public String GetLoadedCustom()
	{		
		return loadedCustom;
	}
}
