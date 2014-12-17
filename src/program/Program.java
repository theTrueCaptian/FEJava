package program;

import java.util.ArrayList;

public interface Program {
	//Executes the program on each line of lines[]
	ArrayList<String> executeLines(ArrayList<String> lines);
	//Executes the program on the whole region
	ArrayList<String> execute(String region);


}
