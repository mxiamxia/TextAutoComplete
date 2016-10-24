package co.synonym.jena;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import co.synonym.log.Log;
import co.synonym.process.SynonymDict;
import co.synonym.utils.ContextConfig;
import co.synonym.utils.PropertiesLoader;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

public class JenaManager {
	
	@SuppressWarnings("unchecked")
	public static void loadModel()
	{
		String ontology = SynonymDict.p.getProperty("ontology");
		if(StringUtils.isEmpty(ontology))
		{
			Log.error("Can not find ontology file path from property files");
		}
		else
		{
			try
			{
				String[] path = ontology.split(";");
				loadModel(path);
			}
			catch(Exception e)
			{
				Log.error(e);
			}
		}
	}

	private static void loadModel(String[] path) 
	{
		// TODO Auto-generated method stub
		
		for(String pair : path)
		{
			Model model = ModelFactory.createDefaultModel();
			try
			{
				String[] ont = pair.split(",");
				String f_path = ContextConfig.ont_folder + ont[1];
				loadModelDir(model, f_path);
				showModelSize(model, ont[0]);
				loadSynonym(model,ont[0]);
				SynonymDict.sources.add(ont[0]);
				model.close();
			}
			catch(Exception e)
			{
				Log.error(e);
				model.close();
			}
		}
		
	}

	private static void loadModelDir(Model m, String ont_folder)
	{
		// TODO Auto-generated method stub
		File folder = new File(ont_folder);
		try
		{
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) 
				{
					System.out.println(ont_folder + " is not a ttl file");
				} 
				else 
				{
					if (fileEntry.isFile()) 
					{
						String fileName = ont_folder + File.separator + fileEntry.getName();
						FileManager.get().readModel(m, fileName);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected static void showModelSize(Model m, String ont) 
	{
		Log.info(String.format("The model contains %d triples for model %s",
				m.size(), ont));
	}

	/**
	 * List the names of cheeses to stdout
	 * @param ont 
	 */
	protected static void loadSynonym(Model m, String ont) 
	{
		Map data = new Hashtable();
		Property p = m.createProperty(JenaSchema.TERM + "synonym");
		Selector selector = new SimpleSelector(null, p, (RDFNode)null);
		for (StmtIterator i = m.listStatements(selector); i.hasNext();) 
		{
			Statement r = i.next();
			String sub = r.getSubject().toString();
			String obj = r.getObject().toString();
			addTokenizedWord(obj);
			data.put(sub, obj);
		}
		SynonymDict.syn_dic.put(ont, data);
	}

	
	
	private static void addTokenizedWord(String str) {
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreElements()) {
			String key = st.nextToken();
			if(SynonymDict.word_dict.containsKey(key)) {
				int counter = (Integer) SynonymDict.word_dict.get(key);
				SynonymDict.word_dict.put(key, counter++);
			}
		}
		
	}

	public static void main(String[] arg)
	{
		ContextConfig.initTEMPDIR();
		loadModel();
	}
}
