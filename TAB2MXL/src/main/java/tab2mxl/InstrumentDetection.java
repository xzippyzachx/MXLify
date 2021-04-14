package tab2mxl;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstrumentDetection {

	
	public static int detectInstrument(ArrayList<ArrayList<String>> input)
	{
		if(input == null)
			return 0;
		
		
		String regexPattern = "x|o|f";
		Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;
		boolean matchFound = false;
		
		for(ArrayList<String> line : input) {
			for(int i = 3; i < line.size(); i++)
			{
				matcher = pattern.matcher(line.get(i));
				matchFound = matcher.find();
				if(matchFound)
					return 2;
			}
		}
		
		int stringAmount = 0;
		
		for(int i = 0; i < input.size(); i++) {			
			if(input.get(i).size() < 2)
				break;
			stringAmount++;
		}
		
		if(stringAmount == 6 || stringAmount == 0)
			return 0;
		
		if(stringAmount < 6)
			return 1;
				
		return 0;
	}
	
}
