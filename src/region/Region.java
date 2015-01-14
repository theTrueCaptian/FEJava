package region;

import java.util.ArrayList;
import java.util.Arrays;

import utilsFE.MiscUtil;
/********************************************************************************
Region class/module

********************************************************************************/
public class Region {
	private Text region = null;
	
	private ArrayList<String> regionLines = null;
	
	private ArrayList<Text> positiveRegions = null;

	/*
	 * Define the Region
	 * 
	 * @region is the whole region as a string e.g. "Maeda Hanafi, ....."
	 * 
	 * @positiveRegionsString is the array of positive regions of type String e.g.
	 * ["M", "T"]
	 * 
	 * @positiveIndices is a list of indices in which the positive examples
	 * appear, e.g. [0, 2,...]
	 */
	public Region(Text region, ArrayList<Integer> positiveRegionIndices,
			ArrayList<String> positiveRegionsString,
			ArrayList<Integer> negativeRegionIndices,
			ArrayList<String> negativeRegionsString) {
		
		if(region!=null) this.region = region;
		/*if(positiveRegionsString!=null) this.positiveRegionsString = positiveRegionsString;
		if(negativeRegionsString!=null) this.negativeRegionsString = negativeRegionsString;
		if(positiveRegionIndices!=null) this.positiveRegionIndices = positiveRegionIndices;
		if(negativeRegionIndices!=null) this.negativeRegionIndices = negativeRegionIndices;
*/
		// regionLines is region divided by lines
		if(region!=null) this.regionLines = MiscUtil.splitAndRetainByNewline(this.region.getText());

		this.positiveRegions = formTextArray(positiveRegionsString, positiveRegionIndices);
	}
	
	public Region(Text region, Integer[] positiveRegionIndices,
			String[] positiveRegionsString,
			Integer[] negativeRegionIndices,
			String[] negativeRegionsString) {
		if(region!=null) this.region = region;
	
		// regionLines is region divided by lines
		if(region!=null) this.regionLines = MiscUtil.splitAndRetainByNewline(this.region.getText());
	
		this.positiveRegions = formTextArray(new ArrayList<String>(Arrays.asList(positiveRegionsString)), 
											 new ArrayList<Integer>(Arrays.asList(positiveRegionIndices)));
	}
	
	//Create a Region object from an ArrayList of Texts
	public Region(Text region, ArrayList<Text> positiveRegions){
		if(region!=null) this.region = region;
		// regionLines is region divided by lines
		if(region!=null) this.regionLines = MiscUtil.splitAndRetainByNewline(this.region.getText());
		this.positiveRegions = positiveRegions ;
	}

	//Forms an array of text given the data set in the constructor
	private ArrayList<Text> formTextArray(ArrayList<String> positiveRegionsString, ArrayList<Integer> positiveRegionIndices){
		ArrayList<Text> positiveRegions = new ArrayList<Text>();
		//Iterate over positiveRegionsString and convert the information into Texts
		for(int i=0; i<positiveRegionsString.size(); i++){
			positiveRegions.add(new Text( positiveRegionsString.get(i), positiveRegionIndices.get(i)));
		}
		return positiveRegions;
	}
	
	public String getRegionString() {
		return this.region.getText();
	}

	public Text getRegion() {
		return this.region;
	}

	
	// Returns an arraylist of positive regions in Text object, e.g. [Text, Text, ..]
	public ArrayList<Text> getPositiveRegions() {
		return this.positiveRegions;
	}
		
	// Returns an arraylist of strings of positive regions, e.g. ["protiends", ...]
	/*public ArrayList<String> getPositiveRegionsString() {
		return this.positiveRegionsString;
	}

	public ArrayList<String> getNegativeRegionsString() {
		return this.negativeRegionsString;
	}
	*/
	
	// Returns the number of positive regions
	public int getPositiveRegionsCount() {
		return this.positiveRegions.size();
	}

	/*
	// Returns an array of positive regions in string
	public ArrayList<Integer> getPositiveIndices() {
		return this.positiveRegionIndices;
	}

	public ArrayList<Integer> getNegativeIndices() {
		return this.positiveRegionIndices;
	}

	// Returns the index'th positive regions in string
	public String getPositiveRegionsStringAt(int index) {
		return this.positiveRegionsString.get(index);
	}

	/*
	 * Given index, return the positive index from positiveRegionIndices array
	 * So the return is the location of postive example number, index
	 */
	/*public int getPositiveIndexOf(int index) {
		return this.positiveRegionIndices.get(index);
	}*/

	// Return the whole region divided by lines in an array
	public ArrayList<String> getRegionLines() {
		return this.regionLines;
	}

	// Returns the line number given the index WRT to this.region
	/*
	 * public int getLineNumber(int indexInRegion){ var count = 0 for(var
	 * t=0;t<this.regionLines.length; t++){ var stBoundary = count var
	 * endBoundary = count + this.regionLines[t].length
	 * 
	 * if(stBoundary<=indexInRegion && indexInRegion<endBoundary){ return (t+1)
	 * } count = endBoundary } return undefined }
	 */

	public String toString() {
		return MiscUtil.escape("Region("/* + this.region + ", " */+ this.positiveRegions
				+ ")");
	};

	/*
	 * Region.prototype.obj = function () {
	 * 
	 * return {'Region':{ 'region':this.region,
	 * 'positiveRegionsString':this.positiveRegionsString,
	 * 'negativeRegionsString':this.negativeRegionsString,
	 * 'positiveRegionIndices':this.positiveRegionIndices,
	 * 'negativeRegionIndices':this.negativeRegionIndices }} };
	 */
}
