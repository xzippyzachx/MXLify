package tab2mxl;

import java.util.ArrayList;
import gui_panels.MyFrame;
import gui_popups.ClearPopUp;

public class Main {
	
	public static MyFrame myFrame;
	
	public static boolean isInPopUp;
	static ClearPopUp clearPopUp;
	static ClearPopUp successPopUp;

	public static void main(String[] args) {
		myFrame = new MyFrame();
	}

	public static void FileUploaded(String input)
	{
		if(!myFrame.textInputContentPanel.textField.getText().isEmpty())
			new ClearPopUp(myFrame, input, "Override Current Tablature");
		else
			myFrame.textInputContentPanel.textField.setText(input);
	}
	
	public static void Convert(ArrayList<ArrayList<String>> input, int instrument)
	{
		//System.out.println("I: " + instrument);
		switch(instrument) {
			case 1: //Bass
				new StringParser(input, instrument, "");//added instrument to Parser to use in Tuning class
				break;
			case 2: //Drum
				new DrumParser(input);
				break;
			default: //Guitar
				new StringParser(input, instrument, "");
		}

		new CreateScore(FileGenerator.filepath);
	} 
}
