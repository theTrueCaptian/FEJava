package utilsFE;
import java.util.ArrayList;
import java.util.Arrays;  

public class MiscUtil {
	
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
}
