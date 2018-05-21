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
}