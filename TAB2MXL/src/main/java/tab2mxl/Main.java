package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	//Test
	
	public static void main(String[] args) {
		//new MyFrame();
		new textInput();
	}
	
	public static void FileUploaded(ArrayList<ArrayList<String>> input)
	{
		new Parser(input);
	}

}
