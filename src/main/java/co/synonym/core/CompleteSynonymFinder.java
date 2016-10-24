package co.synonym.core;

public class CompleteSynonymFinder extends SynonymFinder {

	@Override
	public int phraseDistance(String[] a, String[] b) {
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

}
