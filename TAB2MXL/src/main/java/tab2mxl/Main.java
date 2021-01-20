package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		new MyFrame();
	}
	
	public static void FileUploaded(ArrayList<ArrayList<String>> input)
	{
		new Parser(input);
	}

}
