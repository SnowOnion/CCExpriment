package engine;
import java.io.File;

import model.BasicNGram;


public class Run implements AppRunNGram{
	public void run() {
		BasicNGram<Integer> bn = new BasicNGram<>(4);
		bn.preAction(new File("C:\\Users\\HHeart\\Desktop\\CodeCompletion\\CCExpriment\\Data\\dataset1\\8_37216915_2006-8-21_4.5.txt"));
		System.out.println("OK");
		return;
	}
}