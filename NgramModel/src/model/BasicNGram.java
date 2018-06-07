package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import iounit.CorpusImporter;
import tokenunit.Tokencount;
import tokenunit.Tokensequence;
import tokenunit.Tokenstream;

//HashMap <HashSet, HashSet<Tokencount>>
//dic: the set of token

//TODO: add the probablity of sentences to BasicNGram model
//Not noly fix on the code or word completion
//K can be String, Character and other types of tokens
public class BasicNGram<K> {
	//n in NGram, n = 2 in bigram, n = 3 in trigram
	//ndic: n-order Cartesian product
	//model: ndic -> {[(candidate, prop or count)]}
	
	public int n; 
	public HashSet<K> dic;
	public int modelType; //0: natural language model;   1: programming language model
	protected HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> model;
	
	public BasicNGram(int ngramN, int type) {
		this.n = ngramN;
		this.modelType = type;
		this.dic = new HashSet<K>();
		this.model = new HashMap<>();
	}
	
	//import Dictionary of Token Sequence directly
	public  ArrayList<Tokensequence<K>> importCorpus(ArrayList<Tokensequence<K>> sourceDictionary) {
		return sourceDictionary;
	}
	
	//import Dictionary of Token Sequence from single file
	public  ArrayList<Tokensequence<K>> importCorpus(File pfile) {
		//tokenseqlist: the list of token sequence with length (n+1) from the content in the pfile
		//return tokenseqlist
		Tokenstream<K> corpustream = new Tokenstream<K>(n, pfile);
		return (corpustream.getStreamList());
	}
	
	//import Dictionary Token Sequence from the folder containing multiple files
	public ArrayList<Tokensequence<K>> importCorpus() {
		ArrayList<Tokensequence<K>> tokenseqlist = new ArrayList<>();
		CorpusImporter fileImporter = new CorpusImporter(modelType);
		ArrayList<File> filels = fileImporter.trainingDataFileList;
		int fileNum = filels.size();
		
		for (int i = 0; i < 20; i++) {
			tokenseqlist.addAll(importCorpus(filels.get(i)));
			if (i % 100 == 0) {
				System.out.print(i);
				System.out.println("  Import the next 20 files");
			}
		}

		return tokenseqlist;
	}
	
	
	//TODO: need to polish, maybe has high time complexity
	public void trainBasicNGramModel(Tokensequence<K>[] srcdicArr) {
		//train model using srcdicArr
		int len = srcdicArr.length;
		
		//add the element of srcdirArr into the model
		for (int i = 0; i < len; i++) {
			Tokensequence<K> tmptokenseq = srcdicArr[i];
			System.out.println("MODEL N:");
			System.out.println(this.n);
			Tokensequence<K> tmptokeninitseq = new Tokensequence<K>(tmptokenseq.getInitSequence().get()); 
			K tmplasttoken = tmptokenseq.getLastToken().get();
			
			if (model.containsKey(tmptokeninitseq)) {
				HashSet<Tokencount<K>> tokencntset =  model.remove(tmptokeninitseq);
				Iterator<Tokencount<K>> it = tokencntset.iterator();
				
				while(it.hasNext()) {
					Tokencount<K> tokencnt = it.next();
					
					if (tokencnt.getTokenElem().equals(tmplasttoken)) {
						tokencnt.addCount();
						tokencntset.add(tokencnt);
					} else {
						tokencntset.add(new Tokencount<K>(tmplasttoken, 1));
						break;
					}
				}
				
				model.put(tmptokeninitseq, tokencntset);
			} else {
				HashSet<Tokencount<K>> tokencntset = new HashSet<Tokencount<K>>();
				Tokencount<K> tokencnt = new Tokencount<K>(tmplasttoken, 1);
				
				tokencntset.add(tokencnt);
				model.put(tmptokeninitseq, tokencntset);
			}
		}
	}

	public void preAction() {
		//Step 1: Import Corpus, check whether n is matched or not
		long importCorpusMoment1 = System.currentTimeMillis();
		ArrayList<Tokensequence<K>> corpusList = importCorpus();
		System.out.println("Import done");
		long importCorpusMoment2 = System.currentTimeMillis();
		long importCorpusTime = importCorpusMoment2 - importCorpusMoment1;
		System.out.println("Import the corpus: " + String.valueOf(importCorpusTime));
		
		int len = corpusList.size();
		if (len == 0) return;
		if (corpusList.get(0).n != this.n) {
			return;
		}
		
		//Step 2: Convert the type of corpusList from ArrayList to Array
		long convertionMoment1 = System.currentTimeMillis();
		Tokensequence<K>[] srcdicArr = new Tokensequence [len];
		for (int i = 0; i < len; i++) {
			srcdicArr[i] = corpusList.get(i);
		}
		System.out.println("Convertion done");
		long convertionMoment2 = System.currentTimeMillis();
		long convertionTime = convertionMoment2 - convertionMoment1;
		System.out.println("Convert the token: " + String.valueOf(convertionTime));
		
		//Step 3: Train Model
		long trainMoment1 = System.currentTimeMillis();
		trainBasicNGramModel(srcdicArr);
		System.out.println("Training finished");
		long trainMoment2 = System.currentTimeMillis();
		long trainMoment = trainMoment2 - trainMoment1;
		System.out.println("Train the model: " + String.valueOf(trainMoment));
	}
	

	public HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> getBasicNGramModel() {
		//get the model
		//detail: (a1,a2,...,an)--->{(candidate1, count1)...}   
		
		return this.model;
	}
	
	
	public Optional<HashSet<Tokencount<K>>> getBasicNGramCandidates(Tokensequence<K> nseq) {
		//return set of candidates corresponding to nseq, which has the form of {Tokencount} 
		
		HashSet<Tokencount<K>> result = model.get(nseq);
		
		if (result == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}
}