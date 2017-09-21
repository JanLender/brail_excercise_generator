package cz.tyfloservis.brail;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsFilter {
	
	private static final Logger LOGGER = Logger.getLogger(WordsFilter.class.getName());
	
	private List<String> words;
	
	private List<String> filteredWords;
	
	private Pattern pattern;
	
	private List<Character> characters;
	
	
	public WordsFilter(List<String> words) {
		this.words = words;
	}
	
	public List<String> getWords(List<Character> characters) {
		this.characters = characters;
		createPattern();
		filterWords();
		return filteredWords;
	}
	
	public List<String> getWords(String characters) {
		return getWords(createCharacterListFromText(characters));
	}
	
	
	private void filterWords() {
		filteredWords  = new ArrayList<String>();
		for (String word : words) {
			if (word == null) {
				LOGGER.warning("Ignoring null value");
				continue;
			}
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				LOGGER.finest(word);
				filteredWords.add(word);
			}
		}
		
	}
	
	private List<Character>  createCharacterListFromText(String text) {
		List<Character> characters = new ArrayList<Character>();
		char[] charArray = text.toCharArray();
		for (char character :charArray ) {
			characters.add(character);
		}
		return characters;
	}

	private void createPattern() {
		StringBuilder builder = new StringBuilder("^[");
		for (char character : characters) {
			builder.append(character);
		}
		builder.append("]+$");
		String patternString = builder.toString();
		LOGGER.fine(patternString);
		pattern = Pattern.compile(patternString);
	}

}
