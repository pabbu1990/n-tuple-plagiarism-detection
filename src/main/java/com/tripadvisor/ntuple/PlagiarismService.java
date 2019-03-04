package com.tripadvisor.ntuple;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.tripadvisor.entity.Tuple;

@Component
public class PlagiarismService {
	
	private static final Logger logger = LoggerFactory.getLogger(NtupleApplication.class);
	Tuple tuple;
	PlagiarismUtil plagiarismUtil;
	private final String DELIMITER_PATTERN = "[^a-zA-Z0-9]";
	
	@Inject
	public PlagiarismService(PlagiarismUtil plagiarismUtil) {
		this.plagiarismUtil = plagiarismUtil;
	}
	
	public void readInput(String[] args) {
		int tupleSize = (args.length==4)?Integer.parseInt(args[3]):3;
		tuple = new Tuple(args[0], args[1], args[2], tupleSize);
		System.out.println(readAndExecuteInput(tuple));
	}
		
	public String readAndExecuteInput(Tuple tuple) {	
		
		// Read from src/main/resources and call formatFile() method to validate size of file
		String file1 = formatFile(tuple.getFile1Name().endsWith(".txt")?readInputFile(tuple.getFile1Name()):readInputFile(tuple.getFile1Name()+".txt"));
		String file2 = formatFile(tuple.getFile2Name().endsWith(".txt")?readInputFile(tuple.getFile2Name()):readInputFile(tuple.getFile2Name()+".txt"));
		
		if(file1==null || file2 == null) {
			return printUsageMessage();
		}
		
		ArrayList<String> synonym = (tuple.getSynonymsFileName().endsWith(".txt"))?readSynonymsAsArray(tuple.getSynonymsFileName()):readSynonymsAsArray(tuple.getSynonymsFileName()+".txt");
		
		HashMap<String, String> synonymMap = plagiarismUtil.createSynonymMap(synonym);  // build map with synonyms passed

		plagiarismUtil.buildTupleMap(synonymMap, file1, tuple, false);  // build map with tuples from file 1
		plagiarismUtil.buildTupleMap(synonymMap, file2, tuple, true);  // build map with tuples from file 2 and count matches
		
		int plagiarismPercentage = plagiarismUtil.calculateDuplicationPercentage();  // calculate duplication percentage
		return String.valueOf(plagiarismPercentage+"%");
	}
	
	public String printUsageMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("1) file name for a list of synonyms");
		sb.append(System.getProperty("line.separator"));
		sb.append("2) input file 1");
		sb.append(System.getProperty("line.separator"));
		sb.append("3) input file 2");
		sb.append(System.getProperty("line.separator"));
		sb.append("4) (optional) the number N, the tuple size.  If not supplied, the default should be N=3)");
		return sb.toString();
	}
	
	private String formatFile(String fileContent) {
		String temp[] = fileContent.split(DELIMITER_PATTERN);
		
		// If size of either file is less than tuple size, we consider it as invalid
		if(!tuple.isValidTupleSizeForFile(temp.length)) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		for(String s : temp) {
			if(!s.isEmpty()) {
			    builder.append(s);
			    builder.append(" ");
			}
		}
		return builder.toString();
	}

	public static String readInputFile(String fileName){
		 String data = "";
		 BufferedReader br;
		 try{	
			Resource resource =  new ClassPathResource(fileName);
			br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(" ");
	            line = br.readLine();
	        }
	        data = sb.toString();
	        
		 } 
		 catch(FileNotFoundException e) {
			 logger.error(e.toString());
		 }
		 catch(IOException e) {
			 logger.error(e.toString());
		 }
		 return data;		 
	}
	
	public static ArrayList<String> readSynonymsAsArray(String fileName){
		BufferedReader br = null;
		ArrayList<String> list = null;
		 try{
			 Resource resource =  new ClassPathResource(fileName);
			 br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			 list = new ArrayList<String>();
		        String line = br.readLine();

		        while (line != null) {
		        	list.add(line);
		            line = br.readLine();
		        }
		 }
		 catch(FileNotFoundException e) {
			 logger.error(e.toString());
		 }
		 catch(IOException e) {
			 logger.error(e.toString());
		 }
		 return list;
	}

}
