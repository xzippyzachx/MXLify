package tab2mxl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gui_panels.TextInputContentPanel;
import gui_popups.SuccessPopUp;

public class StringParser {

	int instrument;
	int stringAmount;
	int tabLineAmount;
	private int beat;
	private int beatType;
	private ArrayList<char[]> columns;
	public static  Map<String,String> misc;
	private StringTuning tunner;
	private FileGenerator fileGen;
	private double rest = 0.0;
	private int totalDash;
	
	//@SuppressWarnings("unused")
	StringParser(ArrayList<ArrayList<String>> input, int instrument, String path) {
		stringAmount = 0;
		tabLineAmount = 1;
		this.instrument = instrument;
		misc = new HashMap<String, String>();		
		
		addInstrument(TextInputContentPanel.getInstrument());
		addTitle(TextInputContentPanel.getTitle());		
		addTime(TextInputContentPanel.getTimeSig());
		
		//set the time signature to default if the inputed time signature isn't in the right format
		if(misc.get("TimeSig") == "") {
			beat = 4;
			beatType = 4;
		}
		else if(misc.get("TimeSig").indexOf('/') == -1) {
			beat = 4;
			beatType = 4;
		}
		else if(misc.get("TimeSig").charAt(0) == '/') {
			beat = 4;
			beatType = 4;
		}
		else if(misc.get("TimeSig").charAt(misc.get("TimeSig").length()-1) == '/') {
			beat  = 4;
			beatType = 4;
			
		} //set the time signature to default if the inputed time signature isn't in the right format
		else {
			//get the beat and beat-type from the information given in the time signature
			try{
				beat = Integer.parseInt(misc.get("TimeSig").substring(0, misc.get("TimeSig").indexOf('/')));
				beatType = Integer.parseInt(misc.get("TimeSig").substring(misc.get("TimeSig").indexOf('/')+1));
			}catch(NumberFormatException e) {
				beat = 4;
				beatType = 4;
			}
		}
		
		int currentBeat = beat;
		int currentBeatType = beatType;
		
//		System.out.println("beat: " + currentBeat);
//		System.out.println("beatType: " + currentBeatType);
		
		for(int i = 0; i < input.size(); i++) {		
			if(input.get(i).size() < 2)
				break;
			stringAmount++;
		}
		
		for(int i = 0; i < input.size(); i++) {
			if(input.get(i).size() < 2 && input.get(i-1).size() > 2 && i != input.size())
			{
				for(int j = i; j < input.size(); j++)
					if(input.get(j).size() > 2)
					{
						tabLineAmount++;
						break;
					}
			}
		}
		//System.out.println("TabLineAmount: " + tabLineAmount);
		
		//Transpose columns to rows (do you mean rows to col?)
		columns = new ArrayList<char[]>();
		
		//System.out.println("tabLineAmount " + tabLineAmount);
		
		for(int layer = 0; layer < tabLineAmount; layer++) {
			for(int i = 2; i < input.get((layer * stringAmount) + layer).size(); i++) {
				columns.add(new char[stringAmount]);
				for(int l = 0; l < stringAmount; l++) {
					columns.get(columns.size()-1)[l] = input.get(l + (layer * stringAmount) + layer).get(i).charAt(0);
				}
			}		
		}
		
		/////////////////////
		//Parsing Variables//
		/////////////////////
		
		//Common
		int currentColumn = 0;
		int fret = 0;
		int count = 0;
		int measure = 0;
		int line = 0;
		double totalBeatPerMeasure = (1.0 * beat)/beatType;
		int div = getDivisions(beat, beatType, 0);
		double dash = 0; 			
		char character = ' ';		
		char note = ' ';
		char[] col;
		int measureBegin = 0;
		
		//Double digit
		boolean doubleDigit;
		boolean doubleDigitColumn;
		boolean  chordDoubleDigitFlag;
		
		//Chords
		String chordType= "";
		int notesInColumn = 0;
		boolean chord;
		char[] chords = new char[stringAmount];
		int[] chordDot = new int[stringAmount];
		int[] chordsOctave = new int[stringAmount];
		int chordOctave = 0;		
		int[] fretarray = new int[stringAmount];
		int[] linearray = new int[stringAmount];
		
		//HammerOn
		boolean hammerOn = false;
		boolean hammerStart =false;
		boolean hammerContinue = false;
		boolean hammerDone = false;
		int hammerLength = 0;
		int hammerDashes = 0;
		int hammerLocation = -1;
		double beatNote = 0.0;
		
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
		
		//Grace
		boolean grace = false;
		
		/*adds the tuning of the strings to the tune array if the tuning is
		 * specified in the TAB, or the default if it isn't*/
		for(int i = 0; i < stringAmount; i++) {
			if(input.get(i).get(0) != "-" && input.get(i).get(0) != "|") {
				tune[i] = input.get(i).get(0);
			}
		}		
		if(containsOnlyString(tune, "")) {
			tune = StringTuning.getDefaultTuning(stringAmount);
			defaultTune = true;
		}
		
		//Get the octaves
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
			tuningOctave = StringTuning.getDefaultTuningOctave(stringAmount, instrument);
			defaultOctave = true;
		}
		
