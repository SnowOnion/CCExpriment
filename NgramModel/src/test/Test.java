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
    	NLngramRunEngine<Character> runtest = new NLngramRunEngine<Character>(1, 0);
    	runtest.run();
    	
    	ArrayList<Character> query = new ArrayList<Character>();
    	query.add('��');
//    	query.add('��');
//    	query.add('��');
    	
    	Tokensequence<Character> queryseq = new Tokensequence<Character>(query);
    	double prob = runtest.probabilityCalculation(new Tokensequence<Character>(query));
    	System.out.println(prob);
    	
//    	Optional<Character> inferedWord = runtest.tokenInference(queryseq);		
//    	if (inferedWord.isPresent()) {
//    		System.out.println(inferedWord.get());
//    		System.out.println(prob);
//    	} else {
//    		System.out.println("miss value");
//    	}
    	
    	System.out.println("Finish");
    }
}