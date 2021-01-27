package tab2mxl;

import java.util.ArrayList;

public class Main {
    //Labtask2
	public static MyFrame myFrame;
	
	public static void main(String[] args) {
		myFrame = new MyFrame();
	}
	
	public static void FileUploaded(ArrayList<ArrayList<String>> input)
	{
		new Parser(input);
	}
 
}
