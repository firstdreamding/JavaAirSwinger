package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static List<String> readLines(String path) {
		List<String> results = new ArrayList<String>();
		try {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		while ((line = reader.readLine()) != null) {
			results.add(line);
		}
		reader.close();
		} catch (IOException e) {
		}
		return results;
	}
	
	
}
