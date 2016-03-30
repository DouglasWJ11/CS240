package hangman;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EvilHangman implements IEvilHangmanGame {
	private Set<String> wordList;
	private Set<String> currentList;
	private Set<Character> madeGuesses;
	private int numberofGuesses;
	
	public EvilHangman(){
		madeGuesses=new TreeSet<Character>();
		numberofGuesses=0;
	}
	@Override
	public void startGame(File dictionary, int wordLength) {
		// TODO Auto-generated method stub
		FileReader fr;
		try {
			fr = new FileReader(dictionary);
			Scanner s = new Scanner(fr); 
			wordList=new TreeSet<String>();
			while(s.hasNext()){
				wordList.add(s.next().toLowerCase());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentList=new TreeSet<String>();
		numberofGuesses=wordLength;
		for(String word:wordList){
			if(word.length()==wordLength){
				currentList.add(word);
			}
		}
	}
	public String makePattern(String word,char guess){
		StringBuilder pattern=new StringBuilder();
		for(char c:word.toCharArray()){
			if(c!=guess){
				pattern.append('_');
			}
			else pattern.append(c);
		}
		return pattern.toString();
	}
	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		// TODO Auto-generated method stub
		if(madeGuesses.contains(guess))throw new GuessAlreadyMadeException() ;
		else{
			Map<String, TreeSet<String>> patterns=new TreeMap<String,TreeSet<String>>();
			for(String w:currentList){
				String pattern=makePattern(w,guess);
				if(patterns.get(pattern)==null){
					TreeSet<String> patset=new TreeSet<String>();
					patset.add(w);
					patterns.put(pattern,patset);
				}
				else{
					patterns.get(pattern).add(w);
				}
			}
			int largest=0;
			Set<String> currentSet=new TreeSet<String>();
			for (Entry<String, TreeSet<String>> s:patterns.entrySet()){
				if(s.getValue().size()>largest){
					largest=s.getValue().size();
					currentSet=s.getValue();
				}
			}
			madeGuesses.add(guess);
			numberofGuesses++;
			currentList=currentSet;
			return currentSet;
		}
	}

}
