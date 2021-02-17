package tab2mxl;

import java.io.IOException;

public class CreateScore {
	String xmlPath;
	
	CreateScore(String xmlPath){
		this.xmlPath = xmlPath;
		try {
			Runtime.getRuntime().exec("ScoreMaker/dist/xml2score/xml2score.exe "+xmlPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("didnt work");
        }
	}

}
