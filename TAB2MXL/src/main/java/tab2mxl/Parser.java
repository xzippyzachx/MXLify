package tab2mxl;

import java.util.ArrayList;

public class Parser {

	Parser(ArrayList<ArrayList<String>> input) {	
	
		//Print out the input
		for(ArrayList<String> line : input )
		{
			for(String character : line)
			{
				System.out.print(character);
			}
			System.out.println("");
		}
		
	}
	
}
