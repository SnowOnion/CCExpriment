package engine;

import java.util.Optional;
import tokenunit.Tokensequence;

interface NgramRunEngine<K> {
	public Optional<K> tokenInference(Tokensequence<K> nseq);
	public float probabilityCalculation(Tokensequence<K> nseq);
	public void run();
}