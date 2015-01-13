package region;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;

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
	
	/*public SetRegion(ArrayList<Text> arrayOfRegions){
		this
	}
	/*public ListRegion(Region[] inArray, List<Program> programsN1, List<Program> programsN2) {
	    this.arrayOfRegions = new ArrayList<Region>(Arrays.asList(inArray));
	    this.programsN1 = programsN1;
	    this.programsN2 = programsN2;
	}*/

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
	    List<Region> allRegions = new ArrayList<Region>();
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
	public List<String> getAllPositiveExamples(){
		List<String> positives = new ArrayList<String>();
		for(Region region: this.arrayOfRegions){
			//Accumulate all the positives of each Region into positives[]
			positives.addAll(region.getPositiveRegionsString());
		}
		return positives;

	}

	/* Generate subsets for each region in SetRegion, and return it all in an Array
	 * Equivalent to Figure 6, Line 26
	 * Returns an array of regions e.g. [Region(posRegion[0]...), Region(posRegion[1]...) ...]
	 */
	public List<Region> generateSubsets(){
		List<Region> allSubList = new ArrayList<Region>();
		//generateAllSubListRegion(String regionString , List<String> array);
		
		//var allSubList = []

	    //this.arrayOfRegions.forEach(function(theta){
		for(Region theta: this.arrayOfRegions){
			//Create all possible subList of examples within theta
			//List<String> positiveExamples = theta.getPositiveRegions();
	
			//System.out.print(theta.getRegion());
			//System.out.print(positiveExamples);
			//System.out.println("---------------------------");
	        //allSubList = allSubList.concat(generateAllSubsetRegion(theta.getRegion(), positiveExamples));
			//allSubList.add(generateAllSubsetRegion(theta.getRegion(), positiveExamples));
			generateAllSubsetRegion(theta.getRegionString(), theta.getPositiveRegions());
		}//})
	    return allSubList;
		
		/*for(Region theta: this.arrayOfRegions){
			//Create all possible subList of examples within theta
			List<String> positiveExamples = theta.getPositiveRegions();

	        allSubList.add(generateAllSubsetRegion(theta.getRegion(), positiveExamples));
		}*/
		
		//return allSubList;
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
	private List<SetRegion> generateAllSubsetRegion(String regionString , ArrayList<Text> array) {
		Set<Set<Text>> cmb = Sets.powerSet( new HashSet<Text>( array));
		
		List<SetRegion> result = new ArrayList<SetRegion>();
		
		Iterator itr = cmb.iterator();
		while(itr.hasNext()) {
			Object element = itr.next();
			System.out.println(element);
			System.out.println("-----------------");
			
			//Create Region object from element
			//Region regionElement = 
			
			//result.add(new SetRegion(element));
		}
		return result;
		//List<ListRegion> a;
	    //List<ListRegion> result = null;//new List<ListRegion>();
	    /*cmb = Combinatorics.power(array);
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
	    });*/
	    //return result;
		
	}


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
