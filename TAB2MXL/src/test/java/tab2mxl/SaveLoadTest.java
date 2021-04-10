package tab2mxl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gui.UndoRedoTextArea;

public class SaveLoadTest {

	static FileGenerator fileGen;
	String fileContent;
	String[] loadedContent;
	String expected;
	
	@BeforeAll
	static void setUp() {
		new Main();
		Main.main(null);
	}
	
	@Test
	void testSave1() {
		String path = "SaveLoadTests/testSave.mxlify";
		
		SaveManager	saveManager = new SaveManager(path, 0, "test", "4/4", 
				  "|-----------0-----|-0---------------|\n"
				+ "|---------0---0---|-0---------------|\n"
				+ "|-------1-------1-|-1---------------|\n"
				+ "|-----2-----------|-2---------------|\n"
				+ "|---2-------------|-2---------------|\n"
				+ "|-0---------------|-0---------------|", "measure 1-20 4/4");
		
		fileContent = this.readFile(path);
		expected ="instrument 0\n"
				+ "title test\n"
				+ "timesig 4/4\n"
				+ "custom\n"
				+ "measure 1-20 4/4\n"
				+ "tablature\n"
				+ "|-----------0-----|-0---------------|\n"
				+ "|---------0---0---|-0---------------|\n"
				+ "|-------1-------1-|-1---------------|\n"
				+ "|-----2-----------|-2---------------|\n"
				+ "|---2-------------|-2---------------|\n"
				+ "|-0---------------|-0---------------|";
		
		assertEquals(expected, fileContent);
	}
	
	@Test
	void testLoad1() {
		String path = "SaveLoadTests/testSave.mxlify";
		Main.myFrame.textInputContentPanel.textField.focusGained(null);
		Main.myFrame.fileUploadContentPanel.customTextArea.focusGained(null);
		
		LoadManager	loadManager = new LoadManager(path);
		loadedContent = loadManager.GetLoadedData();
		
		assertEquals("0", loadedContent[0]);
		assertEquals("test", loadedContent[1]);
		assertEquals("4/4", loadedContent[2]);
		
		assertEquals("measure 1-20 4/4", loadManager.GetLoadedCustom().trim());
		
		expected ="|-----------0-----|-0---------------|\n"
				+ "|---------0---0---|-0---------------|\n"
				+ "|-------1-------1-|-1---------------|\n"
				+ "|-----2-----------|-2---------------|\n"
				+ "|---2-------------|-2---------------|\n"
				+ "|-0---------------|-0---------------|\n";
		
		assertEquals(expected, loadManager.GetLoadedText().replaceAll("\\r", ""));		
	}
	
	@SuppressWarnings("resource")
	String readFile(String path) {
		String fileContent = null;
		try {
			fileContent = new Scanner(new File(path)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContent;
	}	
	
	
}
