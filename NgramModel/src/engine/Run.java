package engine;
import model.BasicNGram;

public class Run {
	public static void main(String[] args) {
		BasicNGram bn = new BasicNGram(3);
		bn.importDictionary();
		bn.trainBasicNGramModel();
		System.out.println("LOK");
	}
}