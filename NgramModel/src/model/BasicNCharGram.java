package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import tokenunit.Charstream;
import tokenunit.Tokencount;
import tokenunit.Tokensequence;
import tokenunit.Tokenstream;

//HashMap <HashSet, HashSet<Tokencount>>
//dic: the set of token

public class BasicNCharGram {
	//n in NGram, n = 2 in bigram, n = 3 in trigram
	//ndic: n-order Cartesian product
	//model: ndic -> {[(candidate, prop or count)]}
	
	public int n; 
	public HashSet<Character> dic;
	protected HashMap<Tokensequence<Character>, HashSet<Tokencount<Character>>> model;
	
	public BasicNCharGram(int ngramN) {
		this.n = ngramN;
		this.dic = new HashSet<Character>();
		this.model = new HashMap<>();
	}
	
	
	public  ArrayList<Tokensequence<Character>> importCorpus(ArrayList<Tokensequence<Character>> sourceDictionary) {
		return sourceDictionary;
	}
	
	
	public  ArrayList<Tokensequence<Character>> importCorpus(File pfile) {
		//tokenseqlist: the list of token sequence with length (n+1) from the content in the pfile
		//return tokenseqlist
		Charstream corpustream = new Charstream(n + 1, pfile);
		return (corpustream.getStreamList());
	}
	
	
	//TODO: need to polish, maybe has high time complexity
	public void trainBasicNGramModel(Tokensequence<Character>[] srcdicArr) {
		//train model using srcdicArr
		int len = srcdicArr.length;
		
		//add the element of srcdirArr into the model
		for (int i = 0; i < len; i++) {
			Tokensequence<Character> tmptokenseq = srcdicArr[i];
			Tokensequence<Character> tmptokeninitseq = new Tokensequence<Character>(tmptokenseq.getInitSequence().get()); 
			Character tmplasttoken = tmptokenseq.getLastToken().get();
			
			if (model.containsKey(tmptokeninitseq)) {
				HashSet<Tokencount<Character>> tokencntset =  model.remove(tmptokeninitseq);
				Iterator<Tokencount<Character>> it = tokencntset.iterator();
				
				while(it.hasNext()) {
					Tokencount<Character> tokencnt = it.next();
					
					if (tokencnt.getTokenElem().equals(tmplasttoken)) {
						tokencnt.addCount();
						tokencntset.add(tokencnt);
					} else {
						tokencntset.add(new Tokencount<Character>(tmplasttoken, 1));
						break;
					}
				}
				
				model.put(tmptokeninitseq, tokencntset);
			} else {
				HashSet<Tokencount<Character>> tokencntset = new HashSet<Tokencount<Character>>();
				Tokencount<Character> tokencnt = new Tokencount<Character>(tmplasttoken, 1);
				
				tokencntset.add(tokencnt);
				model.put(tmptokeninitseq, tokencntset);
			}
		}
	}
	

	public void preAction(File inputCorpus) {
		//Step 1: Import Corpus, check whether n is matched or not
		ArrayList<Tokensequence<Character>> corpusList = importCorpus(inputCorpus);
		int len = corpusList.size();
		if (len == 0) return;
		if (corpusList.get(0).n - 1 != this.n) {
			return;
		}
		
		//Step 2: Convert the type of corpusList from ArrayList to Array
		Tokensequence<Character>[] srcdicArr = new Tokensequence [len];
		for (int i = 0; i < len; i++) {
			srcdicArr[i] = corpusList.get(i);
		}
		
		
		//Step 3: Train Model
		trainBasicNGramModel(srcdicArr);
		
	}
	
	
	public Optional<Character> tokenInference(Tokensequence<Character> nseq) {
		//nseq: length of n
		//return the most likely post token of nseq, and can be null
		
		//search in model and get the candidates set
		Optional<HashSet<Tokencount<Character>>> opcandidates = getBasicNGramCandidates(nseq);
		
		if (!opcandidates.isPresent()) {
			return Optional.empty();
		}
		
		HashSet<Tokencount<Character>> candidates = opcandidates.get();
		Iterator<Tokencount<Character>> it = candidates.iterator();
		int maxcnt = 0;
		Tokencount<Character> tokencnt = null;
		
		//search
		while(it.hasNext()) {
			Tokencount<Character> tmptc = it.next();
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
	
	
	public HashMap<Tokensequence<Character>, HashSet<Tokencount<Character>>> getBasicNGramModel() {
		//get the model
		//detail: (a1,a2,...,an)--->{(candidate1, count1)...}   
		
		return this.model;
	}
	
	
	public Optional<HashSet<Tokencount<Character>>> getBasicNGramCandidates(Tokensequence<Character> nseq) {
		//return set of candidates corresponding to nseq, which has the form of {Tokencount} 
		
		HashSet<Tokencount<Character>> result = model.get(nseq);
		
		if (result == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}
}