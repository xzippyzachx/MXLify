package tab2mxl;

import java.util.ArrayList;

public class Main {
	
	public static MyFrame myFrame;
	
	public static void main(String[] args) {
		myFrame = new MyFrame();
		//Tuning class test
		Tuning t = new Tuning("GuitarNotes.txt");
		System.out.println(t.getNote("B", 2));
	}

	public static void FileUploaded(ArrayList<ArrayList<String>> input)
	{
		new Parser(input);
	}
 
}
