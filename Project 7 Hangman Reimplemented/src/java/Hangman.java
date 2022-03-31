import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hangman {
    private String secretWord;
    private HashSet<Character> charGuesses;
    private ArrayList<Character> missedLetters;
    private int numWrongGuesses;
    public static final String randomWordsLocation = "C:\\GenSpark-Projects\\Project 7 Hangman Reimplemented\\src\\hangmanWords.txt";
    public static final String scoresLocation = "C:\\GenSpark-Projects\\Project 7 Hangman Reimplemented\\src\\scores.txt";
    public static final String hangmanMenLocation = "C:\\GenSpark-Projects\\Project 7 Hangman Reimplemented\\src\\hangmanMen.txt";

    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public HashSet<Character> getCharGuesses() {
        return charGuesses;
    }

    public void setCharGuesses(HashSet<Character> charGuesses) {
        this.charGuesses = charGuesses;
    }

    public ArrayList<Character> getMissedLetters() {
        return missedLetters;
    }

    public void setMissedLetters(ArrayList<Character> missedLetters) {
        this.missedLetters = missedLetters;
    }

    public int getNumWrongGuesses() {
        return numWrongGuesses;
    }

    public void setNumWrongGuesses(int numWrongGuesses) {
        this.numWrongGuesses = numWrongGuesses;
    }

    public String getRandomWordsLocation() {
        return randomWordsLocation;
    }

    public Hangman(){
        secretWord = generateRandWord();
        charGuesses = new HashSet<>();
        missedLetters = new ArrayList<>();
        numWrongGuesses = 0;
        playerName = "";
    }

    public Hangman(String secretWord){
        this.secretWord = secretWord.toLowerCase();
        charGuesses = new HashSet<>();
        missedLetters = new ArrayList<>();
        numWrongGuesses = 0;
    }
    //returns the hangman random word from a text file of 1000 words
    public String generateRandWord(){
        int max = 999;
        int min = 0;
        int randNum = (int) (Math.random() * (max - min + 1) + min); //generates random number
        String word;
        Path path = Paths.get(randomWordsLocation);

        try {
            word = Files.readAllLines(path).get(randNum);
        } catch(Exception e){
            System.out.println("Failed to read from hangmanWords.txt file");
            return "";
        }

        return word.toLowerCase();
    }
    //prints the game for each letter guess, returns true if player has won
    public boolean printRoundStart(){
        //generates the secret word with missing, unguessed letters as '_'
        String hangmanWord;
        String[] hangmanMen = new String[0];
        String currMan = "";

        //generates proper string for characters guessed and missing in secret word
        hangmanWord = Arrays.stream(secretWord.split(""))
                .map(character -> charGuesses.contains(character.charAt(0)) ? character : "_")
                .reduce((acc, e) -> acc + e).orElse("");

        if(!hangmanWord.contains("_")){
            return true;
        }

        //gets proper hangman graphic from text file
        try {
            hangmanMen = Files.lines(Paths.get(hangmanMenLocation))
                    .reduce((acc, line) -> acc + "\n" + line).orElse("")
                    .split("##########");
            currMan = hangmanMen[numWrongGuesses];
        }catch(Exception e){
            System.out.println("Get hangmanMen from file Failed!");
            return false;
        }

        //generates list of missed letters
        String missedLettersStr = missedLetters.stream()
                .map(Object::toString)
                .reduce((acc, e) -> acc + e).orElse("");
        System.out.println(currMan);
        System.out.println("Missed letters: " + missedLettersStr);
        System.out.println(hangmanWord);

        return false;
    }

    //checks user guess and returns true if its a new guess
    public boolean checkGuess(Scanner s){
        String currGuess;
        char charGuess;
        //input validation: single letter, not already guessed
        currGuess = s.tokens()
                .filter(line -> line.trim().length() == 1 && Character.isAlphabetic(line.trim().charAt(0)) && !charGuesses.contains(line.trim().toLowerCase().charAt(0)))
                .findFirst().orElse("");

        //checks if guessed already
        charGuess = currGuess.toLowerCase().charAt(0);
        if(!charGuesses.contains(charGuess)){
            if(!secretWord.contains("" + charGuess)){
                missedLetters.add(charGuess);
                numWrongGuesses++;
            }
            charGuesses.add(charGuess);
        } else {
            System.out.println("You have already guessed that letter. Choose again.");
            System.out.println("Guess a letter.");
            return false;
        }

        return true;
    }
    //returns true if user still wants to play and resets attributes
    public boolean checkPlayAgain(Scanner s){
        String userInput;
        boolean stillPlaying = true;

        System.out.println("Do you want to play again? (yes or no)");
        userInput = s.tokens()
                .filter(line -> line.trim().equalsIgnoreCase("yes") || line.trim().equalsIgnoreCase("no"))
                .findFirst().orElse("");
        if (userInput.equalsIgnoreCase("yes")) {
            resetClassAttributes();
        } else if (userInput.equalsIgnoreCase("no")) {
            stillPlaying = false;
        }

        return stillPlaying;
    }
    //resets attributes if player plays again
    public void resetClassAttributes(){
        charGuesses = new HashSet<>();
        missedLetters = new ArrayList<>();
        numWrongGuesses = 0;
        secretWord = generateRandWord();
    }

    public String recordAndCheckScore(){
        List<String> allScores = new ArrayList<>();
        List<String> scoresOfInterest = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        String playerInFile;
        int scoreInFile;

        //reads file into List
        Path pathToScores = Paths.get(scoresLocation);
        try{
            allScores = Files.readAllLines(pathToScores);
        }catch(Exception e){
            System.out.println("Failed to read from scores.txt file");
            return "";
        }

        //reads in the players entry and any entries with scores equal or better than the players highest
        scoresOfInterest = allScores.stream()
                .filter(line -> (line.contains(", ") && line.substring(0, line.indexOf(',')).equals(playerName)) ||
                        (line.contains(", ") && Integer.parseInt(line.split(", ")[1]) <= numWrongGuesses))
                .collect(Collectors.toList());
        playerInFile = scoresOfInterest.stream()
                .filter(line -> line.substring(0, line.indexOf(',')).equals(playerName))
                .findFirst().orElse("");
        scoreInFile = !playerInFile.equals("") ?
                Integer.parseInt(playerInFile.split(", ")[1]) : -1;

        //replaces or adds a players name and high score (format: {name, score}) to List if returning or new player
        if(!playerInFile.equals("")){
            if(scoreInFile > numWrongGuesses){
                output.append("You have achieved your highest score yet! Only " + numWrongGuesses + " wrong guesses!\n");
                if(scoresOfInterest.size() == 1){
                    output.append("You have achieved the highest score of all players!\n");
                }
                allScores.set(allScores.indexOf(playerInFile), playerInFile.substring(0, playerInFile.indexOf(',')) + ", " + numWrongGuesses);
            }
        } else {
            output.append("Thanks for playing for the first time, you score has been recorded. Only " + numWrongGuesses + " wrong guesses!\n");
            if(allScores.size() == 0){
                output.append("You are the first player to play, so you have the highest score of all players!\n");
            } else if (scoresOfInterest.size() == 0){
                output.append("You have achieved the highest score of all players!\n");
            }
            allScores.add(playerName.toLowerCase() + ", " + numWrongGuesses);
        }

        //writes the List back to file with changes
        try{
            Files.write(pathToScores, allScores);
        }catch(Exception e){
            System.out.println("Failed to write to scores.txt file");
            return "";
        }

        return output.toString();
    }


    public static void main(String[] args) {
        Hangman hangman = new Hangman();
        Scanner s = new Scanner(System.in);
        boolean keepPlaying = true;

        //starts game, gets player name
        System.out.println("Welcome to Hangman!!!");
        System.out.println("\nPlease type your name...");
        String name = s.tokens()
                .filter(line -> line.trim().length() >= 1)
                .findFirst().orElse("");
        hangman.setPlayerName(name);
        System.out.println("Hello " + hangman.getPlayerName() + "!");
        System.out.println();
        System.out.println("GAME START");

        //loops until player wants to stop playing games
        while(keepPlaying) {
            //checks if player has won
            if (hangman.printRoundStart()) {
                System.out.println("Yes! The secret word is \"" + hangman.secretWord + "\"! You have won!");
                System.out.println(hangman.recordAndCheckScore());
                keepPlaying = hangman.checkPlayAgain(s);
            } else {
                System.out.println("Guess a letter.");
                hangman.checkGuess(s);

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
