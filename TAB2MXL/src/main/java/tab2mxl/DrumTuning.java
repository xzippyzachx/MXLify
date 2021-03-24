package tab2mxl;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class DrumTuning {
	
	private HashMap<String, String> drumNotes;
	private HashMap<String, Integer> drumOctaves;
	private HashMap<String, String> drumID;
	private HashMap<String, Integer> drumVoice;
	
	private String[] drums;
	private int drumAmount;
	private final String FILE_NAME = "DrumNotes.txt";
	private File tuningFile;
	
	public boolean unSupportedDrum;
	
	DrumTuning(String[] drum, int d){
		drumNotes = new HashMap<String, String>();
		drumOctaves = new HashMap<String, Integer>();
		drumID = new HashMap<String, String>();
		drumVoice = new HashMap<String, Integer>();
		drums = drum;
		drumAmount = d;
		unSupportedDrum = false;
		tuningFile = new File(FILE_NAME);
		
		String drumName = "";
		String drumIDName = "";
		
		try {
			Scanner fileScanner = new Scanner(tuningFile);
			String line = "";
			
			while(fileScanner.hasNextLine()) {
				line = fileScanner.nextLine();
				drumName = line.substring(0, 2).trim();
				drumIDName += drumName;
				for(int i = 0; i < drums.length; i++) {
					if(drumName.equals(drums[i])) {
						String notePlusOctave = line.substring(4, line.indexOf('|', 4));
						String note = notePlusOctave.substring(0, notePlusOctave.length()-1);
						int octave = Integer.parseInt(notePlusOctave.substring(notePlusOctave.length()-1));
						String symbol = line.substring(line.indexOf('|', 4)+1, line.indexOf('|', 4)+2);
						String Id = line.substring(line.indexOf('|', 4)+3, line.indexOf('|', line.indexOf('|', 4)+3));
						drumIDName += symbol;
						drumNotes.put(drumName, note);
						drumOctaves.put(drumName, octave);
						System.out.println("NAME+SYMBOL: " + drumIDName);
						System.out.println("ID: "+ Id);
						drumID.put(drumIDName, Id);
					}
				}
				drumIDName = "";
			}
			fileScanner.close();
		}catch(Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
		}
		
		if(drumNotes.size() != drumAmount)
			this.unSupportedDrum = true;
		if(drumOctaves.size() != drumAmount)
			this.unSupportedDrum = true;
	}
	
	public String getNote(String drum) {
		return drumNotes.get(drum);
	}
	
	public int getOctave(String drum) {
		try {
			return drumOctaves.get(drum);
		}catch(NullPointerException e) {
			return -1;
		}
	}
	
	public String getID(String drum, String symbol) {
		return drumID.get(drum+symbol);
	}
	
	public int getVoice(String drum) {
		if(drum == "BD") {
			return 2;
		}else {
			return 1;
		}
	}
	
}
