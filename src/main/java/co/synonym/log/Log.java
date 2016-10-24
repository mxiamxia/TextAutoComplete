package co.synonym.log;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import co.synonym.utils.ContextConfig;

public class Log {

		private static Logger logger = Logger.getLogger("synonym");
		
		private static String log_p = "conf/synonym.properties";
		
		static{
			String config = ContextConfig.SYS_DIR + File.separator + log_p;
			PropertyConfigurator.configure(config);
		}
		

		public static void info(String mes) {
			logger.info(mes);
		}
		
		public static void error(String mes) {
			logger.error(mes);
		}
		
		public static void error(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		public static void error(String mes, Exception e) {
			logger.error(mes + "\n" + e.getMessage(), e);
		}
		
		public static void main(String[] args) throws Exception {
	        try{
	        	String p = null;
	        	p.equals("ddd");
	        }catch(Exception e){
	        	Log.error(e);
	        }
		}


}
