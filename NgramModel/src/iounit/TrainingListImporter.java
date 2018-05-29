package iounit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class TrainingListImporter {
	static public String rootdir;
	public ArrayList<File> trainingDataFileList;
	public int datatype;
	//0: natural language data, char
	//1: programming language data, token with type K which K is type variable
	
	public TrainingListImporter(int type) {
		rootdir = "C:\\Users\\HHeart\\Desktop\\CodeCompletion\\CCExpriment\\";
		String dataInputPath = rootdir + "NgramModel\\Config\\DataInput.properties";
		datatype = type;
		trainingDataFileList = new ArrayList<File>();
		
		Properties properties = new Properties();  
		try {  
		    InputStream in = new BufferedInputStream(new FileInputStream(dataInputPath));  //error-prone
		    properties.load(in);  
		} catch (Exception e) {  
		    e.printStackTrace();  
		}
		
		String trainingDataSrcDir = rootdir;
		if (type == 0) {
			trainingDataSrcDir += properties.getProperty("TRAINING_NL_DATAFILEDIR");
		} else {
			trainingDataSrcDir += properties.getProperty("TRAINING_PL_DATAFILEDIR");
		}
		
		System.out.println(properties.getProperty("TRAINING_NL_DATAFILEDIR"));
		System.out.println(trainingDataSrcDir);
		generateTrainingDataList(trainingDataSrcDir);
	}
	
	
	private void generateTrainingDataList(String trainingDataSrcDir) {
		//collect names of files in trainingDataSrcDir and store in trainingDataList
		File DataSrcDir = new File(trainingDataSrcDir);
		trainingDataFileList = new ArrayList<>(Arrays.asList(DataSrcDir.listFiles()));
	}
}