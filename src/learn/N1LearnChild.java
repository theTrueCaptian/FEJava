package learn;

import java.util.ArrayList;

import program.Program;
import region.GlobalDocument;
import region.SetRegion;
import utilsFE.MiscUtil;
/**
 * This is called by an independent child process, in order to speed up the learning process
 * This is a fork to learn a subset of positive examples
 */
public class N1LearnChild implements Runnable {

	private SetRegion setRegion;
	private GlobalDocument document;
	private int ID;
	
	/*public N1LearnChild( ) {
		 
	}

	public void run() {
		task( );
	}

	public ArrayList<String> task( ) {
		// each content in arraylist is a line of string from the html file
		//ArrayList<String> fileContent = grabFile(URL);
		// search all occurrences of that search string
		System.out.println("Parsing " );
		//ArrayList<String> urlList = parseFile(URL, fileContent, search);
		return null;//urlList;
	}*/
	
	public N1LearnChild(SetRegion setRegion, GlobalDocument document, int ID) {
		this.setRegion = setRegion;
		this.document = document;
		this.ID = ID;
	    
	}
	public void run(){
		//Program that holds the final program, a set of functions and associated params, {func:predFunction, 'params':[]}
	    ArrayList<Program> prog = new ArrayList<Program>();

	    MiscUtil.logTime(37, "Thread "+ID+": START: SynthesizeSeqRegionProg");

	

	    //Call the method that learns SS, which would spawn the learn for PS, LS, and BLS
	    /*var SSLinesMap = SSLearn.LinesMapLearnSS(SetRegion, document)

	    if(!Util.isEmpty(SSLinesMap)){
	        prog = prog.concat(SSLinesMap)
	        console.log("Thread "+ID+": SS-LinesMap:"+SSLinesMap.length)
	    }else{
	        console.log("Thread "+ID+": SS-LinesMap:0")
	    }
	    console.log("Thread "+ID+": -----")

	    //Call EndSeqMapLearnSS
	    var SSEndSeqMap = SSLearn.EndSeqMapLearnSS(SetRegion, document)
	    if(!Util.isEmpty(SSEndSeqMap)){
	        prog = prog.concat(SSEndSeqMap)
	        console.log("Thread "+ID+": SS-EndSeqMap:" + SSEndSeqMap.length)
	    }else {
	        console.log("Thread "+ID+": SS-EndSeqMap:0")
	    }
	    console.log("Thread "+ID+": -----")

	    //Call StartSeqMapLearnSS
	    var SSStartSeqMap = SSLearn.StartSeqMapLearnSS(SetRegion, document)
	    if(!Util.isEmpty(SSStartSeqMap)){
	        prog = prog.concat(SSStartSeqMap)
	        console.log("Thread "+ID+": SS-StartSeqMap:" + SSStartSeqMap.length)
	    }else {
	        console.log("Thread "+ID+": SS-StartSeqMap:0")
	    }
	    console.log("Thread "+ID+": -----")

	    Util.logTime(37, "Thread "+ID+": END: SynthesizeSeqRegionProg")

	    console.log("Thread "+ID+": SynthesizeSeqRegionProg() time:" + Util.getTimeDifference(37))
	    console.log("Thread "+ID+": SynthesizeSeqRegionProg() length:" + prog.length)

	    console.log("Thread "+ID+": -----")

	    console.log("Thread "+ID+": prog length:"+prog.length)
	    SetRegion.setProgramsN1(prog)
	    sendSetWithProgs(SetRegion)
*/
	    //return prog;
	}

	public int getID(){
		return this.ID;
	}
	
		
}
