package token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/********************************************************************************
	Regex/regex Generator Classes
********************************************************************************/

public class Regex {
	private List<TokenSeq> regex = new ArrayList<TokenSeq>();
	//Format of character classes: C+ or [^C]+ or special token
	//Character classes: Numeric digits, alphabet both lower and upper, lower case, upper case, accented alphabet, alphanumeric, whitespace character, all character 
	//private String[] incCharCharacter = {"[0-9]+", "[a-zA-Z]+", "[a-z]+", "[A-Z]+", "[\\u00C0-\\u00FF]+", "[0-9a-zA-Z]+", "[\\s]+" };
	//private String[] notCharCharacter = {"[^0-9]+", "[^a-zA-Z]+", "[^a-z]+", "[^A-Z]+", "[^\\u00C0-\\u00FF]+", "[^0-9a-zA-Z]+", "[^\\s]+" };
	//Special character classes
	//private String[] specialChar = {"^", "$", "-", "\\.", ";", ":", ",", "\\\\", "\\/", "{", "}", "\\(", "\\)", "\\]", "\\[" };
	
	//Combine all arrays holding characters:
	private String[] allTokens = {"[0-9]+", "[a-zA-Z]+", "[a-z]+", "[A-Z]+", "[\\u00C0-\\u00FF]+", "[0-9a-zA-Z]+", "[\\s]+" ,
			"[^0-9]+", "[^a-zA-Z]+", "[^a-z]+", "[^A-Z]+", "[^\\u00C0-\\u00FF]+", "[^0-9a-zA-Z]+", "[^\\s]+",
			"^", "$", "-", "\\.", ";", ":", ",", "\\\\", "\\/", "{", "}", "\\(", "\\)", "\\]", "\\[" };
	
	public Regex(){
		generateRegex();
	}
	
	public List<TokenSeq> getRegex(){
		return this.regex;
	}
	private void generateRegex(){
		List<String> f = Lists.newArrayList( allTokens);
		List<List<String>> lregex = new ArrayList<List<String>>();
		lregex.addAll( processSubsets( f, 1 ));
		lregex.addAll( processSubsets( f, 2 ));
		lregex.addAll( processSubsets( f, 3 ));
		/*GeneratePermutations g = new GeneratePermutations();
		ArrayList<String> permutations = g.generatePermutations(allTokens);
*/
        for ( List<String> s : lregex)
        {
            this.regex.add(new TokenSeq(s));
        }
	}
	
	public List<List<String>> processSubsets( List<String> set, int k ) {
	    if ( k > set.size() ) {
	      k = set.size();
	    }
	    List<List<String>> result = Lists.newArrayList();
	    List<String> subset = Lists.newArrayListWithCapacity( k );
	    for ( int i = 0; i < k; i++ ) {
	      subset.add( null );
	    }
	    return processLargerSubsets( result, set, subset, 0, 0 );
	  }

	  private List<List<String>> processLargerSubsets( List<List<String>> result, List<String> set, List<String> subset, int subsetSize, int nextIndex ) {
	    if ( subsetSize == subset.size() ) {
	      result.add( ImmutableList.copyOf( subset ) );
	    } else {
	      for ( int j = nextIndex; j < set.size(); j++ ) {
	        subset.set( subsetSize, set.get( j ) );
	        processLargerSubsets( result, set, subset, subsetSize + 1, j + 1 );
	      }
	    }
	    return result;
	  }

	  public Collection<List<String>> permutations( List<String> list, int size ) {
	    Collection<List<String>> all = Lists.newArrayList();
	    if ( list.size() < size ) {
	      size = list.size();
	    }
	    if ( list.size() == size ) {
	      all.addAll( Collections2.permutations( list ) );
	    } else {
	      for ( List<String> p : processSubsets( list, size ) ) {
	        all.addAll( Collections2.permutations( p ) );
	      }
	    }
	    return all;
	  }
}


 class GeneratePermutations

{
    /*public static void main(String args[])
    {
        GeneratePermutations g = new GeneratePermutations();
        String[] elements = {"a","b","c",};
       

        //System.out.println(permutations.get(999999));
    }*/

   /* public ArrayList<String> generatePermutations( String[] elements )
    {
        ArrayList<String> permutations = new ArrayList<String>();
        if ( elements.length == 2 )
        {

            String x1 = elements[0]  + elements[1]; 
            String x2 = elements[1]  + elements[0];  
            permutations.add(x1);
            permutations.add(x2);

        }
        else {
            for (  int i = 0 ; i < elements.length  ; i++)
            {
                String[] elements2 = new String[elements.length -1];
                int kalo = 0;
                for( int j =0 ; j< elements2.length ; j++ )
                {
                    if( i == j)
                    {
                        kalo = 1;
                    }
                    elements2[j] = elements[j+kalo];
                }
                ArrayList<String> k2 = generatePermutations(elements2);
                for( String x : k2 )
                {
                    String s = elements[i]+x;
                    permutations.add(s);
                }
            }
        }

        return permutations;
    }*/
}
