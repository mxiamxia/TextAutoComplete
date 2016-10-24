package co.synonym.core;

public class PartialSynonymFinder extends SynonymFinder {

	@Override
	public int phraseDistance(String[] a, String[] b) {
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

}
