package engine;
import model.BasicNGram;
import model.Tokensequence;

public class Run {
	public static void main(String[] args) {
		BasicNGram<Integer> bn = new BasicNGram<>(3);
		
		//bn.importDictionary();
		//bn.trainBasicNGramModel();
		//Test1: method splitToken in Tokensequence
		Integer[] intArr1 = {1,2,3,4,5};
		Tokensequence<Integer> seq1 = new Tokensequence<Integer>(intArr1);
		Integer[] intArr2 = {1,2,4,3,5};
		Tokensequence<Integer> seq2 = new Tokensequence<Integer>(intArr2);
		System.out.println(seq1.equals(seq2));
		
//		if (seq.n > 2) { 
//			Integer i1 = seq.getLastToken().get();
//			System.out.println(i1);
//		}
//		System.out.println(seq.sequence.size());
		
		System.out.println("OK");
	}
}