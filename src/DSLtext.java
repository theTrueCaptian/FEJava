import java.util.ArrayList;
import java.util.List;

import region.*;
import learn.*;
import program.*;
import utilsFE.*;


/*
 * Maeda Hanafi
 * FlashExtract Java version
 */

public class DSLtext {
	private GlobalDocument document;

	// this program takes in arguments in the following format: <URL> <a search
	// string>
	public static void main(String[] arg) {

		new DSLtext("Test");
	}

	/*
	 * @type can be either "Test" or "Run", "Run" being the one with
	 * user-defined parameters
	 */
	public DSLtext(String type) {
		if (type.equals("Test")) {
			this.document = new GlobalDocument(
					"DATASET 200:\nproteins (01)\ncalories 20\nproteins (80)\n"
							+ "DATASET 100:\nproteins (90)\ncalories 90\nproteins (600)\n"
							+ "DATASET 400:\nproteins (102)\ncalories 432\nproteins (324)\n");

			//String regions (ancestors)
			Text blueregion1 = new Text("proteins (01)\ncalories 20\nproteins (80)\n", 0);
			Text blueregion2 = new Text("proteins (90)\ncalories 90\nproteins (600)\n",0);
			Text blueregion3 = new Text("proteins (102)\ncalories 432\nproteins (324)\n", 0);
			
			//A positive example
			String pos1 = "calories 20\n";
			
			Region region1 = new Region(
						blueregion1, 
					new Integer[] { 
						blueregion1.getText().indexOf(pos1)
					}, new String[] { 
							pos1
					}, null, null
			); 
			Region region2 = new Region(
					blueregion2, 
				new Integer[] {}, new String[] {}, null, null); 
			Region region3 = new Region(
					blueregion3, 
				new Integer[] {}, new String[] {}, null, null); 
			
			List<Region> regionList = new ArrayList<Region>();
			regionList.add(region1);
			regionList.add(region2);
			regionList.add(region3);
			
			SetRegion input = new SetRegion(regionList, null, null);
			
			/*String blueregion1 = "DATASET 200:\nproteins (01)\ncalories 20\nproteins (80)\n"
					+ "DATASET 100:\nproteins (90)\ncalories 90\nproteins (600)\n"
					+ "DATASET 400:\nproteins (102)\ncalories 432\nproteins (324)\n";

			// Here are the positive examples to extract from the blues:
			String pos1 = "proteins (600)\n";
			String pos2 = "proteins (102)\n";
			//String pos3 = "proteins (01)\n";

			//Here is a region
			Region region1 = new Region(
					blueregion1, new Integer[] { 
							blueregion1.indexOf(pos1),
							blueregion1.indexOf(pos2)
					}, new String[] { 
							pos1,
							pos2 
					}, null, null
			); 
			
			// We form the array of regions to be sent to Merge.Learn
			// Algorithm 2, line 6: The regions in the array must contain
			// positive examples, or else those regions that don't will not be
			// learned upon
			List<Region> regionList = new ArrayList<Region>();
			regionList.add(region1);
			SetRegion input = new SetRegion(regionList, null, null);
			
			/*SetRegion input = new SetRegion(new Region[] 
					{ new Region(
							blueregion1, new Integer[] { blueregion1.indexOf(pos1),
							blueregion1.indexOf(pos2),
							blueregion1.indexOf(pos3) }, new String[] { pos1,
							pos2, pos3 }, null, null) 
					}, null, null);*/

			run(input, document);
		}

	}

	// Runs the learn
	private void run(SetRegion input, GlobalDocument document) {
		MiscUtil.logLearn("Learning Started!");
		// var programs = []
		if (isCallN2Learn(input)) {
			
			ArrayList<?> learnedProgs = new N2Learn().SynthesizeRegionProg(input);
			 /* MiscUtil.logLearn("Learning Ended!");
			 * 
			 * MiscUtil.logLearn("Done Learning!");
			 * 
			 * if(!Util.isEmpty(learnedProgs)) { learnedProgs.forEach(function
			 * (prog) { Util.logLearn(prog.debug) Util.logLearn(input.run(prog,
			 * false)) })
			 * 
			 * callback(input.run(learnedProgs[0], false))
			 * 
			 * 
			 * } Util.writeLogTimeToFile() Util.writeLogLearnedToFile()
			 * process.exit(1);
			 */

		} else {
			Program[] learnedProgs = new N1Learn().MergeLearn(document, input);
			MiscUtil.logLearn("Learning Ended!");

			MiscUtil.logLearn("Done Learning!");

			/*if (learnedProgs.length > 0) {
				for (Program prog : learnedProgs) {
					MiscUtil.logLearn(Program.debug);
					MiscUtil.logLearn(prog.execute(document.getDocument()).toString());

				}

				// callback(learnedProgs[0].execute(document.getDocument()))
			}*/

			// Util.writeLogTimeToFile()
			// Util.writeLogLearnedToFile()
			// process.exit(1);

		}

	}

	/*
	 * Given a SetRegion, determine whether to call N2 Learn or N1 Learn Given a
	 * SetRegion, N2Learn learn programs only if the ancestor is a structure
	 * ancestor of the field we are extracting i.e. there is no sequence in
	 * between the ancestor and the field, thus we only extract one thing from
	 * the ancestor
	 */
	private boolean isCallN2Learn(SetRegion inSetRegion) {

		for (int i = 0; i < inSetRegion.getRegionCount(); i++) {
			Region aRegion = inSetRegion.getRegionAt(i);
			// Only learn if there is only one positive example to learn from in
			// aRegion
			if (aRegion.getPositiveRegionsCount() == 1) {
				MiscUtil.logLearn("Call N2! Learn Pair Programs");

				return true;
			}
		}
		MiscUtil.logLearn("Call N1! Learn Merge Programs");

		return false;
	}

}
