package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrumTuning {
	
	private HashMap<String, String> drumNotes;
	private HashMap<String, Integer> drumOctaves;
	private HashMap<String, String> drumID;
	
	private String[] drums;
	private final static String FILE_NAME = "DrumNotes.txt";
	private File drumFile;
	
	
	DrumTuning(String[] drum, int d){
		drumNotes = new HashMap<String, String>();
		drumOctaves = new HashMap<String, Integer>();
		drumID = new HashMap<String, String>();
		drums = drum;
		drumFile = new File(FILE_NAME);
		
		//String drumName = "";
		String drumIDName = "";
		
		try {
			Scanner fileScanner = new Scanner(drumFile);
			String line = "";
			String drumNames;
			int index = 0;
			while(fileScanner.hasNextLine()) {
				index = 0;
				line = fileScanner.nextLine();
				drumNames = line.substring(0, line.indexOf(" "));
				for(int i = 0; i < drums.length; i++) {
					if(drumNames.contains(drum[i])) {
						index = line.indexOf(" ");
						drumIDName += drums[i];
						String notePlusOctave = line.substring(index+1, line.indexOf(" ", index+1));
						index = line.indexOf(" ", index+1);
						//Get the note
						String note = notePlusOctave.substring(0, notePlusOctave.length()-1);
						//Get the octave
						int octave = Integer.parseInt(notePlusOctave.substring(notePlusOctave.length()-1));
						String symbol = line.substring(index, line.indexOf(" ", index+1));
						index = line.indexOf(" ", index+1);
						String Id = line.substring(index, line.indexOf(" ", index+1));
						drumIDName += symbol.trim();
						drumNotes.put(drum[i].trim(), note.trim());
						drumOctaves.put(drum[i].trim(), octave);
						drumID.put(drumIDName.trim(), Id.trim());
						drumIDName = "";
					}
				}
				
			}
			fileScanner.close();
		}catch(	Exception e) {
			System.out.println("DrumTuning Constructor: " + e.getMessage());
		}
	}
	
	public static boolean drumSupportCheck(String drum) {
		File file = new File(FILE_NAME);
		boolean output = false;
		try {
			Scanner fileScanner = new Scanner(file);
			String drumName = "";
			String line = "";
			while(fileScanner.hasNextLine()) {
				line = fileScanner.nextLine();
				drumName = line.substring(0, line.indexOf(" "));
				if(drumName.trim().contains(drum.trim())) {
					output = true;
					break;
				}
			}
			fileScanner.close();
		}catch(Exception e) {
			System.out.println("drumSupportCheckError: " + e.getMessage());
		}
		return output;
	}
	
	public String getNote(String drum) {
		return drumNotes.get(drum).trim();
	}
	
	public int getOctave(String drum) {
		try {
			return drumOctaves.get(drum.trim());
		}catch(NullPointerException e) {
			return -1;
		}
	}
	
	public String getID(String drum, String symbol) {
		for(String s : drumID.keySet()) {
			if(s.contains(drum) && s.contains(symbol)) {
				return drumID.get(s).trim();
			}
		}
		return null;
	}
	
	public static int getVoice(String drum) {
		if(drum.trim().equals("BD")||drum.trim().equals("BA")||drum.trim().equals("B")) {
			return 2;
		}else {
			return 1;
		}
	}
	
}
