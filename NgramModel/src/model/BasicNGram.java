package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

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
	
	
	public  ArrayList<Tokensequence<K>> importDictionary(ArrayList<Tokensequence<K>> sourceDictionary) {
		return sourceDictionary;
	}
	
	//TODO
	public void importDictionary() {
		return;
	}
	
	
	//TODO: need to polish
	//!!! VITAL IMPORTANT	
	public void trainBasicNGramModel(Tokensequence<K>[] srcdicArr) {
		//get model in single cycle of training
		//obtain model
		int len = srcdicArr.length;
		
		for (int i = 0; i < len; i++) {
			Tokensequence<K> tmptokenseq = srcdicArr[i];
			Tokensequence<K> tmptokeninitseq = new Tokensequence<K>(tmptokenseq.getInitSequence().get()); 
			K tmplasttoken = tmptokenseq.getLastToken().get();
			
			//model.containsKey(tmptokeninitseq)
			if (model.containsKey(tmptokeninitseq)) {
				HashSet<Tokencount<K>> tokencntset =  model.remove(tmptokeninitseq);
				Iterator<Tokencount<K>> it = tokencntset.iterator();
				while(it.hasNext()) {
					Tokencount<K> tokencnt = it.next();
					if (tokencnt.getToken().equals(tmplasttoken)) {
						tokencnt.addCount();
						tokencntset.add(tokencnt);
					} else {
						tokencntset.add(new Tokencount(tmplasttoken, 1));
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
	
	
	//TODO
	public void preAction(ArrayList<Tokensequence<K>> sourceDictionary) {
		int len = sourceDictionary.size();
		if (len == 0) return;
		if (sourceDictionary.get(0).n - 1 != this.n) {
			return;
		}
		
		Tokensequence<K>[] srcdicArr = new Tokensequence [len];
		
		for (int i = 0; i < len; i++) {
			srcdicArr[i] = sourceDictionary.get(i);
		}
		
		trainBasicNGramModel(srcdicArr);
	}
	
	
	public Optional<K> tokenInference(Tokensequence<K> nseq) {
		Optional<HashSet<Tokencount<K>>> opcandidates = getBasicNGramCandidates(nseq);
		if (!opcandidates.isPresent()) {
			return Optional.empty();
		}
		
		HashSet<Tokencount<K>> candidates = opcandidates.get();
		Iterator<Tokencount<K>> it = candidates.iterator();
		int maxcnt = 0;
		Tokencount<K> tokencnt = null;
		
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
	
	
	/*get the model
	 *model has the form: {Tokensequence ---> [Tokencount]}
	 *detail: (a1,a2,...,an)--->{(candidate1, count1)...}   
	 */	
	public HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> getBasicNGramModel() {
		return this.model;
	}
	
	
	/*get the model
	 *model has the form: {Tokensequence ---> [Tokencount]}
	 *return [Tokencount]   
	 */
	public Optional<HashSet<Tokencount<K>>> getBasicNGramCandidates(Tokensequence<K> nseq) {
		HashSet<Tokencount<K>> result = model.get(nseq);
		if (result == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}
}