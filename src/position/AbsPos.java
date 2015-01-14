package position;

public class AbsPos implements Pos{
	private int k = -1;
	private String debug = "";
	
	public AbsPos(int k){
		this.k = k;
	    this.debug = "k:"+k;
	}
	
	public int getK(){
		return k;
	}
	
}
