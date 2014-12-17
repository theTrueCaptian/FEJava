import region.*;
import learn.*;
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
	 * @type can be either "Test" or "Run", "Run" being the one with user-defined parameters
	 */
	public DSLtext(String type){
		if(type.equals("Test")){
			this.document = new GlobalDocument("DATASET 200:\nproteins (01)\ncalories 20\nproteins (80)\n"
				    + "DATASET 100:\nproteins (90)\ncalories 90\nproteins (600)\n"
				    + "DATASET 400:\nproteins (102)\ncalories 432\nproteins (324)\n");
			
			String blueregion1 ="DATASET 200:\nproteins (01)\ncalories 20\nproteins (80)\n" +
			        "DATASET 100:\nproteins (90)\ncalories 90\nproteins (600)\n"
			        + "DATASET 400:\nproteins (102)\ncalories 432\nproteins (324)\n";

			    //Here are the positive examples to extract from the blues:
			    String pos1 ="proteins (600)\n";
			    String pos2 ="proteins (102)\n";
			    String pos3 ="proteins (01)\n";

			    //We form the array of regions to be sent to Merge.Learn
			    //Algorithm 2, line 6: The regions in the array must contain positive examples, or else those regions that don't will not be learned upon
			    SetRegion input = new SetRegion(new Region[]{
			    		new Region(
			    				blueregion1, 
			    				new Integer[] {blueregion1.indexOf(pos1), blueregion1.indexOf(pos2), blueregion1.indexOf(pos3)},
			    				new String[] {pos1, pos2, pos3},
			    				null, null
			    		)
			    }, null, null );


			    run(input, document);
		}
		
		
	}
	
	private void run(SetRegion input, GlobalDocument document){
		
	}


}
