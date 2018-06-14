package iounit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import tokenunit.Tokensequence;
import tokenunit.Tokenstream;


/**
 * @author HHeart
 * CorpusImporter: import the corpus from external files
 * @param <K>: type of token
 */

public class CorpusImporter<K> {
	static public String rootdir;
	public ArrayList<File> trainingDataFileList;
	public int datatype; //0: natural language data; 1: programming language data
	public int n;        //n in n-gram
	
	public CorpusImporter(int type) {
		rootdir = "C:\\Users\\HHeart\\Desktop\\CodeCompletion\\CCExpriment\\";
		String dataInputPath = rootdir + "NgramModel\\Config\\DataInput.properties";
		datatype = type;
		
		Properties properties = new Properties();  
		try {  
		    InputStream in = new BufferedInputStream(new FileInputStream(dataInputPath));  //error-prone
		    properties.load(in);  
		} catch (Exception e) {  
		    e.printStackTrace();  
		}
		
		String trainingDataSrcDir = rootdir;
		if (datatype == 0) {
			trainingDataSrcDir += properties.getProperty("TRAINING_NL_DATAFILEDIR");
		} else {
			//for programming language
		}
		
		File DataSrcDir = new File(trainingDataSrcDir);
		trainingDataFileList = new ArrayList<>(Arrays.asList(DataSrcDir.listFiles()));
		importCorpusFromBase(n);
	}
	
	//import Dictionary of Token Sequence from single file
	public  ArrayList<Tokensequence<K>> importCorpusFromSingleFile(File pfile, int n) {
		Tokenstream<K> corpustream = new Tokenstream<K>(n, pfile);
		return (corpustream.getStreamList());
	}
	
	//import Dictionary Token Sequence from the folder containing multiple files
	public ArrayList<Tokensequence<K>> importCorpusFromBase(int n) {
		//collect names of files in trainingDataSrcDir and store in trainingDataList
		int fileNum = trainingDataFileList.size();
		ArrayList<Tokensequence<K>> tokenseqlist = new ArrayList<>();
		
		//POLISH
		for (int i = 0; i < fileNum; i++) {
			tokenseqlist.addAll(importCorpusFromSingleFile(trainingDataFileList.get(i), n));
		}
		
		return tokenseqlist;
	}
}