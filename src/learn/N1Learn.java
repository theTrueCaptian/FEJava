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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import program.*;
import region.*;
import utilsFE.*;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
/*
 * Maeda Hanafi
 * N1Learn.java learns N1 programs; Spawns threads
 */

public class N1Learn {
	private final int NTHREDS = 500;
	
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

	    //Learn programs for the filtered X's
	    learnX(filtered, document, input, positiveTotal/*, callback*/);
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
	        }
		}
		
		return filtered;
	
	}
	
	//Check if subset[] is a minimal partition of Y
	private boolean isMinimal(SetRegion subset, List<Text> Y) {

	    List<Text> subsetArray = subset.getAllPositiveExamples();
	    boolean isMin = isMinimalPartition(subsetArray, Y);
	     
	    return isMin;
	}


	//Check if set1 is a minimal subset of set2
	private boolean isMinimalPartition(List<Text> set1, List<Text> set2Org){
		List<Text> set2 = new ArrayList<Text>(set2Org);
		if(set2.size()<set1.size()){ //If set1 is bigger, then there is no way set2 is its superset
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
	        	set2.remove(find);
	        }else if(find==-1){  //If there exists an element in set1 that doesn't exist in set2, then return with false
	        	return false;
	        }

	    }
	    //At this point all the checks are passed, return true
	    return true;
	}
	
	//Learn programs for X
	private void learnX(ArrayList<SetRegion> inX, GlobalDocument document, SetRegion input, List<Text> positiveTotal/*, callback*/){
	    List<SetRegion> X = new ArrayList<SetRegion>(inX);
	    int countClosed = 0;

	    //String URL = "http://blog.dianpelangi.com/";
	    //String search = "hijab";
	    //N1LearnChild mainThread = new N1LearnChild(URL, search);
	    //ArrayList<String> urls = mainThread.task(URL, search);

	    // for every url, assign it the task (searching) to an executor
	    ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
	    ArrayList<Runnable> workers = new ArrayList<Runnable>();
	    for (int i = 0; i < X.size(); i++) {
	    	SetRegion setRegion = X.get(i);
	    	N1LearnChild worker = new N1LearnChild(setRegion, document, i);
	    	executor.execute(worker);
	    	workers.add(worker);
	    }
	    //System.out.println("DONEODNOE");
	    executor.shutdown(); // Disable new tasks from being submitted
	    try {
	      // Wait a while for existing tasks to terminate
	      if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
	    	  executor.shutdownNow(); // Cancel currently executing tasks
	        // Wait a while for tasks to respond to being cancelled
	        if (!executor.awaitTermination(60, TimeUnit.SECONDS))
	            System.err.println("Pool did not terminate");
	      }
	    } catch (InterruptedException ie) {
	      // (Re-)Cancel if current thread also interrupted
	    	executor.shutdownNow();
	      // Preserve interrupt status
	      Thread.currentThread().interrupt();
	    } 
	    
	    for (int i = 0; i < workers.size(); i++) {
	    	System.out.println(((N1LearnChild) workers.get(i)).getID());
	    }
	    //Holds all the subsets along with the
	    /*var globalX = []

	    for(var i=0; i< X.length; i++) {
	        console.log("Spawning child number:"+i)
	        var fork = require('child_process').fork;

	        var kid = fork(__dirname + '/N1LearnChild.js');

	        kid.on('message', function(response) {
	            var setRegion = Converter.convertToSetRegion(response)
	            //console.log(setRegion)
	            //If it is empty set returned, then remove the subset too
	            if (!Util.isEmpty(setRegion.getProgramsN1())) {
	                globalX.push(setRegion)
	            }
	            // globalX.push(response)

	            Util.logLearn("-----")


	        });

	        // Listen for an exit event:
	        kid.on('exit', function (exitCode) {
	            console.log("Child exited with code: " + exitCode);

	            ///Determine whether all Children have been killed in order to Learn Merge
	            countClosed++
	            if(countClosed== X.length) {
	                console.log("All killed")
	                contMerge(globalX, document, input, positiveTotal, callback)
	            }
	        });
	        var subset = X[i]

	        //Send information to N1Learn
	        kid.send({'SetRegion': subset.obj(), 'document':document.obj(), 'id':i});


	    }*/

	}

	
}
