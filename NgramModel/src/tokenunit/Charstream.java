package tokenunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class Charstream{
	public File charSourceFile;  //sourcefile                    
	public int n; //sequence length
	
	private ArrayList<Character> wholeStream;
	private ArrayList<Tokensequence<Character>> streamList;
	
	public Charstream(int N, File ptokenSourceFile) {
		this.n = N;
		this.charSourceFile = ptokenSourceFile;
		
		importCharStreamFromFile();
		convertWholeStreamToStreamList();
	}
	
	
	//import character stream from sourcefile
	private void importCharStreamFromFile() {
        Reader reader = null;
        wholeStream = new ArrayList<Character>();
        
        try {
            reader = new InputStreamReader(new FileInputStream(charSourceFile));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                if (((char) tempchar) != '\r') {
                    wholeStream.add(new Character((char) tempchar));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	//extract the streamList from token stream
	private void convertWholeStreamToStreamList() {
		streamList = new ArrayList<Tokensequence<Character>>();
		int len = wholeStream.size();
		int modelN = this.n;
		
		if (wholeStream.size() < modelN) {
			return;
		}
		
		for (int i = 0; i < len - modelN; i++) {
			ArrayList<Character> seq = new ArrayList<Character>();
			for (int k = 0; k < modelN; k++) {
				seq.add(wholeStream.get(i + k));
			}
			Tokensequence<Character> tokenseq = new Tokensequence<Character>(seq); 
			streamList.add(tokenseq);
		}
	}	


	//get streamList
	public ArrayList<Tokensequence<Character>> getStreamList() {
		return (this.streamList);
	}
}