package engine;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

import iounit.TrainingListImporter;
import model.BasicNCharGram;
import tokenunit.Tokensequence;


public class Run implements AppRunNGram{
	public void run() {
		BasicNCharGram bn = new BasicNCharGram(4, 0);
		bn.preAction();
		
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
		System.out.println("OK");
		if (inferedWord.isPresent()) {
			System.out.println(inferedWord.get());
		}
		
		return;
	}
}