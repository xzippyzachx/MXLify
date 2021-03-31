package tab2mxl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gui_panels.TextInputContentPanel;
import gui_popups.SuccessPopUp;

public class Parser {

	int instrument;
	int stringAmount;
	int tabLineAmount;
	private int beat;
	private int beatType;
	private ArrayList<char[]> columns;
	public static  Map<String,String> misc;
	private Tuning tunner;
	private FileGenerator fileGen;
	private double rest;
	private int totalDash;
	
	//@SuppressWarnings("unused")
	Parser(ArrayList<ArrayList<String>> input, int in ,String path) {
		stringAmount = 0;
		tabLineAmount = 1;
		misc = new HashMap<String, String>();
		instrument = in;
		
		addInstrument(TextInputContentPanel.getInstrument());
		addTitle(TextInputContentPanel.getTitle());		
		addTime(TextInputContentPanel.getTimeSig());
		
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
//		System.out.println("beat: " + beat);
//		System.out.println("beatType: " + beatType);
		
		for(int i = 0; i < input.size(); i++) {		
			if(input.get(i).size() < 2)
				break;
			stringAmount++;
		}
		
		for(int i = 0; i < input.size(); i++) {
			if(input.get(i).size() < 2 && input.get(i-1).size() > 2 && i != input.size())
				tabLineAmount++;
			/*if(input.get(i).size() < 2 && input.get(i-1).size() < 2 && i != input.size())
				input.remove(i);*/
		}
		System.out.println("TabLineAmount: " + tabLineAmount);
		
		//Transpose columns to rows (do you mean rows to col?)
		columns = new ArrayList<char[]>();
		
		for(int layer = 0; layer < tabLineAmount; layer++) {
			for(int i = 2; i < input.get((layer * stringAmount) + layer).size(); i++) {
				columns.add(new char[stringAmount]);
				for(int l = 0; l < stringAmount; l++) {
					columns.get(columns.size()-1)[l] = input.get(l + (layer * stringAmount) + layer).get(i).charAt(0);
				}
			}		
		}

		
		//Other		
		int currentColumn = 0;
		int fret = 0;
		int count = 0;
		int measure = 0;
		int line = 0;
		int gate = 0;
		double totalBeatPerMeasure = (1.0 * beat)/beatType;
		double beatTypeNote = 1.0/beatType;
		int div = getDivisions(beat);
		double dash = 0; 
		char[] col;
		int[] fretarray = new int[stringAmount];
		int[] linearray = new int[stringAmount];
		char character = ' ';
		int notesInColumn = 0;
		char note = ' ';
		rest = 0.0;
		double totalNote = 0.0;		
		//Double digit
		boolean doubleDigit;
		boolean doubleDigitColumn;
		boolean  chordDoubleDigitFlag;
		//Chords
		String chordType= "";
		boolean chord;
		char[] chords = new char[stringAmount];
		int[] chordDot = new int[stringAmount];
		int chordOctave = 0;
		int[] chordsOctave = new int[stringAmount];
		//HammerOn
		boolean hammerOn = false;
		boolean hammerStart =false;
		boolean hammerContinue = false;
		boolean hammerDone = false;
		int hammerLength = 0;
		int hammerDuration = 0;
		int hammerLocation = -1;		
		//Repeat
		boolean wasRepeat = false;
		boolean isRepeat = false;
		boolean startRepeat = false;
		boolean endRepeat = false;
		int repeatAmount = 0;		
		//Tunning
		String[] tune = new String[stringAmount];
		int[] tuningOctave = new int[stringAmount];
		boolean defaultTune = false;
		boolean defaultOctave = false;		
		//Sharps
        boolean[] sharp = new boolean[stringAmount];
        boolean sharpnote = false;
        //Harmonic
        boolean harmonic = false;
		boolean[] hchord = new boolean[stringAmount];
		boolean grace = false;
		int gdash = 0;
		boolean breakout = false;
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
			tuningOctave = Tuning.getDefaultTuningOctave(stringAmount, instrument);
			defaultOctave = true;
		}
		tunner = new Tuning(tune, stringAmount, tuningOctave);

