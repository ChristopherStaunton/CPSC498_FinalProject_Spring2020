import java.io.*;

public class runPython {
	final private static String scraperFile = "./src/scraper";
	public static void runScraperPython() throws IOException {
		System.out.println("start of runPython");
		Runtime.getRuntime().exec(scraperFile);
		System.out.println("end of runPython");
	}
}