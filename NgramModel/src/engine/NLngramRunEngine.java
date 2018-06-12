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
	
	/**
	 * @param n: n-gram model parameter
	 * @param type:  0:natural language model;   1: programming language model
	 */
	public NLngramRunEngine(int n, int type){
		this.ngram = new BasicNGram<K>(n, type);
		this.gramArray = (BasicNGram<K>[]) new BasicNGram [3];
		
		for (int i = 1; i <= 3; i++) {
			this.gramArray[i - 1] = new BasicNGram<K>(i, type);
		}
	}
	
	public NLngramRunEngine(int n, int type, int m){
		this.ngram = new BasicNGram<K>(n, type);
		this.gramArray = (BasicNGram<K>[]) new BasicNGram [m];
		
		for (int i = 2; i <= m; i++) {
			this.gramArray[i - 1] = new BasicNGram<K>(i, type);
		}
	}
	
	public void preAction() {
		ngram.preAction();
		System.out.println("NGRAM PREACTION DONE!");
		
		for (int i = 1; i < gramArray.length; i++) {
			gramArray[i].preAction();
			System.out.println("NGRAMARR SINGLE PREACTION DONE!");
		}
		System.out.println("NGRAM PREACTION DONE!");
	}
	
	public Optional<K> tokenInference(Tokensequence<K> nseq) {
		//nseq: length of n
		//return the most likely post token of nseq, and can be null
		
		//search in model and get the candidates set
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
	}
	
	
	//TODO
	public float probabilityCalculation(Tokensequence<K> nseq) {
		//a small test, maybe unvalid and meaningless
		if (ngram.getBasicNgramProbModel().keySet().contains(nseq)) {
			return (ngram.getBasicNgramProbModel().get(nseq).floatValue());
		}
		return 0;
	}
	
	
	public void run() {
		preAction();
		
		return;
	}
}