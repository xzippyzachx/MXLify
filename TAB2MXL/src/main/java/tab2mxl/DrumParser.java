package tab2mxl;

import java.util.ArrayList;

public class DrumParser {
	
	public static void main(String[] args) {
		//Testing DrumTuning
		String[] a = {"BD", "CC", "F"};
		int d = 2;
		DrumTuning test = new DrumTuning(a, d);
		
		System.out.print(test.getNote("BD"));
		System.out.println(test.getOctave("BD"));
		System.out.print(test.getNote("CC"));
		System.out.println(test.getOctave("CC"));
		System.out.print(test.getNote("F"));
		System.out.println(test.getOctave("F"));
		System.out.println(test.unSupportedDrum);
		System.out.println(test.getID("CC", "x"));
		System.out.println(test.getVoice("CC"));
	}
	
	public DrumParser (ArrayList<ArrayList<String>> input) {
				
	}
}
