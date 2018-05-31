package engine;
import java.util.ArrayList;
import java.util.Optional;
import model.BasicNCharGram;
import model.BasicNGram;
import tokenunit.Tokensequence;


public class NLngramRunEngine implements NgramRunEngine{
	public void run() {
		BasicNGram bn = new BasicNGram<Character>(4, 0);
		bn.preAction();
        System.out.println("Done");
		
//		System.out.println("----------Model------------");
//		System.out.print("N:");
//		System.out.println(bn.n);
//		System.out.print("Size of ngramap:");
//		System.out.println(bn.getBasicNGramModel().keySet().size());
//		
//		ArrayList<Character> query = new ArrayList<Character>();
//		query.add('当');
//		query.add('时');
//		query.add('前');
//		query.add('台');
//				
//		Tokensequence<Character> queryseq = new Tokensequence<Character>(query);
//		Optional<Character> inferedWord = bn.tokenInference(queryseq);
//		System.out.println("OK");
//		
//		
//		//can not search out the correct output
//		if (inferedWord.isPresent()) {
//			System.out.println(inferedWord.get());
//			System.out.println("OK");
//		}
		
		return;
	}
}