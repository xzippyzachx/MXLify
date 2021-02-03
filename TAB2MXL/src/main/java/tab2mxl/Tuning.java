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
	//the file containign the notes for each string and fret
	private File tuningFile;
	
	//Constructor for the Tuning class
	Tuning(String fileName){
		//initialize the instance variables
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
				
				//adding the notes to the notes ArrayList
				for(int i = 0; i < line.length()-1; i++) {
					if(line.charAt(i) == '|' && line.charAt(i+1) != '|') {
						notes.add(line.substring(i+1, line.indexOf("|", i+1)).trim());
					}
				}
				//getting the name for each string
				stringName = line.substring(0, 1);
				//adding the stringName as the key and the List of notes as the notes for each fret
				stringNotes.put(stringName, notes);
			}
			
			noteScanner.close();
			
		}catch(FileNotFoundException e) {
			e.getMessage();
		}
	}
	
	//to get the note for each specified string and fret
	public String getNote(String string, int fret) {
		if(fret <= 0)
			return string;
		String output = stringNotes.get(string).get(fret-1);
		
		return output;
	}
	
}
