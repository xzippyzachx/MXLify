package tab2mxl;
import gui_panels.TextInputContentPanel;
import gui_popups.SuccessPopUp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;

public class DrumParser{
	ArrayList<ArrayList<String>> input;
	ArrayList<ArrayList<String>> TransInput = new ArrayList<>();
	private boolean	hasTuning;
	private ArrayList<ArrayList<ArrayList<String>>> Measure = new ArrayList<>();
	public static Map<String,String> misc;
	private int beat;
	private int beatType;
	private ArrayList<Double> divisionsArray;
	private ArrayList<String> instrumentsArray;
	private int[] voices;
	private String[] instruments;
	private int totalDash;
	private double rest;
	private DrumTuning tuning;
	private FileGenerator fileGen;
	private ArrayList<Integer[]> repeats = new ArrayList<>();
	private Map<Integer, Integer[]> rep;
	protected static String path = "";
	private double beam = 0.0;
	private int bm = 0;
	private double totalBeatInMeasure = 0.0;
	private int beamCount = 0;
	private boolean eighthCheck = true;


	
//	
//	
//	
//	public static void main(String[] args) {
//
//		//Testing DrumTuning
//		String[] a = {"BD", "CC", "F"};
//		int d = 2;
//		DrumTuning test = new DrumTuning(a, d);
//		System.out.print(test.getNote("BD"));
//		System.out.println(test.getOctave("BD"));
//		System.out.print(test.getNote("CC"));
//		System.out.println(test.getOctave("CC"));
//		System.out.print(test.getNote("F"));
//		System.out.println(test.getOctave("F"));
//		System.out.println(test.unSupportedDrum);
//		System.out.println(test.getID("CC", "x"));
//		System.out.println(test.getVoice("CC"));
//	}

