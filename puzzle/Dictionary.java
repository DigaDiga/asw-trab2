package wwwordz.puzzle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;

import wwwordz.puzzle.Trie.Search;

public class Dictionary extends java.lang.Object{

	private static Dictionary dict;
	private static Trie trie= new Trie();
	
	private Dictionary() {
		InputStream input = ClassLoader.getSystemResourceAsStream("wwwordz/puzzle/pt-PT-AO.dic");
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		try {
			String word;
			word=reader.readLine();
			 while((word = reader.readLine()) != null)  {
				int i;
				for(i =0;i<word.length();i++) {
					if(!Character.isLetter(word.charAt(i)))
						break;
				}
				word = word.substring(0,i);
				word = Normalizer.normalize(word.toUpperCase(Locale.ENGLISH),Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				trie.put(word);
				
			}
			input.close();
			reader.close();
		}
		catch(Exception e) {
			System.out.println("Exception  "+ e.getStackTrace()[2] + "  :"+e);
		}
	}

	public static Dictionary getInstance() {
		if (dict == null) 
			dict = new Dictionary();
			return dict;
	}
	
	public Trie.Search startSearch(){
		Search s = new Search(trie.startSearch());
		return s;
		
	}
	
	public java.lang.String getRandomLargeWord(){
		return trie.getRandomLargeWord();
	}
}
