package tab2mxl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class FormatChecker {

	ArrayList<String> inputFeild = new ArrayList<String>();
	ArrayList<String> outputFeild = new ArrayList<String>();
	int errorType = 0;   //0-Good, 1-Warning, 2-Error
	
	JLabel errorText;
	JLabel warningText;
	JTextArea textField;
		
	public FormatChecker(String inputTextFeild, int instrument)
	{
		//System.out.println("I: " + instrument);
		//System.out.println(inputTextFeild);
		
		Collections.addAll(inputFeild, inputTextFeild.split("\n"));
		errorText = Main.myFrame.textInputContentPanel.errorText;
		warningText = Main.myFrame.textInputContentPanel.warningText;
		textField = Main.myFrame.textInputContentPanel.textField;
		
		//Detect if the text area is empty
		if(inputTextFeild.isEmpty())
		{
			errorText.setText("Text Area Empty");
			errorType = 2;
			return;
		}
		
		int frontOfLine = 0;
		
		String regexPattern = "(-)(\\|)";
		Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;
		boolean matchFound = false;
		
		///////////////////////////////////////////
		//Loop through each line of the inputFeild
		for (int i = 0; i < inputFeild.size(); i++) {
			String line = inputFeild.get(i).replace("\n", "  ").replace("\r", "  ");;
						
			if(line.trim().isEmpty() || line == null)
			{
				outputFeild.add("");
				frontOfLine += 2;
				continue;
			}
			
			matcher = pattern.matcher(line);
			matchFound = matcher.find();
			if(!matchFound)
			{
				highlight(frontOfLine, frontOfLine + line.length(), new Color(209, 209, 209));
				frontOfLine += line.length();
				continue;
			}
				
			outputFeild.add(line.strip());
			frontOfLine += line.length();
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
		
		//for (String line: output)
			//System.out.println(line);
		
		//System.out.println("");
		//System.out.println("Size: " + outputFeild.size());
		
		return output;
	}
	
	private void highlight(int start, int end, Color colour) {
        
        try { 
              Highlighter highlighter = textField.getHighlighter();
              HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(colour);
                
              highlighter.addHighlight(start, end, painter );
              
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
    }
	
}
