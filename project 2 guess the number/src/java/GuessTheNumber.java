import java.util.Scanner;

public class GuessTheNumber {
    private String playerName;
    private int randNum;
    private int numGuesses;
    private boolean wonTheGame;

    public GuessTheNumber(){
        int max = 20;
        int min = 1;
        //generates random number to guess
        randNum = (int) (Math.random() * (max - min + 1) + min);
        wonTheGame = false;
        numGuesses = 0;
        playerName = "";
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getRandNum() {
        return randNum;
    }

    public boolean isWonTheGame() {
        return wonTheGame;
    }

    public void setWonTheGame(boolean wonTheGame) {
        this.wonTheGame = wonTheGame;
    }

    public void setRandNum(int randNum) {
        this.randNum = randNum;
    }
    //reads user input using Scanner
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
    //evaluates users guess
    public String checkGuess(String currGuess){
        String guessResponse;

        numGuesses++;
        if (Integer.parseInt(currGuess) == randNum) {
            guessResponse = "Good job, " + playerName + "! You guessed my number in " + numGuesses + " guesses!";
            wonTheGame = true;
        } else {
            if(Integer.parseInt(currGuess) > randNum){
                guessResponse = "Your guess is too high.";
            } else {
                guessResponse = "Your guess is too low.";
            }
        }

        return guessResponse;
    }

    //evaluates user input to keep playing
    public boolean checkPlayAgain(Scanner s, String userInput) {
        boolean stillPlaying = true;
        boolean badInput = true;

        while(badInput) {
            if (userInput.equals("y")) {
                badInput = false;
            } else if (userInput.equals("n")) {
                stillPlaying = false;
                badInput = false;
            } else {
                System.out.println("Please enter only y or n.");
                userInput = readUserInput(s);
            }
        }

        return stillPlaying;
    }

    //gives user 6 guesses for the random number
    public void playerGuesses(Scanner s, int maxGuesses){
        String currGuess;
        while (maxGuesses > 0) {
            currGuess = readUserInput(s);

            //exits loop if correct
            System.out.println(checkGuess(currGuess));
            if(isWonTheGame()){
                break;
            }
            System.out.println("Take a guess.");

            maxGuesses--;
        }
    }

    public void startGame(Scanner s){
        System.out.println("Hello! What is your name? ");
        String name = readUserInput(s);
        setPlayerName(name);

        //Loop continues until player no longer wants to play
        while(true) {
            int max = 20;
            int min = 1;
            //generates random number to guess
            randNum = (int) (Math.random() * (max - min + 1) + min);

            System.out.println("Well, " + name + ", I am thinking of a number between 1 and 20.");
            System.out.println("Take a guess.");

            //gives user 6 guesses for the random number
            playerGuesses(s, 6);

            numGuesses = 0;
            wonTheGame = false;

            //User input try catch for playing again
            System.out.println("Would you like to play again? (y or n)");
            String playAgain = readUserInput(s);

            //evaluates user input to keep playing
            if(!checkPlayAgain(s, playAgain)){
                break;
            }
        }
    }

    public static void main(String[] args) {
        GuessTheNumber guessTheNumber = new GuessTheNumber();
        Scanner s = new Scanner(System.in);
        guessTheNumber.startGame(s);
        s.close();
    }
}
