import java.util.ArrayList;
import java.util.Collection;

import program.PairProgram;
import program.Program;
import region.Region;
import region.SetRegion;
import utilsFE.MiscUtil;


/**
 * Learns Pair
 */
public class N2Learn {

	public N2Learn(){
		
	}
	
	/********************************************************************************
	    SynthesizeRegionProg
	 ********************************************************************************/
	/* Given a SetRegion, learn programs only if the ancestor is a structure ancestor of the field we are extracting
	 * i.e. there is no sequence in between the ancestor and the field, thus we only extract one thing  from the ancestor
	 */
	public ArrayList<PairProgram> SynthesizeRegionProg(SetRegion setregion) {
	    MiscUtil.logTime(35, "START: SynthesizeRegionProg");

	    ArrayList<PairProgram> programs = new ArrayList<PairProgram>();
	    for(int i=0; i<setregion.getRegionCount(); i++){
	        Region aRegion = setregion.getRegionAt(i);
	        //Only learn if there is only one positive example to learn from in aRegion
	        if(aRegion.getPositiveRegionsCount()==1) {
	            //Learn a pair of positions to extract the positive examples from inRegion
	             programs.addAll( (ArrayList<PairProgram>) PairLearn.PairLearnRegionPos(aRegion));
	            MiscUtil.logLearn("N2-Pair:" + programs.size());
	            MiscUtil.logLearn("-----");
	        }
	    }

	    MiscUtil.logTime(35, "END: SynthesizeRegionProg");

	    MiscUtil.logLearn("SynthesizeRegionProg() time:" + MiscUtil.getTimeDifference(35));
	    MiscUtil.logLearn("SynthesizeRegionProg() length:" + programs.size());

	    MiscUtil.logLearn("-----");

	    //callback(programs)
	    return programs;

	}

}
