package refineunit;

/**
 * @author Lamp
 */

public class LaplaceSmoothing {
	public double lambda;
	public int ngramModelCount;
	
	/** 
	 * @param alpha: nonnegative
	 * @param ngramcount: total number of sequence in n-gram model
	 */
	public LaplaceSmoothing(double alpha, int ngramcount) {
		this.lambda = alpha;
		this.ngramModelCount = ngramcount;
	}
	
	/**
	 * @param count1: total count of w1,w2,..,wn-1 (with length of n - 1)
	 * @param count2: total count of w1,w2,...,wn (with length of n)
	 * @return probability of sequence after smoothing
	 */
	public double probAfterSmoothing(int count1, int count2) {
		double a = count1 + lambda;
		double b = count2 + lambda * ngramModelCount;
		return (a / b);
	}
}
