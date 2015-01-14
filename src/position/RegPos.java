package position;

import token.TokenSeq;

public class RegPos implements Pos{
	
	private int k = -1;
	private String debug = "";
	private TokenSeq r1, r2;
	
	public RegPos(int k, TokenSeq r1, TokenSeq r2){
		this.k = k;
		//Contents of r comes straight from PositionLearn. 
		//r1 is an array of TokenSeqArr (IParts)
	    this.r1 = r1;
	    this.r2 = r2;
	    this.debug = "k:"+k +", r1:["+getFirstR1TokenSeq(r1).getRegexString()+
	    		"], r2:["+getFirstR2TokenSeq(r2).getRegexString()+"]";

	}
	
	public int getK(){
		return k;
	}
	public TokenSeq getR1(){
		return r1;
	}
	public TokenSeq getR2(){
		return r2;
	}
	
	public  TokenSeq getFirstR1TokenSeq(TokenSeq r1){
	    return null;//getFirstR1TokenSeqLocal(this.r1);
	};

	public  TokenSeq getFirstR2TokenSeq (TokenSeq r2){
	    return null;//getFirstR2TokenSeqLocal(this.r2)
	};
	
	

}
