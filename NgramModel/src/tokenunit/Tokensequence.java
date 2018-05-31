package tokenunit;

import java.util.ArrayList;
import java.util.Optional;

public class Tokensequence<K> {
	public int n;  //length of token sequence
	public ArrayList<K> sequence;
	
	private Optional<ArrayList<K>> initsequence;
	private Optional<K> lastoken;
	
	//constructors
	//The second constructor needs extra check before using
	public Tokensequence(int n) {
		this.n = n;
		sequence = new ArrayList<K>();
	}
	
	
	//If strArray.length is not equal to N in NGram model, it needs to report the failure
	public Tokensequence(K[] tokenArray) {
		int len = tokenArray.length;
		this.n = len;
		this.sequence = new ArrayList<K>();  //need to guarantee the pre-post order
	
		for (int i = 0; i < len; i++) {
			this.sequence.add(tokenArray[i]);
		}
		
		splitTokenSeq();
	}
	
	
	public Tokensequence(ArrayList<K> tokenList) {
		n = tokenList.size();
		sequence = tokenList;
		splitTokenSeq();
	}
	
	
	private void splitTokenSeq() {
		if (n > 1) {
			ArrayList<K> tmpsequence = (ArrayList<K>) sequence.clone();
			K lastElem = tmpsequence.remove(n - 1);
			initsequence = Optional.of(tmpsequence);
			lastoken = Optional.of(lastElem);
		} else {
			initsequence = Optional.empty();
			lastoken = Optional.empty();
			
		}
	}
	
	
	public ArrayList<K> getSequence() {
		return this.sequence;
	}
	
	
	public Optional<ArrayList<K>> getInitSequence() {
		return this.initsequence;
	}
	
	
	public Optional<K> getLastToken() {
		return this.lastoken;
	}
	
	public int hashCode() {
		//need to polish
		String str = toString();
		return (str.hashCode());
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Tokensequence)) {
			return false;
		}
		
		Tokensequence<?> tokenseq = (Tokensequence<?>) obj;
		boolean b1 = (this.n == tokenseq.n);
		boolean b2 = true;
		
		if (this.n != tokenseq.n) {
			b2 = false;
		} else {
			for (int i = 0; i < n; i++) {
				if (!(this.sequence.get(i).equals(tokenseq.sequence.get(i)))) {
					b2 = false;
					break;
				}
			}
		}
		
		return (b1 && b2);
	}
	
	public String toString() {
		Integer ninteger = new Integer(n);
		String str = ninteger.toString();
		int len = sequence.size();
		
		for (int i = 0; i < len; i++) {
			String s = sequence.get(i).toString();
			str = str.concat(s);
		}
		
		return str;
	}
}