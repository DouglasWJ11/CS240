package hangman;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EvilHangman game=new EvilHangman();
		File dictionary=new File(args[0]);
		int wordLength=Integer.parseInt(args[1]);
		int guesses=Integer.parseInt(args[2]);
		game.startGame(dictionary,wordLength);
		String guess;
		int guessesUsed=0;
		int blanks=wordLength;
		Scanner in=new Scanner(System.in);
		TreeSet<String> words=new TreeSet<String>();
		TreeSet<Character> usedLetters=new TreeSet<Character>();
		char[] word=new char[wordLength];
		for(int i=0;i<wordLength;i++)
		{
			word[i]='_';
		}
		while(guessesUsed<guesses&&blanks>0)
		{
			System.out.println("You have "+(guesses-guessesUsed)+" guesses left.\n Used Letters: " +usedLetters.toString()+
				"\nWord: "+String.valueOf(word)+"\nGuess a letter: ");
			guess=in.nextLine();
			while(guess.isEmpty()){
				System.out.println("Bad input. Guess a letter");
				guess=in.nextLine();
			}
			char g=guess.charAt(0);
			while(!Character.isLetter(g)||guess.length()>1){
				System.out.println("Bad input. Guess a letter");
				guess=in.nextLine();
				while(guess.isEmpty()){
					System.out.println("Bad input. Guess a letter");
					guess=in.nextLine();
				}
				g=guess.charAt(0);
			}
			g=Character.toLowerCase(g);
			try {
				words=(TreeSet<String>) game.makeGuess(g);
				String c = null;
				c=Character.toString(g);
				if(!words.first().contains(c)){
					guessesUsed++;
				}
				String temp;
				temp=game.makePattern(words.first(),g);
				for(int i=0;i<wordLength;i++){
					if(temp.toCharArray()[i]!='_'){
						word[i]=temp.toCharArray()[i];
						blanks--;
					}
				}
				usedLetters.add(g);
			} catch (GuessAlreadyMadeException e) {
				// TODO Auto-generated catch block
				System.out.print("Letter already guessed. Guess another letter\n");
				//guess=in.nextLine();
			}
		}
		if(words.size()==1&&blanks==0){
			System.out.print("Congratulations you guessed the word! It was "+words.first());
		}
		else{
			String [] a = new String[words.size()];
			words.toArray(a);
			Random rnd=new Random();
			rnd.setSeed(31);
			System.out.print("You lose. The word was: "+ a[rnd.nextInt(a.length)]+'\n');
		}
		in.close();
	}
}
