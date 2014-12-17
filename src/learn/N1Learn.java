package learn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import program.*;
import region.*;
import utilsFE.*;
/*
 * Maeda Hanafi
 * N1Learn.java learns N1 programs; Spawns threads
 */
/*String URL = "http://blog.dianpelangi.com/";
String search = "hijab";
SearchThread mainThread = new SearchThread(URL, search);
ArrayList<String> urls = mainThread.task(URL, search);

// for every url, assign it the task (searching) to an executor
ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
for (int i = 0; i < urls.size(); i++) {
	SearchThread worker = new SearchThread(urls.get(i), search);
	executor.execute(worker);
}*/
public class N1Learn {
	private final int NTHREDS = 10;
	
	/********************************************************************************
	 Learning Function for Merge operator: MergeLearn
	 ********************************************************************************/
	/* @document is a String of the whole document
	 * @input is an Array of Regions
	 */
	public Program[] MergeLearn(GlobalDocument document, SetRegion input){

	    Util.logTime(0, "START: MergeLearn()")

	    /* We have organized it so that the positive regions are the ones we extract and inRegion is
	     * the ancestor region to extract these positive region from
	     */
	    var Thetas = input

	    /* All that parts in inRegion that we will form partitions on
	     * This is the Y from Figure 6. Line 27
	     */
	    var positiveTotal = Thetas.getAllPositiveExamples()

	    /* Generate subsets for each region in SetRegion
	     * (We make subsets of theta1 and theta2)
	     */
	    var allSubsets = Thetas.generateSubsets()

	    //Create subsets of allSubsets[], and we call this X[]
	    var X = generateAllSubset(allSubsets)

	    //Filter and generate programs
	    var m = input.getRegionCount()
	    var filtered = filterX(X, positiveTotal, m);


	    //Learn programs for the filtered X's
	    learnX(filtered, document, input, positiveTotal, callback)

	}
	
	class SearchThread implements Runnable {
		String executeURL, search;

		public SearchThread(String executeURL, String search) {
			this.executeURL = executeURL;
			this.search = search;
		}

		public void run() {
			task(executeURL, search);
		}

		public ArrayList<String> task(String URL, String search) {
			// each content in arraylist is a line of string from the html file
			ArrayList<String> fileContent = grabFile(URL);
			// search all occurrences of that search string
			System.out.println("Parsing " + URL);
			ArrayList<String> urlList = parseFile(URL, fileContent, search);
			return urlList;
		}

		// this method takes in the file content and prints out the occurences
		// of search string
		// this also returns an arraylist of urls in the page
		private ArrayList<String> parseFile(String userURL,
				ArrayList<String> fileContent, String search) {
			ArrayList<String> urlList = new ArrayList<String>();
			// for loop in each line(content of the arraylist)
			for (int i = 0; i < fileContent.size(); i++) {
				// use String.split to split the line and find if there are
				// occurences
				String[] tokenLine = fileContent.get(i).split("\\s");
				for (int j = 0; j < tokenLine.length; j++) {
					// this if statement checks for search string occurrence
					if (tokenLine[j].matches(search + ".*")) {
						System.out.println("Occurence of " + search
								+ " in line " + (i + 1) + " in URL:"
								+ executeURL);
					}
					// this statement checks if there is http
					if (tokenLine[j].matches("href=\"/.*")
							|| tokenLine[j].matches("href=\"http.*")) {
						// add to urlList
						urlList.add(cleanURL(userURL, tokenLine[j]));
					}
				}
			}
			return urlList;
		}

		// gets only the url in between the quotes
		public String cleanURL(String URL, String dirtyURL) {
			try {
				String extract = dirtyURL.split("\"")[1];
				// append url website if needed
				if (!extract.matches("http.*"))
					extract = URL + extract;
				return extract;
			} catch (ArrayIndexOutOfBoundsException ex) {
				String extract = dirtyURL.split("\"")[0];
				// append url website if needed
				if (!extract.matches("http.*"))
					extract = URL + extract;
				return extract;
			}

		}

		private ArrayList<String> grabFile(String URLSTRING) {
			// Declare buffered stream for reading text for the URL
			BufferedReader infile = null;
			URL url = null;
			ArrayList<String> result = new ArrayList<String>();
			String status = "";
			try {
				// Obtain URL
				url = new URL(URLSTRING);

				// Create a buffered stream
				InputStream is = url.openStream();
				infile = new BufferedReader(new InputStreamReader(is));

				String inLine;
				// Read a line and append the line to the arraylist
				while ((inLine = infile.readLine()) != null) {
					result.add(inLine + '\n');
				}

				status = ("File loaded successfully");
			} catch (FileNotFoundException e) {
				status = ("URL " + url + " not found.");
			} catch (IOException e) {
				status = e.getMessage();
			} finally {
				try {
					if (infile != null)
						infile.close();
				} catch (IOException ex) {
				}
			}
			System.out.println(status);
			return result;
		}
	}
}
