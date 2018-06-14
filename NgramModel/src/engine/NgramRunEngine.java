package engine;

import java.util.Optional;
import tokenunit.Tokensequence;

/**
 * @author HHeart
 * 
 * n-gram model engine: 
 * ----inference token  
 * ----calculate probability of token sequences(sentence, phrase etc.)
 * 
 * @param <K>: type of element in n-gram model
 */

interface NgramRunEngine<K> {
	public Optional<K> tokenInference(Tokensequence<K> nseq);
	public double probabilityCalculation(Tokensequence<K> nseq);
	public void run();
}