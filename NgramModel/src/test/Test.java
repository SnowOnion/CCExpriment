package test;

import java.util.ArrayList;
import java.util.Optional;

import engine.NLngramRunEngine;
import tokenunit.Tokensequence;

/**
 * @author HHeart
 * Test model
 */

public class Test {
    
    public static void main(String[] args) {
    	NLngramRunEngine<Character> runtest = new NLngramRunEngine<Character>(2, 0);
    	//Character inference beginning
    	ArrayList<Character> query = new ArrayList<Character>();
    	query.add('·þ');
    	
    	runtest.run();
    	Tokensequence<Character> queryseq = new Tokensequence<Character>(query);
    	Optional<Character> inferedWord = runtest.tokenInference(queryseq);
    			
    	if (inferedWord.isPresent()) {
    		System.out.println(inferedWord.get());
    	} else {
    		System.out.println("miss value");
    	}
    			
    	System.out.println("Finish");
    }
}