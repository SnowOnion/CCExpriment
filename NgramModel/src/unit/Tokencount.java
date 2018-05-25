package unit;


public class Tokencount<K> {
	public Token<K> token;
	public int count;
	
	public Tokencount(K ptokenElem, int pcount) {
		this.token = new Token(ptokenElem);
		this.count = pcount;
	}
	
	public Tokencount(Token<K> ptoken, int pcount) {
		this.token = ptoken;
		this.count = pcount;
	}
	
	public K getTokenElem() {
		return this.token.tokenElem;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void addCount() {
		count++;
	}
	
	public int hashCode() {
		String tokenStr = toString();
		return (tokenStr.hashCode());
	}
	
	public boolean equals(Tokencount<K> tc) {
		boolean cnteq = (count == tc.count);
		boolean tokeneq = (token == tc.token);
		
		return (cnteq && tokeneq);
	}
	
	public String toString() {
		String str = token.tokenElem.toString();
		return str;
	}
}