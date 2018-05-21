public class Run {
	public static void main(string[] args) {
		BasicNGram<int> bn = new BasicNGram<int>();
		bn.importDictionary();
		bn.trainBasicNGramModel();
	}
} 