package region;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.escape.CharEscaper;

import program.Program;
import region.*;
import utilsFE.*;

/* Define the ListRegion
 * @inArray is an Array of Regions
 * @programs is an Array of programs that can be run on the Regions
 */
public class SetRegion {
	private List<Region> arrayOfRegions = null;
	private List<Program> programsN1 = null, programsN2 = null;
	
	public SetRegion(List<Region> inArray, List<Program> programsN1, List<Program> programsN2) {
	    this.arrayOfRegions = inArray;
	    this.programsN1 = programsN1;
	    this.programsN2 = programsN2;
	}
	
	public SetRegion(ArrayList<Region> inArray, List<Program> programsN1, List<Program> programsN2) {
	    this.arrayOfRegions = inArray;
	    this.programsN1 = programsN1;
	    this.programsN2 = programsN2;
	}
	
	//Returns the number of Regions in the List
	public List<Region> getListRegions  (){
	    return this.arrayOfRegions;
	}

	//Returns the number of Regions in the List
	public int getRegionCount  (){
	    return this.arrayOfRegions.size();
	}

	//Return the region at index
	public Region getRegionAt  (int index){
	    return this.arrayOfRegions.get(index);
	}

	/* Given a program, run it on the regions
	 * @isDividedByLines indicate if the regions must be divided by lines before the program runs on it
	 */
	public List<String> run(Program program, boolean isDividedByLines){
	    List<String> results = new ArrayList<String>();
	    for(Region region: this.arrayOfRegions){
	    	if(isDividedByLines){
	    		List<String> regionResult = program.executeLines(region.getRegionLines());
	    	    results.addAll(regionResult);
	    	}else{
	            results.addAll(program.execute(region.getRegionString()));

	    	}
	    }
	    return results;
	}

	//Checks if the List contains multiple regions that are the same
	public boolean isRegionsUnique(){
	    List<Region> allRegions = this.arrayOfRegions;
	    //Loop through all Regions, and then check against the other regions
	    for(int i=0; i<allRegions.size(); i++){
	    	for(int j=0; j<allRegions.size(); j++){
	    		//Check with Object.equals(Object)
	    		if(i!=j && allRegions.get(i).equals(allRegions.get(j))){
	    			return false;
	    		}
	    	}
	    }
	    return true;
	   
	}

	//Given an array of regions, return all the positive examples from every single region in one Array
	public List<Text> getAllPositiveExamples(){
		List<Text> positives = new ArrayList<Text>();
		for(Region region: this.arrayOfRegions){
			//Accumulate all the positives of each Region into positives[]
			positives.addAll(region.getPositiveRegions());
		}
		return positives;

	}

	/* Generate subsets for each region in SetRegion, and return it all in an Array
	 * Equivalent to Figure 6, Line 26
	 * Returns an array of regions e.g. [Region("proteins (600)"), Region("proteins (600)", "proteins (102)") ...]
	 */
	public List<Region> generateSubsets(){
		List<Region> allSubsets = new ArrayList<Region>();
		
		for(Region theta: this.arrayOfRegions){
			//Create all possible subList of examples within theta
			allSubsets.addAll( generateAllSubsetRegion(theta.getRegionString(), theta.getPositiveRegions()));
		}
	    return allSubsets;
		
	}

	public void ListProgramsN1  (List<Program> inPrograms) {
	    this.programsN1 = inPrograms;
	}
	public void ListProgramsN2  (List<Program> inPrograms) {
	    this.programsN2 = inPrograms;
	}

	public List<Program> getProgramsN1(){
	    return this.programsN1;
	}
	public List<Program> getProgramsN2(){
	    return this.programsN2;
	}

	/*
	 * @array is an array of positiveExamples of type String e.g. ["proteins (200)", ...]
	 * @return 
	 */
	private List<Region> generateAllSubsetRegion(String regionString , ArrayList<Text> array) {
		Set<Set<Text>> cmb = Sets.powerSet( new HashSet<Text>( array));
		
		List<Region> result = new ArrayList<Region>();
		
		Iterator itr = cmb.iterator();
		while(itr.hasNext()) {
			Set<Text> element = (Set<Text>) itr.next();
			
			//Create Region object from element
			Region regionElement = new Region( regionString, new ArrayList<Text>(element));
			//System.out.println(regionElement);
			//System.out.println("-----------------");
			
			result.add(regionElement);
		}
		return result;
		
	}


	public String toString() {
	    String str = "";
	    for(Region region: this.arrayOfRegions){
	        str = str + region.toString()+", ";
	    }
	    return MiscUtil.escape("SetRegion(" + str + ")");
	};

	//This function turns an object instance into an object literal
	/*public void obj() {
	    var arrayOfRegions = _.map(this.arrayOfRegions, function(region){
	        //return an obj
	        return region.obj()
	    })
	    var programsN1 =undefined, programsN2=undefined
	    if(!Util.isEmpty(this.programsN1)) {
	        programsN1 = _.map(this.programsN1, function (prog) {
	            return prog.obj()
	        })
	    }
	    if(!Util.isEmpty(this.programsN2)) {
	        var programsN2 = _.map(this.programsN2, function (prog) {
	            return prog.obj()
	        })
	    }
	    return {
	            'arrayOfRegions':arrayOfRegions,
	            'programsN1':programsN1,
	            'programsN2':programsN2
	    }
	}*/
}
