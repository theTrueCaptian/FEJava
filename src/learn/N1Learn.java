package learn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import program.*;
import region.*;
import utilsFE.*;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
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
	//private final int NTHREDS = 10;
	
	/********************************************************************************
	 Learning Function for Merge operator: MergeLearn
	 ********************************************************************************/
	/* @document is a String of the whole document
	 * @input is an Array of Regions
	 */
	public Program[] MergeLearn(GlobalDocument document, SetRegion input){

	    MiscUtil.logTime(0, "START: MergeLearn()");

	    /* We have organized it so that the positive regions are the ones we extract and inRegion is
	     * the ancestor region to extract these positive region from
	     */
	    SetRegion Thetas = input;

	    /* All that parts in inRegion that we will form partitions on
	     * This is the Y from Figure 6. Line 27
	     */
	    List<Text> positiveTotal = Thetas.getAllPositiveExamples();

	    /* Generate subsets for each region in SetRegion
	     * (We make subsets of theta1 and theta2)
	     */
	    ArrayList<Region> allSubsets = (ArrayList<Region>) Thetas.generateSubsets();
	    	    
	    //Create subsets of allSubsets[], and we call this X[]
	    ArrayList<SetRegion> X = generateAllSubset(allSubsets);
	    
	    //Filter and generate programs
	    int m = input.getRegionCount();
	    ArrayList<SetRegion> filtered = filterX(X, positiveTotal, m);

	    System.out.println(filtered.size());
	    //Learn programs for the filtered X's
	    //learnX(filtered, document, input, positiveTotal, callback)
	    return null;
	}
	
	/* Generates X, Figure 6 Line 26
	 * @array The array of Regions to run the powerset on
	 * @return ArrayList of SetRegions e.g. [SetRegion(Region("proteins (600)")), SetRegion(Region("proteins (600)"), Region("proteins (102)")), ...]
  	 */
	private ArrayList<SetRegion> generateAllSubset(ArrayList<Region> array) {
		
		Set<Set<Region>> cmb = Sets.powerSet( new HashSet<Region>( array));
		
		ArrayList<SetRegion> result = new ArrayList<SetRegion>();
		
		Iterator itr = cmb.iterator();
		while(itr.hasNext()) {
			Set<Region> element = (Set<Region>) itr.next();
			
			if(!element.isEmpty() ){
				//Create SetRegion with the Set<Region> object from element
				SetRegion newSetRegion = new SetRegion(new ArrayList<Region>(element), null, null);
				
				result.add(newSetRegion);
				//System.out.println(newSetRegion);
			}
		}
		return result;
		
	}
	
	/* Given the set X, filter it based on Y, all the positive examples.  
	 * e.g. A valid X : [SetRegion(Region("proteins(600)")), SetRegion(Region("proteins(120)")), SetRegion(Region("proteins(600)", "proteins(120)"))]
	 * @m is the number of regions in input (from MergeLearn()), Figure 6, Line 26
	 */
	private ArrayList<SetRegion> filterX(ArrayList<SetRegion> X, List<Text> Y, int m){
		ArrayList<SetRegion> filtered = new ArrayList<SetRegion>();
		//Iterate through each SetRegion in X, and make sure that each one is valid and add it to filtered[]
		for(int i=0; i< X.size(); i++){
			SetRegion subset = X.get(i);
			/* Check if the set is minimal subset WRT Y
	         * And the number of regions in subset must be equal to the number of regions in input (from MergeLearn()), Figure 6, Line 26
	         * And make sure that subset doesn't contain the same region multiple times
	         * And subset must contain at least one positive example
	         * If subset doesn't meet all the above criteria, then don't add it to filtered[]
	         */
			if(isMinimal(subset, Y) && m==subset.getRegionCount()
	            && subset.isRegionsUnique() && subset.getAllPositiveExamples().size()>0){
	            //We do the learning of subset later in learnX()
				filtered.add(subset);
				System.out.println(subset);
				System.out.println("---------");
	        }
		}
		
		return filtered;
	
	}
	
	//Check if subset[] is a minimal partition of Y
	private boolean isMinimal(SetRegion subset, List<Text> Y) {

	    List<Text> subsetArray = subset.getAllPositiveExamples();
	    boolean isMin = isMinimalPartition(subsetArray, Y);
	    /*System.out.println("Test");
	    System.out.println(subsetArray);
	    //System.out.println("******");
	    System.out.println(Y);
	    System.out.println(isMin);
	    System.out.println("--------------");*/
	    
	    return isMin;
	}


	//Check if set1 is a minimal subset of set2
	private boolean isMinimalPartition(List<Text> set1, List<Text> set2Org){
		List<Text> set2 = new ArrayList<Text>(set2Org);
		//System.out.println(set1.size());
		//System.out.println(set2.size());
		if(set2.size()<set1.size()){ //If set1 is bigger, then there is no way set2 is its superset
	        //System.out.println("Set2 is smaller than set1");
	    	return false;
	    }
	    //Loop through set1(the smaller set)
	    for(int j=0; j<set1.size(); j++){
	        //Find the elem1's match in set2, let's call this elem2
	        final Text elem1 = set1.get(j);
	        
	        //Find elem1 from set2
	        int find = Iterables.indexOf(set2, new Predicate<Text>() {
	            public boolean apply(Text elem2) {
	            	return elem1.isEqual(elem2) || elem1.isSubstring(elem2);//set2Elem.indexOf(elem1)!=-1 ;//
	            }
	        });
	            
	        if(find!=-1  ){
	            /* The elem1 is found in set2, remove it
	             * Remove elem2 from set2 (because it has already been counted)
	             */
	            //set2.splice(find, 1)
	        	//System.out.println("FOUND elem1 in set2: "+set2.get(find));
	        	set2.remove(find);
	        	//System.out.println(set2);
	        }else if(find==-1){  //If there exists an element in set1 that doesn't exist in set2, then return with false
	            //System.out.println("There exists an elem1 that doesn't exist in set2: "+elem1);
	        	return false;
	        }

	    }
	    //At this point all the checks are passed, return true
	    return true;
	}
	
	/*class SearchThread implements Runnable {
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
	}*/
}
