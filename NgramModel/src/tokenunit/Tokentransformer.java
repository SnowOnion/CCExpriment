package tokenunit;

//Maybe redundant

/**
 * @author HHeart
 * @param <T1>: original type of element in token
 * @param <T2>: target type of element in token
 */

public class Tokentransformer<T1, T2> {
	public T1 token1;
		
	public Tokentransformer (T1 token1) {
		this.token1 = token1;
	}
	
	public T2 transform() {
		T2 token2 = (T2) token1;
		return token2;
	}
}