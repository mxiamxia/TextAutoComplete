package co.synonym.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import co.synonym.log.Log;

public class FileLoader {
	public static List<String> loadStringFromFile(String fileName) {
		List<String> ret = new ArrayList<String>();
		File file = new File(FileLoader.class.getClassLoader().getResource(fileName).getFile());
		Scanner scan;
		try {
			scan = new Scanner(file);
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				ret.add(line);
			}
		} catch (FileNotFoundException e) {
			Log.error(e);
		}
		return ret;
	}

}
