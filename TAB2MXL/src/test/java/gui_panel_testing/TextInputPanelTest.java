package gui_panel_testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui_panels.TextInputContentPanel;

public class TextInputPanelTest {

	TextInputContentPanel panel;
	
	@BeforeEach
	void setUp() throws Exception {
		 panel = new TextInputContentPanel();
		 panel.textField.focusGained(null);
		 
	}

	@Test
	void test() {
		panel.textField.setText("text");;
		
		String[] expected = {"[e, , |, -, -, -, -, -, -, -, -, 0, -, -, -, -, -, |, -, -, -, -, -, -, -, -, -, -, -, -, -, -, |]",
				"[B, , |, -, -, -, -, -, -, -, -, -, -, -, 3, -, -, |, -, -, 3, -, -, -, -, -, -, -, -, -, -, -, |]",
				"[G, , |, -, -, -, -, -, -, -, -, -, -, -, -, -, -, |, -, -, -, -, -, -, -, -, -, -, -, -, -, -, |]",
				"[D, , |, -, -, -, -, -, 2, -, -, 0, -, -, 2, -, -, |, -, -, 3, -, -, -, -, -, -, -, -, -, -, -, |]",
				"[A, , |, -, -, 3, -, -, -, -, -, -, -, -, 0, -, -, |, -, -, 3, -, -, -, -, -, -, -, -, -, -, -, |]",
				"[E, , |, -, -, -, -, -, -, -, -, 3, -, -, -, -, -, |, -, -, -, -, -, 1, -, -, -, -, -, -, -, -, |]"};
		
		String[] input = { "e|--------0-----|--------------|",
				"B|-----------3--|--3-----------|",
				"G|--------------|--------------|",
				"D|-----2--0--2--|--3-----------|",
				"A|--3--------0--|--3-----------|",
				"E|--------3-----|-----1--------|"};
		
		ArrayList<ArrayList<String>> out = panel.GetInput(input, false);
		
		assertEquals(out.size(), 6);
		assertEquals(out.get(0).size(), 33);
		
		for (int i = 0; i < out.size(); i++) {
			assertEquals(out.get(i).toString(), expected[i]);
		}
		
	}
	
	@Test
	void test2() {
		panel.textField.setText("");
		
		String[] input = { "e|--------0-----|--------------|",
				"B|-----------3--|--3-----------|",
				"G|--------------|--------------|",
				"D|-----2--0--2--|--3-----------|",
				"A|--3--------0--|--3-----------|",
				"E|--------3-----|-----1--------|"};
		
		ArrayList<ArrayList<String>> out = panel.GetInput(input, false);
		
		assertNull(out);
		
	}
	
}
