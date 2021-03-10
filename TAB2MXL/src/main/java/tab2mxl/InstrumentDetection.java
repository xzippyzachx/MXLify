package tab2mxl;

import java.util.ArrayList;

public class InstrumentDetection {

	
	public static int detectInstrument(ArrayList<ArrayList<String>> input)
	{
		if(input == null)
			return 0;
		
		boolean hasX = false;
		boolean hasO = false;
		
		for(ArrayList<String> line : input) {
			for(String character : line)
			{
				if(character.toLowerCase().equals("x"))
					hasX = true;
				if(character.toLowerCase().equals("o"))
					hasO = true;
				if(hasX && hasO)
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
