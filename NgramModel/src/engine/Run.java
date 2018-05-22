package engine;
import model.BasicNGram;

public class Run {
	public static void main(String[] args) {
		BasicNGram<Integer> bn = new BasicNGram<>(3);
		
		//bn.importDictionary();
		//bn.trainBasicNGramModel();
		System.out.println("OK");
	}
}