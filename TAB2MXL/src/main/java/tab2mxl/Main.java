package tab2mxl;

import java.util.ArrayList;

public class Main {
	
	public static MyFrame myFrame;
	static ClearPopUp clearPopUp;
	
	public static void main(String[] args) {
		myFrame = new MyFrame();
	}

	public static void FileUploaded(String input)
	{
		
		if(!myFrame.textInputContentPanel.textField.getText().isEmpty())
		{
			new ClearPopUp(myFrame, input);
		}
		else
			myFrame.textInputContentPanel.textField.setText(input);
	}
	
	public static void Convert(ArrayList<ArrayList<String>> input)
	{
		new Parser(input);
	}
 
}
