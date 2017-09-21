package cz.tyfloservis.brail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordsFileReader {
	

	private static final Logger LOGGER = Logger.getLogger(WordsFileReader.class.getName());
	
	public List<String> read() {
		long start = System.currentTimeMillis();
		List<String> words = new ArrayList<String>();
		try {
		
			InputStream inputStream = WordsFileReader.class.getResourceAsStream("words");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while (true) {
				String line = reader.readLine();
				words.add(line);
				if (line == null) {
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		long duration = System.currentTimeMillis() - start;
		LOGGER.info("Readen: " + words.size() + ", duration: " + duration);
		return words;
	}

}
