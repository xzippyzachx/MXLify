package tab2mxl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		ReadFile("GuitarTab.txt");	
		// yassers lab task
		//Christophers lab task
		// Faruq's lab task
		// Erika's Lab Task
	}
	
	public static void ReadFile(String fileName)
	{
		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
			myReader.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
