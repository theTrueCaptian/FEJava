package region;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.*;

import program.Program;
import region.*;
/* Define the SetRegion
 * @inArray is an Array of Regions
 * @programs is an Array of programs that can be run on the Regions
 */
public class SetRegion {
	private ArrayList<Region> arrayOfRegions = null;
	private ArrayList<Program> programsN1 = null, programsN2 = null;
	
	public SetRegion(ArrayList<Region> inArray, ArrayList<Program> programsN1, ArrayList<Program> programsN2) {
	    this.arrayOfRegions = inArray;
	    this.programsN1 = programsN1;
	    this.programsN2 = programsN2;
	}
	public SetRegion(Region[] inArray, ArrayList<Program> programsN1, ArrayList<Program> programsN2) {
	    this.arrayOfRegions = new ArrayList<Region>(Arrays.asList(inArray));
	    this.programsN1 = programsN1;
	    this.programsN2 = programsN2;
	}

	//Returns the number of Regions in the Set
	public ArrayList<Region> getSetRegions  (){
	    return this.arrayOfRegions;
	}

	//Returns the number of Regions in the Set
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
	public ArrayList<String> run(Program program, boolean isDividedByLines){
	    ArrayList<String> results = new ArrayList<String>();
	    for(Region region: this.arrayOfRegions){
	    	if(isDividedByLines){
	    		ArrayList<String> regionResult = program.executeLines(region.getRegionLines());
	    	    results.addAll(regionResult);
	    	}else{
	            results.addAll(program.execute(region.getRegion()));

	    	}
	    }
	    return results;
	}

	//Checks if the set contains multiple regions that are the same
	public boolean isRegionsUnique(){
	    ArrayList<Region> allRegions = new ArrayList<Region>();
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
	public ArrayList<String> getAllPositiveExamples(){
		ArrayList<String> positives = new ArrayList<String>();
		for(Region region: this.arrayOfRegions){
			//Accumulate all the positives of each Region into positives[]
			positives.addAll(region.getPositiveRegions());
		}
		return positives;

	}

	//Generate subsets for each region in SetRegion, and return it all in an Array
	//Equivalent to Figure 6, Line 26
	public ArrayList<SetRegion> generateSubsets(){
		ArrayList<SetRegion> allSubsets = new ArrayList<SetRegion>();

		/*for(Region theta: this.arrayOfRegions){
			//Create all possible subsets of examples within theta
			ArrayList<String> positiveExamples = theta.getPositiveRegions();

	        allSubsets.add(generateAllSubsetRegion(theta.getRegion(), positiveExamples));
		}*/
		
		return allSubsets;
	}

	public void setProgramsN1  (ArrayList<Program> inPrograms) {
	    this.programsN1 = inPrograms;
	}
	public void setProgramsN2  (ArrayList<Program> inPrograms) {
	    this.programsN2 = inPrograms;
	}

	public ArrayList<Program> getProgramsN1(){
	    return this.programsN1;
	}
	public ArrayList<Program> getProgramsN2(){
	    return this.programsN2;
	}

	/*
	 * @array is an array of positiveExamples of type String
	 */
	/*private SetRegion generateAllSubsetRegion(String regionString , Set<String> array) {
		//Set<Set<String>> cmb = Collections2.powerSet(array);
		/*Set<Set<SetRegion>> cmb;
		Set<SetRegion> a;
	    Set<SetRegion> result = new Set<SetRegion>();
	    cmb = Combinatorics.power(array);
	    cmb.forEach(function(a){ 
	        //Create a Region object from a
	        var strRegion =regionString
	        //Form index
	        var indices = []
	        a.forEach(function(posExample){
	            indices.push(strRegion.indexOf(posExample))
	        })
	        var region = new Region(
	            strRegion,   //string
	            indices,//positive examples's index
	            a//positive examples (which is the whole region
	        )
	        result.push(region)
	    });
	    return result;
	}*/


	public String toString() {
	    String str = "";
	    for(Region region: this.arrayOfRegions){//this.arrayOfRegions.forEach(function(region){
	        str = str + region.toString()+", ";
	    }
	    return "SetRegion(" + str + ")";
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
