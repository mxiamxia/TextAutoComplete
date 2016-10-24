package co.synonym.utils;

import java.util.Hashtable;


public class XmlUtil {

	final static String[] xcodes = { "&amp;", "&quot;", "&apos;", "&lt;", "&gt;" };
	
	final static int[] xcLen = { 5, 6, 6, 4, 4 };
	
	final static Hashtable entities = new Hashtable();
	static {
		entities.put("amp", new char[] { '&' });
		entities.put("quot", new char[] { '"' });
		entities.put("apos", new char[] { '\'' });
		entities.put("lt", new char[] { '<' });
		entities.put("gt", new char[] { '>' });
	}
	
	public static String replaceString(String strData, String regex,
			String replacement) {
		if (strData == null) {
			return null;
		}
		int index;
		index = strData.indexOf(regex);
		String strNew = "";
		if (index >= 0) {
			while (index >= 0) {
				strNew += strData.substring(0, index) + replacement;
				strData = strData.substring(index + regex.length());
				index = strData.indexOf(regex);
			}
			strNew += strData;
			return strNew;
		}
		return strData;
	}

	
	public static String encodeString(String strData) {
		if (strData == null) {
			return "";
		}
		strData = replaceString(strData, "&", "&amp;");
		strData = replaceString(strData, "<", "&lt;");
		strData = replaceString(strData, ">", "&gt;");
		strData = replaceString(strData, "&apos;", "&apos;");
		strData = replaceString(strData, "\"", "&quot;");
		return strData;
	}
	
	public static String encodingSting(String strData) {
		if (strData == null) {
			return "";
		}
		strData = replaceString(strData, "&", "&amp;");
		strData = replaceString(strData, "<", "&lt;");
		strData = replaceString(strData, ">", "&gt;");
		strData = replaceString(strData, "'", "&apos;");
		strData = replaceString(strData, "\"", "&quot;");
		return strData;
	}

	
	/**
	 * Decode a string encoded by function
	 * <code>String encode(String str)</code>
	 * 
	 * @see XMLUtil#public static String encode(String str)
	 * 
	 * @param str
	 *            a encoded string. <code>str != null</code>
	 * @throws Exception 
	 */
	public static String Unencode(StringBuffer osb) throws Exception {
		if (osb == null || osb.length() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int len = osb.length();
		for (int ii = 0; ii < len; ii++) {
			char ch = osb.charAt(ii);
			if (ch != '&' || ii == (len - 1)) {
				sb.append(ch);
				continue;
			}

			if (ii < len - 1) {
				// ugly solution
				char xch = osb.charAt(ii + 1);
				if (xch != 'a' && xch != 'q' && xch != 'l' && xch != 'g' && xch != '#') {
					sb.append(ch);
					continue;
				}
				if (!isEntity(osb, ii)) {
					sb.append(ch);
					continue;
				}
			}

			char nch = '\0';
			StringBuffer keyBuf = new StringBuffer();
			for (int jj = ii + 1; jj < len; jj++) {
				nch = osb.charAt(jj);
				ii = jj;
				if (nch == ';') {
					break;
				}
				keyBuf.append(nch);
			}

			String key = keyBuf.toString();
			keyBuf.setLength(0);
			keyBuf = null;
			if (key.charAt(0) == '#') {
				try {
					if (key.charAt(1) == 'x') {
						ch = (char) Integer.parseInt(key.substring(2), 16);
					} else {
						ch = (char) Integer.parseInt(key.substring(1), 10);
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Unknown or invalid entity: &" + key + ";", nfe);
				}
				sb.append(ch);
			} else {
				char[] value = (char[]) entities.get(key);
				if (value == null) {
					throw new Exception("Unknown or invalid entity: &" + key + ";");
				}
				sb.append(value);
			}
			key = null;
		}
		return sb.toString();
	}
	
	private static boolean isEntity(StringBuffer ostr, int pos) {
		boolean yn = false;
		int len = ostr == null ? -1 : ostr.length();
		for (int ii = 0; !yn && len > 0 && ii < xcodes.length; ii++) {
			yn = equals(ostr, len, pos, xcodes[ii], xcLen[ii]);
		}
		return yn;
	}
	
	private static boolean equals(StringBuffer ostr, int len, int pos, String tag, int offset) {
		boolean yn = pos >= 0 && (pos + offset <= len);
		int loc = pos;
		while (yn && loc < pos + offset) {
			yn = ostr.charAt(loc) == tag.charAt(loc - pos);
			loc++;
		}
		return yn;
	}

	public static void main(String[] args) {
//		String string = "<TRAIN_CARD xmlns='http://www.cyberobject.com/2012/12/cyberobject/gcas/ui#' xmlns:ui='http://www.cyberobject.com/2012/12/ui#' xmlns:term='http://www.cyberobject.com/2012/12/term#' class='ui:WINDOW' template=”train_card”> <INPUT id=':input_s1’ param=':s1'></INPUT> <SEARCH id=':search_s2’ param=':s2'></SEARCH> <BUTTON id=':train’ appcmd=’train :s1 as :s2’>Train</BUTTON></TRAIN_CARD>";
		String string = "<xul> " + 
		 "<train_card xmlns=\"http://www.cyberobject.com/2012/12/cyberobject/gcas/uix#\" xmlns:ui=\"http://www.cyberobject.com/2012/12/ui#\" xmlns:term=\"http://www.cyberobject.com/2012/12/term#\" id=\"http://www.cyberobject.com/2012/12/cyberobject/gcas/uix#train_card27\" typeof=\"ui:WINDOW\"> " + 
		 "<input id=\"http://www.cyberobject.com/2012/12/cyberobject/gcas/uix#input27\" typeof=\"ui:INPUT\" param=\":s1\">a</input> " + 
		 "<search id=\"http://www.cyberobject.com/2012/12/cyberobject/gcas/uix#search27\" typeof=\"ui:SEARCH\" param=\":s2\">b</search> " + 
		 "<button id=\"http://www.cyberobject.com/2012/12/cyberobject/gcas/uix#button27\" typeof=\"ui:BUTTON\" appcmd=\"train s1 as s2\">Train</button> " + 
		 "</train_card> " + 
		 "</xul>";
		StringBuffer sb = new StringBuffer("&lt;speak&gt;Hello. Welcome to Cyber Object Virtual Intelligent Service Agent. CyberObject has been helping customers achieve their business goals and exceed their expectations since 1995. Your conversation may be monitored and recorded for quality purposes. My name is Alice.&lt;/speak&gt;");
		try {
			System.out.println(XmlUtil.encodingSting(string));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
