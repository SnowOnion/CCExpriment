import java.util.HashMap;
import java.util.HashSet;
import model.Tokencount;

public class BasicNGram<K> {
	//n in NGram, n = 2 in bigram, n = 3 in trigram
	//(a1, a2,... an) -> [(candidate, prop)]
	
	public int n; 
	public HashSet<K> dic;
	public HashSet<K> ndic;   //n-order Cartesian product;
	protected HashMap<K, HashSet<Tokencount>> model;
	
	public BasicNGram<K> (int ngramN) {
		this.n = ngramN;
		this.dic = new HashSet<K>();
		this.ndic = new HashSet<K>();
		this.model = new HashMap<K, HashSet<Tokencount>>();
	}
	
	//undefined
	public void importDictionary() {
		 //obtain dic and ndic
	}
	
	public void trainBasicNGramModel() {
		//get model in single cycle of training
		//obtain model
	}
	
	public HashMap<K, HashSet<Tokencount>> getBasicNGramModel() {
		return this.model;
	}
}