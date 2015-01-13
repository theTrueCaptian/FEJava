package region;

/*
 * Text class contains a GlobalDocument, a text from the GlobalDocument, and
 * its integer position in that GlobalDocument.
 * 
 * This class is used when doing operations on Regions
 */
public class Text {
	private  GlobalDocument doc;
	private String docString;
	private String text;
	private int position;
	
	
	public Text(GlobalDocument doc, String text, int position){
		this.doc = doc;
		this.text = text;
		this.position = position;
		this.docString = doc.getDocument();
		
	}
	
	public Text(String docString, String text, int position){
		this.docString = docString;
		this.text = text;
		this.position = position;
		
	}
	
	public GlobalDocument getDoc(){
		return this.doc;
	}
	
	public String getDocString(){
		return this.docString;
	}
	public String getText(){
		return text;
	}
	
	public int position(){
		return position;
	}
	
	public String toString(){
		return "{"+this.text+", "+this.position+"}";
	}
}
