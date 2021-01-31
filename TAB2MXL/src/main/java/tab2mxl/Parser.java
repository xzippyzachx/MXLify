package tab2mxl;

import java.util.ArrayList;

public class Parser {

	int stringAmount;
	int tabLineAmount;
	
	Parser(ArrayList<ArrayList<String>> input) {
		stringAmount = 0;
		tabLineAmount = 1;
		
		for(int i = 0; i < input.size(); i++)
		{			
			if(input.get(i).size() < 2)
				break;
			stringAmount++;
		}
		
		for(int i = 0; i < input.size(); i++)
			if(input.get(i).size() < 2 && input.get(i-1).size() > 2 && i != input.size())
				tabLineAmount++;
				
		//Transpose columns to rows
		ArrayList<char[]> columns = new ArrayList<char[]>();
		
		for(int layer = 0; layer < tabLineAmount; layer++)
		{
			for(int i = 0; i < input.get((layer * stringAmount) + layer).size(); i++)
			{				
				columns.add(new char[stringAmount]);
				for(int l = 0; l < stringAmount; l++)
				{
					columns.get(columns.size()-1)[l] = input.get(l + (layer * stringAmount) + layer).get(i).charAt(0);
				}
			}			
		}
		
		//Create the file generator to generate the MusicXML file
		FileGenerator FileGen = new FileGenerator ();
		
		//Calling the methods in the FileGenerator to build the MusicXML
		//This is just to test
		FileGen.AddInfo();
		FileGen.OpenMeasure(1);
		FileGen.AddNote(0,1,"A");
		FileGen.CloseMeasure();
		FileGen.End();
		int stringcheck = 0;
		int fret = 0;
		int count = 0;
		int measure = 0;
		int line = 0;
		int gate = 0;
		//Print out the input to the console
		for(char[] col : columns)
		{
			for(char character : col)
			{
				if (character == '|') {
					count++;
			      }
				if (count == 6) {
				  measure++;
				  count = 0;
				 System.out.println("measure " + measure);
			 }	
				if (character != '-' && stringcheck <= 5) {
					stringcheck++;
					System.out.println("string " + character);
				}
				gate++;
				line++;
				if (character != '-' && character != '|' && gate>=7) {
					fret = Character.getNumericValue(character);
					System.out.println("line " + line + " and fret " + fret);
				}
				if (line == 6) {
					line=0;
				}
		 }	
	
	}
	}
}
