package model;

import java.util.ArrayList;

public class Tokensequence {
	public int n;                        //length of token sequence
	public ArrayList sequence;
	
	//constructors
	//The second constructor needs extra check before using
	public Tokensequence(int n) {
		this.n = n;
		sequence = new ArrayList();
	}
	
	//If strArray.length is not equal to N in NGram model, it needs to report the failure
	public Tokensequence(String[] strArray) {
		int len = strArray.length;
		this.n = len;
		sequence = new ArrayList(sequence);  //need to guarantee the pre-post order
	}
}