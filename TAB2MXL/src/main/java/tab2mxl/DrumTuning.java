package tab2mxl;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class DrumTuning {
	
	private HashMap<String, String> drumNotes;
	private HashMap<String, Integer> drumOctaves;
	private HashMap<String, String> drumID;
	//private HashMap<String, Integer> drumVoice;
	
	private String[] drums;
	private int drumAmount;
	private final static String FILE_NAME = "DrumNotes.txt";
	private File drumFile;
	
	public boolean unSupportedDrum;
	
	DrumTuning(String[] drum, int d){
		drumNotes = new HashMap<String, String>();
		drumOctaves = new HashMap<String, Integer>();
		drumID = new HashMap<String, String>();
		drums = drum;
		drumAmount = d;
		unSupportedDrum = false;
		drumFile = new File(FILE_NAME);
		
		String drumName = "";
		String drumIDName = "";
		
		try {
			Scanner fileScanner = new Scanner(drumFile);
			String line = "";
			
			while(fileScanner.hasNextLine()) {
				line = fileScanner.nextLine();
				drumName = line.substring(0, 2).trim();
				drumIDName += drumName;
				for(int i = 0; i < drums.length; i++) {
//					if(drums[i].equals("H"))
//						drums[i] = "HH";
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
	
	public static boolean drumSupportCheck(String drum) {
		File file = new File(FILE_NAME);
		boolean output = false;
		try {
			Scanner fileScanner = new Scanner(file);
			String drumName = "";
			
			while(fileScanner.hasNextLine()) {
				drumName = fileScanner.nextLine().substring(0, 2).trim();
				if(drum.trim().equals(drumName)) {
					output = true;
					break;
				}
			}
			
			fileScanner.close();
		}catch(Exception e) {
			System.out.println("drumSupportCheck: " + e.getMessage());
		}

		return output;
	}
	
	public String getNote(String drum) {
		System.out.println("DrumName: " + drum);
		return drumNotes.get(drum);
	}
	
	public int getOctave(String drum) {
		System.out.println("DrumName: " + drum);
		try {
			return drumOctaves.get(drum);
		}catch(NullPointerException e) {
			return -1;
		}
	}
	
	public String getID(String drum, String symbol) {
		System.out.println("DrumName: " + drum);
		return drumID.get(drum+symbol);
	}
	
	public static int getVoice(String drum) {
		if(drum.trim().equals("BD")||drum.trim().equals("BA")||drum.trim().equals("B")) {
			return 2;
		}else {
			return 1;
		}
	}
	
}
