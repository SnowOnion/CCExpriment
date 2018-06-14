package engine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import model.BasicNGram;
import tokenunit.Tokencount;
import tokenunit.Tokensequence;

/**
 * @author HHeart
 * 
 * n-gram natural language model engine:
 * ----inference token  
 * ----calculate probability of token sequences(sentence, phrase etc.)
 * 
 * @param <K>: type of element in n-gram model
 */

public class NLngramRunEngine<K> implements NgramRunEngine<K>{
	BasicNGram<K> ngram;
	BasicNGram<K> [] gramArray;   //unigram, bigram, trigram or unigram ...ngram
	int maxN;                     //maximal parameter in gramArray
	
	/**
	 * @param n: n-gram model parameter
	 * @param type:  0:natural language model;   1: programming language model
	 */
	public NLngramRunEngine(int n, int type){
		this.ngram = new BasicNGram<K>(n, type);
		this.gramArray = (BasicNGram<K>[]) new BasicNGram [4];
		
		for (int i = 1; i <= 3; i++) {
			this.gramArray[i] = new BasicNGram<K>(i, type);
		}
		
		maxN = 3;
	}
	
	/**
	 * @param n: n-gram model parameter
	 * @param type:  0:natural language model;   1: programming language model
	 * @param m: the maximal parameter of model in the gramArray
	 */
	public NLngramRunEngine(int n, int type, int m){
		this.ngram = new BasicNGram<K>(n, type);
		this.gramArray = (BasicNGram<K>[]) new BasicNGram [m + 1];
		
		//gramArray[1]: unary gram     gramArray[i]: i-gram
		for (int i = 1; i <= m; i++) {
			this.gramArray[i] = new BasicNGram<K>(i, type);
		}
		
		maxN = m;
	}
	
	public void preAction() {
		System.out.println("N-gram engine for natural language warms up");
		ngram.preAction();
		System.out.println("n-gram preaction finished");
		System.out.println("---------------------------------");
		System.out.println();
		
		for (int i = 1; i < gramArray.length; i++) {
			gramArray[i].preAction();
			System.out.print(Integer.toString(i) + "-gram");
			System.out.println(" single preaction finished");
			System.out.println("---------------------------------");
			System.out.println();
		}
		
		System.out.println("N-gram engine for natural language is prepared");
	}
	
	public Optional<K> tokenInference(Tokensequence<K> nseq) {
		//nseq: length of n
		//return the most likely post token of nseq, and can be null
		
		//search in model and get the candidates set
		/*
		Optional<HashSet<Tokencount<K>>> opcandidates = ngram.getBasicNGramCandidates(nseq);
		
		if (!opcandidates.isPresent()) {
			return Optional.empty();
		}
		
		HashSet<Tokencount<K>> candidates = opcandidates.get();
		Iterator<Tokencount<K>> it = candidates.iterator();
		int maxcnt = 0;
		Tokencount<K> tokencnt = null;
		
		//Need to polish, select the Tokencount with the maximal count in the set.
		while(it.hasNext()) {
			Tokencount<K> tmptc = it.next();
			int tmpcnt = tmptc.getCount();
			
			if (maxcnt < tmpcnt) {
				maxcnt = tmpcnt;
				tokencnt = tmptc;
			}  
		}
		
		if (tokencnt == null) {
			return Optional.empty();
		} else {
			return Optional.of(tokencnt.getTokenElem());
		}
		*/
		
		return Optional.empty();
	}
	
	
	//TODO, IMPORTANT
	/** maxN = 3
	 * using refineunit such as LidstoneSmoothing
	 * P(a1 a2 a3 a4 ... ak ... a_(n-1), an) 
	 * = p(a1) * p(a2 | a1) * p(a3 | a1 a2) * ... * p(an | a_(n-2) a_(n-1))
	 * = p(a1) * (p(a1 a2) / p(a1)) * (p(a1 a2 a3) / p(a1 a2)) * ... * p(a_(n-2) a_(n-1) a_n) / p(a_(n-2) a_(n-1)) 
	 * = p(a1) * p(a1 a2) * p(a1 a2 a3) * p(a2 a3 a4) * ... * p(a_(n-2) a_(n-1) a_(n)) / 
	 *   p(a1) * p(a1 a2) * p(a2 a3) * p(a3 a4) * p(a4 a5) * ... * p(a_(n-2) a_(n-1))
	 */
	
	public double probabilityCalculation(Tokensequence<K> nseq) {
		//a small test, maybe invalid and meaningless
		if (ngram.getBasicNgramProbModel().keySet().contains(nseq)) {
			return (ngram.getBasicNgramProbModel().get(nseq).doubleValue());
		}
		
		int len = nseq.length();
		if (len == 1 || len == 2) {
			return (gramArray[len].getBasicNgramProbModel().get(nseq).doubleValue());
		}
		
		//without smoothing
		double prob = 1.0;

		for (int i = 0; i < maxN; i++) {
			
		}
		
		for (int i = 0; i < nseq.length(); i++) {
			//p(a1) * p(a1 a2) * p(a1 a2 a3) * p(a2 a3 a4) * ... * p(a_(n-2) a_(n-1) a_(n)) /
			//p(a1) * p(a1 a2) * p(a2 a3) * p(a3 a4) * p(a4 a5) * ... * p(a_(n-2) a_(n-1))
		}
		
		return prob;
	}
	
	public void run() {
		preAction();	
		return;
	}
}