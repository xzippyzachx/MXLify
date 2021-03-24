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
	private String[] tuning;
	private int[] tuningOctave;
	//Default tuning for different string amounts.
	private static final String[] DEFAULT_TUNING4 = {"G", "D", "A", "E"};	//Bass
	private static final String[] DEFAULT_TUNING5 = {"B", "G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING6 = {"e", "B", "G", "D", "A", "E"};
	private static final String[] DEFAULT_TUNING7 = {"e", "B", "G", "D", "A", "E", "B"};
	private static final String[] DEFAULT_TUNING8 = {"e", "B", "G", "D", "A", "E", "B", "F#"};
	private static final String[] DEFAULT_TUNING9 = {"e", "B", "G", "D", "A", "E", "B", "F#", "C#"};
	//Default tuning octaves
	private static final int[] DEFAULT_OCTAVE4 = {3,3,2,2};	//Bass
	private static final int[] DEFAULT_OCTAVE5 = {3,3,3,2,2};
	private static final int[] DEFAULT_OCTAVE6 = {4,3,3,3,2,2};
	private static final int[] DEFAULT_OCTAVE7 = {4,3,3,3,2,2,2};
	private static final int[] DEFAULT_OCTAVE8 = {4,3,3,3,2,2,2,1};
	private static final int[] DEFAULT_OCTAVE9 = {4,3,3,3,2,2,2,1,1};
	//the file containing the notes for each string and fret
	private File tuningFile;
	
	public boolean unSupportedTune;
	public boolean unSupportedOctave;
	
	//Constructor for the Tuning class
	Tuning(String[] tune, int stringAmount, int[] tuneOctave){
		//initialize the instance variables
		//setting the tuning based on whether it should be default or not
		int octave = 0;
		tuning = tune;
		tuningOctave = tuneOctave;
		/*for(String s : tuning) {
			System.out.println(s + "-----");
		}*/
		
		String fileName = "GuitarNotesMod.txt";
		stringNotes = new HashMap<String, List<String>>();
		stringOctaves = new HashMap<String, List<Integer>>();
		tuningFile = new File(fileName);
		
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
				notes = new ArrayList<String>();
				octaves = new ArrayList<Integer>();
				line  = noteScanner.nextLine();
				
				//getting the name for each string
				stringName = line.substring(0, 2).trim();
				/*Checking to add the notes only if they are the same as
				 * the one specified in the tuning array*/
				for(int string  = 0; string < tuning.length; string++) {
					if(stringName.equals(tuning[string]) && octaveCheck(tuningOctave[string])) {
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
						//adding the stringName as the key and the List of notes as the notes for each fret
						stringNotes.put(stringName, notes);
						stringOctaves.put(stringName, octaves);
					}
				}
			}
			/*for(String s : stringOctaves.keySet()) {
				System.out.print(s + "||");
				for(int i = 0; i < stringOctaves.get(s).size(); i++) {
					System.out.print(stringOctaves.get(s).get(i) + "|");
				}
				System.out.print("\n");
			}*/
			
			//Faruq make this check the notes file please - Zach
			//Temporary way to check if the tuning is valid. Need to actually check if the Note Tunes file contains the tune 
			if(stringNotes.size() != stringAmount) {
				unSupportedTune = true;
			}
			if(stringOctaves.size() != stringAmount) {
				unSupportedOctave = true;
			}
				
			noteScanner.close();
			
		}catch(FileNotFoundException e) {
			e.getMessage();
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
		
		if(stringAmount == 4)
			output = Tuning.DEFAULT_TUNING4;
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
	
	public static int[] getDefaultTuningOctave(int stringAmount) {
		int[] output = null;
		
		if(stringAmount == 4)
			output = Tuning.DEFAULT_OCTAVE4;
		if(stringAmount == 5)
			output = Tuning.DEFAULT_OCTAVE5;
		if(stringAmount == 6)
			output = Tuning.DEFAULT_OCTAVE6;
		if(stringAmount == 7)
			output = Tuning.DEFAULT_OCTAVE7;
		if(stringAmount == 8)
			output = Tuning.DEFAULT_OCTAVE8;
		if(stringAmount == 9)
			output = Tuning.DEFAULT_OCTAVE9;
		
		return output;
	}
	
	public int[] getTuningOctave() {
		return tuningOctave;
	}
	
	public int getOctave(String string, int fret) {
		int output = stringOctaves.get(string).get(fret);
		
		return output;
	}
	
	//to get the note for each specified string and fret
	public String getNote(String string, int fret) {
		if(fret < 0)
			return string;
		String output = stringNotes.get(string).get(fret);
		
		return output;
	}
	
}