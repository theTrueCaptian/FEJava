package utilsFE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import region.Region;
import region.Text;

public class MiscUtil {
	
	static private boolean isConsoleLog = true;

	//Given a set of Texts, convert each Text into a Region
	//Returns a list of regions
	public static ArrayList<Region> createRegion(Set<Text> setText){
		ArrayList<Region> listRegion = new ArrayList<Region>();
		System.out.println("Create Region");
		System.out.println(setText);
		
		Iterator itr = setText.iterator();
		while(itr.hasNext()) {
			Object textElem = itr.next();
			
			//Create Region object from element
			//Region regionElement = convertToTextObj(textElem);
			
			//listRegion.add(regionElement);
		}
		return listRegion;
	}
	
	//@inText is the text to convert to Region
	//public static Region convertToTextObj(Text inText){
	//	return new Region();
	//}
	
	// Given a string, return an ArrayList<String> split by newline, and making
	// sure to retain the newline
	public static ArrayList<String> splitAndRetainByNewline(String string) {
		ArrayList<String> newString = new ArrayList<String>(
				Arrays.asList(string.split("\n")));

		// Drop the last newline if it exists
		if (newString.get(newString.size() - 1).equals("")) {
			newString.remove(newString.size() - 1);
		}

		// Retain the newline in the end of each element in the region
		for (String element : newString) {
			element = element + "\n";
		}

		return newString;

	}

	/********************************************************************************
	 * Functions for logging run time. At some point writeLogTimeToFile() must
	 * be called to see the results in a file
	 ********************************************************************************/
	static ArrayList<LoggedTime> loggedTimes = new ArrayList<LoggedTime>();
	static boolean isLogTime = true;

	// @ID indicates the loggedTime belongs to a particular task and should be
	// grouped
	public static void logTime(int ID, String functionString) {

		if (isLogTime) {
			long date = System.currentTimeMillis() / 1000;
			if (isConsoleLog) {
				 System.out.println(date + "\t" +
						 /*jsStringEscape*/(functionString));
			}
			// loggedTimes.add(new LoggedTime(ID,
			// jsStringEscape(functionString), date));

		}
	}

	// Given the ID, take the difference of the last two logs to get the time
	// difference
	public static long getTimeDifference(int ID) {
		int count = 0; // Flag to indicate which time appear before the other
		long difference = 0;

		for (int i = loggedTimes.size() - 1; i >= 0; i--) {
			if (loggedTimes.get(i).ID == ID && count < 2) {
				if (count == 0) { // Ending time
					difference = loggedTimes.get(i).time;
				} else if (count == 1) { // Starting time
					difference = difference - loggedTimes.get(i).time;

				}
				count++;
				if (count == 2) {
					return difference;
				}
			}
		}

		return -1;

	}

	private static ArrayList<LoggedTime> organizeByID() {
		ArrayList<LoggedTime> organized = new ArrayList<LoggedTime>();

		for (int i = 0; i < loggedTimes.size(); i++) {
			LoggedTime item = loggedTimes.get(i);
			int id = item.ID;

			// Find the id in organized
			/*
			 * int find = _.find(organized, function(elem){return
			 * _.isEqual(elem.ID,id)})
			 * 
			 * if(find!=undefined){ //If the id is found in organized[], add the
			 * item to the appropriate place
			 * 
			 * //Find the id in organized[] for(var j=0; j<organized.length;
			 * j++){ if(_.isEqual(organized[j].ID, id)){ //delete item.id item =
			 * DeleteKey(item, ['ID']) organized[j].items.push(item) break; } }
			 * 
			 * }else{ //If the id could not be found in organized, then create a
			 * new item in organized[] item = DeleteKey(item, ['result'])
			 * organized.push({'ID': id, 'items':[item]}) }
			 */
		}
		return organized;
	}

	private static void formSummary(){
		ArrayList<LoggedTime> organized = organizeByID();
	    String summary = "";

	    /*for(int i=0; i<organized.length; i++){
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
	    }*/
	    //FileIO.writeToFile("logtimessummary.txt",  (summary) )

	}

	public static void writeLogTimeToFile() {
		formSummary();
		// FileIO.writeToFile("logtimes.txt", FileIO.toString(loggedTimes) )
	}

	private class LoggedTime {
		int ID;
		String message;
		long time;
	}

	/********************************************************************************
	 * Functions for logging run time
	 ********************************************************************************/
	static boolean isLogLearn = true;
	static ArrayList<String> loggedLearnedMessages = new ArrayList<String>();

	public static void logLearn(String message) {

		if (isLogLearn) {
			if (isConsoleLog) {
				System.out.println(/*jsStringEscape*/(message));
			}
			loggedLearnedMessages.add(/* jsStringEscape */(message));

		}
	}

	public static void writeLogLearnedToFile() {
		// FileIO.writeToFile("loglearn.txt",
		// FileIO.toString(loggedLearnedMessages ))
	}
}