		if(tunner.unSupportedOctave == true && !defaultOctave)
		{
			Main.myFrame.textInputContentPanel.errorText.setText("Octave Not Recognized");
			return;
		}
		if(tunner.unSupportedTune == true && !defaultTune)
		{
			Main.myFrame.textInputContentPanel.errorText.setText("Tune Not Recognized");
			return;
		}

		//Create the file generator to generate the MusicXML file
		fileGen = new FileGenerator(path);
		
		if(fileGen.failed) //Check if failed to save file to location
			return;
		
		//Calling the methods in the FileGenerator to build the MusicXML
		
		//Start the musicxml file
		fileGen.addInfo(misc.get("Title"), misc.get("Instrument"));
		
		//Open part
		fileGen.openPart(1);
		
		//////////////////////////////////
		//Loop through the inputed columns
		//////////////////////////////////
		for(int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			chordDoubleDigitFlag = false;
			chord = false;
			doubleDigitColumn = false;

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
			
			///////////////////////////////////////
			//Loop through each row of the column//
			///////////////////////////////////////
			for(int j = 0; j < col.length; j++)
			{
				doubleDigit = false;
				character = col[j];																
				//Finds if there is a new measure				
				if (character == '|' && (i == 0 || columns.get(i-1)[j] != '|'))
				{
					count++;					
					//Check for repeat start
					if(columns.size() > i+2 && character == '|' && columns.get(i+1)[j] == '|' && columns.get(i+2)[j] == '*')
					{
						isRepeat = true;
						startRepeat = true;						
						for(int c = i; c < columns.size(); c++)
						{
							if(Character.isDigit(columns.get(c)[0]) && columns.get(c)[1] == '|')
							{
								repeatAmount = Character.getNumericValue(columns.get(c)[0]);
								break;
							}
						}
					}
				}
				if (count == stringAmount) {
					measure++;
					count = 0;					
					
					if(fileGen.measureOpen)
						fileGen.closeMeasure(wasRepeat, repeatAmount);
					if(columns.size() > currentColumn + 1) {
						fileGen.openMeasure(measure, isRepeat, repeatAmount);						
						if(measure == 1) {
							fileGen.attributes(div, 0, beat, beatType, "G", tune, tuningOctave);
						}
					}					
					wasRepeat = isRepeat;
				}
				
				//Check for end of repeat
				if(columns.size() > i+1 && character == '*' && columns.get(i+1)[j] == '|')
					endRepeat = true;
				
				//This checks for double digits
				if(i+1<columns.size() && Character.isDigit(columns.get(i)[j]) && Character.isDigit(columns.get(i+1)[j]))
				{
					doubleDigit = true;
					doubleDigitColumn = true;
				}
                if (i<columns.size() && columns.get(i)[j] == 'g'){
                    grace = true;
                    char gcharacter = 'g';
                    while(gcharacter == 'g' || Character.isDigit(gcharacter) || gcharacter == 'h' || gcharacter == 'p') {
                    	for(int z = 0; z < col.length; z++)
                    	{
                    	if(Character.isDigit(columns.get(z)[j]) && dash > 0) {
                    		    breakout = true;
                    			break;
                		}
                		}
                    	if (breakout == true){
                    		break;
                    	}
                    	dash++;
                    	gcharacter = columns.get(i+1)[j];
                    	
                }
                }
				//Hammer on
				if(i+1 < columns.size() && j<col.length && (columns.get(i+1)[j] == 'h' || (doubleDigit &&columns.get(i+2)[j]== 'h')) && !hammerOn){ // if same row, next col is an h then then begin hammer on
																 // (i+1) only works for single digit frets
					int m = i;
					char currentChar = columns.get(i)[j];
					hammerLength++;
					int lastHammerFret = 0;
					while(Character.isDigit(currentChar) || currentChar == 'h'){ // checks length of hammer on
						if(currentChar == 'h') hammerLength++;
						lastHammerFret++;
						m++;
						currentChar =  columns.get(m)[j];
						hammerLocation = j;
					}
					hammerDuration = hamererOnDuration(columns.get(i+2)[j],i+lastHammerFret-1); // sets necessary flags to true
					hammerOn = true;
					hammerStart = true;

				}
				else if(character != 'h' && character != '-' && character != '|' && !hammerOn &&!chordDoubleDigitFlag) {
					dash = 1;
					int offset = 0;
					if(columns.size() > i+1 && Character.isDigit(columns.get(i+1)[j])) {
						chordDoubleDigitFlag = true;
						offset++;
					}
					boolean test;
					for(int k = i+1+offset; k < columns.size()-1; k++) {
						if(!containsOnlyChar(columns.get(k), '|')) {
							test = containsOnlyChar(columns.get(k), '-');
							if(test) {
								dash++;
							}else
								break;
						}else
							break;
					}
					
					//System.out.println("Offset: " + offset);					
				}			

				double beatNote;
				if(hammerOn){
					beatNote = beatNote((hammerDuration * totalBeatPerMeasure)/totalDash);/*beatNote((hammerDuration * beatTypeNote)/div);*/
				}
				else {beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);/*beatNote((dash * beatTypeNote)/div)*/}

				//Finds the string and fret of a note
				gate++;
				line++;
				
				if (Character.isDigit(character) && gate>=7) {
					fret = Character.getNumericValue(character);
					if (i-1>0 && columns.get(i-1)[j] == '[') {
						harmonic = true;
					}
					if(doubleDigit) fret = (fret * 10) + Character.getNumericValue(columns.get(i+1)[j]);
					
					if(fret < 0)
					{
						System.out.println("Bad Char:(" + character + ")");
						fret = 0;
					}
					if (!chord) {
						if (tunner.getNote(tune[line-1], fret, line).substring(tunner.getNote(tune[line-1], fret, line).length()-1,tunner.getNote(tune[line-1], fret, line).length()).equals("#")){
							sharpnote = true;
						}
						fileGen.addNote(line, fret, tunner.getNote(tune[line-1], fret, line).charAt(0), noteType(beatNote), getDuration(beatNote) - getDuration(rest), tunner.getOctave(tune[line-1], fret, line), dot(beatNote),sharpnote, hammerStart,hammerContinue,hammerDone,harmonic,grace);
						harmonic = false;
						grace = false;
//						System.out.println("");
//						System.out.println("Dash: " + dash);
//						System.out.println("Duration: " + getDuration(beatNote));
//						System.out.println("");
						if(rest > 0) {
							fileGen.addRest(getDuration(rest), noteType(rest), -1);
							rest = 0.0;
						}
							
						if(hammerOn){
							hammerLength--;
						}
						sharpnote = false;

						if(hammerStart){ // indicates that we have past first note of hammer on
							hammerStart = false;
							hammerContinue = true;
						}
						if(hammerLength == 1){ // indicates that the next note is the end of the hammer on
							hammerContinue = false;
							hammerDone = true;
						}
						if (hammerLength == 0 ){ // indicates that the hammer on is over and back to regular scheduled programming
							hammerDone = false;
							hammerOn = false;
							hammerDuration = 0;
							hammerLength = 0;
						}

					}
                    else {
                    	if (i-1>0 && columns.get(i-1)[j] == '[') {
    						hchord[j] = true;
    					}
						linearray[j] = line;
						fretarray[j] = fret;
						note = tunner.getNote(tune[line-1], fret, line).charAt(0);
						chordOctave = tunner.getOctave(tune[line-1], fret, line);
						chords[j] = note;
						chordsOctave[j] = chordOctave;
						chordType = noteType(beatNote);
						chordDot[j] = dot(beatNote);
						if (tunner.getNote(tune[line-1], fret, line).substring(tunner.getNote(tune[line-1], fret, line).length()-1,tunner.getNote(tune[line-1], fret, line).length()).equals("#")){
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
			
			
			//Chord
			if (chord) {
				double beatNote;
				if(hammerOn){
					beatNote = beatNote((hammerDuration * totalBeatPerMeasure)/totalDash);/*beatNote((hammerDuration * beatTypeNote)/div);*/
					totalNote += beatNote;
				}
				else {
					beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);/*beatNote((dash * beatTypeNote)/div)*/
				}

				fileGen.addChord(chords,chordType, getDuration(beatNote) - getDuration(rest), chordsOctave,linearray,fretarray, chordDot,sharp,hammerLocation,hammerStart,hammerContinue,hammerDone,hchord);
				if(rest > 0) {
					fileGen.addRest(getDuration(rest), noteType(rest), -1);
					rest = 0.0;
				}
				linearray = new int[stringAmount];
				fretarray = new int[stringAmount];
				chords = new char[stringAmount];
				chordsOctave = new int[stringAmount];
				chordDot = new int[stringAmount];
				if(hammerOn){
					hammerLength--;
				}
				if(hammerStart){ // indicates that we have past first note of hammer on
					hammerStart = false;
					hammerContinue = true;
				}
				if(hammerLength == 1){ // indicates that the next note is the end of the hammer on
					hammerContinue = false;
					hammerDone = true;
				}
				if (hammerLength == 0 ){ // indicates that the hammer on is over and back to regular scheduled programming
					hammerDone = false;
					hammerOn = false;
					hammerDuration = 0;
					hammerLength = 0;
				}
			}
			
			//Skip next line if double digit
			if(doubleDigitColumn) { 
				i++;
				currentColumn++;
			}
			
			//Skip next 2 columns
			if(startRepeat)
			{
				i+= 2;
				currentColumn += 2;
				startRepeat = false;
			}
			
			//Skip 1 column if end of repeat
			if(endRepeat)
			{
				//Skip next column
				i++;
				currentColumn++;
				endRepeat = false;
			}
			
			currentColumn++;
		}
		
		//End the musicxml file
		if(fileGen.measureOpen)
			fileGen.closeMeasure(isRepeat, repeatAmount);
		if(fileGen.partOpen)
			fileGen.closePart();
		fileGen.end();
		
		new SuccessPopUp(Main.myFrame, FileGenerator.filepath);
	}
	
	private boolean containsOnlyChar(char[] cs, char o) {
		boolean output = true;
		
		for(Object t : cs) {
			output = (output && t.equals(o)) || (output && t.equals('[')) || (output && t.equals(']'));
		}
		
		return output;
	}

	private int hamererOnDuration(char afterhammer, int i){
		if( afterhammer!= '-' && afterhammer != '|') {
			int dash = 1;
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
			return dash;
		}
		return 1;
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
//		System.out.println("");
//		System.out.println("DotBeatNote: " + beatNote);
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
		
//		System.out.println("DotCheck: " + check);
		temp = check;
		check2 = check;
		
		while(true) {
			check = check + temp/div;
			if(check2 < beatNote && beatNote > check) {
//				System.out.println(check2 + " < " + beatNote + " > " + check);
				output++;
			}else if(beatNote == check) {
//				System.out.println(beatNote + " == " + check);
				output++;
				break;
			}else if(beatNote < check) {
				if(check - beatNote < check - check2) {
					//add the rest here
					rest = beatNote(beatNote - check2);
//					System.out.println("DotRest: " + rest);
				}else {
					break;
				}
			}
			check2 = check2 + temp/div;
			div *= 2;
			}
//		System.out.println("");
		return output;
		}

	
	private double beatNote(double b) {
		double output = 0.0;
		
//		System.out.println("BeatNote: " + b);
		if(b >= 1.0/256) {
			if(b >= 2.0) {
				output = 2.0;
				output += beatNote(b - output);
			}else if(b >= 1.0) {
				output = 1.0;
				output += beatNote(b - output);
			}else if(b >= 1.0/2.0) {
				output = 1.0/2;
				output += beatNote(b - output);
			}else if(b >= 1.0/4.0) {
				output = 1.0/4;
				output += beatNote(b - output);
			}else if(b  >= 1.0/8.0) {
				output = 1.0/8;
				output += beatNote(b - output);
			}else if(b < 1.0/8.0) {
				double div = ((1.0/8.0)/b);
				int maxIndex = 0;
				double power = 0.0;
				if(div % 2 != 0.0) {
					while(div % 2 != 0.0) {
						if(div < Math.pow(2.0, power)) {
							div = (int) Math.pow(2, power);
						}else {
							power += 1.0;
						}
					}
				}
//				System.out.println("Power: " + power);
				while(div != 1) {
					div = div/2;
					maxIndex++;
				}
				int temp = (int)(Math.pow(2, maxIndex) * 8);
				output = 1.0/temp;
//				System.out.println("Temp: " + temp);
				output += beatNote(b - output);
			}
		}
		
//		System.out.println("BeatNoteOut: " + output);
		
		return output;
		}
	
	protected static String noteType(double beatNote) {
		String output = "";
		
		if(beatNote >= 1.0) {
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
//		System.out.println("DurationOutput: " + output);
		if(output%0.5 == 0)
			return (int)output;
		else
			return (int)Math.round(output);
	}
	
	private double noteFromDash(int d) {
		double output = 0.0;
		
		output = ((1.0 * beat/beatType)*d)/totalDash;
		
		return output;
	}
	
	private int getDivisions(int beatSig) {
		int hyfenNumber = 0;
		int boundary = 0;
		int value = 0;
		int doubleDigit = 0;
		//System.out.println("");
		
		for(int i = 0; i < columns.size(); i++) {
			if(containsOnlyChar(columns.get(i), '-')) {
				//System.out.println("Dash");
			}
			if(containsOnlyChar(columns.get(i), '|')) {
				boundary++;
				//System.out.println("Boundary");
			}
			if(boundary == 2) {
				//System.out.println("Break");
				break;
			}
			if(!containsOnlyChar(columns.get(i), '-')) {
				value++;
				if(doubleDigit(columns.get(i), columns.get(i+1)))
					doubleDigit++;
				System.out.println("Value: " + value);
			}
			if(boundary == 1 && value > 1) {				
				hyfenNumber++;
				//System.out.println("Hyfen Increase: " + hyfenNumber);
			}
		}
		hyfenNumber -= doubleDigit;
		System.out.println("HyfenNumber: " + hyfenNumber);
		System.out.println("DoubleDigit: " + doubleDigit);
		/*
		for(int i=0;i< columns.size();i++) {
			
			if(columns.get(i)[0] == '|'){
				boundary++;
			}
			if (boundary == 2) {
				break;
			}
			
			if(columns.get(i)[0] == '-' || columns.get(i)[0] == 'h') {
				hyfenNumber++;
				System.out.println("- || h");
			}
			else {
				if(i==0) {
				
				}
				else if(columns.get(i-1)[0] == 'h') {
					hyfenNumber++;
					System.out.println("h");
				}
				else if(columns.get(i-1)[0] != '-') {
					
				}
				else {
					hyfenNumber++;
					System.out.println("Else");
				}
		}
			}*/
		//System.out.println("");
		totalDash = hyfenNumber;
		double beatNote = 1.0/beatType;
		double bSig  = 1.0 * beatSig;
		double totalBeatPerMeasure = bSig/beatType;
		double division = (hyfenNumber * beatNote)/totalBeatPerMeasure;
		//System.out.println("TBM: " + totalBeatPerMeasure);
		//System.out.println("hyfen number is "+hyfenNumber);
		//System.out.println("Division: " + division);

		if(division%0.5 == 0)
            return (int)division;
        else
            return (int)Math.round(division);
	}

	private boolean doubleDigit(char[] b, char[] a) {
		String nb = "";
		String na = "";
		String n = "";
		boolean output = false;
		
		for(int i  = 0; i < b.length; i++) {
			nb = Character.toString(b[i]);
			na = Character.toString(a[i]);
			n = nb + na;
			try {
				Integer.parseInt(n);
				output = output || true;
			}catch(NumberFormatException e) {
				output = output || false;
			}
		}
		return output;
		
	}
	
	static void addInstrument(String instrument){
		misc.put("Instrument", instrument);
	}

	static void addTitle(String title){
		misc.put("Title", title);
	}
		
	static void addTime(String timeSig){
		misc.put("TimeSig", timeSig);
	}
}