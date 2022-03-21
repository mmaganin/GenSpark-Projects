import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        int max = 20;
        int min = 1;
        //generates random number to guess
        int randNum = (int) (Math.random() * (max - min + 1) + min);

        System.out.println("Hello! What is your name? ");
        Scanner s = new Scanner(System.in);
        String name = "";
        try{
            name = s.nextLine();
        }catch(Exception e){
            System.out.println("User input failed");
            return;
        }

        //Loop continues until player no longer wants to play
        boolean stillPlaying = true;
        while(stillPlaying) {
            System.out.println("Well, " + name + ", I am thinking of a number between 1 and 20.");
            System.out.println("Take a guess.");

            //Loop gives user 6 guesses for the random number
            String currGuess;
            int maxGuesses = 6;
            int numGuesses = 0;
            while (maxGuesses > 0) {
                //reads user inputted guess
                try{
                    currGuess = s.nextLine();
                }catch(Exception e){
                    System.out.println("User input failed");
                    return;
                }
                //evaluates users guess and exits loop if correct
                if (Integer.parseInt(currGuess) == randNum) {
                    numGuesses++;
                    System.out.println("Good job, " + name + "! You guessed my number in " + numGuesses + " guesses!");
                    break;
                } else {
                    if(Integer.parseInt(currGuess) > randNum){
                        System.out.println("Your guess is too high.");
                    } else {
                        System.out.println("Your guess is too low.");
                    }
                    System.out.println("Take a guess.");
                }

                numGuesses++;
                maxGuesses--;
            }

            //User input try catch for playing again
            String playAgain;
            System.out.println("Would you like to play again? (y or n)");
            try{
                playAgain = s.nextLine();
            }catch(Exception e){
                System.out.println("User input failed");
                return;
            }

            //evaluates user input to keep playing
            if(playAgain.length() == 1){
                if(playAgain.equals("y")){
                } else if(playAgain.equals("n")){
                    stillPlaying = false;
                }
                else{
                    System.out.println("Please enter only y or n.");
                }
            } else {
                System.out.println("Please enter only y or n.");
            }
        }
    }
}
