package model;


public class Tokencount {
	private String token;
	private int count;
	
	public Tokencount(String ptoken, int pcount) {
		this.token = ptoken;
		this.count = pcount;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void addCount() {
		count++;
	}
}