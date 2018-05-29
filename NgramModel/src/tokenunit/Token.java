package tokenunit;

public class Token<K> {
	public K tokenElem;
	
	public Token(K elem) {
		this.tokenElem= elem;
	}
	
	public Token(Token<K> ptoken) {
		this.tokenElem = ptoken.tokenElem;
	}
	
	public int hashCode() {
		int tokenKhashCode = toString().hashCode();
		return tokenKhashCode;
	}
	
	public boolean equals(Token<K> token) {
		return (this.tokenElem.equals(token.tokenElem));
	}
	
	public String toString() {
		return tokenElem.toString();
	}
}