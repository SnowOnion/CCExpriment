package engine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import model.BasicNGram;
import tokenunit.Tokensequence;


public class NLngramRunEngine implements NgramRunEngine{
	public void run() {
		BasicNGram<Character> bn = new BasicNGram<Character>(4, 0);
		bn.preAction();
		
		//Character inference beginning
		ArrayList<Character> query = new ArrayList<Character>();
		query.add('¹ý');
		query.add('Éú');
		query.add('ÈÕ');
		Tokensequence<Character> queryseq = new Tokensequence<Character>(query);
		Optional<Character> inferedWord = bn.tokenInference(queryseq);
		
		if (inferedWord.isPresent()) {
			System.out.println(inferedWord.get());
		} else {
			System.out.println("miss value");
		}
		
        System.out.println("Finish");
		
		return;
	}
}