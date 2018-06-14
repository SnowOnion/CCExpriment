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
	
	//maybe unnecessary
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
		
	//need to polish, maybe has high time complexity
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
		System.out.println("---------------------------------");
		System.out.println("Corpus import begins");
		long importCorpusMoment1 = System.currentTimeMillis();
		ArrayList<Tokensequence<K>> corpusList = importCorpus();
		System.out.println("Import done");
		long importCorpusMoment2 = System.currentTimeMillis();
		long importCorpusTime = importCorpusMoment2 - importCorpusMoment1;
		System.out.println("Time cost: " + String.valueOf(importCorpusTime) + " ms");
		System.out.println("---------------------------------");
		
		int len = corpusList.size();
		if (len == 0) return;
		if (corpusList.get(0).n != this.n) {
			return;
		}
		
		//Step 2: Convert the type of corpusList from ArrayList to Array
		System.out.println("---------------------------------");
		System.out.println("Convertion begins");
		long convertionMoment1 = System.currentTimeMillis();
		Tokensequence<K>[] srcdicArr = new Tokensequence [len];
		for (int i = 0; i < len; i++) {
			srcdicArr[i] = corpusList.get(i);
		}
		System.out.println("Convertion done");
		long convertionMoment2 = System.currentTimeMillis();
		long convertionTime = convertionMoment2 - convertionMoment1;
		System.out.println("Time cost: " + String.valueOf(convertionTime) + " ms");
		System.out.println("---------------------------------");
		
		//Step 3: Train Model
		//Step 3.1: Train seqProbModel
		System.out.println("---------------------------------");
		System.out.println("Prob Model Training begins");
		long trainMoment1 = System.currentTimeMillis();
		trainBasicNGramProbModel(srcdicArr);
		System.out.println("Prob Model Training finished");
		long trainMoment2 = System.currentTimeMillis();
		long trainTime1 = trainMoment2 - trainMoment1;
		System.out.println("Time cost " + String.valueOf(trainTime1) + " ms");
		System.out.println("---------------------------------");
		
		/*
		//Step 3.2: Train seqCntModel
		System.out.println("---------------------------------");
		System.out.println("Count Model Training begins");
		long trainMoment3 = System.currentTimeMillis();
		trainBasicNGramProbModel(srcdicArr);
		System.out.println("Count Model Training finished");
		long trainMoment4 = System.currentTimeMillis();
		long trainTime2 = trainMoment4 - trainMoment3;
		System.out.println("Time cost " + String.valueOf(trainTime2) + " ms");
		System.out.println("---------------------------------");
		*/
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