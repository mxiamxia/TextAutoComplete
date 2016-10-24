package co.synonym.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import co.synonym.jena.JenaManager;
import co.synonym.process.SynonymDict;
import co.synonym.utils.ContextConfig;
import co.synonym.utils.PropertiesLoader;

public class InitCxtListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ContextConfig.initTEMPDIR();
		SynonymDict.p = PropertiesLoader.load();
		try {
			// load ttl
			JenaManager.loadModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}
}
