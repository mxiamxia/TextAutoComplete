package co.synonym.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import co.synonym.log.Log;
import co.synonym.lru.LRUCache;
import co.synonym.process.OutputFormater;
import co.synonym.process.QuerySynonym;
import co.synonym.process.SynonymDict;
import junit.framework.Assert;

public class SynonymServlet extends HttpServlet {

	private static final long serialVersionUID = -3299293999574078342L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf8");
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			processRequest(request, response);

		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String body = null;
		String status = null;
		String output = "";
		String code = "9999";
		String requestData = request.getParameter("request");
		String mode = request.getParameter("mode");
		String src = request.getParameter("src");
		String type = request.getParameter("type");
		String method = request.getParameter("method");
		String userId = request.getParameter("name");
		OutputFormater fmt = new OutputFormater();
		String header = fmt.formatHeader();
		;
		JSONObject jresp = new JSONObject();

		if ("lruList".equalsIgnoreCase(method)) {
			LRUCache lru = AppContext.getLruCache(userId.trim());
			List<String> phrases = lru.getLruList();
			if (!phrases.isEmpty()) {
				code = "0000";
				String text = "found lru phrases";
				JSONObject jheader = generateHeader();
				JSONObject jbody = generateLruListBody(phrases);
				JSONObject jstatus = generateStatus(code, text);
				jresp.put("header", jheader);
				jresp.put("body", jbody);
				jresp.put("status", jstatus);
			} else {
				code = "9999";
				String text = "not found lru phrases";
				JSONObject jheader = generateHeader();
				JSONObject jstatus = generateStatus(code, text);
				jresp.put("header", jheader);
				jresp.put("status", jstatus);
			}
			Log.info("The query lruList response: " + jresp.toString());
			fmt.writeOutput(response, jresp.toString());

		} else if ("lruUpdate".equalsIgnoreCase(method)) {
			String phrase = request.getParameter("phrase");
			LRUCache lru = AppContext.getLruCache(userId.trim());
			lru.set(requestData, phrase);
			code = "0000";
			String text = "found lru phrases";
			JSONObject jheader = generateHeader();
			JSONObject jstatus = generateStatus(code, text);
			jresp.put("header", jheader);
			jresp.put("status", jstatus);
			Log.info("The query lruUpdate response: " + jresp.toString());
			fmt.writeOutput(response, jresp.toString());
		} else {
			if (StringUtils.isEmpty(mode)) {
				mode = "partial";
			}

			if (StringUtils.isEmpty(requestData))
				requestData = getRequestData(request);

			if (StringUtils.isEmpty(requestData)) {
				String resp = String.format(fmt.getStatusString(), "9999", "Server is running. Request is blank.", "");
				Log.info("Request is blank.");
				fmt.writeOutput(response, resp);
				return;
			}
			Log.info("The query synonym request: " + requestData);
			try {
				long start = System.currentTimeMillis();
				QuerySynonym query = new QuerySynonym();
				String phrases = query.querySynonym(requestData, mode, src, type);
				Log.info("Time spent on query: " + (System.currentTimeMillis() - start) + "ms");
				if (!StringUtils.isEmpty(phrases)) {
					Log.info("The synonym found. ");
					code = "0000";
					body = fmt.formatBody(phrases);
					status = fmt.formatStatus(code);
				} else {
					body = "";
					Log.info("The synonym not found. ");
					status = fmt.formatStatus(code);
				}
			} catch (Throwable e) {
				String details = e.getMessage();
				String resp = String.format(fmt.getStatusString(), "9999", details);
				Log.info("Exception : " + details);
				Log.info("The query synonym response: " + resp);
				fmt.writeOutput(response, resp);
			}
			output = "<response>" + header + body + status + "</response>";
			Log.info("The query synonym response: " + output);
			JSONObject json = XML.toJSONObject(output);
			Log.info("Json resonse" + json.toString());
			fmt.writeOutput(response, json.toString());
		}
	}

	private JSONObject generateStatus(String code, String text) {
		// TODO Auto-generated method stub
		JSONObject status = new JSONObject();
		JSONObject c = new JSONObject();
		c.put("value", code);
		status.put("code", c);
		status.put("text", text);
		return status;
	}

	private JSONObject generateLruListBody(List<String> phrases) {
		// TODO Auto-generated method stub
		JSONObject body = new JSONObject();
		JSONArray list = new JSONArray();
		for (String phrase : phrases) {
			list.put(phrase);
		}
		body.put("phrases", list);
		return body;
	}

	private JSONObject generateHeader() {
		// TODO Auto-generated method stub
		JSONObject header = new JSONObject();
		JSONObject action = new JSONObject();
		JSONObject sessionid = new JSONObject();
		JSONObject userid = new JSONObject();
		action.put("value", "synonym");
		sessionid.put("value", "ss303242138079246682526945424915066257926");
		userid.put("value", "co-synonym");
		header.put("action", action);
		header.put("sessionid", sessionid);
		header.put("userid", userid);
		return header;
	}

	private String getRequestData(HttpServletRequest request) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buf.append(line);
			}
			return buf.toString();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception ex) {
			}
			reader = null;
		}
	}

}
