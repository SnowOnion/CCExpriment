package model;

import java.util.HashMap;
import java.util.HashSet;
import model.Tokencount;
import model.Tokensequence;

//HashMap <HashSet, HashSet<Tokencount>>
//dic: the set of token

public class BasicNGram {
	//n in NGram, n = 2 in bigram, n = 3 in trigram
	//ndic: n-order Cartesian product
	//model: ndic -> {[(candidate, prop or count)]}
	
	public int n; 
	public HashSet dic;
	protected HashMap model;
	
	public BasicNGram(int ngramN) {
		this.n = ngramN;
		this.dic = new HashSet();
		this.model = new HashMap();
	}
	

	public void importDictionary() {
		 //obtain dic and ndic
	}
	
	public void trainBasicNGramModel() {
		//get model in single cycle of training
		//obtain model
	}
	
	public void preAction() {
		importDictionary();
		trainBasicNGramModel();
	}
	
	
	/*get the model
	 *model has the form: {Tokensequence ---> [Tokencount]}
	 *detail: (a1,a2,...,an)--->{(candidate1, count1)...}   
	 */	
	public HashMap getBasicNGramModel() {
		return this.model;
	}
	
	
	/* get the key set of model
	 * element has the form of (a1,a2,...,an)
	 */
	public Object getBasicNGramNDictionary() {
		return model.keySet();
	}
	
	
	/*get the model
	 *model has the form: {Tokensequence ---> [Tokencount]}
	 *return [Tokencount]   
	 */		
	public Object getBasicNGramCandidates(Tokensequence nseq) {
		return model.get(nseq);
	}
}