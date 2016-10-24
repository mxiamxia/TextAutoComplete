package co.synonym.lru;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.synonym.log.Log;
import co.synonym.utils.FileLoader;

public class LRUCache {
	private HashMap<String, DoubleLinkedListNode> map = new HashMap<String, DoubleLinkedListNode>();
	private DoubleLinkedListNode end;
	private int capacity;
	private int len;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		len = 0;
	}
	
	private DoubleLinkedListNode head;

	public void preLoad() {
		List<String> preload = FileLoader.loadStringFromFile("preLoad.txt");
		for(String phrase : preload) {
			try {
				String hash = Sha1Hex.makeSHA1Hash(phrase);
				set(hash, phrase);
			} catch (NoSuchAlgorithmException e) {
				Log.error(e);
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				Log.error(e);
			}
		}
	}
	
	public String get(String key) {
		if (map.containsKey(key)) {
			DoubleLinkedListNode latest = map.get(key);
			removeNode(latest);
			setHead(latest);
			return latest.val;
		} else {
			return null;
		}
	}

	public void removeNode(DoubleLinkedListNode node) {
		DoubleLinkedListNode cur = node;
		DoubleLinkedListNode pre = cur.pre;
		DoubleLinkedListNode post = cur.next;

		if (pre != null) {
			pre.next = post;
		} else {
			head = post;
		}

		if (post != null) {
			post.pre = pre;
		} else {
			end = pre;
		}
	}

	public void setHead(DoubleLinkedListNode node) {
		node.next = head;
		node.pre = null;
		if (head != null) {
			head.pre = node;
		}

		head = node;
		if (end == null) {
			end = node;
		}
	}
	
	public void setEnd(DoubleLinkedListNode node) {
		node.pre = end;
		node.next = null;
		if (end != null) {
			end.next = node;
		}
		end = node;
		if (head == null) {
			head = node;
		}
	}
	
	public void setWithSequence(DoubleLinkedListNode node) {
		DoubleLinkedListNode pointer = head;
		while(pointer != null) {
			if(node.frequency < pointer.frequency) {
				pointer = pointer.next;
			} else {
				node.pre = pointer.pre;
				pointer.pre = node;
				node.next = pointer;
				node.pre.next = node;
				break;
			}
		}
	}

	public void set(String key, String value) {
		if (map.containsKey(key)) {
			DoubleLinkedListNode oldNode = map.get(key);
			oldNode.val = value;
			oldNode.frequency++;
			removeNode(oldNode);
			if(head != null && oldNode.frequency < head.frequency) {
				setWithSequence(oldNode);
			} else {
				setHead(oldNode);
			}
		} else {
			DoubleLinkedListNode newNode = new DoubleLinkedListNode(key, value, 1);
			if (len < capacity) {
				if(head==null || head.frequency == 1) {
					setHead(newNode);
				} else {
					setEnd(newNode);
				}
				map.put(key, newNode);
				len++;
			} else {
				map.remove(end.key);
				end = end.pre;
				if (end != null) {
					end.next = null;
				}
				setEnd(newNode);
				map.put(key, newNode);
			}
		}
	}
	
	public List<String> getLruList() {
		List<String> ret = new ArrayList<String>();
		DoubleLinkedListNode tmp = head;
		while(tmp!=null) {
			ret.add(tmp.val);
			tmp=tmp.next;
		}
		return ret;
	}

	class DoubleLinkedListNode {
		public String val;
		public String key;
		public int frequency;
		public DoubleLinkedListNode pre;
		public DoubleLinkedListNode next;

		public DoubleLinkedListNode(String key, String value, int freq) {
			this.val = value;
			this.key = key;
			this.frequency = freq;
		}

	}
	public static void main(String[] args) {
		LRUCache lru = new LRUCache(20);
				
	}
	
}
