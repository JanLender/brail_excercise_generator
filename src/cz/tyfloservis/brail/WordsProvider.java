package cz.tyfloservis.brail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordsProvider {
	
//	private static final Logger LOGGER = Logger.getLogger(WordsProvider.class.getName());
	
	private String characters;
	
	private String preferredChararacters;
	
	private List<String> words;
	
	private List<String> filteredWords;
	
	private int count;
	
	private  List<String> doGenerate() {
		WordsFileReader reader = new WordsFileReader();
		WordsFilter filter = new WordsFilter(reader.read());
		words = filter.getWords(characters);
		filteredWords = new ArrayList<String>();
		if (preferredChararacters == null || preferredChararacters.length() < 1) {
			preferredChararacters = characters;
		}
		if (words.size() > 0) {
			do  {
				findWordForEachPreferredCharacter();				
			} while(filteredWords.size() > 0 && filteredWords.size() < count);
		}
		
		return filteredWords;
	}

	private void findWordForEachPreferredCharacter() {
		for (char character : preferredChararacters.toCharArray()) {
			Collections.shuffle(words);
			findWordForCharacter(character);
			if (filteredWords.size() == count) {
				break;
			}
		}
	}

	private void findWordForCharacter(char character) {
		for (String word : words) {
			if (word.indexOf(character) > -1) {
				filteredWords.add(word);
				break;
			}
		}
	}
	
	public String generate() {
		doGenerate();
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (String word : filteredWords) {
			if (! first) {
				builder.append(" ");
			}
			builder.append(word);
			first = false;
			
		}
		
		return builder.toString();
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public String getPreferredChararacters() {
		return preferredChararacters;
	}

	public void setPreferredChararacters(String preferredChararacters) {
		this.preferredChararacters = preferredChararacters;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
