package region;

import java.util.ArrayList;
import java.util.Arrays;

import utilsFE.MiscUtil;
/********************************************************************************
Region class/module

********************************************************************************/
public class Region {
	private String region = null;
	private ArrayList<Integer> positiveRegionIndices = null,
								negativeRegionIndices = null;
	private ArrayList<String> positiveRegions = null, negativeRegions = null;

	private ArrayList<String> regionLines = null;

	/*
	 * Define the Region
	 * 
	 * @region is the whole region as a string e.g. "Maeda Hanafi, ....."
	 * 
	 * @positiveRegions is the array of positive regions of type String e.g.
	 * ["M", "T"]
	 * 
	 * @positiveIndices is a list of indices in which the positive examples
	 * appear, e.g. [0, 2,...]
	 */
	public Region(String region, ArrayList<Integer> positiveRegionIndices,
			ArrayList<String> positiveRegions,
			ArrayList<Integer> negativeRegionIndices,
			ArrayList<String> negativeRegions) {
		this.region = region;
		this.positiveRegions = positiveRegions;
		this.negativeRegions = negativeRegions;
		this.positiveRegionIndices = positiveRegionIndices;
		this.negativeRegionIndices = negativeRegionIndices;

		// regionLines is region divided by lines
		this.regionLines = MiscUtil.splitAndRetainByNewline(this.region);
	}
	
	public Region(String region, Integer[] positiveRegionIndices,
			String[] positiveRegions,
			Integer[] negativeRegionIndices,
			String[] negativeRegions) {
		this.region = region;
		this.positiveRegions = new ArrayList<String>(Arrays.asList(positiveRegions)); 
		this.negativeRegions = new ArrayList<String>(Arrays.asList(negativeRegions)); 
		this.positiveRegionIndices = new ArrayList<Integer>(Arrays.asList(positiveRegionIndices)); 
		this.negativeRegionIndices = new ArrayList<Integer>(Arrays.asList(negativeRegionIndices));

		// regionLines is region divided by lines
		this.regionLines = MiscUtil.splitAndRetainByNewline(this.region);
	}

	public String getRegion() {
		return this.region;
	}

	// Returns an array of positive regions
	public ArrayList<String> getPositiveRegions() {
		return this.positiveRegions;
	}

	public ArrayList<String> getNegativeRegions() {
		return this.negativeRegions;
	}

	// Returns the number of positive regions
	public int getPositiveRegionsCount() {
		return this.positiveRegions.size();
	}

	// Returns an array of positive regions in string
	public ArrayList<Integer> getPositiveIndices() {
		return this.positiveRegionIndices;
	}

	public ArrayList<Integer> getNegativeIndices() {
		return this.positiveRegionIndices;
	}

	// Returns the index'th positive regions in string
	public String getPositiveRegionsAt(int index) {
		return this.positiveRegions.get(index);
	}

	/*
	 * Given index, return the positive index from positiveRegionIndices array
	 * So the return is the location of postive example number, index
	 */
	public int getPositiveIndexOf(int index) {
		return this.positiveRegionIndices.get(index);
	}

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
		return "Region(" + this.region + ", " + this.positiveRegionIndices
				+ ", " + this.positiveRegions + ", "
				+ this.negativeRegionIndices + ", " + this.negativeRegions
				+ ")";
	};

	/*
	 * Region.prototype.obj = function () {
	 * 
	 * return {'Region':{ 'region':this.region,
	 * 'positiveRegions':this.positiveRegions,
	 * 'negativeRegions':this.negativeRegions,
	 * 'positiveRegionIndices':this.positiveRegionIndices,
	 * 'negativeRegionIndices':this.negativeRegionIndices }} };
	 */
}
