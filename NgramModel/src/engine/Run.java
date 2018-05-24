package engine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import model.BasicNGram;
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
		
		System.out.println(opIntInfered);
		System.out.println("OK");
	}
}