	public DrumParser (ArrayList<ArrayList<String>> input) {
		misc = new HashMap<String, String>();

		addTitle(TextInputContentPanel.getTitle());
		addInstrument(TextInputContentPanel.getInstrument());
		addTime(TextInputContentPanel.getTimeSig());

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
		int currentBeat = beat;
		int currentBeatType = beatType;

		this.input = input;

		ArrayList<ArrayList<String>> transposedInput = transpose(input);
		Measure = CreateMeasureArray(transposedInput);

		divisionsArray = new ArrayList<>();
		createDivisionsArray(); // adds the divisions for each measure into the divisions Array


		System.out.println("instruments");
		ArrayList<String> instrmnt = new ArrayList<>();
		for(int inst = 0; inst < transposedInput.get(0).size(); inst++) {
			instrmnt.add(transposedInput.get(0).get(inst)+transposedInput.get(1).get(inst));
		}
		instruments = getInstruments(instrmnt);
		instrmnt.clear();

		tuning = new DrumTuning(instruments, instruments.length);
		for(int g  = 0; g < instruments.length; g++)
			System.out.println("Instrument: " + instruments[g]);

		voices = setVoice(tuning,instruments);

		fileGen = new FileGenerator(path, false);
		
		if(fileGen.failed) //Check if failed to save file to location
			return;
		
		fileGen.addDrumInfo(misc.get("Title"));
		
		fileGen.openPart(1);

		double totalBeatPerMeasure =0;
		int duration = 0;
		int totalDuration = 0;
		ArrayList<String> chords = new ArrayList<String>();
		ArrayList<String> chordNotes = new ArrayList<String>();
		ArrayList<Integer> chordOctaves = new ArrayList<Integer>();
		ArrayList<String> chordIDs = new ArrayList<String>();
		ArrayList<String> chordSymbols = new ArrayList<String>();
		int dash = 1;
		int nextDash = 0;
		int dot = 0;
		int currentSpan = 0;
		int runningSpanCount = 0;
		boolean repeatOpen = false;
		int measurecount =0;
		int maxVoice = 0;
		int minVoice = 0;



		for (int i= 0;i< Measure.size();i++) {//For each measure
			
			currentSpan = repeats.get(i)[1];
			maxVoice = 0;
			minVoice = 1;
			if(Measure.get(i).size() ==2){
				for(int inst = 0; inst < Measure.get(i).get(0).size(); inst++) {
					instrmnt.add(Measure.get(i).get(0).get(inst)+Measure.get(i).get(1).get(inst));
				}
				instruments = getInstruments(instrmnt);
				tuning = new DrumTuning(instruments, instruments.length);
				voices = setVoice(tuning,instruments);
				instrmnt.clear();
			}else{
				for(Integer v : voices) {
					if(v > maxVoice) {
						maxVoice = v;
					}
				}
				minVoice = maxVoice;
				for(Integer v : voices) {
					if(v <= minVoice) {
						minVoice = v;
					}
				}
				totalBeatInMeasure = 0.0;
				measurecount++;
				System.out.println("Entered measure: " + measurecount);
				if(repeats.get(i)[0] == 0) {
					fileGen.openMeasure(measurecount, false, 0);
				}else {
					if(!repeatOpen) {
						fileGen.openMeasure(measurecount, true, repeats.get(i)[0]);
						runningSpanCount++;
						repeatOpen = true;
					}else {
						fileGen.openMeasure(measurecount, false, 0);
						runningSpanCount++;
					}
				}
				if (i == 0) {//first measure
					fileGen.drumAttributes((int) ((divisionsArray.get(0)) / 1), beat, beatType);//Add attributes
					totalBeatPerMeasure = (1.0 * currentBeat)/currentBeatType;
				}
				if(TextInputContentPanel.customMeasureMap.containsKey(measurecount)){
					System.out.println("change this measure");
					if( currentBeat == Integer.parseInt(TextInputContentPanel.customMeasureMap.get(measurecount).substring(0,TextInputContentPanel.customMeasureMap.get(measurecount).indexOf("/")))
							&&
							currentBeatType == Integer.parseInt(TextInputContentPanel.customMeasureMap.get(measurecount).substring(TextInputContentPanel.customMeasureMap.get(measurecount).indexOf("/")+1))){

					}
					else{
						currentBeat = Integer.parseInt(TextInputContentPanel.customMeasureMap.get(measurecount).substring(0,TextInputContentPanel.customMeasureMap.get(measurecount).indexOf("/")));
						currentBeatType = Integer.parseInt(TextInputContentPanel.customMeasureMap.get(measurecount).substring(TextInputContentPanel.customMeasureMap.get(measurecount).indexOf("/")+1));
						fileGen.drumAttributes((int) ((divisionsArray.get(i)) / 1), currentBeat, currentBeatType);
						totalBeatPerMeasure = (1.0 * currentBeat)/currentBeatType;
					}
				}
				else{
					if(currentBeat == beat && currentBeatType == beatType){

					}
					else{
						currentBeat = beat;
						currentBeatType = beatType;
						fileGen.drumAttributes((int) ((divisionsArray.get(i)) / 1), beat, beatType);
						totalBeatPerMeasure = (1.0 * beat)/beatType;
					}

				}
				
				for (int j = minVoice-1; j < maxVoice; j++) {//for each voice in measure
					beam = 0.0;
					bm = 0;
					beamCount = 0;
					eighthCheck = true;
					totalBeatInMeasure = 0.0;
					totalDash = getTotalDashes(Measure.get(i), j+1);
					for (int k = 0; k < Measure.get(i).size(); k++) {//for each column of measure
						String[] column = new String[Measure.get(i).get(k).size()];
						int chord = 0;
						for (int l = 0; l < Measure.get(i).get(k).size(); l++) {
							if (voices[l] == j + 1 && !Measure.get(i).get(k).get(l).equals("-")) {
								column[l] = Measure.get(i).get(k).get(l);
								chord++;
							} else {
								column[l] = "-";
							}
						}
						double beatNote = 0;
						double nextBeatNote = 0;

						if (chord > 1) {
							// fileGen Chord
							for (int inst = 0; inst < column.length; inst++) {
								if (!column[inst].equals("-")) {
									chords.add(column[inst]);
									chordNotes.add(tuning.getNote(instruments[inst]));
									chordOctaves.add(tuning.getOctave(instruments[inst]));
									chordIDs.add(tuning.getID(instruments[inst], column[inst]));
									chordSymbols.add(column[inst]);
									dash = dash(i,j,k);
									if(nextKPos(i,j,k) != -1) {
										nextDash = dash(i,j,nextKPos(i,j,k));
										nextBeatNote = beatNote((nextDash * totalBeatPerMeasure) / totalDash);
									}
								}
							}
							
							beatNote = beatNote((dash * totalBeatPerMeasure) / totalDash);
							// to do is make array for total dash
							dot = dot(beatNote);
							totalBeatInMeasure += beatNote(noteType(beatNote), dot);
							beamCheck(beatNote, nextBeatNote, i, k,currentBeat,currentBeatType);
							duration = getDuration(beatNote, Measure.get(i),currentBeat,currentBeatType) - getDuration(rest, Measure.get(i),currentBeat,currentBeatType);
							totalDuration += duration;
							
							//Add beam check

							fileGen.addDrumChord(chords, duration, chordNotes, chordOctaves, chordIDs, chordSymbols, dot, noteType(beatNote), j + 1, bm);
							if (rest > 0 && getDuration(rest, Measure.get(i),currentBeat, currentBeatType) > 0) {
								fileGen.addRest(getDuration(rest, Measure.get(i), currentBeat,currentBeatType), noteType(rest), j + 1);
								totalBeatInMeasure += beatNote(noteType(rest), 0);
								rest = 0.0;
							}
							//Clear the ArrayLists after use
							chords.clear();
							chordNotes.clear();
							chordOctaves.clear();
							chordIDs.clear();
							chordSymbols.clear();
						} else {
							//filGen note
							for (int inst = 0; inst < column.length; inst++) {//For each char in column
								if (!column[inst].equals("-")) { //Is an x or o
									dash = dash(i,j,k);
									if(nextKPos(i,j,k) != -1) {
										nextDash = dash(i,j,nextKPos(i,j,k));
										nextBeatNote = beatNote((nextDash * totalBeatPerMeasure) / totalDash);
									}
									beatNote = beatNote((dash * totalBeatPerMeasure)/totalDash);
									// to do is make array for total dash
									dot = dot(beatNote);
									totalBeatInMeasure += beatNote(noteType(beatNote), dot);
									beamCheck(beatNote, nextBeatNote, i, k,currentBeat,currentBeatType);
									duration = getDuration(beatNote, Measure.get(i),currentBeat, currentBeatType) - getDuration(rest, Measure.get(i),currentBeat, currentBeatType);
									totalDuration += duration;
									fileGen.addDrumNote(column[inst], duration, tuning.getNote(instruments[inst]), tuning.getOctave(instruments[inst]), tuning.getID(instruments[inst], column[inst]), noteType(beatNote), voices[inst], dot, bm);
									if (rest > 0 && getDuration(rest, Measure.get(i),currentBeat, currentBeatType) > 0) {
										fileGen.addRest(getDuration(rest, Measure.get(i),currentBeat,currentBeatType), noteType(rest), voices[inst]);
										totalBeatInMeasure += beatNote(noteType(rest), 0);
										rest = 0.0;
									}
								}
							}
						}
						if(k == Measure.get(i).size()-1 && totalBeatPerMeasure>totalBeatInMeasure) {
							rest = totalBeatPerMeasure-totalBeatInMeasure;
							fileGen.addRest(getDuration(rest, Measure.get(i),currentBeat, currentBeatType), noteType(rest), j+1);
							rest = 0.0;
						}
					}
					if (j == 0) {
						fileGen.backup(totalDuration);
					}
					totalDuration = 0;
				}
			}
			/*if(i == Measure.size()-1 || Measure.get(i+1).size() ==2) {
				fileGen.addBar();
			}*/

			System.out.println("closing measure");
			 if(fileGen.measureOpen){
				 if(repeats.get(i)[1]-runningSpanCount == 0) {
				 fileGen.closeMeasure(true, repeats.get(i)[0]);
				 runningSpanCount = 0;
				 repeatOpen = false;
				 }else {
					 fileGen.closeMeasure(false, 0); 
				 }
			 }

		}
		fileGen.closePart();
		fileGen.end();
		
		new SuccessPopUp(Main.myFrame, FileGenerator.filepath);

		// Printing for testing
		int i = 0;
		for (ArrayList<ArrayList<String>> measure : Measure){
			System.out.println(repeats.get(i)[0] + "," + repeats.get(i)[1]);
			i++;
			for(ArrayList<String> row : measure){
				for(String c:row){
					System.out.print(c);
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
		}

	}

	private String[] getInstruments(ArrayList<String> col){ // returns null if the tuning does
		String column = String.join("",col);
		String regexPattern = "(BD|BA|B|T2|R|C|CC|HH|H|RC|Rd|SD|SN|S|HT|T|T1|MT|FT|F){1,10}";
		Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(column);
		boolean matchFound = matcher.find();
		System.out.println(matchFound);
		if(matchFound){
			String[] instruments = new String[col.size()];
			for(int i = 0; i<col.size();i++){
				instruments[i] = col.get(i);
			}
			return instruments;
		}
		return null;
	}

	private ArrayList<ArrayList<String>> transpose(ArrayList<ArrayList<String>> input){
		ArrayList<ArrayList<ArrayList<String>>> sanitizeInput = new ArrayList<>();
		rep = new HashMap<Integer, Integer[]>();
		///////////////////
		//finding repeats//
		///////////////////
		String regexPattern = "repeat";
		Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;
		boolean matchFound = false;
		int col = 0;
		int start = 0;
		int end = 0;
		int repeat = 0;
		String r = "";
		Integer[] store = null;
		String[] split = null;
		
		for(int rows = 0; rows < input.size(); rows++) {
			String row = "";
			for(String s : input.get(rows)) {
				row += s;
			}
			matcher = pattern.matcher(row);
			matchFound = matcher.find();
			if(matchFound) {
				split = row.split("\\|");
				start = col-1;
				end = 0;
				for(String s : split) {
					matcher = pattern.matcher(s);
					matchFound = matcher.find();
					if(matchFound) {
						store = new Integer[2];
						end = start + s.length()+1;
						for(int c = 0; c < s.length(); c++) {
							if(Character.isDigit(s.charAt(c))) {
								repeat = s.charAt(c)-'0';
							}
						}
						store[0] = end;
						store[1] = repeat;
						rep.put(start, store);
						start += s.length()+1;
					}else {
						start += s.length()+1;
					}
				}
				input.remove(rows);
			}
			if(row.strip().length() == 0 || rows == input.size()-1) {
				col += input.get(rows-1).size(); // throws error if first line is blank
			}
		}
		
		for(int rows =0; rows< input.size();rows++){
			ArrayList<ArrayList<String>> drumBlock = new ArrayList<>();
			while(rows<input.size()){
				if(input.get(rows).contains("|") && input.get(rows).contains("-")){
					drumBlock.add(input.get(rows));
					rows++;
				}
				else{
					break;
				}
			}
			sanitizeInput.add(drumBlock);
		}
		
		ArrayList<ArrayList<String>> transposed = new ArrayList<>();
		for(ArrayList<ArrayList<String>> block:sanitizeInput ) {
			if(block.size()!=0) {
				for (int i = 0; i < block.get(0).size(); i++) {           //for all cols
					ArrayList<String> transposedCol = new ArrayList<>();
					for (int j = 0; j < block.size(); j++) {              //for all rows
						transposedCol.add(block.get(j).get(i));      //Create col
					}
					transposed.add(transposedCol);
				}
			}
		}
		return transposed;
	}


 	private ArrayList<ArrayList<ArrayList<String>>> CreateMeasureArray(ArrayList<ArrayList<String>> transposedinput){
		ArrayList<ArrayList<ArrayList<String>>> Measure = new ArrayList<>();

		int startLocation;
		boolean measureStart;
		ArrayList<String> row		;
		ArrayList<ArrayList<String>> currentMeasure = null;
		
		for(startLocation = 0; startLocation < transposedinput.size(); startLocation++){//For all rows
			measureStart = false;
			row = transposedinput.get(startLocation);
			measureStart = String.join("",row).equals("|".repeat(row.size()));

			if(measureStart) {
				currentMeasure = new ArrayList<>();//Create new measure
				boolean hasRepeat = false;
				int repeatValue = 0;
				int start = 0;
				int end = 0;
				
				while (measureStart && startLocation < transposedinput.size() - 1) {
					if(!hasRepeat) {
						for(int k : rep.keySet()) {
							if(startLocation > k && startLocation< rep.get(k)[0]) {
								hasRepeat = true;
								start = k;
								end = rep.get(k)[0];
								repeatValue = rep.get(k)[1];
							}
						}
					}
					startLocation++;
					row = transposedinput.get(startLocation);
					if (String.join("", row).equals("|".repeat(row.size()))) {
						startLocation--;
						break;
					}
					currentMeasure.add(row);
				}
				System.out.println(currentMeasure.size());
				double span = (end - start)*(1.0)/currentMeasure.size();
				span = Math.round(span);
				repeats.add(new Integer[]{repeatValue, (int)span});
				Measure.add(currentMeasure);
			}
		}

		Measure.remove(Measure.size() - 1); //Remove last empty measure

		//CONSIDER DIFF LENGTH MEASURE ROWS

		return Measure;

	}

 	private int dash(int i , int j, int k) {//i is the
 		int dash = 1;
 		int increment = 1;
 		
		while (true) {
			if (((k + increment) < Measure.get(i).size()) && containsOnlyStringV(Measure.get(i).get(k + increment), j + 1, "-")) {
				dash++;
				increment++;
			} else
				break;
		}
 		
 		return dash;
 	}

 	private void beamCheck(double beatNote, double nextBeatNote, int i, int k, int currentBeat,int currentBeatType) {

 		if(nextBeatNote == 0) {
 			beam = 0.0;
 			if(beam != 0) bm = 3;
 			else bm = 0;
			beamCount = 0;
			eighthCheck = true;
			return;
 		}

 		if(beatNote == 0.125 && eighthCheck) {
			if(beam == 0 && ((beatNote + nextBeatNote) > ((1.0*currentBeat)/currentBeatType)/2 || totalBeatInMeasure == ((1.0*currentBeat)/currentBeatType)/2)) {
				bm = 0;
				beamCount = 0;
			}else if(beam != 0 && beamCount == 1 && ((totalBeatInMeasure + nextBeatNote >= ((1.0*currentBeat)/currentBeatType) || totalBeatInMeasure + nextBeatNote == ((1.0*currentBeat)/currentBeatType)/2))) {
				beam = 0.0;
				bm = 3;
				beamCount = 0;
			}else if((beam != 0 && (beam + beatNote + nextBeatNote) > ((1.0*currentBeat)/currentBeatType)/2) || (totalBeatInMeasure == ((1.0*currentBeat)/currentBeatType)/2) || (k == Measure.get(i).size()-1)) {
				beam = 0.0;
				bm = 3;
				beamCount = 0;
			}else if(beam == 0 && (beatNote + nextBeatNote) <= ((1.0*currentBeat)/currentBeatType)/2) {
				beam += beatNote;
				bm = 1;
				beamCount = 1;
			}else if(beam != 0 && (beam + beatNote + nextBeatNote) <= ((1.0*currentBeat)/currentBeatType)/2) {
				beam += beatNote;
				bm = 2;
				beamCount++;
			}
		}else if(beatNote < 0.25) {
			eighthCheck = false;
			if(beam == 0 && (((beatNote + nextBeatNote) > 1.0/currentBeatType) || (totalBeatInMeasure == ((1.0*currentBeat)/currentBeatType)/2))) {
				bm = 0;
				beamCount = 0;
			}else if((beam != 0 && (beam + beatNote + nextBeatNote) > 1.0/currentBeatType) || (totalBeatInMeasure == ((1.0*currentBeat)/currentBeatType)/2) || (k == Measure.get(i).size()-1)) {
				beam = 0.0;
				bm = 3;
				beamCount = 0;
				eighthCheck = true;
			}else if(beam == 0 && (beatNote + nextBeatNote) <= 1.0/currentBeatType) {
				beam += beatNote;
				bm = 1;
				beamCount = 1;
			}else if(beam != 0 && (beam + beatNote + nextBeatNote) <= 1.0/currentBeatType) {
				beam += beatNote;
				bm = 2;
				beamCount++;
			}
		}else {
			beam = 0.0;
			bm = 0;
			beamCount = 0;
			eighthCheck = true;
		}
 	}

 	private int nextKPos(int i , int j, int k) {
 		int increment = 1;
 		int output = 0;
 		while (true) {
			if (((k + increment) < Measure.get(i).size()) && containsOnlyStringV(Measure.get(i).get(k + increment), j + 1, "-")) {
				increment++;
			} else {
				output = k+increment;
				break;
			}
		}
 		if(output >= Measure.get(i).size())
 			return -1;
 		else
 			return output;
 	}

	private double getDivision(ArrayList<ArrayList<String>> measure,int currentBeat,int currentBeatType) {
		double division = 0;
		int runningcount;

		for (runningcount = 0; runningcount < measure.size(); runningcount++) {     //Go through each column

			ArrayList<String> col = (measure.get(runningcount));  //Each column

			if(!containsOnlyString(col,"-")){                   //First column with info
				int dashes = measure.size() - runningcount;

				totalDash = dashes;
				double beatNote = 1.0/currentBeatType;
				double bSig  = 1.0 * currentBeat;
				double totalBeatPerMeasure = bSig/currentBeatType;
				division = (dashes * beatNote)/totalBeatPerMeasure;
				//System.out.println("TBM: " + totalBeatPerMeasure);
				//System.out.println("hyfen number is "+hyfenNumber);
				//System.out.println("Division: " + division);
				if(division%0.5 == 0)
					return (int)division;
				else
					return (int)Math.round(division);
			}
		}
		return division;
	}

	private int getTotalDashes(ArrayList<ArrayList<String>> measure, int v) {
		int dashes = 1;

		for (int i = 0; i < measure.size(); i++) {     //Go through each column
			ArrayList<String> col = (measure.get(i));  //Each column
			if(!containsOnlyStringV(col, v, "-")){                  //First column with info
				dashes = measure.size() - i;
				break;
			}
		}
		return dashes;
	}

	private void createDivisionsArray(){
		int measureNum = 1;
		for(int i =0; i< Measure.size();i++){
			if(Measure.get(i).size() ==2){
				divisionsArray.add(0.0);
			}
			else{
				if(TextInputContentPanel.customMeasureMap.containsKey(measureNum)){
					int currentBeat = Integer.parseInt(TextInputContentPanel.customMeasureMap.get(measureNum).substring(0,TextInputContentPanel.customMeasureMap.get(measureNum).indexOf("/")));
					int currentBeatType = Integer.parseInt(TextInputContentPanel.customMeasureMap.get(measureNum).substring(TextInputContentPanel.customMeasureMap.get(measureNum).indexOf("/")+1));
					divisionsArray.add(getDivision(Measure.get(i),currentBeat,currentBeatType));
					measureNum++;
				}
				else {
					divisionsArray.add(getDivision(Measure.get(i), beat, beatType));
					measureNum++;
				}
			}
		}



	}

	public int[] setVoice(DrumTuning tuner, String[] instruments){
		int[] voices = new int[instruments.length];
		for(int i = 0; i <instruments.length;i++){
			voices[i] = tuner.getVoice(instruments[i]);
		}
		return voices;
	}

	private int getDuration(double noteType,ArrayList<ArrayList<String>> measure, int currentBeat, int currentBeatType) {
		double output = 0;
		double div = getDivision(measure, currentBeat, currentBeatType);
		double beatTypeNote = 1.0/currentBeatType;
		output = (noteType * div)/beatTypeNote;
//		System.out.println("DurationOutput: " + output);
		if(output%0.5 == 0)
			return (int)output;
		else
			return (int)Math.round(output);
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
	
	protected static String noteType(double beatNote) {
		String output = "";

		if(beatNote >= 1.0/256) {
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
		}
		return output;
	}

	private double beatNote(String note, int dot) {
		double output = 0.0;

		if(note.equals("whole")) {
			output += 1.0;
		}else if(note.equals("half")) {
			output += 0.5;
		}else if(note.equals("quarter")) {
			output += 0.25;
		}else if(note.equals("eighth")) {
			output += 0.125;
		}else if(note.equals("16th")){
			output += 0.0625;
		}else if(note.equals("32nd")){
			output += 0.03125;
		}else if(note.equals("64th")){
			output += 0.015625;
		}else if(note.equals("128th")){
			output += 0.015625/2;
		}else if(note.equals("256th")){
			output += 0.015625/4;
		}
		double temp = output;
		for(int i = 1; i <= dot; i++) {
			output += temp/Math.pow(2, i);
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
		return output;
	}

	private boolean containsOnlyStringV(ArrayList<String> cs, int v, String o){
		boolean output = true;

		for(int i = 0; i < voices.length; i++) {
			if(voices[i] == v)
				output = output && cs.get(i).equals(o);
			else
				output = output && true;
		}

		return output;
	}

	private boolean containsOnlyString(ArrayList<String> cs, String o) {
		boolean output = true;

		for(Object t : cs) {
			output = output && t.equals(o) ;
		}

		return output;
	}

	static void addTitle(String title){
		misc.put("Title", title);
	}

	static void addInstrument(String instrument){
		misc.put("Instrument", instrument);
	}

	static void addTime(String timeSig){
		misc.put("TimeSig", timeSig);
	}


}
