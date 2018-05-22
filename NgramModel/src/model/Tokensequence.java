package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokensequence<K> {
	public int n;                        //length of token sequence
	public ArrayList<K> sequence;
	
	//constructors
	//The second constructor needs extra check before using
	public Tokensequence(int n) {
		this.n = n;
		sequence = new ArrayList<K>();
	}
	
	//If strArray.length is not equal to N in NGram model, it needs to report the failure
	public Tokensequence(K[] strArray) {
		int len = strArray.length;
		this.n = len;
		sequence = new ArrayList<K>(sequence);  //need to guarantee the pre-post order
	}
	
	public Tokensequence<K>[] splitToken() {
		if (n < 2) {
			return null;
		} else {
			
		}

		K[] seqArray = null;
		sequence.toArray(seqArray);
		
		Tokensequence<K> tsq1 = new Tokensequence<K>(1);
		Tokensequence<K> tsq2 = new Tokensequence<K>(n - 1);
		for (int i = 0; i < n - 1; i++) {
			tsq1.sequence.add(seqArray[i]);
		}
		tsq2.sequence.add(seqArray[n - 1]);
		
		Tokensequence<K>[] retTokenSeq = new Tokensequence [2];
		retTokenSeq[0] = tsq1;
		retTokenSeq[1] = tsq2;
		
		return retTokenSeq;
	}
	
	public K getToken() {
		if (n == 1) {
			return (sequence.get(0));
		} else {
			return null;
		}
	}
}