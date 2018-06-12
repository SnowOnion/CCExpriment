package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import iounit.CorpusImporter;
import tokenunit.Tokencount;
import tokenunit.Tokensequence;
import tokenunit.Tokenstream;

/**
 * @author HHeart
 * @param <K>: type of token in basic n-gram model
 */

public class BasicNGram<K> {
	public int n;              //n>=2, n = 2 in bigram; n = 3 in trigram
	public HashSet<K> dic;     //dictionary
	public int modelType;      //0: natural language model;   1: programming language model
	protected HashMap<Tokensequence<K>, Double> seqProbModel; //kernel model in n-gram
	protected HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> seqCntModel;  //kernel model in n-gram
	
	public BasicNGram(int ngramN, int type) {
		this.n = ngramN;
		this.modelType = type;
		this.dic = new HashSet<K>();
		this.seqProbModel = new HashMap<>();
		this.seqCntModel = new HashMap<>();
	}
	
	//import Dictionary of Token Sequence directly
	public  ArrayList<Tokensequence<K>> importCorpus(ArrayList<Tokensequence<K>> sourceDictionary) {
		return sourceDictionary;
	}
	
	//import Dictionary Token Sequence from the folder containing multiple files
	public ArrayList<Tokensequence<K>> importCorpus() {
		CorpusImporter<K> corpusImporter = new CorpusImporter<K>(modelType);
		ArrayList<Tokensequence<K>> tokenseqlist = corpusImporter.importCorpusFromBase(n);
		return tokenseqlist;
	}
	
	//train seqProbModel to calculate the probability of a given sequence
	private void trainBasicNGramProbModel(Tokensequence<K>[] srcdicArr) {
		int len = srcdicArr.length;
		
		HashMap<Tokensequence<K>, Integer> cntmap = new HashMap<>();
		for (int i = 0; i < len; i++) {
			Tokensequence<K> tmptokenseq = srcdicArr[i];
			if (cntmap.containsKey(tmptokenseq)) {
				int cnt = cntmap.remove(tmptokenseq);
				cntmap.put(tmptokenseq, cnt + 1);
			} else {
				cntmap.put(tmptokenseq, 1);
			}
		}
		
		double t = 0.0;
		for (Map.Entry<Tokensequence<K>, Integer> entry : cntmap.entrySet()) {
			seqProbModel.put(entry.getKey(), (entry.getValue() * 1.0 / len));
		}
		System.out.println("ProbModel done");
	}
		
	//TODO: need to polish, maybe has high time complexity
	private void trainBasicNGramCntModel(Tokensequence<K>[] srcdicArr) {
		//train model using srcdicArr
		int len = srcdicArr.length;
		
		//add the element of srcdirArr into the model
		for (int i = 0; i < len; i++) {
			Tokensequence<K> tmptokenseq = srcdicArr[i];
			//System.out.println("MODEL N:");
			//System.out.println(this.n);
			Tokensequence<K> tmptokeninitseq = new Tokensequence<K>(tmptokenseq.getInitSequence().get()); 
			K tmplasttoken = tmptokenseq.getLastToken().get();
			
			if (seqCntModel.containsKey(tmptokeninitseq)) {
				HashSet<Tokencount<K>> tokencntset =  seqCntModel.remove(tmptokeninitseq);
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
				
				seqCntModel.put(tmptokeninitseq, tokencntset);
			} else {
				HashSet<Tokencount<K>> tokencntset = new HashSet<Tokencount<K>>();
				Tokencount<K> tokencnt = new Tokencount<K>(tmplasttoken, 1);
				
				tokencntset.add(tokencnt);
				seqCntModel.put(tmptokeninitseq, tokencntset);
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
		trainBasicNGramProbModel(srcdicArr);
		trainBasicNGramCntModel(srcdicArr);
		System.out.println("Training finished");
		long trainMoment2 = System.currentTimeMillis();
		long trainMoment = trainMoment2 - trainMoment1;
		System.out.println("Train the model: " + String.valueOf(trainMoment));
	}
	

	public HashMap<Tokensequence<K>, HashSet<Tokencount<K>>> getBasicNGramCntModel() {
		//get the model
		//detail: (a1,a2,...,an)--->{(candidate1, count1)...}   
		
		return this.seqCntModel;
	}
	
	public HashMap<Tokensequence<K>, Double> getBasicNgramProbModel() {
		return this.seqProbModel;
	}
	
	
	public Optional<HashSet<Tokencount<K>>> getBasicNGramCandidates(Tokensequence<K> nseq) {
		//return set of candidates corresponding to nseq, which has the form of {Tokencount} 
		
		HashSet<Tokencount<K>> result = seqCntModel.get(nseq);
		
		if (result == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}
}