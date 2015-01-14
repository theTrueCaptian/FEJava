package program;

import java.util.ArrayList;

import region.Text;
/********************************************************************************
A Pair program
********************************************************************************/

public class PairProgram implements Program{

	private Program startingPt, endingPt ;
	private String type, debug;
    
	//A and B are of type positions that extract a position given a region
	public PairProgram( Program A, Program B, String type) {
	    this.startingPt = A;
	    this.endingPt = B;
	    this.type = type;
	    this.debug = " Pair(Program A:"+ A.debug+", Program B:"+ B.debug+", type:"+this.type+")";
	};
	
	@Override
	public ArrayList<Text> executeLines(ArrayList<Text> lines) {
	    return null;

	}

	@Override
	public ArrayList<Text> execute(Text region) {
		if(this.type.equals( "pair") ){   //Then the previous program is a pair of positions (LinesMapSS)
	        // return this.PairPos(region, this);
	    }else if(this.type.equals( "prefix")){
	        return this.PairPrefixPos(region, this);
	    }else if(this.type.equals( "suffix")){
	        //return this.PairSuffixPos(region, this);
	    }

	    return null;
	}
	

	/* Execution for a Pair program learned in PairLearnPrefixPos()
	 * @x is the string/array of strings to execute PairPrefixPos on
	 * @prog is the pair program
	 */
	public ArrayList<Text> PairPrefixPos(Text  x, PairProgram prog){
		PairProgram endPtProg = (PairProgram) prog.endingPt;
	    //The endpoint can either be LS or PS:
	    //var resultEndpoint = Position.executePosPS(endPtProg, x);
	
	    /*if(Util.isEmpty(resultEndpoint))
	        return null*/
	
	    ArrayList<Text> results = new ArrayList<Text>();
	
	    /*for(var i=0; i<resultEndpoint.length; i++) {
	        var elem = resultEndpoint[i]
	
	        var string = elem.string
	        if(string != undefined) {
	            var endingPoint = _.max(elem.position)
	            var startpoint = Position.getPosK(prog.startingPt, string)
	            if (_.isArray(startpoint)) startpoint = _.min(startpoint)
	
	            results.push(Pair(string, startpoint, endingPoint))
	        }
	    }*/
	
	    return results;
	}
	
	public ArrayList<Text> PairPrefixPos(ArrayList<Text>  x, PairProgram prog){
		return null;
	}

/* Execution for a Pair program learned in PairLearnSuffixPos()
 * @x is the string/array of strings to execute PairSuffixPos on
 * @pairSuffixProgram is the pair program
 */
/*PairProgram.prototype.PairSuffixPos = function(  x, prog){
    //The startpoint can either be LS or PS:
    var stPtProg = prog.startingPt
    var resultStpoint = Position.executePosPS(stPtProg, x)

    if(Util.isEmpty(resultStpoint))
        return null

    var results = []

    for(var  i=0; i<resultStpoint.length; i++) {
        var elem = resultStpoint[i]

        var string = elem.string
        if(string != undefined) {
            var startpoint = _.min(elem.position)
            var endingPoint = Position.getPosK(prog.endingPt, string)
            if (_.isArray(endingPoint)) endingPoint = _.max(endingPoint)

            results.push(Pair(string, startpoint, endingPoint))
        }
    }

    return results

}


/* Execution of a pair program on a String/Array x
 * @x is the string/Array to execute aPairProgram
 */
/*PairProgram.prototype.PairPos = function(x, aPairProgram){
    var stPtProg = aPairProgram.startingPt
    var endPtProg = aPairProgram.endingPt
    var results = []

    var endingPoint
    var startingPoint
    var substr = ""
   if(x instanceof  Array){
         x.forEach(function (line) {
            endingPoint = Position.getPosK(endPtProg, line )
            if(endingPoint instanceof  Array) endingPoint = _.max(endingPoint)

             startingPoint = Position.getPosK(stPtProg, line )
             if(startingPoint instanceof  Array) startingPoint = _.min(startingPoint)

             substr = Pair(line, startingPoint, endingPoint)
             results.push(substr)
        })

    }else{ //In this case it is pure string, then simple pass inRegion to Pair
        endingPoint = Position.getPosK(endPtProg, x )
        if(endingPoint instanceof  Array) endingPoint = _.max(endingPoint)

        startingPoint = Position.getPosK(stPtProg, x )
        if(startingPoint instanceof  Array) startingPoint = _.min(startingPoint)

        substr = Pair(x, startingPoint, endingPoint)
        results.push(substr)
    }

    return results
}
*/

public String  Pair(String region, int stPt, int enPt){
    return region.substring(stPt, enPt);
}

/*
Pair.prototype.obj = function (){
    var startingPt = this.startingPt.obj()
    var endingPt = this.endingPt.obj()

    return {
        "Pair":{
            'startingPt':startingPt,
            'endingPt':endingPt,
            'type':this.type,
            'program_type':"Pair"
        }
    }
}*/

}
