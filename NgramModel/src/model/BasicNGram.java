package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import unit.Tokencount;
import unit.Tokensequence;

//HashMap <HashSet, HashSet<Tokencount>>
//dic: the set of token

public class BasicNGram<K> {
	//n in NGram, n = 2 in bigram, n = 3 in trigram
	//ndic: n-order Cartesian product
	//model: ndic -> {[(candidate, prop or count)]}
	
	public int n; 
	public HashSet<K> dic;
	protected HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> model;
	
	public BasicNGram(int ngramN) {
		this.n = ngramN;
		this.dic = new HashSet<K>();
		this.model = new HashMap<>();
	}
	
	
	public  ArrayList<Tokensequence<K>> importCorpus(ArrayList<Tokensequence<K>> sourceDictionary) {
		return sourceDictionary;
	}
	
	//TODO
	public  ArrayList<Tokensequence<K>> importCorpus(File pfile) {
		//tokenseqlist: the list of token sequence with length (n+1) from the content in the pfile
		//return tokenseqlist
		ArrayList<Tokensequence<K>> tokenseqlist = new ArrayList<Tokensequence<K>>();
		return tokenseqlist;
	}
	
	
	//TODO: need to polish, maybe has high time complexity
	public void trainBasicNGramModel(Tokensequence<K>[] srcdicArr) {
		//train model using srcdicArr
		int len = srcdicArr.length;
		
		//add the element of srcdirArr into the model
		for (int i = 0; i < len; i++) {
			Tokensequence<K> tmptokenseq = srcdicArr[i];
			Tokensequence<K> tmptokeninitseq = new Tokensequence<K>(tmptokenseq.getInitSequence().get()); 
			K tmplasttoken = tmptokenseq.getLastToken().get();
			
			if (model.containsKey(tmptokeninitseq)) {
				HashSet<Tokencount<K>> tokencntset =  model.remove(tmptokeninitseq);
				Iterator<Tokencount<K>> it = tokencntset.iterator();
				
				while(it.hasNext()) {
					Tokencount<K> tokencnt = it.next();
					
					if (tokencnt.getToken().equals(tmplasttoken)) {
						tokencnt.addCount();
						tokencntset.add(tokencnt);
					} else {
						tokencntset.add(new Tokencount<K>(tmplasttoken, 1));
						break;
					}
				}
				
				model.put(tmptokeninitseq, tokencntset);
			} else {
				HashSet<Tokencount<K>> tokencntset = new HashSet<Tokencount<K>>();
				Tokencount<K> tokencnt = new Tokencount<K>(tmplasttoken, 1);
				
				tokencntset.add(tokencnt);
				model.put(tmptokeninitseq, tokencntset);
			}
		}
	}
	

	public void preAction(File inputCorpus) {
		//Step 1: Import Corpus, check whether n is matched or not
		ArrayList<Tokensequence<K>> corpusList = importCorpus(inputCorpus);
		int len = corpusList.size();
		if (len == 0) return;
		if (corpusList.get(0).n - 1 != this.n) {
			return;
		}
		
		//Step 2: Convert the type of corpusList from ArrayList to Array
		Tokensequence<K>[] srcdicArr = new Tokensequence [len];
		for (int i = 0; i < len; i++) {
			srcdicArr[i] = corpusList.get(i);
		}
		
		//Step 3: Train Model
		trainBasicNGramModel(srcdicArr);
	}
	
	
	public Optional<K> tokenInference(Tokensequence<K> nseq) {
		//nseq: length of n
		//return the most likely post token of nseq, and can be null
		
		//search in model and get the candidates set
		Optional<HashSet<Tokencount<K>>> opcandidates = getBasicNGramCandidates(nseq);
		
		if (!opcandidates.isPresent()) {
			return Optional.empty();
		}
		
		HashSet<Tokencount<K>> candidates = opcandidates.get();
		Iterator<Tokencount<K>> it = candidates.iterator();
		int maxcnt = 0;
		Tokencount<K> tokencnt = null;
		
		//search
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
			return Optional.of(tokencnt.getToken());
		}
	}
	
	
	public HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> getBasicNGramModel() {
		//get the model
		//detail: (a1,a2,...,an)--->{(candidate1, count1)...}   
		
		return this.model;
	}
	
	
	public Optional<HashSet<Tokencount<K>>> getBasicNGramCandidates(Tokensequence<K> nseq) {
		//return set of candidates corresponding to nseq, which has the form of {Tokencount} 
		
		HashSet<Tokencount<K>> result = model.get(nseq);
		
		if (result == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}
}