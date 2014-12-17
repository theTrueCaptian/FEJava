package utilsFE;
import java.util.ArrayList;
import java.util.Arrays;  


public class MiscUtil {
	private boolean isConsoleLog = true;

	//Given a string, return an ArrayList<String> split by newline, and making sure to retain the newline
	public static ArrayList<String> splitAndRetainByNewline(String string) {
	    ArrayList<String> newString = new ArrayList(Arrays.asList(string.split("\n")));

	    //Drop the last newline if it exists
	    if(newString.get(newString.size()-1).equals("")){ 
	    	newString.remove(newString.size()-1);
	    }

	    //Retain the newline in the end of each element in the region
	    for(String element : newString){
	    	element = element+ "\n";
	    }
	    
	    return newString;

	}
	
	/********************************************************************************
	  Functions for logging run time. At some point writeLogTimeToFile()
	  must be called to see the results in a file
	 ********************************************************************************/
	ArrayList<LoggedTime> loggedTimes = new ArrayList<LoggedTime>();
	boolean isLogTime = true;

	//@ID indicates the loggedTime belongs to a particular task and should be grouped
	public void logTime(int ID, String functionString){

	    if(isLogTime) {
	        long date = System.currentTimeMillis();
	        if(isConsoleLog) {
	            console.log((date.getTime() / 1000) + "\t" + jsStringEscape(functionString))
	        }
	        loggedTimes.push({"ID":ID, "message":jsStringEscape(functionString), "time":(date.getTime() / 1000)})

	    }
	}

	//Given the ID, take the difference of the last two logs to get the time difference
	public void getTimeDifference(ID){
	    var count = 0;  //Flag to indicate which time appear before the other
	    var difference = 0;

	    for(var i=loggedTimes.length-1; i>=0; i--){
	        if(loggedTimes[i].ID==ID && count<2){
	            if(count == 0){ //Ending time
	                difference = loggedTimes[i].time
	            }else if(count==1){ //Starting time
	                difference = difference - loggedTimes[i].time

	            }
	            count ++;
	            if(count == 2){
	                return difference
	            }
	        }
	    }

	}

	private void organizeByID(){
	    var organized = []

	    for(var i=0; i<loggedTimes.length; i++){
	        var item = loggedTimes[i]
	        var id = loggedTimes[i].ID

	        //Find the id in organized
	        var find = _.find(organized, function(elem){return _.isEqual(elem.ID,id)})

	        if(find!=undefined){   //If the id is found in organized[], add the item to the appropriate place

	            //Find the id in organized[]
	            for(var j=0; j<organized.length; j++){
	                if(_.isEqual(organized[j].ID, id)){
	                    //delete item.id
	                    item = DeleteKey(item, ['ID'])
	                    organized[j].items.push(item)
	                    break;
	                }
	            }

	        }else{      //If the id could not be found in organized, then create a new item in organized[]
	            item = DeleteKey(item, ['result'])
	            organized.push({'ID': id, 'items':[item]})
	        }
	    }
	    return organized
	}
	private void formSummary(){
	    var organized = organizeByID()
	    var summary = ""

	    for(var i=0; i<organized.length; i++){
	        var items = organized[i].items
	        var ID = organized[i].ID
	        var message = items[0].message

	        summary = summary +"ID: "+ID+" Message:"+ message + " "+(parseInt(items.length)/2)+" - Duration (seconds)"+"\n"
	        for(var j=0; j<items.length-1; j=j+2){
	            var time1 = items[j].time
	            var time2 = items[j+1].time
	            var duration = parseInt(time2 - time1)

	            if(duration>0) {
	                summary = summary + " " + duration + "\n"
	            }
	        }
	    }
	    FileIO.writeToFile("logtimessummary.txt",  (summary) )

	}

	public void writeLogTimeToFile(){
	    formSummary()
	    FileIO.writeToFile("logtimes.txt", FileIO.toString(loggedTimes) )
	}
	private class LoggedTime{
		int ID;
		String message;
		long time;
	}
/********************************************************************************
 Functions for logging run time
 ********************************************************************************/
var isLogLearn = true;
var loggedLearnedMessages = []
public void logLearn(message){

    if(isLogLearn) {
        if(isConsoleLog) {
            console.log(jsStringEscape(message))
        }
        loggedLearnedMessages.push(jsStringEscape(message))

    }
}


public void writeLogLearnedToFile(){
    FileIO.writeToFile("loglearn.txt", FileIO.toString(loggedLearnedMessages ))
}
}
