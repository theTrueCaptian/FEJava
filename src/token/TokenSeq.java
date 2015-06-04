package token;

import java.util.List;
import java.lang.StringBuilder;
/********************************************************************************
//Define the tokenSeq constructor
//Holds an array of tokens
********************************************************************************/


public class TokenSeq {
	/* 
	 * Holds an array of tokens: e.g. [ '^', '[0-9]+', '^', '[\\s]+' ]
	 */
	private String[] tokens = null;
	
	public TokenSeq(String[] tokens) {
	    this.tokens = tokens;
	}
	
	public TokenSeq(List<String> listtokens) {
		this.tokens = new String[listtokens.size()];
		int i=0;
		for(String tok : listtokens){
			this.tokens[i]= tok;
			i++;
		}
	
	}
	
	public String getRegexString(){
		StringBuilder builder = new StringBuilder();

		for (String string : tokens) {
		    if (builder.length() > 0) {
		        builder.append(" ");
		    }
		    builder.append(string);
		}

		return builder.toString();
	}
	
	/*public getRegex  = function(){
	    return new RegExp(this.tokens.join(""));
	}
	TokenSeq.prototype.getRegexGlobal  = function(){
	    return new RegExp(this.tokens.join(""), "g");
	}*/
	public String[] getTokenSeq(){
	    return this.tokens;
	}
	public int getNumTok (){
	    return this.tokens.length;
	}

}
