package engine;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import model.BasicNCharGram;
import unit.Tokensequence;


public class Run implements AppRunNGram{
	public void run() {
		BasicNCharGram bn = new BasicNCharGram(4);
		File file = new File("C:\\Users\\HHeart\\Desktop\\CodeCompletion\\CCExpriment\\Data\\dataset1\\8_37216915_2006-8-21_4.5.txt");
		bn.preAction(file);
		
		System.out.println("----------Model------------");
		
		System.out.print("N:");
		System.out.println(bn.n);
		
		System.out.print("Size of ngramap:");
		System.out.println(bn.getBasicNGramModel().keySet().size());
		
		ArrayList<Character> query = new ArrayList<Character>();
		query.add('Îå');
		query.add('ÐÇ');
		query.add('¼¶');
		query.add('±ö');
				
		Tokensequence<Character> queryseq = new Tokensequence<Character>(query);
		Optional<Character> inferedWord = bn.tokenInference(queryseq); 
		if (inferedWord.isPresent()) {
			System.out.println(inferedWord.get());
		}
		return;
	}
}