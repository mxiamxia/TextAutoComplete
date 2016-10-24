package co.synonym.core;

public abstract class SynonymFinder {
	
	public int phraseDistance(String input, String dict) {
		int distance = -1;
		String[] a = input.split("\\s+");
		String[] b = dict.split("\\s+");
		distance = phraseDistance(a, b);
		return distance;
	}

	public abstract int phraseDistance(String[] a, String[] b);
}
