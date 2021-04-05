package tab2mxl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

public class FormatChecker {

	ArrayList<String> inputFeild = new ArrayList<String>();
	ArrayList<String> outputFeild = new ArrayList<String>();
	int errorType = 0;   //0-Good, 1-Warning, 2-Error
	
	JLabel errorText;
	JLabel warningText;
		
	public FormatChecker(String inputTextFeild)
	{
		System.out.println(inputTextFeild);
		
		Collections.addAll(inputFeild, inputTextFeild.split("\n"));
		errorText = Main.myFrame.textInputContentPanel.errorText;
		warningText = Main.myFrame.textInputContentPanel.warningText;
		
		//Detect if the text area is empty
		if(inputTextFeild.isEmpty())
		{
			errorText.setText("Text Area Empty");
			errorType = 2;
			return;
		}
		
		String regexPattern = "(-)(\\|)";
		Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;
		boolean matchFound = false;
		
		///////////////////////////////////////////
		//Loop through each line of the inputFeild
		for (int i = 0; i < inputFeild.size(); i++) {
			String line = inputFeild.get(i).replace("\n", "").replace("\r", "");;
			
			if(line.isEmpty())
			{
				outputFeild.add("\n");
				continue;
			}
			
			matcher = pattern.matcher(line);
			matchFound = matcher.find();
			if(!matchFound)
				continue;
				
			outputFeild.add(line + "\n");
		}
		
		if(outputFeild.size() == 0)
		{
			errorText.setText("No Valid Tablature");
			errorType = 2;
			return;
		}
	}
	
	public int GetErrorType()
	{
		return errorType;
	}
	
	public String[] GetOuput()
	{
		String[] output = new String[outputFeild.size()];
		output = outputFeild.toArray(output);
		
		for (String line: output)
			System.out.print(line);
		
		System.out.println("");
		System.out.println("Size: " + outputFeild.size());
		
		return output;
	}
	
}
