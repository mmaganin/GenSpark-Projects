import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

public class Hangman {
    private String secretWord;
    private HashSet<Character> charGuesses;
    private ArrayList<Character> missedLetters;
    private int numWrongGuesses;
    private String currMan;
    private final String randomWordsLocation = "C:\\GenSpark-Projects\\Project 5 Hangman\\src\\hangmanWords.txt";

    public Hangman(){
        try{
            Scanner s = new Scanner(new File(randomWordsLocation));
            secretWord = generateRandWord(s);
            s.close();
        }catch(Exception e){
            System.exit(1);
        }

        charGuesses = new HashSet<>();
        missedLetters = new ArrayList<>();
        numWrongGuesses = 0;
        currMan =
                "+---+\n" +
                        "    |\n" +
                        "    |\n" +
                        "    |\n" +
                        "   ===";
    }

    public Hangman(String secretWord){
        this.secretWord = secretWord.toLowerCase();
        charGuesses = new HashSet<>();
        missedLetters = new ArrayList<>();
        numWrongGuesses = 0;
        currMan =
                "+---+\n" +
                "    |\n" +
                "    |\n" +
                "    |\n" +
                "   ===";
    }
    //returns the hangman random word from a text file of 1000 words
    public String generateRandWord(Scanner s){
        int max = 1000;
        int min = 1;
        int randNum = (int) (Math.random() * (max - min + 1) + min); //generates random number

        String word = "";
        for (int i = 0; i < randNum; i++) {
            word = s.nextLine();
        }

        return word;
    }
    //prints the game for each letter guess, returns true if player has won
    public boolean printRoundStart(){
        //generates the secret word with missing, unguessed letters as '_'
        boolean hasWonYet = true;
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < secretWord.length(); i++){
            if(charGuesses.contains(secretWord.charAt(i))){
                str.append(secretWord.charAt(i));
            } else {
                hasWonYet = false;
                str.append('_');
            }
        }

        if(hasWonYet){
            return true;
        }
        //generates proper hangman graphic
        char[] newMan = currMan.toCharArray();
        switch(numWrongGuesses){
            case 1:
                //head
                newMan[6] = 'O';
                break;
            case 2:
                //torso
                newMan[12] = '|';
                break;
            case 3:
                //one arm
                newMan[13] = '-';
                break;
            case 4:
                //2 arms plus torso
                newMan[12] = '+';
                newMan[13] = ' ';
                break;
            case 5:
                //one leg
                newMan[19] = '\\';
                break;
        }
        currMan = String.valueOf(newMan);

        //displays guessed, incorrect letters
        StringBuilder missedLettersStr = new StringBuilder();
        for(var letter : missedLetters){
            missedLettersStr.append(letter);
        }
        System.out.println(currMan);
        System.out.println("Missed letters: " + missedLettersStr);
        System.out.println(str);

        return false;
    }
    //returns a user's input
    public String readUserInput(Scanner s){
        String userInput = "";

        try{
            userInput = s.nextLine();
        }catch(Exception e){
            System.out.println("User input failed");
            System.exit(1);
        }

        return userInput;
    }
    //checks user guess and returns true if its a new guess
    public boolean checkGuess(Scanner s){
        String currGuess = readUserInput(s);
        //input validation
        while(currGuess.length() != 1){
            System.out.println("Please enter one letter.");
            currGuess = readUserInput(s);
        }
        //checks if guessed already
        char charGuess = currGuess.toLowerCase().charAt(0);
        if(!charGuesses.contains(charGuess)){
            if(!secretWord.contains("" + charGuess)){
                missedLetters.add(charGuess);
                numWrongGuesses++;
            }
            charGuesses.add(charGuess);
        } else {
            return false;
        }

        return true;
    }
    //returns true if user still wants to play and resets attributes
    public boolean checkPlayAgain(Scanner s){
        String userInput;
        boolean badInput = true;
        boolean stillPlaying = true;

        System.out.println("Do you want to play again? (yes or no)");
        userInput = readUserInput(s);
        while(badInput) {
            if (userInput.equals("yes")) {
                badInput = false;
                resetClassAttributes();
            } else if (userInput.equals("no")) {
                stillPlaying = false;
                badInput = false;
            } else {
                System.out.println("Please enter only yes or no.");
                userInput = readUserInput(s);
            }
        }

        return stillPlaying;
    }
    //resets attributes if player plays again
    public void resetClassAttributes(){
        charGuesses = new HashSet<>();
        missedLetters = new ArrayList<>();
        numWrongGuesses = 0;
        currMan = "+---+\n" +
                "    |\n" +
                "    |\n" +
                "    |\n" +
                "   ===";
        try{
            Scanner s2 = new Scanner(new File(randomWordsLocation));
            secretWord = generateRandWord(s2);
            s2.close();
        }catch(Exception e){
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Hangman hangman = new Hangman();
        Scanner s = new Scanner(System.in);
        String currGuess;
        boolean keepPlaying = true;

        //starts game
        System.out.println("HANGMAN");
        while(keepPlaying) {
            //checks if player has won
            if (hangman.printRoundStart()) {
                System.out.println("Yes! The secret word is \"" + hangman.secretWord + "\"! You have won!");
                keepPlaying = hangman.checkPlayAgain(s);
            } else {

                System.out.println("Guess a letter.");
                while (!hangman.checkGuess(s)) {
                    System.out.println("You have already guessed that letter. Choose again.");
                    System.out.println("Guess a letter.");
                }
                //checks if player has lost
                if(hangman.numWrongGuesses > 5){
                    System.out.println("You have lost the game! You have ran out of guesses.");
                    keepPlaying = hangman.checkPlayAgain(s);
                }
            }
        }

        s.close();
    }
}
