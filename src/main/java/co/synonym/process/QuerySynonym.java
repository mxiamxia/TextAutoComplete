package co.synonym.process;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.synonym.model.Term;

import co.synonym.core.SynonymFactory;
import co.synonym.log.Log;

public class QuerySynonym {

	Map<Term, Integer> map = new TreeMap();

	public String querySynonym(String input, String mode, String src, String type) throws Throwable {

		String rst = null;
		if ("word".equalsIgnoreCase(type)) {
			Map wordMap = queryWordList(input, mode);
			if (!wordMap.isEmpty()) {
				rst = addWordSynonym(map);
			}

		} else if ("all".equalsIgnoreCase(type)) {
			Iterator it = SynonymDict.syn_dic.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Map> entry = (Entry<String, Map>) it.next();
				Map subMap = entry.getValue();
				Iterator subIt = subMap.entrySet().iterator();
				while (subIt.hasNext()) {
					Map.Entry<String, String> subEntry = (Entry<String, String>) subIt.next();
					String id = subEntry.getKey();
					String dict = subEntry.getValue();
					int dis = SynonymFactory.getFinder(mode).phraseDistance(input, dict);
					if (dis != -1) {
						Term term = new Term();
						term.setId(id);
						term.setSynonym(dict);
						term.setDis(dis);
						map.put(term, dis);
					}
				}
			}
		} else {
			Map data = SynonymDict.syn_dic.get(src);
			if (data == null || data.size() == 0) {
				throw new Exception("Can not find ontology source");
			}
			Iterator it = data.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				String id = entry.getKey();
				String dict = entry.getValue();
				int dis = SynonymFactory.getFinder(mode).phraseDistance(input, dict);
				if (dis != -1) {
					Term term = new Term();
					term.setId(id);
					term.setSynonym(dict);
					term.setDis(dis);
					map.put(term, dis);
				}
			}
			Log.info("the matched phrases result size " + map.size());
		}

		if (!map.isEmpty()) {
			rst = addSynonym(map);
		}

		return rst;
	}

	private String addWordSynonym(Map<Term, Integer> map2) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map queryWordList(String input, String mode) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new TreeMap<String, Integer>();

		return map;
	}

	private String addSynonym(Map<Term, Integer> sortedMap2) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Iterator it = sortedMap2.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Term, Integer> entry = (Entry<Term, Integer>) it.next();
			Term term = (Term) entry.getKey();
			String id = term.getId();
			String dict = term.getSynonym();
			int dis = (Integer) entry.getValue();
			sb.append(addSynonym(dis, id, dict));
		}
		return sb.toString();
	}

	private String addSynonym(Integer dis, String id, String phrase) {
		// TODO Auto-generated method stub
		String output = "<phrase id=\"%s\" dist=\"%s\">%s</phrase>";
		String rst = String.format(output, id, dis, phrase);
		return rst;
	}

	class ValueComparator implements Comparable {

		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k1).compareTo(map.get(k2));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		return sortedByValues;
	}

}
