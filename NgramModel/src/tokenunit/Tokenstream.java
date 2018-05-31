package tokenunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class Tokenstream<K> {
	public File tokenSourceFile;  //sourcefile containing tokens with type K   
	public int n; //sequence length
	
	private ArrayList<K> wholeStream;
	private ArrayList<Tokensequence<K>> streamList;
	
	public Tokenstream(int N, File ptokenSourceFile) {
		this.n = N;
		this.tokenSourceFile = ptokenSourceFile;
		
		importTokentStreamFromFile();
		convertWholeStreamToStreamList();
	}
	
	//need to polish
	//import token stream from sourcefile
	private void importTokentStreamFromFile() {
		ArrayList<Character> strStream = new ArrayList<Character>();
		try {
			//strStream = importCharStreamFromFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		convertCharStreamToTokenStream(strStream);
	}
	
	
	//import character stream from sourcefile
	private ArrayList<K> importCharStreamFromFile() {
        Reader reader = null;
        ArrayList<K> strStream = new ArrayList<K>();
        
        try {
            reader = new InputStreamReader(new FileInputStream(tokenSourceFile));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                if (((char) tempchar) != '\r') {
                  //  wholeStream.strStream(new Character((char) tempchar));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return strStream;
    }


	private void convertCharStreamToTokenStream(ArrayList<Character> strStream) {
		wholeStream = new ArrayList<K>();
		int len = strStream.size();
		
		for (int i = 0; i < len; i++) {
			wholeStream.add((K)strStream.get(i));
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