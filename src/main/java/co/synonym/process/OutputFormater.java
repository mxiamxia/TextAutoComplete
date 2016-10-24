package co.synonym.process;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import co.synonym.utils.XmlUtil;

public class OutputFormater {

	
	public String formatHeader()
	{
		String header = "<header><action value=\"synonym\"/><sessionid value=\"ss303242138079246682526945424915066257926\"/><userid value=\"cm-test-1424970665638\"/></header>";
		return header;
	}
	
	public String formatBody(String input)
	{
		String rst = "<body><phrases>";
		rst += input;
		rst += "</phrases></body>";
		return rst;
	}
	
	public String formatStatus(String code)
	{
		String details = "";
		String status = "<status><code value=\"%s\" /><text>%s</text></status>";
		if("9999".equalsIgnoreCase(code))
		{
			details = "no synonym found";
		}
		else if("0000".equalsIgnoreCase(code))
		{
			details = "found synonym for input";
		}
		return String.format(status, code, details);
	}
	
	public String getStatusString()
	{
		return "<response><status><code value=\"%s\" /><text>%s</text></status></response>";
	}
	
	public void writeOutput(HttpServletResponse response, Object obj) throws IOException
	{
		if (obj == null)
			return;

		String str = null;
		if (obj instanceof String)
			str = (String) obj;
		else
			str = obj.toString();
//		str = XmlUtil.encodeString(str);
		response.getWriter().print(str);
	}
	
}
