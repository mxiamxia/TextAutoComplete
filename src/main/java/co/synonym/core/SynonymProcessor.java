package co.synonym.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SynonymProcessor {

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
			int dist = new SynonymProcessor().phraseDistance(target, p, "complete");
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

	public int phraseDistance(String input, String dict, String mode) {
		int distance = -1;
		int N = input.length();

		String[] a = input.split("\\s+");
		String[] b = dict.split("\\s+");

		if ("complete".equalsIgnoreCase(mode)) {
			distance = completePhraseDistance(a, b);
		} else {
			distance = partialPhraseDistance(a, b);
		}

		return distance;
	}

	private int completePhraseDistance(String[] a, String[] b) {
		int distance = -1;
		int N = a.length;
		boolean[] mark = new boolean[N];
		boolean complete = true;

		for (int i = 0; i < mark.length; i++) {
			mark[i] = false;
		}

		int[] costs = new int[b.length + 1];
		for (int j = 0; j < costs.length; j++)
			costs[j] = j;
		for (int i = 1; i <= a.length; i++) {
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= b.length; j++) {
				if (a[i - 1].equalsIgnoreCase(b[j - 1])) {
					mark[i - 1] = true;
				}
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
						a[i - 1].equalsIgnoreCase(b[j - 1]) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}

		for (int i = 0; i < mark.length; i++) {
			if (!mark[i]) {
				complete = false;
				break;
			}
		}
		if (complete) {
			distance = costs[b.length];
		}
		return distance;
	}

	private int partialPhraseDistance(String[] a, String[] b) {
		int distance = -1;
		boolean match = false;

		int[] costs = new int[b.length + 1];
		for (int j = 0; j < costs.length; j++)
			costs[j] = j;
		for (int i = 1; i <= a.length; i++) {
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= b.length; j++) {
				if (a[i - 1].equalsIgnoreCase(b[j - 1])) {
					match = true;
				}
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
						a[i - 1].equalsIgnoreCase(b[j - 1]) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}

		if (match) {
			distance = costs[b.length];
		}
		return distance;
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