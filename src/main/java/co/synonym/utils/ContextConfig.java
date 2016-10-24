package co.synonym.utils;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import co.synonym.log.Log;

public class ContextConfig {

	public static String SYS_DIR = null;
	// public final static String env = "prod";
	public final static String env = "local";
	public final static String local_dir_folder = "iplatform/application/co-termnet-synonym/";
	public final static String prod_dir_folder = "cyberobject/apps/co-termnet-synonym/";
	public final static String local_ont_folder = "iplatform/application/co-termnet-synonym/ontology/";
	public final static String prod_ont_folder = "cyberobject/apps/co-termnet-synonym/ontology/";
	public static String ont_folder = null;
	static {
		initTEMPDIR();
	}

	// ${cyberobject.home}/apps/co-nlu-ontology/conf/engine.xml
	public static void initTEMPDIR() {
		// get from system environment.
		String tempDir = "";
		String ntelDir = null;
		String fileDir = null;
		String sysDir = null;
		if (ContextConfig.env.equalsIgnoreCase("local")) {
			ntelDir = System.getProperty("NTEL.DIR");
			fileDir = local_ont_folder;
			sysDir = local_dir_folder;
		} else {
			ntelDir = System.getProperty("catalina.home");
			fileDir = prod_ont_folder;
			sysDir = prod_dir_folder;
		}
		if (StringUtils.isEmpty(ntelDir)) {
			try {
				ntelDir = System.getenv("catalina.home");
			} catch (Throwable e) {
				System.out.println("Can't get TAFIDIR from system env.");
			}
		}
		if (ntelDir == null) {
			ntelDir = "";
		}
		if (ntelDir.endsWith(File.separator)) {
			ntelDir = ntelDir.substring(0, ntelDir.length() - 1);
		}
		ContextConfig.SYS_DIR = ntelDir + File.separator + sysDir;
		Log.info("Synonym System Dir " + ContextConfig.SYS_DIR);
		tempDir = ntelDir + File.separator + fileDir;
		ont_folder = tempDir;
	}

}
