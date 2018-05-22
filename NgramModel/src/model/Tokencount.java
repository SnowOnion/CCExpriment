package model;


public class Tokencount<K> {
	private K token;
	private int count;
	
	public Tokencount(K ptoken, int pcount) {
		this.token = ptoken;
		this.count = pcount;
	}
	
	public K getToken() {
		return this.token;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void addCount() {
		count++;
	}
	
	public boolean equals(Tokencount<K> tc) {
		boolean cnteq = (count == tc.count);
		boolean tokeneq = (token == tc.token);
		return (cnteq && tokeneq);
	}
}