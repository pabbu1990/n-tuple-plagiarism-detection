package com.tripadvisor.ntuple;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.tripadvisor.entity.Tuple;

@Component
public class PlagiarismUtil {
	
	private int positiveMatch = 0;
	private int negativeMatch = 0;
	HashMap<String, Integer> tupleMap = null;
	
	// build map with synonyms passed
	public HashMap<String, String> createSynonymMap(ArrayList<String> list) {
		HashMap<String, String> synonymMap = new HashMap<String, String>();
		for (String synonyms : list) {
			String temp[] = synonyms.split(" ");
			for(int i = 0; i < temp.length ; i++)  {
				if(!synonymMap.containsKey(temp[i].toLowerCase())) {
					synonymMap.put(temp[i].toLowerCase(), temp[0].toLowerCase());
				}
			}
		}
		return synonymMap;
	}
	
	// build map for tuples formed
	public HashMap<String, Integer> buildTupleMap(HashMap<String, String> synonymMap, String file, Tuple tuple, boolean countMatches) {
		int mapSize = 0;
		if(!countMatches) {
			tupleMap = new HashMap<String, Integer>();
		} else {
			for(String tup: tupleMap.keySet()) {
				mapSize+=tupleMap.get(tup);
			}
		}
		String temp[] = file.split(" ");
		for(int i = 0; i <= temp.length - tuple.getSize() ; i++)  {
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j < tuple.getSize() ; j++) {
			    if(synonymMap.containsKey(temp[i+j])) {
			    		builder.append(synonymMap.get(temp[i+j]));
			    } else {
			    		builder.append(temp[i+j]);
			    }
			}		
			if(!tupleMap.containsKey(builder.toString().toLowerCase())) {
				tupleMap.put(builder.toString().toLowerCase(), 1);
				if(countMatches) {
					negativeMatch+=1;
				}
			} else {
				tupleMap.put(builder.toString().toLowerCase(), tupleMap.get(builder.toString().toLowerCase())+1);
				if(countMatches) {
					positiveMatch+=1;
				}
			}
			mapSize-=1;;
		}
		if(mapSize>0) {
			negativeMatch+=mapSize;
		}
		return tupleMap;
	}
	
	// calculate duplication percentage
	public int calculateDuplicationPercentage() {		
		Float ratio = ((float)positiveMatch/(float)(positiveMatch+negativeMatch));
		return (int) (ratio*100);
	}

}
