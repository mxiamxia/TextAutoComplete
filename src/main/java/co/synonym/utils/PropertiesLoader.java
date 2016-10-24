package co.synonym.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import co.synonym.log.Log;

public class PropertiesLoader {

	public static String config = "conf/synConfig.properties";

	public static Properties load() {
		Properties prop = new Properties();

		try {
			String prop_dir = null;
			if (!ContextConfig.ont_folder.endsWith(File.separator)) {
				prop_dir = ContextConfig.SYS_DIR + File.separator + config;
			} else {
				prop_dir = ContextConfig.SYS_DIR + config;
			}
			Log.info("sysPropertydir=" + prop_dir);
			prop.load(new FileInputStream(new File(prop_dir)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}

}
