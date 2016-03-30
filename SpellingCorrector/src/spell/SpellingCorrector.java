package spell;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeSet;
//testing
/*
 * Inside of TRIE class
 * possibly an array of nodes
 * wordCount
 * nodeCount
 * rootNode
 * 
 * Inside of NODE
 * frequencyCount
 * letter represented by node
 * an array of nodes.
 * position of an array is char - 97
 */
public class SpellingCorrector implements ISpellCorrector {
	private Trie dictionary;
	SpellingCorrector(){
		dictionary=new Trie();
	}
	public String toString(){
		return dictionary.toString();
	}
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		// TODO Auto-generated method stub
		FileReader fr=new FileReader(dictionaryFileName);
		Scanner s = new Scanner(fr); 
		while(s.hasNext()){
			this.dictionary.add(s.next());
		}
		s.close();
	}
	@Override
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		// TODO Auto-generated method stub
		if(dictionary.find(inputWord.toLowerCase()) != null){
			return inputWord.toLowerCase();
		}
		else{
			StringBuilder word=new StringBuilder();
			TreeSet<String> set=new TreeSet<String>();
			word.append(inputWord.toLowerCase());
			deletionDistance(word,set);
			transpositionDistance(word,set);
			alterationDistance(word,set);
			insertionDistance(word,set);
			int highFreq=0;
			String current=new String();
			for(String w:set){
				if(dictionary.find(w)!=null){
					int value=dictionary.find(w).getValue();
					if(value>highFreq){
						current=w;
						highFreq=value;
					}
				}
			}
			if (highFreq==0){
				for(String w:set){
					StringBuilder secondWord=new StringBuilder();
					TreeSet<String> secondSet=new TreeSet<String>();
					secondWord.append(w.toLowerCase());
					deletionDistance(secondWord,secondSet);
					transpositionDistance(secondWord,secondSet);
					alterationDistance(secondWord,secondSet);
					insertionDistance(secondWord,secondSet);
					for(String wo:secondSet){
						if(dictionary.find(wo)!=null){
							int value=dictionary.find(wo).getValue();
							if(value>highFreq){
								current=wo;
								highFreq=value;
							}
						}
					}
				}
			}
			if(highFreq==0)throw new NoSimilarWordFoundException();
			
			else{
				return current;
			}
		}
	}
	public void deletionDistance(StringBuilder word,TreeSet<String> set){
			for(int i=0;i<word.length();i++){
				StringBuilder change= new StringBuilder();
				change.append(word.toString());
				change.deleteCharAt(i);
				set.add(change.toString());
			}
	}
	public void transpositionDistance(StringBuilder word, TreeSet<String> set){
		for(int i=0;i<word.length()-1;i++){
			char temp=word.charAt(i);
			StringBuilder some=new StringBuilder();
			some.append(word);
			some.deleteCharAt(i);
			some.insert(i+1, temp);
			set.add(some.toString());
		}
	}
	public void alterationDistance(StringBuilder word, TreeSet<String> set){
		for(int i=0;i<26;i++){
			for(int j=0;j<word.length();j++){
				int ascii=i+97;
				String temp=Character.toString((char)ascii);
				StringBuilder change= new StringBuilder();
				change.append(word);
				change.deleteCharAt(j);
				change.insert(j,temp);
				set.add(change.toString());
			}
		}
	}
	public void insertionDistance(StringBuilder word, TreeSet<String> set){
		for(int i=0;i<=word.length();i++){
			for(int j=0;j<26;j++){
				int ascii=j+97;
				String temp=Character.toString((char)ascii);
				StringBuilder change=new StringBuilder();
				change.append(word);
				change.insert(i, temp);
				set.add(change.toString());
			}
		}
	}
}