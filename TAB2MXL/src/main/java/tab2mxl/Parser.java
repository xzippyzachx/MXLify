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
		FileGenerator fileGen = new FileGenerator();
		Tuning tunner = new Tuning("GuitarNotes.txt");
		
		//Calling the methods in the FileGenerator to build the MusicXML
		
		//Start the musicxml file
		fileGen.addInfo("London Bridge is Falling Down");
		
		fileGen.openPart(1);
		
		int currentColumn = 0;
		int stringcheck = 0;
		int fret = 0;
		int count = 0;
		int measure = 0;
		int line = 0;
		int gate = 0;
		
		String[] tune = new String[stringAmount];
		
		//Loop through the inputed columns
		for(char[] col : columns)
		{
			for(char character : col)
			{
				//Finds the string tunes
				if (character != '-' && stringcheck <= 5) {		
					System.out.println("string " + character);
					tune[stringcheck] = Character.toString(character);
					
					stringcheck++;
				}
				
				//Finds if there is a new measure
				if (character == '|')
					count++;				
				if (count == 6) {
					measure++;
					count = 0;
					
					
					if(fileGen.measureOpen)
						fileGen.closeMeasure();
					if(columns.size() > currentColumn + 1) {
						System.out.println("measure " + measure);
						fileGen.openMeasure(measure);
					}
				}			
				
				//Finds the string and fret of a note
				gate++;
				line++;
				if (character != '-' && character != '|' && gate>=7) {
					fret = Character.getNumericValue(character);
					System.out.println("line " + line + " and fret " + fret);
					fileGen.addNote(line, fret, tunner.getNote(tune[line], fret));
				}				
				if (line == 6) {
					line = 0;
				}
				
			}
			currentColumn++;
		}
		
		
		//End the musicxml file
		if(fileGen.measureOpen)
			fileGen.closeMeasure();
		if(fileGen.partOpen)
			fileGen.closePart();
		fileGen.end();
		
	}
}
