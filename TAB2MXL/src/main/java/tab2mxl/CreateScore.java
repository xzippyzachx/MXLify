package tab2mxl;

import java.io.IOException;

public class CreateScore {
	String xmlPath;
	private static String OS = System.getProperty("os.name").toLowerCase();

	CreateScore(String xmlPath){
		if(isWindows()) {
		this.xmlPath = xmlPath;
		try {
			Runtime.getRuntime().exec("ScoreMaker/dist/xml2score/xml2score.exe "+xmlPath);
        } catch (IOException e) {
            e.printStackTrace();  
            System.out.println("didnt work");
        }
	}
		else {
			System.out.println("unable to generate Score, this is a windows only feature");
		}
		}
	public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

}
