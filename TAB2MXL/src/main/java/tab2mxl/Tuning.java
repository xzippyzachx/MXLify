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
	private HashMap<String, List<Integer>> stringOctaves;
	private int[] tuningOctave;
	private String[] tuning;
	//Default tuning for different string amounts.
	private static final String[] DEFAULT_TUNING1 = {"E"};
	private static final String[] DEFAULT_TUNING2 = {"A", "E"};
	private static final String[] DEFAULT_TUNING3 = {"D", "A", "E"};
	private static final String[] DEFAULT_TUNING4 = {"G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING5 = {"B", "G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING6 = {"e", "B", "G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING7 = {"e", "B", "G", "D", "A", "E", "B"};
	private static final String[] DEFAULT_TUNING8 = {"e", "B", "G", "D", "A", "E", "B", "F#"};
	private static final String[] DEFAULT_TUNING9 = {"e", "B", "G", "D", "A", "E", "B", "F#", "C#"};
	//Default tuning octaves
	private static final int[] DEFAULT_OCTAVE1 = {2};
	private static final int[] DEFAULT_OCTAVE2 = {2,2};
	private static final int[] DEFAULT_OCTAVE3 = {3,2,2};
	private static final int[] DEFAULT_OCTAVE4 = {3,3,2,2};
	private static final int[] DEFAULT_OCTAVE5 = {3,3,3,2,2};
	private static final int[] DEFAULT_OCTAVE6 = {4,3,3,3,2,2};
	private static final int[] DEFAULT_OCTAVE7 = {4,3,3,3,2,2,2};
	private static final int[] DEFAULT_OCTAVE8 = {4,3,3,3,2,2,2,1};
	private static final int[] DEFAULT_OCTAVE9 = {4,3,3,3,2,2,2,1,1};
	//the file containing the notes for each string and fret
	private File tuningFile;
	private final String FILE_NAME = "GuitarNotes.txt";
	
	public boolean unSupportedTune;
	public boolean unSupportedOctave;
	
	//Constructor for the Tuning class
	Tuning(String[] tune, int stringAmount, int[] tuneOctave){
		//initialize the instance variables
		//setting the tuning based on whether it should be default or not
		int octave = 0;
		tuning = tune;
		tuningOctave = tuneOctave;

		stringNotes = new HashMap<String, List<String>>();
		stringOctaves = new HashMap<String, List<Integer>>();
		tuningFile = new File(FILE_NAME);
		
		//name for each string of the guitar, either E, B,...etc
		String stringName = "";
		try {			
			Scanner noteScanner=  new Scanner(tuningFile);
			/*the List of notes for each string and frets arranged as 
			 * position 0 for fret 1 up to position 21 for fret 22*/
			List<String> notes;
			List<Integer> octaves;
			String line = "";
			
			while(noteScanner.hasNextLine()) {
				int fret = 0;
				line  = noteScanner.nextLine();
				
				//getting the name for each string
				stringName = line.substring(0, 2).trim();
				/*Checking to add the notes only if they are the same as
				 * the one specified in the tuning array*/
				for(int string  = 0; string < tuning.length; string++) {
					notes = new ArrayList<String>();
					octaves = new ArrayList<Integer>();
					if(stringName.equals(tuning[string].toUpperCase()) && octaveCheck(tuningOctave[string])) {
						octave = tuningOctave[string];
						//adding the notes to the notes ArrayList
						for(int i = 0; i < line.length()-1; i++) {
							if(line.charAt(i) == '|' && line.charAt(i+1) != '|') {
								String note = line.substring(i+1, line.indexOf("|", i+1)).trim();
								//System.out.println(fret);
								if(fret > 0) {
									if(note.equals("C")) {
										octave++;
									}
								}
								if(octave > 9) {
									octave = 0;
								}
								octaves.add(octave);
								notes.add(note);
								fret++;
							}
						}
						/*adding the stringName and the string number as the key 
						 * and the List of notes as the notes for each fret
						 * as well as the list of octaves*/
						String key = stringName+(string+1);
						stringNotes.put(key, notes);
						stringOctaves.put(key, octaves);
					}
				}
			}
			
			/* For printing out tunes
			for(String s : stringOctaves.keySet()) {
				//System.out.print(s + "||");
				for(int i = 0; i < stringOctaves.get(s).size(); i++) {
					System.out.print(stringOctaves.get(s).get(i) + "|");
				}
				System.out.print("\n");
			}
			
			for(String s : stringNotes.keySet()) {
				System.out.print(s + "||");
				for(int i = 0; i < stringNotes.get(s).size(); i++) {
					System.out.print(stringNotes.get(s).get(i) + "|");
				}
				System.out.print("\n");
			}
			*/
			
			//Temporary way to check if the tuning is valid. Need to actually check if the Note Tunes file contains the tune 
			if(stringNotes.size() != stringAmount) {
				unSupportedTune = true;
			}
			if(stringOctaves.size() != stringAmount) {
				unSupportedOctave = true;
			}
				
			noteScanner.close();
			
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean octaveCheck(int o) {
		boolean output = false;
		
		if(o != -1) 
			output = output || true;
		if(o >= 0 && o <= 9)
			output = output || true;
		
		return output;
	}
	
	public static String[] getDefaultTuning(int stringAmount) {
		String[] output = null;
		
		switch (stringAmount)
		{
			case 1:
				output = Tuning.DEFAULT_TUNING1;
				break;
			case 2:
				output = Tuning.DEFAULT_TUNING2;
				break;
			case 3:
				output = Tuning.DEFAULT_TUNING3;
				break;
			case 4:
				output = Tuning.DEFAULT_TUNING4;
				break;
			case 5:
				output = Tuning.DEFAULT_TUNING5;
				break;
			case 6:
				output = Tuning.DEFAULT_TUNING6;
				break;
			case 7:
				output = Tuning.DEFAULT_TUNING7;
				break;
			case 8:
				output = Tuning.DEFAULT_TUNING8;
				break;
			case 9:
				output = Tuning.DEFAULT_TUNING9;
				break;				
			default:
				break;
		}
		
		return output;		
	}
	
	public static int[] getDefaultTuningOctave(int stringAmount, int i) {
		int[] output = null;
		
		switch (stringAmount)
		{
			case 1:
				output = Tuning.DEFAULT_OCTAVE1;
				break;
			case 2:
				output = Tuning.DEFAULT_OCTAVE2;
				break;
			case 3:
				output = Tuning.DEFAULT_OCTAVE3;
				break;
			case 4:
				output = Tuning.DEFAULT_OCTAVE4;
				break;
			case 5:
				output = Tuning.DEFAULT_OCTAVE5;
				break;
			case 6:
				output = Tuning.DEFAULT_OCTAVE6;
				break;
			case 7:
				output = Tuning.DEFAULT_OCTAVE7;
				break;
			case 8:
				output = Tuning.DEFAULT_OCTAVE8;
				break;
			case 9:
				output = Tuning.DEFAULT_OCTAVE9;
				break;				
			default:
				break;
		}

		if(i == 1) {
			for(int j = 0; j < output.length; j++) {
				output[j]--;
			}
		}
		
		return output;
	}
	
	public int[] getTuningOctave() {
		return tuningOctave;
	}
	
	public int getOctave(String string, int fret, int line) {
		String key = string.toUpperCase() + line;
		int i = fret/12;
		return (stringOctaves.get(key).get(fret%12)+i);
	}
	
	//to get the note for each specified string and fret
	public String getNote(String string, int fret, int line) {
		String key = string.toUpperCase() + line;
		//System.out.println("Key: " + key);
		if(fret < 0)
			return string;
		String output = stringNotes.get(key).get(fret%12);
		
		return output;
	}
	
}