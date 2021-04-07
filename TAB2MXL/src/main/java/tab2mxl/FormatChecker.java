package tab2mxl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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
		errorText.setText("");
		warningText = Main.myFrame.textInputContentPanel.warningText;
		warningText.setText("");
		textField = Main.myFrame.textInputContentPanel.textField;
		
		//Detect if the text area is empty
		if(inputTextFeild.isEmpty())
		{
			errorText.setText("Text Area Empty");
			errorType = 2;
			return;
		}
		
		boolean tabStarted = false;
		boolean newTab = true;
		int[] measureLengths = new int[0];
		int frontOfLine = 0;
		String lastLine = "";
		
		Pattern dashPattern = Pattern.compile("-+", Pattern.CASE_INSENSITIVE);
		Matcher dashMatcher = null;
		Pattern pipePattern = Pattern.compile("\\|+", Pattern.CASE_INSENSITIVE);
		Matcher pipeMatcher = null;
		
		///////////////////////////////////////////
		//Loop through each line of the inputFeild
		for (int i = 0; i < inputFeild.size(); i++) {
			String line = inputFeild.get(i);
			int lineLength = line.length() + 1;
			line = line.replaceFirst("\\s++$", "");
			
			//Check for empty lines
			if(line.isEmpty() || line == null)
			{
				if(tabStarted)
				{
					lastLine = line.replaceFirst("\\s++$", "");
					outputFeild.add(lastLine);
				}
				frontOfLine += lineLength;
				newTab = true;
				continue;
			}
			
			tabStarted = true;
			
			//Check for non tablature lines
			dashMatcher = dashPattern.matcher(line);
			pipeMatcher = pipePattern.matcher(line);
			if(!dashMatcher.find() || !pipeMatcher.find())
			{
				highlight(frontOfLine, frontOfLine + line.length(), new Color(209, 209, 209));
				frontOfLine += lineLength;
				continue;
			}
			
			//Check measure amount/length
			if(newTab && !line.toLowerCase().contains("repeat"))
			{
				newTab = false;
				String[] check = line.split("\\|+");
				measureLengths = new int[check.length];
				for (int j = 1; j < measureLengths.length; j++)
				{
					if(Character.isDigit(check[j].charAt(check[j].length()-1)))
						measureLengths[j] = check[j].length() - 1;
					else						
						measureLengths[j] = check[j].length();
				}
			}
			else if(!line.toLowerCase().contains("repeat"))
			{
				Pattern measurePattern = Pattern.compile("\\|+");
				Matcher measureMatcher = measurePattern.matcher(line);
				int count = 0;
				while (measureMatcher.find()) {
				    count++;
				}
				
				if(count != measureLengths.length)
				{
					highlight(frontOfLine, frontOfLine + line.length(), new Color(245, 234, 88));
					warningText.setText("Measure Amount Incorrect");
					errorType = 1;
					
					if(lastLine.length() > line.length())
						line = line.substring(0, line.length()) + lastLine.substring(line.length()).replaceAll("\\d|\\w", "-");	
					else
						line = line.substring(0, lastLine.length());
				}
				else
				{
					String[] check = line.split("\\|+");
					int highlightStart = check[0].length() + 1;
					for (int j = 1; j < measureLengths.length; j++)
					{					
						if(measureLengths[j] != check[j].length())
						{
							highlight(frontOfLine + highlightStart, frontOfLine + highlightStart + check[j].length(), new Color(245, 234, 88));
							warningText.setText("Measure Length Incorrect");
							errorType = 1;
							
							int diff = measureLengths[j] - check[j].length();
							if(diff > 0)
								for(int d = 0; d < diff; d++)
									line = line.substring(0, highlightStart + check[j].length()) + "-" + line.substring(highlightStart + check[j].length());
							else
								for(int d = -1; d >= diff; d--)
									line = line.substring(0, highlightStart + check[j].length() + d) + line.substring(highlightStart + check[j].length() + d + 1);
						}
						highlightStart += check[j].length() + 1;
					}	
				}
			}
			
			//Check tuning format
			if(instrument == 0 || instrument == 1)
			{
				String[] check = line.split("\\|");
				check[0] = check[0].replaceAll("\\d", "");
				if(check[0].strip() != "")
				{
					if(!StringTuning.tuningSupportCheck(check[0].toUpperCase()))
					{
						highlight(frontOfLine, frontOfLine + check[0].length(), new Color(255, 89, 89));
						errorText.setText("Tune Not Recognized");
						errorType = 2;
					}
				}
			}
			else if (instrument == 2)
			{
				String[] check = line.split("\\|");
				check[0] = check[0].replaceAll("\\d", "");
				if(check[0].strip() != "")
				{
					if(!DrumTuning.drumSupportCheck(check[0].toUpperCase()))
					{
						highlight(frontOfLine, frontOfLine + check[0].length(), new Color(255, 89, 89));
						errorText.setText("Tune Not Recognized");
						errorType = 2;
					}
				}
			}
			
			//Check octave format
			if(instrument == 0 || instrument == 1)
			{
				String[] check = line.split("\\|");
				int tuneLength = check[0].length();
				check[0] = check[0].replaceAll("[^\\d.]", "");
				if(isNumeric(check[0]))
				{
					int octave = Integer.parseInt(check[0]);
					if(!(octave >= 0 && octave <= 9))
					{
						highlight(frontOfLine + tuneLength - check[0].length(), frontOfLine + tuneLength, new Color(255, 89, 89));
						errorText.setText("Octave Not Recognized");
						errorType = 2;
					}
				}
			}

			lastLine = line.replaceFirst("\\s++$", "");
			outputFeild.add(lastLine);
			frontOfLine += lineLength;
		}
		
		//Check for no valid tablature
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
		
//		for (String line: output)
//			System.out.println(line);
		
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
	
	private boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
}
