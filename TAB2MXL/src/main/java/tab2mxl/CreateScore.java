package tab2mxl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CreateScore {
	String xmlPath;
	private static String OS = System.getProperty("os.name").toLowerCase();

	CreateScore(String xmlPath){
		if(isWindows()) {
			this.xmlPath = xmlPath;
			File script = new File("ScoreMaker/dist/xml2score/xml2score.exe");
			String[] command = {script.getAbsolutePath(),xmlPath};
			ProcessBuilder processbuilder = new ProcessBuilder(command);
			File directory = new File("ScoreMaker");
			processbuilder.directory(new File(directory.getAbsolutePath()));
			try {
				Process process = processbuilder.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while((line = reader.readLine()) != null){
					System.out.println(line);
				}
				int exitCode = process.waitFor();
				System.out.println("ExitCode = "+ exitCode );
//				Runtime.getRuntime().exec("ScoreMaker/dist/xml2score/xml2score.exe "+xmlPath);
//				TimeUnit.SECONDS.sleep(1); // Score.svg is generated in parallel so we need to wait for it to finish first
				File score = new File("ScoreMaker/Score.svg");
				score.renameTo(new File( new File(xmlPath).getParentFile() +"/"+ Parser.misc.get("Title")+"_Score.svg" ));
				score.delete();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
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