		tunner = new StringTuning(tune, stringAmount, tuningOctave);

		//Display tuning errors
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
		fileGen = new FileGenerator(path, true);
		
		if(fileGen.failed) //Check if failed to save file to location
			return;
		
		//Calling the methods in the FileGenerator to build the MusicXML
		
		//Start the musicxml file
		fileGen.addStringInfo(misc.get("Title"), misc.get("Instrument"));
		
		//Open part
		fileGen.openPart(1);
		
		////////////////////////////////////
		//Loop through the inputed columns//
		////////////////////////////////////
		for(int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			chordDoubleDigitFlag = false;
			chord = false;
			doubleDigitColumn = false;

			//Check for chords
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
				line++;
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
				//Open measure and sent attributes
				if (count == stringAmount) {
					measure++;
					count = 0;
					
					
					if(fileGen.measureOpen)
						fileGen.closeMeasure(wasRepeat, repeatAmount);
					if(columns.size() > currentColumn + 2) {
						div = getDivisions(currentBeat, currentBeatType, isRepeat ? i+2 : i);
						Map<Integer, String> measureMap = TextInputContentPanel.customMeasureMap;

						fileGen.openMeasure(measure, isRepeat, repeatAmount);						
						if(measure == 1) {
							fileGen.stringAttributes(div, beat, beatType, "G", tune, tuningOctave);
						}
						if(measureMap.containsKey(measure)){
							if(currentBeat == Integer.parseInt(measureMap.get(measure).substring(0,measureMap.get(measure).indexOf("/"))) &&
									currentBeatType == Integer.parseInt(measureMap.get(measure).substring(measureMap.get(measure).indexOf("/")+1))){
							}
							else{
								currentBeat = Integer.parseInt(measureMap.get(measure).substring(0,measureMap.get(measure).indexOf("/")));
								currentBeatType = Integer.parseInt(measureMap.get(measure).substring(measureMap.get(measure).indexOf("/")+1));
								fileGen.stringAttributes((int) (div / 1), currentBeat, currentBeatType, "G", tune, tuningOctave);
								totalBeatPerMeasure = (1.0 * currentBeat)/currentBeatType;
							}
						}
						else{
							if(currentBeat == beat && currentBeatType == beatType){
								
							}
							else{
								currentBeat = beat;
								currentBeatType = beatType;
								fileGen.stringAttributes((int) (div / 1), beat, beatType, "G", tune, tuningOctave);
								totalBeatPerMeasure = (1.0 * beat)/beatType;
							}

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
				
				//////////////
				//Grace note
                if (i<columns.size() && columns.get(i)[j] == 'g'){
                	beatNote = 0.0;
                	dash = 0;
                    grace = true;
                    char gcharacter = 'g';
                    boolean breakout = false;                    
                    
                    //gets dash amount
                    for(int g = i; columns.get(g)[j] != '|'; g++)
                    {
	                      for (int b = 0; b<col.length;b++) {
	                    	  	if(columns.get(g)[b] != '-' && b != j) {  
	                    	  		breakout = true;
	                    	  		break;
	                    	  	}
	                      }
	                      if(breakout) {
	                    	  break;
	                      }
	                      dash++;
                    }
                    
                    //Look forward until end of the measure
                    for(int z = i; gcharacter != '-' && gcharacter != '|'; z++)
                    {
                    	if(Character.isDigit(gcharacter) && !hammerOn) 
                    	{
                    		fret = Character.getNumericValue(gcharacter);
                    		if(columns.get(z+1)[j] == 'h')
                    		{
                    			hammerStart = true;
                    			hammerOn = true;
                    	    }
                    		if (tunner.getNote(tune[line-1], fret, line).substring(tunner.getNote(tune[line-1], fret, line).length()-1,tunner.getNote(tune[line-1], fret, line).length()).equals("#")){
    							sharpnote = true;
    						}
                    	    beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);
                    	    int dot = dot(beatNote);
                    	    fileGen.addStringNote(line, fret, tunner.getNote(tune[line-1], fret, line).charAt(0), noteType(beatNote), getDuration(beatNote, currentBeat, currentBeatType, div) - getDuration(rest, currentBeat, currentBeatType, div), tunner.getOctave(tune[line-1], fret, line), dot,sharpnote, hammerStart,hammerContinue,hammerDone,harmonic,grace);
    						if(rest > 0) {
    							fileGen.addRest(getDuration(rest, currentBeat, currentBeatType, div), noteType(rest), -1);
    							rest = 0.0;
    						}
                    	}
                    	else if(Character.isDigit(gcharacter)) {
                    		fret = Character.getNumericValue(gcharacter);
                    		hammerStart = false;
                    		hammerDone = true;
                    		grace = false;
                    		hammerOn = false;
                    		beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);
                    		if (tunner.getNote(tune[line-1], fret, line).substring(tunner.getNote(tune[line-1], fret, line).length()-1,tunner.getNote(tune[line-1], fret, line).length()).equals("#")){
    							sharpnote = true;
    						}
                    		int dot = dot(beatNote);
                    		fileGen.addStringNote(line, fret, tunner.getNote(tune[line-1], fret, line).charAt(0), noteType(beatNote), getDuration(beatNote, currentBeat, currentBeatType, div) - getDuration(rest, currentBeat, currentBeatType, div), tunner.getOctave(tune[line-1], fret, line), dot,sharpnote, hammerStart,hammerContinue,hammerDone,harmonic,grace);
                    	    hammerDone = false;
    						if(rest > 0) {
    							fileGen.addRest(getDuration(rest, currentBeat, currentBeatType, div), noteType(rest), -1);
    							rest = 0.0;
    						}
                    	}
                    	columns.get(z)[j] = '-';
                    	gcharacter = columns.get(z+1)[j];
                	    sharpnote = false;
                    }
                }
                
                //////////////
				//Hammer on
				if(i+1 < columns.size() && j<col.length && (columns.get(i+1)[j] == 'h' || (doubleDigit &&columns.get(i+2)[j]== 'h')) && !hammerOn && !grace){ // if same row, next col is an h then then begin hammer on
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
					hammerDashes = hamererOnDashes(columns.get(i+2)[j],i+lastHammerFret-1); // sets necessary flags to true
					hammerOn = true;
					hammerStart = true;

				}
				else if(character != 'h' && character != '-' && character != '|' && !hammerOn &&!chordDoubleDigitFlag && !grace) {
					dash = 1;
					int offset = 0;
					if(columns.size() > i+1 && Character.isDigit(columns.get(i+1)[j])) {
						chordDoubleDigitFlag = true;
						offset++;
					}
					boolean test;
					for(int k = i+1+offset; k < columns.size()-1; k++) {
						if(!boundary(columns.get(k))) {
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
				
				if(hammerOn){
					beatNote = beatNote((hammerDashes * totalBeatPerMeasure)/totalDash);/*beatNote((hammerDuration * beatTypeNote)/div);*/
				}
				else {
					beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);/*beatNote((dash * beatTypeNote)/div)*/
				}

				////////////////
				//Regular notes				
				if (Character.isDigit(character) && !grace) {
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
						int dot = dot(beatNote);
						fileGen.addStringNote(line, fret, tunner.getNote(tune[line-1], fret, line).charAt(0), noteType(beatNote), getDuration(beatNote, currentBeat, currentBeatType, div) - getDuration(rest, currentBeat, currentBeatType, div), tunner.getOctave(tune[line-1], fret, line), dot,sharpnote, hammerStart,hammerContinue,hammerDone,harmonic,grace);
						harmonic = false;
						grace = false;
						if(rest > 0) {
							fileGen.addRest(getDuration(rest, currentBeat, currentBeatType, div), noteType(rest), -1);
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
							hammerDashes = 0;
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
			
			////////////
			//Chord
			if (chord && !grace) { 
				beatNote = 0.0;
				if(hammerOn){
					beatNote = beatNote((hammerDashes * totalBeatPerMeasure)/totalDash);/*beatNote((hammerDuration * beatTypeNote)/div);*/
				}
				else {
					beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);/*beatNote((dash * beatTypeNote)/div)*/
				}

				fileGen.addStringChord(chords,chordType, getDuration(beatNote, currentBeat, currentBeatType, div) - getDuration(rest, currentBeat, currentBeatType, div), chordsOctave,linearray,fretarray, chordDot,sharp,hammerLocation,hammerStart,hammerContinue,hammerDone,hchord);
				if(rest > 0) {
					fileGen.addRest(getDuration(rest, currentBeat, currentBeatType, div), noteType(rest), -1);
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
					hammerDashes = 0;
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
			if(grace) {
				grace = false;
				
			}
			
			currentColumn++;
		}
		
		//End the musicxml file
		if(fileGen.measureOpen)
			fileGen.closeMeasure(isRepeat, repeatAmount);
		if(fileGen.partOpen)
			fileGen.closePart();
		fileGen.end();
		
		//Show success popup
		new SuccessPopUp(Main.myFrame, FileGenerator.filepath);
	}
	
	//////////////////
	//Helper Methods//
	//////////////////
	
	private boolean containsOnlyChar(char[] cs, char o) {
		boolean output = true;
		
		for(Object t : cs) {
			output = (output && t.equals(o)) || (output && t.equals('[')) || (output && t.equals(']'));
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
	
	private boolean containsChar(char[] cs,char o) {
		
		for(Object t : cs) {
			if(t.equals(o)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean boundary(char[] cs) {
		
		for(Object t : cs) {
			if(t.equals('|'))return true;
			if(t.equals('*')) return true;
		}
		
		return false;
	}
	
	private int hamererOnDashes(char afterhammer, int i){
		if( afterhammer!= '-' && afterhammer != '|') {
			int dash = 1;
			boolean test;
			for(int k = i+1; k < columns.size()-1; k++) {
				if(!boundary(columns.get(k)))
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
		
		temp = check;
		check2 = check;
		
		while(true) {
			check = check + temp/div;
			if(check2 < beatNote && beatNote > check) {
				output++;
			}else if(beatNote == check) {
				output++;
				break;
			}else if(beatNote < check) {
				if(check - beatNote < check - check2) {
					//add the rest here
					rest = beatNote(beatNote - check2);
				}else {
					break;
				}
			}
			check2 = check2 + temp/div;
			div *= 2;
			}
		return output;
	}

	private double beatNote(double b) {
		double output = 0.0;

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
				while(div != 1) {
					div = div/2;
					maxIndex++;
				}
				int temp = (int)(Math.pow(2, maxIndex) * 8);
				output = 1.0/temp;
				output += beatNote(b - output);
			}
		}else {
			output = 0;
		}		
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
	
	private int getDuration(double noteType, int currentBeat, int currentBeatType, double div) {
		double output = 0;
		double beatTypeNote = 1.0/currentBeatType;
		output = (noteType * div)/beatTypeNote;
		if(output%0.5 == 0)
			return (int)output;
		else
			return (int)Math.round(output);
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
			if(boundary(columns.get(i))) {
				boundary++;
			}
			if(boundary == 2) {
				break;
			}
			if(!containsOnlyChar(columns.get(i), '-')) {
				value++;
				if(doubleDigit(columns.get(i), columns.get(i+1)))
					doubleDigit++;
			}
			if(boundary == 1 && value > 1) {				
				hyfenNumber++;
			}
		}
		hyfenNumber -= doubleDigit;
		
		double beatNote = 1.0/beatType;
		double bSig  = 1.0 * beatSig;
		double totalBeatPerMeasure = bSig/beatType;
		double division = (hyfenNumber * beatNote)/totalBeatPerMeasure;

		if(division%0.5 == 0)
            return (int)division;
        else
            return (int)Math.round(division);
	}
	
	
	private int getDivisions(int beat, int beatType, int startIndex) {
		int hammerEnd = 0;
		int hammer = 0;
		int hyfenNumber = 0;
		int boundary = 0;
		int value = 0;
		int doubleDigit = 0;
		
		for(int i = startIndex; i < columns.size(); i++) {
			if(containsChar(columns.get(i),'h')) {
				hammer++;
			}
			if(containsOnlyChar(columns.get(i), '-')) {
				for(int j = 0; j < hammer; j++)
					hammerEnd++;
				if(!containsOnlyChar(columns.get(i+1), '-')) {
					hammer = 0;
				}
			}
			if(boundary(columns.get(i))) {
				boundary++;
			}
			if(boundary == 2) {
				break;
			}
			if(!containsOnlyChar(columns.get(i), '-')) {
				value++;
				if(doubleDigit(columns.get(i), columns.get(i+1)))
					doubleDigit++;
			}
			if(boundary == 1 && value > 1 && !containsChar(columns.get(i),'h')) {				
				hyfenNumber++;
			}
		}
		hyfenNumber -= doubleDigit;
		hyfenNumber += hammerEnd;
		
		totalDash = hyfenNumber;
		double beatNote = 1.0/beatType;
		double bSig  = 1.0 * beat;
		double totalBeatPerMeasure = bSig/beatType;
		double division = (hyfenNumber * beatNote)/totalBeatPerMeasure;

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
	
	////////////////////////
	//Misc HashMap Setters//
	////////////////////////
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