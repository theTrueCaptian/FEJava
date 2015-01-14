import java.util.ArrayList;

import position.Pos;
import program.PairProgram;
import program.Program;
import region.Region;
import region.Text;
import utilsFE.MiscUtil;


public class PairLearn {
	//PairLearnPos list of learned programs, because it takes 10 seconds ot learn it (See BLSLearn.js)
	//var learnedPairLearnPos = []
	//var limit = 20

	//Limit on the number of total positions learned
	//var positionLimit = 8;

	//Learns a pair of positions that define the positive regions
	public static ArrayList<PairProgram> PairLearnPos(Region inRegion){

	    MiscUtil.logTime(3, "START: PairLearnPos()");

	    ArrayList<Program> possibleEndPositions = new ArrayList<Program>();
	    ArrayList<Program> possibleStartingPositions =new ArrayList<Program>();

	    /*var foundIndex = _.findIndex(learnedPairLearnPos, function(elem){ return MiscUtil.isRegionEqual(inRegion, elem.inRegion)})
	    if(foundIndex!=-1){

	        possibleEndPositions = learnedPairLearnPos[foundIndex].possibleEndPositions
	        possibleStartingPositions = learnedPairLearnPos[foundIndex].possibleStartingPositions

	    }else{*/
	        //line is a positive example we want to learn prefixes from
	        Text line = inRegion.getPositiveRegionsAt(0);
	        Text region = inRegion.getRegion();

	        int kst = region.indexOf(line);
	        int kend = kst + line.length();

	        MiscUtil.logTime(100, "START: PairLearnPos() PositionLearn");
	       
	        ArrayList<Pos> possiblePositions = new ArrayList<Pos>();
	        for(int k = kst; k<kend; k++) {
	            //possiblePositions = possiblePositions.concat(Position.PositionLearn(region, k));
	        }
	        return null;

	        /*MiscUtil.logTime(100, "END: PairLearnPos() PositionLearn");

	        MiscUtil.logTime(101, "START: PairLearnPos() Filter")
	        //Filter
	        possiblePositions.forEach(function(apos){
	            var execPos = Position.executePos(apos, region)

	            /* Check for prefixes
	             * We make sure that execPos prefix is exactly the same as the positive example (line)
	             */
	          /*  var findIndex = execPos.indexOf( line)

	            if(findIndex==0){   //A possible valid starting position, so add it to possibleStartingPositions[]
	                possibleStartingPositions.push(apos)
	            }

	            /* Check for suffix
	             * We make sure that execPos suffix is exactly the same as the positive example (line)
	             */
	            /*var compareIndex = execPos.length-line.length
	            if(findIndex==compareIndex && findIndex!=-1){
	                possibleEndPositions.push(apos)
	            }


	        })
	        MiscUtil.logTime(101, "END: PairLearnPos() Filter")

	        //Limit the number of positions for now
	        if(possibleStartingPositions.length > positionLimit/2){
	            possibleStartingPositions = possibleStartingPositions.slice(0, positionLimit/2)
	            //possibleStartingPositions = possibleStartingPositions.slice(0, positionLimit/4).concat(
	            //    possibleStartingPositions.slice(possibleStartingPositions.length - positionLimit/4, possibleStartingPositions.length))
	        }
	        if(possibleEndPositions.length > positionLimit/2){
	            possibleEndPositions = possibleEndPositions.slice(0, positionLimit/2)
	            //possibleEndPositions = possibleEndPositions.slice(0, positionLimit/4).concat(
	            //    possibleEndPositions.slice(possibleEndPositions.length - positionLimit/4, possibleEndPositions.length))
	        }

	        MiscUtil.logLearn("PairLearnPos() start pos filtered length:" + possibleStartingPositions.length)
	        MiscUtil.logLearn("PairLearnPos() end pos filtered length:" + possibleEndPositions.length)

	        //Add the learned programs to learnedPairLearnPos[]
	        learnedPairLearnPos.push({'inRegion':inRegion, 'possibleEndPositions':possibleEndPositions
	                                  ,  'possibleStartingPositions':possibleStartingPositions})

	        //Make sure learnedPairLearnPos doesn't exceed limits, otherwise delete the first item in the list
	        if(learnedPairLearnPos.length>limit){
	            learnedPairLearnPos.splice(0, 1)
	        }
	    //}

	    var programs = []
	    //If B or A is null set, then we return empty
	    if(possibleStartingPositions.length==0 || possibleEndPositions.length==0) {
	        MiscUtil.logTime(3, "END: PairLearnPos()")
	        return []
	    }else{
	        //Otherwise crossproduct the two and add them to programs[]
	        possibleStartingPositions.forEach(function(programA){
	            possibleEndPositions.forEach(function(programB){
	                var candidateProgram = new PairProgram(programA, programB, 'pair')
	                var result = candidateProgram.execute(inRegion.getRegion())
	                //Add it only if the execution of it is valid
	                if(result) {
	                    programs.push(candidateProgram)
	                }
	            })
	        })

	    }

	    MiscUtil.logTime(3, "END: PairLearnPos()")

	    MiscUtil.logLearn("PairLearnPos() time:" + MiscUtil.getTimeDifference(3))
	    MiscUtil.logLearn("PairLearnPos() length:" + programs.length)
	    return programs;*/
	        
	}

	//The N2 non-terminal calls only a PairLearn for inRegion to learn a positive examples
	public static ArrayList<PairProgram>  PairLearnRegionPos(Region inRegion){
		ArrayList<PairProgram> programs = PairLearnPos(inRegion);
	    //To test we can run programs on inRegion
	    for(Program aPair: programs){ 
	        MiscUtil.logLearn(aPair.debug);
	        MiscUtil.logLearn(aPair.execute(inRegion.getRegion()).toString());
	    }

	    return programs;
	}

}
