package program;

import java.util.ArrayList;

import region.Text;

public interface Program {
	String debug = "";
	//Executes the program on each line of lines[]
	ArrayList<Text> executeLines(ArrayList<Text> lines);
		
	//Executes the program on the whole region
	ArrayList<Text> execute(Text region);
	
	


}
