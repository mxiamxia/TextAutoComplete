package co.synonym.servlet;

import java.util.HashMap;
import java.util.Map;

import co.synonym.lru.LRUCache;

public class AppContext {
	
	private static Map<String, LRUCache> lruMap = new HashMap<String, LRUCache>();
	
	public static LRUCache getLruCache(String key) 
	{
		LRUCache ret = null;
		if(lruMap.containsKey(key)) 
		{
			ret = lruMap.get(key);
		} 
		else
		{
			ret = new LRUCache(10);
			ret.preLoad();
			lruMap.put(key, ret);
		}
		return ret;
	}
	
}
