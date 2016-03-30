package spell;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import spell.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		Trie trie=new Trie();
		Trie tried=new Trie();
		FileReader fr=new FileReader(dictionaryFileName);
		Scanner s = new Scanner(fr); 
		while(s.hasNext()){
			trie.add(s.next());
		}
		FileReader dr=new FileReader(dictionaryFileName);
		Scanner a=new Scanner(dr);
		while(a.hasNext()){
			tried.add(a.next());
		}
		FileWriter fw=new FileWriter("out.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		PrintWriter pw=new PrintWriter(bw);
		pw.println(trie.toString());
		pw.println(trie.toString());
		pw.println(tried.toString());
		pw.close();
		System.out.print(trie.toString());
		System.out.print(tried.toString());
		/*ISpellCorrector corrector = new SpellingCorrector();
		ISpellCorrector corrected = new SpellingCorrector();
		corrector.useDictionary(dictionaryFileName);
		corrected.useDictionary(dictionaryFileName);
		System.out.print(corrector.toString());
		System.out.print(corrected.toString());
		String suggestion = corrector.suggestSimilarWord(inputWord);

		
		System.out.println("Suggestion is: " + suggestion);*/
	}

}
