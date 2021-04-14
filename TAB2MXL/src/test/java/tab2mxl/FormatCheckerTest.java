package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FormatCheckerTest {

	@BeforeAll
	static void setUp() throws Exception {
		new Main();
		Main.main(null);
	}
	
	@Test
	void testFormat1() {
		
		String input = "";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
		
		assertEquals(2,formatChecker.GetErrorType());
	}
	
	@Test
	void testFormat2() {
		
		String input = "awdadadaawd";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
				
		assertEquals(2,formatChecker.GetErrorType());
	}
	
	@Test
	void testFormat3() {
		
		String input ="\n"
					+ "|-----------0-----|-0---------------|\n"
					+ "|---------0---0---|-0---------------|\n"
					+ "|-------1-------1-|-1---------------|\n"
					+ "|-----2-----------|-2---------------|\n"
					+ "|---2-------------|-2---------------|\n"
					+ "|-0---------------|-0---------------|";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
		
		String output = "";
		for (String line : formatChecker.GetOuput())
			output += line + "\n";
		
		String expected = "|-----------0-----|-0---------------|\n"
						+ "|---------0---0---|-0---------------|\n"
						+ "|-------1-------1-|-1---------------|\n"
						+ "|-----2-----------|-2---------------|\n"
						+ "|---2-------------|-2---------------|\n"
						+ "|-0---------------|-0---------------|\n";
				
		assertEquals(0,formatChecker.GetErrorType());
		assertEquals(expected, output);
	}
	
	@Test
	void testFormat4() {
		
		String input ="|-----------0-----|-0---------------|\n"
					+ "|---------0---0---|-0---------------|\n"
					+ "|-------1-------1-|-1----------|\n"
					+ "|-----2-----------|-2---------------|\n"
					+ "|---2-------------|-2---------------|\n"
					+ "|-0---------------|-0----------|";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
				
		String output = "";
		for (String line : formatChecker.GetOuput())
			output += line + "\n";
		
		String expected = "|-----------0-----|-0---------------|\n"
						+ "|---------0---0---|-0---------------|\n"
						+ "|-------1-------1-|-1---------------|\n"
						+ "|-----2-----------|-2---------------|\n"
						+ "|---2-------------|-2---------------|\n"
						+ "|-0---------------|-0---------------|\n";
		
		assertEquals(1,formatChecker.GetErrorType());
		assertEquals(expected, output);
	}
	
	@Test
	void testFormat5() {
		
		String input ="|-----------0-----|-0---------------|\n"
					+ "|---------0---0---|-0---------------|\n"
					+ "|-------1-------1-|-1---------------|\n"
					+ "|-----2-----------|-2---------------|\n"
					+ "|---2-------------|-2---------------|\n"
					+ "|-0---------------|-0---------";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
				
		String output = "";
		for (String line : formatChecker.GetOuput())
			output += line + "\n";
		
		String expected = "|-----------0-----|-0---------------|\n"
						+ "|---------0---0---|-0---------------|\n"
						+ "|-------1-------1-|-1---------------|\n"
						+ "|-----2-----------|-2---------------|\n"
						+ "|---2-------------|-2---------------|\n"
						+ "|-0---------------|-0---------------|\n";
		
		assertEquals(1,formatChecker.GetErrorType());
		assertEquals(expected, output);
	}
	
	@Test
	void testFormat6() {
		
		String input ="e|-----------0-----|-0---------------|\n"
					+ "X|---------0---0---|-0---------------|\n"
					+ "G|-------1-------1-|-1---------------|\n"
					+ "D|-----2-----------|-2---------------|\n"
					+ "Z|---2-------------|-2---------------|\n"
					+ "E|-0---------------|-0---------------|\n";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
				
		String output = "";
		for (String line : formatChecker.GetOuput())
			output += line + "\n";
		
		String expected = "e|-----------0-----|-0---------------|\n"
						+ "X|---------0---0---|-0---------------|\n"
						+ "G|-------1-------1-|-1---------------|\n"
						+ "D|-----2-----------|-2---------------|\n"
						+ "Z|---2-------------|-2---------------|\n"
						+ "E|-0---------------|-0---------------|\n";
				
		assertEquals(2,formatChecker.GetErrorType());
		assertEquals(expected, output);
	}
	
	@Test
	void testFormat7() {
		
		String input ="e1|-----------0-----|-0---------------|\n"
					+ "B2|---------0---0---|-0---------------|\n"
					+ "G3|-------1-------1-|-1---------------|\n"
					+ "D11|-----2-----------|-2---------------|\n"
					+ "A5|---2-------------|-2---------------|\n"
					+ "E6|-0---------------|-0---------------|\n";
		
		FormatChecker formatChecker = new FormatChecker(input, 0);
				
		String output = "";
		for (String line : formatChecker.GetOuput())
			output += line + "\n";
		
		String expected = "e1|-----------0-----|-0---------------|\n"
						+ "B2|---------0---0---|-0---------------|\n"
						+ "G3|-------1-------1-|-1---------------|\n"
						+ "D11|-----2-----------|-2---------------|\n"
						+ "A5|---2-------------|-2---------------|\n"
						+ "E6|-0---------------|-0---------------|\n";
				
		assertEquals(2,formatChecker.GetErrorType());
		assertEquals(expected, output);
	}
	
	@Test
	void testFormat8() {
		
		String input ="CC|x---------------|--------x-------|x---------------|--------x-------|\n"
					+ "HH|--x-x-x-x-x-x-x-|----------------|--x-x-x-x-x-x-x-|----------------|\n"
					+ "SD|----o-------o---|oooo------------|----o-------o---|oooo------------|\n"
					+ "ZZ|----------------|----oo----------|----------------|----oo----------|\n"
					+ "MT|----------------|------oo--------|----------------|----oo----------|\n"
					+ "BD|o-------o-------|o-------o-------|----------------|----oo----------|\n";
		
		FormatChecker formatChecker = new FormatChecker(input, 2);
				
		String output = "";
		for (String line : formatChecker.GetOuput())
			output += line + "\n";
		
		String expected = "CC|x---------------|--------x-------|x---------------|--------x-------|\n"
						+ "HH|--x-x-x-x-x-x-x-|----------------|--x-x-x-x-x-x-x-|----------------|\n"
						+ "SD|----o-------o---|oooo------------|----o-------o---|oooo------------|\n"
						+ "ZZ|----------------|----oo----------|----------------|----oo----------|\n"
						+ "MT|----------------|------oo--------|----------------|----oo----------|\n"
						+ "BD|o-------o-------|o-------o-------|----------------|----oo----------|\n";
				
		assertEquals(2,formatChecker.GetErrorType());
		assertEquals(expected, output);
	}
	
}
