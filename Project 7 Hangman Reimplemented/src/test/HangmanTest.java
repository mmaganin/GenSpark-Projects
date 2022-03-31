import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTest {

    Hangman hangman;
    static Scanner scanner;

    @BeforeAll
    static void setUpAll(){
        scanner = new Scanner(new ByteArrayInputStream("a\nt\nt\no\ne\ns\nw\nr\nd".getBytes()));
        try{
            Files.write(Path.of(Hangman.scoresLocation), new ArrayList<String>());
        }catch(Exception e){
            System.out.println("Failed to make scores.txt empty in Test Setup");
        }
    }

    @BeforeEach
    void setUp() {
        hangman = new Hangman("testWord");
    }

    @DisplayName("Test generateRandWord")
    @Test
    void generateRandWord() {
        String secretWord1 = "";
        String secretWord2 = "";

        secretWord1 = hangman.generateRandWord();
        secretWord2 = hangman.generateRandWord();
        System.out.println(secretWord1 + " " + secretWord2);

        assertNotEquals(secretWord1, "", "generateRandWord Failed (empty string)");
        assertNotEquals(secretWord1, secretWord2, "generateRandWord Failed (equal strings (possible to fail test but very unlikely when working), \n" +
                "(text file uses 1000 words))");
    }

    @DisplayName("Test printRoundStart")
    @Test
    void printRoundStart() {
        String secretWord = hangman.getSecretWord();
        HashSet<Character> charGuesses = hangman.getCharGuesses();
        int i;
        for(i = 0; i < secretWord.length() - 1; i++){
            charGuesses.add(secretWord.charAt(i));
            assertFalse(hangman.printRoundStart(), "Failed printRoundStart (won game unexpectedly))");
        }
        charGuesses.add(secretWord.charAt(i));
        assertTrue(hangman.printRoundStart(), "Failed printRoundStart (should have guessed all characters and won))");
    }

    @DisplayName("Test checkGuess")
    @Test
    void checkGuess() {
        assertTrue(hangman.checkGuess(scanner), "checkGuess Failed (should be new guess->true)"); //new guess 'a' yields true
        assertTrue(hangman.checkGuess(scanner), "checkGuess Failed (should be new guess->true)"); //new guess 't' yields true
        assertTrue(hangman.checkGuess(scanner), "checkGuess Failed (should be new guess->true)"); //new guess 'o' yields true
        assertEquals(1, hangman.getNumWrongGuesses(), "checkGuess Failed (wrong number of wrong guesses)"); //wrong guess 'a' yields 1
        scanner.close();
        scanner = new Scanner(new ByteArrayInputStream("a\nt\nt\no\ne\ns\nw\nr\nd".getBytes()));
    }

    @DisplayName("Test checkPlayAgain")
    @Test
    void checkPlayAgain() {
        Scanner s = new Scanner(new ByteArrayInputStream("a\nyes\nno".getBytes()));
        assertTrue(hangman.checkPlayAgain(s), "checkPlayAgain Failed (should ret true, user said yes after invalid input)");
        assertFalse(hangman.checkPlayAgain(s), "checkPlayAgain Failed (should ret true, user said no)");
        s.close();
    }

    @DisplayName("Test recordAndCheckScore")
    @Test
    void recordAndCheckScore() {
        StringBuilder expected = new StringBuilder();

        hangman.setPlayerName("mike");
        hangman.setNumWrongGuesses(3);
        expected.append("Thanks for playing for the first time, you score has been recorded. Only " + hangman.getNumWrongGuesses() + " wrong guesses!\n");
        expected.append("You are the first player to play, so you have the highest score of all players!\n");
        assertEquals(expected.toString(), hangman.recordAndCheckScore(), "recordAndCheckScore Failed (first player ever failed)");

        hangman.setNumWrongGuesses(2);
        expected = new StringBuilder();
        expected.append("You have achieved your highest score yet! Only " + hangman.getNumWrongGuesses() + " wrong guesses!\n");
        expected.append("You have achieved the highest score of all players!\n");
        assertEquals(expected.toString(), hangman.recordAndCheckScore(), "recordAndCheckScore Failed (player beats overall high score failed)");

        hangman.setPlayerName("mike2");
        hangman.setNumWrongGuesses(1);
        expected = new StringBuilder();
        expected.append("Thanks for playing for the first time, you score has been recorded. Only " + hangman.getNumWrongGuesses() + " wrong guesses!\n");
        expected.append("You have achieved the highest score of all players!\n");
        assertEquals(expected.toString(), hangman.recordAndCheckScore(), "recordAndCheckScore Failed (new player beats overall high score failed)");
    }

    @AfterAll
    static void tearDownAll() {
        scanner.close();
    }
}