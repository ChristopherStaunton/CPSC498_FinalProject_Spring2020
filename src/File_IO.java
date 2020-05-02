import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Accesses the input and output files and calls for the scraper
 */
public class File_IO {
	
	final private static String inputFile = "input.txt";
	final private static String outputFile = "output.txt";
	static PrintWriter inputStream = null;
	static BufferedReader outputStream = null;
	private static ArrayList<String> results = new ArrayList<String>();
	private static String line = "";
	static Scanner s;
	static FileReader out = null;
	
	/*
	 * Prints search topic into input file
	 * @param t String search topic
	 */
	public static void setInput(String t) throws IOException {
		inputStream = new PrintWriter(new FileWriter(inputFile));
		inputStream.print(t);
		if (inputStream != null) {
			inputStream.close();
		}
	}
	
	/*
	 * Calls method for running scraper
	 */
	public static void runScraper() throws IOException {
		runPython.runScraperPython();
	}
	
	/*
	 * Gathers content from output file in form of ArrayList<String>
	 * @return ArrayList<String> containing content in output file
	 */
	public static ArrayList<String> getOutput() throws IOException {
		outputStream = new BufferedReader(new FileReader(outputFile));
		while ((line = outputStream.readLine()) != null) {
			if (!line.equals("")) {
				results.add(line);
			}
		}
		if (outputStream != null) {
			outputStream.close();
		}
		return results;
	}
	
}
