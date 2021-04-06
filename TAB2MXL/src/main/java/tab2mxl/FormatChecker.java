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
			String line = inputFeild.get(i);
			int lineLength = line.length() + 1;
			line = line.trim();
			
			if(line.isEmpty() || line == null)
			{
				outputFeild.add("");
				frontOfLine += lineLength;
				continue;
			}
			
			matcher = pattern.matcher(line);
			matchFound = matcher.find();
			if(!matchFound)
			{
				highlight(frontOfLine, frontOfLine + line.length(), new Color(209, 209, 209));
				frontOfLine += lineLength;
				continue;
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

			outputFeild.add(line.strip());
			frontOfLine += lineLength;
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
	
	private boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
}
