package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Tuning {

	//hash map for the relation between the strings and the frets
	private HashMap<String, List<String>> stringNotes;
	private String[] tuning;
	//Default tuning for different string amounts.
	private static final String[] DEFAULT_TUNING5 = {"B", "G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING6 = {"E", "B", "G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING7 = {"E", "B", "G", "D", "A", "E", "B"};
	private static final String[] DEFAULT_TUNING8 = {"E", "B", "G", "D", "A", "E", "B", "F#"};
	private static final String[] DEFAULT_TUNING9 = {"E", "B", "G", "D", "A", "E", "B", "F#", "C#"};
	//the file containing the notes for each string and fret
	private File tuningFile;
	
	//Constructor for the Tuning class
	Tuning(String[] tune, int stringAmount){
		//initialize the instance variables
		//setting the tuning based on whether it should be default or not
		tuning = tune;
		/*for(String s : tuning) {
			System.out.println(s + "-----");
		}*/
		
		String fileName = "GuitarNotes.txt";
		stringNotes = new HashMap<String, List<String>>();
		tuningFile = new File(fileName);
		
		//name for each string of the guitar, either E, B,...etc
		String stringName = "";
		try {
			
			Scanner noteScanner=  new Scanner(tuningFile);
			/*the List of notes for each string and frets arranged as 
			 * position 0 for fret 1 up to position 21 for fret 22*/
			List<String> notes;
			String line = "";
				while(noteScanner.hasNextLine()) {
					notes = new ArrayList<String>();
					line  = noteScanner.nextLine();
					
					//getting the name for each string
					stringName = line.substring(0, 2).trim().toUpperCase();
					/*Checking to add the notes only if they are the same as
					 * the one specified in the tuning array*/
				for(int string  = 0; string < tuning.length; string++) {
					if(stringName.equals(tuning[string].toUpperCase())) {
						//adding the notes to the notes ArrayList
						for(int i = 0; i < line.length()-1; i++) {
							if(line.charAt(i) == '|' && line.charAt(i+1) != '|') {
								notes.add(line.substring(i+1, line.indexOf("|", i+1)).trim());
							}
						}
						//adding the stringName as the key and the List of notes as the notes for each fret
						stringNotes.put(stringName, notes);
					}
				}
				}
			noteScanner.close();
			
		}catch(FileNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static String[] getDefaultTuning(int stringAmount) {
		String[] output = null;
		
		if(stringAmount == 5)
			output = Tuning.DEFAULT_TUNING5;
		if(stringAmount == 6)
			output = Tuning.DEFAULT_TUNING6;
		if(stringAmount == 7)
			output = Tuning.DEFAULT_TUNING7;
		if(stringAmount == 8)
			output = Tuning.DEFAULT_TUNING8;
		if(stringAmount == 9)
			output = Tuning.DEFAULT_TUNING9;
		return output;
		
	}
	
	//to get the note for each specified string and fret
	public String getNote(String string, int fret) {
		if(fret <= 0)
			return string;
		String output = stringNotes.get(string).get(fret-1);
		
		return output;
	}
	
}
