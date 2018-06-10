package tokenunit;

/**
 * @author HHeart
 * @param <K>: type of element in token
 */

public class Token<K> {
	public K tokenElem;
	
	public Token(K elem) {
		this.tokenElem= elem;
	}
	
	public Token(Token<K> ptoken) {
		this.tokenElem = ptoken.tokenElem;
	}
	
	//POLISH
	public int hashCode() {
		int tokenKhashCode = toString().hashCode();
		return tokenKhashCode;
//		return tokenElem.hashCode();
	}
	
	public boolean equals(Token<K> token) {
		return (this.tokenElem.equals(token.tokenElem));
	}
	
	public String toString() {
		return tokenElem.toString();
	}
}