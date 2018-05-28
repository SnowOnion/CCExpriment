package unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tokenstream<K> {
	public File tokenSourceFile;  //sourcefile                    
	public int n; //sequence length
	
	private ArrayList<K> wholeStream;
	private ArrayList<Tokensequence<K>> streamList;
	
	public Tokenstream(int N, File ptokenSourceFile) {
		this.n = N;
		this.tokenSourceFile = ptokenSourceFile;
		
		importStreamFromFile();
		convertWholeStreamToStreamList();
	}
	
	//import token stream from sourcefile
	//TODO
	private void importStreamFromFile() {
		wholeStream = new ArrayList<K>();
		try {
			FileInputStream inputstream = new FileInputStream(tokenSourceFile);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//extract the streamList from token stream
	private void convertWholeStreamToStreamList() {
		streamList = new ArrayList<Tokensequence<K>>();
		int len = wholeStream.size();
		
		if (wholeStream.size() < this.n) {
			return;
		}
		
		for (int i = 0; i < len; i++) {
			ArrayList<K> seq = (ArrayList<K>) wholeStream.subList(i, i + this.n);   ///low efficient
			Tokensequence<K> tokenseq = new Tokensequence<K>(seq); 
			streamList.add(tokenseq);
		}
	}
	
	//get streamList
	public ArrayList<Tokensequence<K>> getStreamList() {
		return (this.streamList);
	}
}