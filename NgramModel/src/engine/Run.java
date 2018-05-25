package engine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import model.BasicNGram;
import unit.Token;
import unit.Tokensequence;


public class Run {
	public static void main(String[] args) {
		BasicNGram<Integer> bn = new BasicNGram<>(4);

		Integer[] intArr1 = {1,2,3,4,5};
		Tokensequence<Integer> seq1 = new Tokensequence<Integer>(intArr1);
		Integer[] intArr2 = {1,2,4,3,5};
		Tokensequence<Integer> seq2 = new Tokensequence<Integer>(intArr2);
		Integer[] intArr3 = {1,2,3,4,5};
		Tokensequence<Integer> seq3 = new Tokensequence<Integer>(intArr3);
		Integer[] intArr4 = {1,2,3,4,6};
		Tokensequence<Integer> seq4 = new Tokensequence<Integer>(intArr4);
		
		System.out.println("Test the EQUALS() and HASHCODE()");
		System.out.println(seq2.equals(seq1));
		System.out.println(seq3.equals(seq1));
		
		Token<Integer> token1 = new Token<Integer> (1);
		Token<Integer> token2 = new Token<Integer> (1);
		System.out.println(token1.equals(token2));
		
		ArrayList<Tokensequence<Integer>> tokenseqdictionary = new ArrayList<Tokensequence<Integer>>();
		tokenseqdictionary.add(seq1);
		tokenseqdictionary.add(seq2);
		tokenseqdictionary.add(seq3);
		tokenseqdictionary.add(seq4);
		
		//bn.preAction(tokenseqdictionary);
		Integer[] intArr = {1,2,3,4};
		Tokensequence<Integer> seq = new Tokensequence<Integer>(intArr);
		Optional<Integer> opIntInfered = bn.tokenInference(seq);
		
		if (opIntInfered.isPresent()) {
			Integer intInfered = opIntInfered.get();
			System.out.println(intInfered);
		} else {
			System.out.println("No candidate");
		}
		
		Tokensequence<Integer> tokenseq1 = new Tokensequence<Integer>(intArr1);
		Tokensequence<Integer> tokenseq2 = new Tokensequence<Integer>(intArr3);
		HashSet<Tokensequence<Integer>> s = new HashSet<Tokensequence<Integer>>();
		s.add(tokenseq1);

		for (int i = 0; i < 1000000; i++) {
			ArrayList<Integer> ls = new ArrayList<Integer>();
			ls.add(1);
			ls.add(2);
			ls.add(3);
			ls.add(4);
			ls.add(i);
			Tokensequence<Integer> tokenseq = new Tokensequence<Integer>(ls);
			s.add(tokenseq);
		}
		
		System.out.println(s.size());
		System.out.println(s.contains(tokenseq2));
		System.out.println(tokenseq1.equals(tokenseq2));
		System.out.println(tokenseq1.toString());

		System.out.println(seq1.hashCode());
		System.out.println(seq2.hashCode());
		System.out.println(seq3.hashCode());
		System.out.println(seq4.hashCode());
		System.out.println(opIntInfered);
		
		System.out.println("OK");
	}
}