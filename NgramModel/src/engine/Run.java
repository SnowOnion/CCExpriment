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
		BasicNCharGram bn = new BasicNCharGram(4);
		Properties congfig = new Properties();
		File file = new File("C:\\Users\\HHeart\\Desktop\\CodeCompletion\\CCExpriment\\Data\\dataset1\\8_37216915_2006-8-21_4.5.txt");
		bn.preAction(file);
		
		System.out.println("----------Model------------");
		
		System.out.print("N:");
		System.out.println(bn.n);
		
		System.out.print("Size of ngramap:");
		System.out.println(bn.getBasicNGramModel().keySet().size());
		
		ArrayList<Character> query = new ArrayList<Character>();
		query.add('��');
		query.add('��');
		query.add('��');
		query.add('��');
				
		Tokensequence<Character> queryseq = new Tokensequence<Character>(query);
		Optional<Character> inferedWord = bn.tokenInference(queryseq); 
		if (inferedWord.isPresent()) {
			System.out.println(inferedWord.get());
		}
		
		TrainingListImporter filels = new TrainingListImporter(0);
		System.out.println(filels.trainingDataFileList.size());
		
		return;
	}
}