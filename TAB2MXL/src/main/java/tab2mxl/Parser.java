package tab2mxl;

import java.util.ArrayList;

public class Parser {

	int stringAmount;
	
	Parser(ArrayList<ArrayList<String>> input) {	

		stringAmount = 0;
		for(int i = 0; i < input.size(); i++)
		{			
			if(input.get(i).size() == 0)
				break;
			stringAmount++;
		}
		
		//Transpose columns to rows
		ArrayList<char[]> columns = new ArrayList<char[]>();		
		for(int i = 0; i < input.get(0).size(); i++)
		{
			columns.add(new char[stringAmount]);
			for(int l = 0; l < stringAmount; l++)
			{
				columns.get(i)[l] = (input.get(l).get(i).charAt(0));
			}
		}
		
		//Create the file generator to generate the MusicXML file
		FileGenerator FileGen = new FileGenerator (columns);
		
		//Calling the methods in the FileGenerator to build the MusicXML
		FileGen.AddInfo();
		FileGen.OpenMeasure(1);
		FileGen.AddNote(0,1,"A");
		FileGen.CloseMeasure();
		FileGen.End();
		
		//Print out the input to the console
		for(char[] col : columns)
		{
			for(char character : col)
			{
				System.out.print(character);
			}
			System.out.println("");
		}
		
	}
	
}
