package tab2mxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	int stringAmount;
	int tabLineAmount;
	private int beat;
	private int beatType;
	private ArrayList<char[]> columns;
	public static  Map<String,String> misc;
	
	//@SuppressWarnings("unused")
	Parser(ArrayList<ArrayList<String>> input) {
		stringAmount = 0;
		tabLineAmount = 1;
		misc = new HashMap<String, String>();
		
		addTitle(TextInputContentPanel.title);
		addTabType(TextInputContentPanel.tabType);
		addTime(TextInputContentPanel.timeSig);
		
		//set the time signature to default if the inputed time signature isn't in the right format
		if(misc.get("TimeSig") == "") {
			beat = 4;
			beatType = 4;
		}else if(misc.get("TimeSig").indexOf('/') == -1) {
			beat = 4;
			beatType = 4;
		}else if(misc.get("TimeSig").charAt(0) == '/') {
			beat = 4;
			beatType = 4;
		}else if(misc.get("TimeSig").charAt(misc.get("TimeSig").length()-1) == '/') {
			beat  = 4;
			beatType = 4;
			//set the time signature to default if the inputed time signature isn't in the right format
		}else {
			//get the beat and beat-type from the information given in the time signature
			try{
				beat = Integer.parseInt(misc.get("TimeSig").substring(0, misc.get("TimeSig").indexOf('/')));
				beatType = Integer.parseInt(misc.get("TimeSig").substring(misc.get("TimeSig").indexOf('/')+1));
			}catch(NumberFormatException e) {
				beat = 4;
				beatType = 4;
			}
		}
		
		for(int i = 0; i < input.size(); i++)
		{			
			if(input.get(i).size() < 2)
				break;
			stringAmount++;
		}
		
		for(int i = 0; i < input.size(); i++)
			if(input.get(i).size() < 2 && input.get(i-1).size() > 2 && i != input.size())
				tabLineAmount++;
				
		//Transpose columns to rows (do you mean rows to col?)
		columns = new ArrayList<char[]>();
		
		for(int layer = 0; layer < tabLineAmount; layer++)
		{
			for(int i = 2; i < input.get((layer * stringAmount) + layer).size(); i++)
			{
				columns.add(new char[stringAmount]);
				for(int l = 0; l < stringAmount; l++)
				{
						columns.get(columns.size()-1)[l] = input.get(l + (layer * stringAmount) + layer).get(i).charAt(0);					}
			}			
		}

		char[] chords = new char[stringAmount];
		int[] chordsOctave = new int[stringAmount];
		String chordType= "";
		int[] chordDot = new int[stringAmount];		
		int currentColumn = 0;
		int fret = 0;
		int count = 0;
		int measure = 0;
		int line = 0;
		int gate = 0;
		double beatTypeNote = 1.0/beatType;
		int div = getDivisions(beat);
		double dash = 0; 
		char[] col;
		int[] fretarray = new int[stringAmount];
		int[] linearray = new int[stringAmount];
		char character = ' ';
		int notesInColumn = 0;
		char note = ' ';
		int chordOctave = 0;
		boolean chord;
		String[] tune = new String[stringAmount];
		int[] tuningOctave = new int[stringAmount];
		Boolean defaultTune = false;
		Boolean defaultOctave = false;
        boolean[] sharp = new boolean[stringAmount];
        boolean sharpnote = false;
		
		/*adds the tuning of the strings to the tune array if the tuning is
		 * specified in the TAB, or the default if it isn't*/
		for(int i = 0; i < stringAmount; i++) {
			if(input.get(i).get(0) != "-" && input.get(i).get(0) != "|") {
				tune[i] = input.get(i).get(0);
			}
		}
		if(containsOnlyString(tune, "")) {
			tune = Tuning.getDefaultTuning(stringAmount);
			defaultTune = true;
		}
		for(int i = 0; i < stringAmount; i++) {
			if(input.get(i).get(1) != "-" && input.get(i).get(1) != "|") {
				if(input.get(i).get(1) == "") {
					tuningOctave[i] = -1;
				}else {
					tuningOctave[i] = Integer.parseInt(input.get(i).get(1));
				}
			}
		}
		if(containsOnlyInt(tuningOctave, -1)) {
			tuningOctave = Tuning.getDefaultTuningOctave(stringAmount);
			defaultOctave = true;
		}	
		
		Tuning tunner = new Tuning(tune, stringAmount, tuningOctave);
		if(tunner.unSupportedTune == true && !defaultTune)
		{
			Main.myFrame.textInputContentPanel.errorText.setText("Tune Not Recognized");
			return;
		}
		if(tunner.unSupportedOctave == true && !defaultOctave)
		{
			Main.myFrame.textInputContentPanel.errorText.setText("Octave Not Recognized");
			return;
		}
		
		//Create the file generator to generate the MusicXML file
		FileGenerator fileGen = new FileGenerator("");
		
		if(fileGen.failed) //Check if failed to save file to location
			return;
		
		//Calling the methods in the FileGenerator to build the MusicXML
		
		//Start the musicxml file
		fileGen.addInfo(misc.get("Title"));
		
		//Open part
		fileGen.openPart(1);
		
		//Loop through the inputed columns
		for(int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			chord = false;
			if(i > 0) {
				notesInColumn = 0;
				for (int s = 0; s < col.length;s++) {
					character = col[s];
					if (character != '-' && character != '|') {
						notesInColumn++;
					}
				}
			}
			if(notesInColumn > 1) {
				chord = true;
			}
			
			for(int j = 0; j < col.length; j++)
			{
				character = col[j];
				// To check what type of note we have, by checking ahead
				if(character != '-' && character != '|') {
					dash = 1;
					boolean test;
					for(int k = i+1; k < columns.size()-1; k++) {
						if(!containsOnlyChar(columns.get(k), '|')) 
						{
							test = containsOnlyChar(columns.get(k), '-');
							if(test) {
								dash++;
								}else {
								break;
								}
						}
						else
							break;
					}
				}
				
				//Finds if there is a new measure
				if (character == '|' && (i == 0 || columns.get(i-1)[j] != '|'))
					count++;				
				if (count == 6) {
					measure++;
					count = 0;					
					
					if(fileGen.measureOpen)
						fileGen.closeMeasure();
					if(columns.size() > currentColumn + 1) {
						fileGen.openMeasure(measure);
						
						if(measure == 1) {
							fileGen.attributes(getDivisions(beat), 0, beat, beatType, "G", tune, tuningOctave);
						}
					}
				}			

				double beatNote = (dash * beatTypeNote)/div;
				//Finds the string and fret of a note
				gate++;
				line++;
				
				if (character != '-' && character != '|' && gate>=7) {
					fret = Character.getNumericValue(character);
					if(fret < 0)
					{
						System.out.println("Bad Char:(" + character + ")");
						fret = 0;
					}
					if (!chord) {
						if (tunner.getNote(tune[line-1], fret).substring(tunner.getNote(tune[line-1], fret).length()-1,tunner.getNote(tune[line-1], fret).length()).equals("#")){
							sharpnote = true;
						}
						fileGen.addNote(line, fret, tunner.getNote(tune[line-1], fret), noteType(beatNote), getDuration(beatNote), tunner.getOctave(tune[line-1], fret), dot(beatNote),sharpnote);
						sharpnote = false;
					}
					else {
						linearray[j] = line;
						fretarray[j] = fret;
						note = tunner.getNote(tune[line-1], fret).charAt(0);
						chordOctave = tunner.getOctave(tune[line-1], fret);
						chords[j] = note;
						chordsOctave[j] = chordOctave;
						chordType = noteType(beatNote);
						chordDot[j] = dot(beatNote);
						if (tunner.getNote(tune[line-1], fret).substring(tunner.getNote(tune[line-1], fret).length()-1,tunner.getNote(tune[line-1], fret).length()).equals("#")){
							sharp[j] = true;
						}
						else 
							sharp[j] = false;
					}
				}
				if (line == stringAmount) {
					line = 0;
				}	
			}
			if (chord) {
				double beatNote = (dash * beatTypeNote)/div;
				fileGen.addChord(chords,chordType, getDuration(beatNote), chordsOctave,linearray,fretarray, chordDot,sharp);
				linearray = new int[stringAmount];
				fretarray = new int[stringAmount];
				chords = new char[stringAmount];
				chordsOctave = new int[stringAmount];
				chordDot = new int[stringAmount];
			}
			currentColumn++;
		}
		
		//End the musicxml file
		if(fileGen.measureOpen)
			fileGen.closeMeasure();
		if(fileGen.partOpen)
			fileGen.closePart();
		fileGen.end();
		
		new SuccessPopUp(Main.myFrame, FileGenerator.filepath);		
	}
	
	private boolean containsOnlyChar(char[] cs, char o) {
		boolean output = true;
		
		for(Object t : cs) {
			output = output && t.equals(o) ;
		}
		
		return output;
	}
	
	private boolean containsOnlyInt(int[] cs, int o) {
		boolean output = true;
		
		for(Object t : cs) {
			output = output && t.equals(o) ;
		}
		
		return output;
	}
	
	private boolean containsOnlyString(String[] cs, String o) {
		boolean output = true;
		
		for(Object t : cs) {
			output = output && t.equals(o) ;
		}
		
		return output;
	}
	
	private int dot(double beatNote) {
		int output = 0;
		double check = 0.0;
		double check2 = 0.0;
		double temp;
		double div = 2.0;
		
		for(double i = 2.0; i > 0.0; i = i/2.0) {
			if(beatNote >= i) {
				check = i;
				break;
			}
		}
		boolean loop = true;

		temp = check;
		check2 = check;
		while(loop) {
			check = check + temp/div;
			if(check2 < beatNote && beatNote <= check) {
				output++;
			}else {
				loop = false;
			}
			check2 = check2 + temp/div;
		}
		
		return output;
	}
	
	protected static String noteType(double beatNote) {
		String output = "";
		
		if(beatNote >= 2) {
			output = "double";
		}else if(beatNote >= 1.0) {
			output = "whole";
		}else if(beatNote >= 1.0/2.0) {
			output = "half";
		}else if(beatNote >= 1.0/4.0) {
			output = "quarter";
		}else if(beatNote  >= 1.0/8.0) {
			output = "eighth";
		}else if(beatNote < 1.0/8.0) {
			int div = (int) ((1.0/8.0)/beatNote);
			int maxIndex = 0;
			double power = 0.0;
			if(div % 2 != 0) {
				while(div % 2 != 0) {
					if(div < Math.pow(2.0, power)) {
						div = (int) Math.pow(2, power);
					}else {
						power = power + 1.0;
					}
				}
			}
			while(div != 1) {
				div = div/2;
				maxIndex++;
			}
			int temp = (int)(Math.pow(2, maxIndex) * 8);
			output = output + temp;
			int lastTwo = Integer.parseInt(output.substring(output.length()-2));
			if(output.charAt(output.length()-1) == '1') {
				output = output + "st";
			}else if(11 <= lastTwo && lastTwo <= 19) {
				output = output + "th";
			}else if(output.charAt(output.length()-1) == '2') {
				output = output + "nd";
			}else if(output.charAt(output.length()-1) == '3') {
				output = output + "rd";
			}else {
				output = output + "th";
			}
		}
		return output;
	}
	
	private int getDuration(double noteType) {
		double output = 0;
		double div = getDivisions(beat);
		double beatTypeNote = 1.0/beatType;
		output = (noteType * div)/beatTypeNote;
		return (int)output;
	}
	
	private int getDivisions(int beatSig) {
		int hyfenNumber = -1;
		int boundary = 0;
		
		for(int i=0;i< columns.size();i++) {
			
			if(columns.get(i)[0] == '|'){
				boundary++;
			}
			if (boundary == 2) {
				break;
			}
			
			if(columns.get(i)[0] == '-') {
				hyfenNumber++;
			}
			else {
				if(i==0) {
				
			}
				else if(columns.get(i-1)[0] != '-') {
					
				}
				else {
					hyfenNumber++;
				}
		}
			}
		double beatNote = 1.0/beatType;
		double bSig  = 1.0 * beatSig;
		double totalBeatPerMeasure = bSig/beatType;
		double division = (hyfenNumber * beatNote)/totalBeatPerMeasure;

		return (int)Math.round(division);
	}
	
	static void addTitle(String title){
		misc.put("Title",title);
	}
	
	static void addTabType(String tabType){
		misc.put("TabType",tabType);
	}
	
	static void addTime(String timeSig){
		misc.put("TimeSig",timeSig);
	}
}
