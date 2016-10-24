package co.synonym.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SynonymFactory {

	public static void main(String[] args) {

		Map<String, Integer> rstSet = new TreeMap<String, Integer>();
		String[] dict = { "bad telephone number", "bad telephone", "bad number", "error type", "ws context",
				"ws enterprise logging", "fault url", "forcecomplete process", "edge forcecomplete process", "number",
				"jobdetail process", "service code info", "tip to ground resistance4", "price plan info",
				"price plan attributes", "company owned ip", "employer name", "combined billing indicator",
				"circuit id", "technology type", "single user code", "default apn indicator", "billed sms summary",
				"billed summary 2 month previous", "ring to ground resistance", "service type name",
				"unit of measure" };

		String target = "telephone number";

		// List<String[]> lst = parseDictToArray(dict);

		for (String p : dict) {
			int dist = new SynonymFactory().getFinder("complete").phraseDistance(target, p);
			if (dist != -1) {
				System.out.println("the distance is " + dist + "for " + p);
			}
		}

		// printSynonym(rstSet, target);

	}

	public static SynonymFinder getFinder(String finder) {
		if ("complete".equalsIgnoreCase(finder)) {
			return new CompleteSynonymFinder();
		} else {
			return new PartialSynonymFinder();
		}
	}

	private String[] preProcess(String input, String dict) {
		// TODO Auto-generated method stub
		String[] opt = new String[2];
		int index = dict.indexOf(input);
		if (index > 0) {
			String mergeInput = input.replaceAll("\\s+", "");
			String mergeDict = dict.replace(input, mergeInput);
			opt[0] = mergeInput;
			opt[1] = mergeDict;

		}
		return opt;
	}

	private static List<String[]> parseDictToArray(String[] dict) {
		// TODO Auto-generated method stub
		List<String[]> lst = new ArrayList<String[]>();
		for (String s : dict) {
			lst.add(s.split("\\s+"));
		}
		return lst;
	}
}