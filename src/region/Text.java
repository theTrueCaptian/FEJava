package region;

import utilsFE.MiscUtil;

/*
 * Text class contains a text from the GlobalDocument and
 * its integer position in that GlobalDocument.
 * 
 * This class is basic block of a Region
 */
public class Text {
	//private  GlobalDocument doc;
	//private String docString;
	private String text;
	private int position;
	
	
	public Text( String text, int position){
		//this.doc = doc;
		this.text = text;
		this.position = position;
		//this.docString = doc.getDocument();
		
	}
	
	/*public Text( String text, int position){
		//this.docString = docString;
		this.text = text;
		this.position = position;
		
	}
	
	public GlobalDocument getDoc(){
		return this.doc;
	}
	
	public String getDocString(){
		return this.docString;
	}*/
	public String getText(){
		return text;
	}
	
	public int getPosition(){
		return position;
	}
	
	public String toString(){
		return MiscUtil.escape("Text("+this.text+", "+this.position+")");
	}
	
	//Checks if the incoming Text is equal to this Text
	public boolean isEqual(Text comp){
		if(comp.getText().equals(this.text) && comp.getPosition()==this.position){
			return true;
		}
		return false;
	}
	
	//Check if incoming Text is within this Text
	public boolean isSubstring(Text comp){
		return this.text.indexOf(comp.getText())!=-1 || 
				(comp.getPosition()>=this.position &&
				comp.getPosition()<=(this.position+this.text.length()));
	}
}
