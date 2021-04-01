package tab2mxl;
import gui_panels.TextInputContentPanel;
import gui_popups.SuccessPopUp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

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
	private Map<Integer,String[]> InstrumentList;

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


		this.input = input;

		ArrayList<ArrayList<String>> transposedInput = transpose(input);
		Measure = CreateMeasureArray(transposedInput);

		divisionsArray = new ArrayList<>();
		createDivisionsArray(); // adds the divisions for each measure into the divisions Array


		System.out.println("instruments");
		instruments = getInstruments(transposedInput.get(0));


		tuning = new DrumTuning(instruments, instruments.length);

		voices = setVoice(tuning,instruments);

		fileGen = new FileGenerator("");
		fileGen.addInfoDrums(misc.get("Title"));

		double totalBeatPerMeasure = (1.0 * beat)/beatType;
		int duration = 0;
		int totalDuration = 0;
		ArrayList<String> chords = new ArrayList<String>();
		ArrayList<String> chordNotes = new ArrayList<String>();
		ArrayList<Integer> chordOctaves = new ArrayList<Integer>();
		ArrayList<String> chordIDs = new ArrayList<String>();
		ArrayList<String> chordSymbols = new ArrayList<String>();
		int dash = 1;
		int dot = 0;



		for (int i= 0;i< Measure.size();i++) {                                     //For each measure

			System.out.println("Entered measure: " + i);
			if(Measure.get(i).size() ==2){
				instruments = getInstruments(Measure.get(i).get(0));
				tuning = new DrumTuning(instruments, instruments.length);
				voices = setVoice(tuning,instruments);
			}

			else{
				fileGen.openDrumMeasure(i + 1);
				if (i == 0) {                                                            //first measure
					fileGen.attributesDrum((int) ((divisionsArray.get(0)) / 1), beat, beatType);    //Add attributes
				}
				for (int j = 0; j < 2; j++) {                                          //for each voice in measure
					for (int k = 0; k < Measure.get(i).size(); k++) {  //for each column of measure
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

						if (chord > 1) {
							// fileGen Chord
							for (int inst = 0; inst < column.length; inst++) {
								if (!column[inst].equals("-")) {
									chords.add(column[inst]);
									chordNotes.add(tuning.getNote(instruments[inst]));
									chordOctaves.add(tuning.getOctave(instruments[inst]));
									chordIDs.add(tuning.getID(instruments[inst], column[inst]));
									chordSymbols.add(column[inst]);
									dash = 1;
									int increment = 1;
									while (true) {
										if (((k + increment) < Measure.get(i).size()) && containsOnlyStringV(Measure.get(i).get(k + increment), j + 1, "-")) {
											dash++;
											increment++;
										} else
											break;
									}
								}
							}
							beatNote = beatNote((dash * totalBeatPerMeasure) / totalDash); // to do is make array for total dash
							System.out.println("Dash: " + dash);
							dot = dot(beatNote);
							duration = getDuration(beatNote, Measure.get(i)) - getDuration(rest, Measure.get(i));
							totalDuration += duration;
							fileGen.addDrumChord(chords, duration, chordNotes, chordOctaves, chordIDs, chordSymbols, dot, noteType(beatNote), j + 1);
							if (rest > 0) {
								fileGen.addRest(getDuration(rest, Measure.get(i)), noteType(rest), j + 1);
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
									dash = 1;
									int increment = 1;
									while (true) {
										if (((k + increment) < Measure.get(i).size()) && containsOnlyStringV(Measure.get(i).get(k + increment), j + 1, "-")) {
											dash++;
											increment++;
										} else
											break;
									}
									beatNote = beatNote((dash * totalBeatPerMeasure) / totalDash); // to do is make array for total dash
									System.out.println("Dash: " + dash);
									dot = dot(beatNote);
									duration = getDuration(beatNote, Measure.get(i)) - getDuration(rest, Measure.get(i));
									totalDuration += duration;
									fileGen.addDrumNote(column[inst], duration, tuning.getNote(instruments[inst]), tuning.getOctave(instruments[inst]), tuning.getID(instruments[inst], column[inst]), noteType(beatNote), voices[inst], dot);
									if (rest > 0) {
										fileGen.addRest(getDuration(rest, Measure.get(i)), noteType(rest), voices[inst]);
										rest = 0.0;
									}
								}
							}
						}
					}
					if (j == 0) {
						fileGen.Backup(totalDuration);
					}
					totalDuration = 0;
				}
			}
			System.out.println("closing measure");
			 if(fileGen.measureOpen){
				 fileGen.closeDrumMeasure();
			 }

		}
		fileGen.closeDrumPart();
		fileGen.end();
		
		new SuccessPopUp(Main.myFrame, FileGenerator.filepath);

		// Printing for testing
		int i = 0;
		for (ArrayList<ArrayList<String>> measure : Measure){
			System.out.println(divisionsArray.get(i));
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

	private ArrayList<String[]> CreateInsturmentList() {
		return null;
	}

	private String[] getInstruments(ArrayList<String> col){ // returns null if the tuning does
		String column = String.join("",col);
		String regexPattern = "(BD|BA|B|T2|R|C|CC|HH|RC|Rd|SD|SN|S|HT|T|T1|MT|FT|F){1,10}";
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




		for(startLocation = 0; startLocation < transposedinput.size(); startLocation++){ //For all rows
			measureStart = false;
			row = transposedinput.get(startLocation);
			measureStart = String.join("",row).equals("|".repeat(row.size()));

			if(measureStart) {
				currentMeasure = new ArrayList<>();//Create new measure

				while (measureStart && startLocation < transposedinput.size() - 1) {
					startLocation++;
					row = transposedinput.get(startLocation);
					if (String.join("", row).equals("|".repeat(row.size()))) {
						startLocation--;
						break;
					}
					currentMeasure.add(row);

				}
				Measure.add(currentMeasure);
			}
		}

		Measure.remove(Measure.size() - 1); //Remove last empty measure

		//CONSIDER DIFF LENGTH MEASURE ROWS

		return Measure;

	}

	private double getDivision(ArrayList<ArrayList<String>> measure) {
		double division = 0;
		int runningcount;

		for (runningcount = 0; runningcount < measure.size(); runningcount++) {     //Go through each column

			ArrayList<String> col = (measure.get(runningcount));  //Each column

			if(!containsOnlyString(col,"-")){                   //First column with info
				int dashes = measure.size() - runningcount;

				totalDash = dashes;
				double beatNote = 1.0/beatType;
				double bSig  = 1.0 * beat;
				double totalBeatPerMeasure = bSig/beatType;
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


	private void createDivisionsArray(){
		for (ArrayList<ArrayList<String>> measure : Measure){
			divisionsArray.add(getDivision(measure));
		}
	}

	public int[] setVoice(DrumTuning tuner, String[] instruments){
		int[] voices = new int[instruments.length];
		for(int i = 0; i <instruments.length;i++){
			voices[i] = tuner.getVoice(instruments[i]);
		}
		return voices;
	}

	private int getDuration(double noteType,ArrayList<ArrayList<String>> measure) {
		double output = 0;
		double div = getDivision(measure);
		double beatTypeNote = 1.0/beatType;
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
