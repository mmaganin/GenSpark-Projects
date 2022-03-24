import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTest {

    Hangman hangman;
    static Scanner scanner;

    @BeforeAll
    static void setUpAll(){
        scanner = new Scanner(new ByteArrayInputStream("a\nt\nt\no\ne\ns\nw\nr\nd".getBytes()));
    }

    @BeforeEach
    void setUp() {
        hangman = new Hangman("testWord");
    }

    @Test
    void generateRandWord() {
        String secretWord1 = "";
        String secretWord2 = "";

        Scanner s = new Scanner(new ByteArrayInputStream("a\nt\nq\no\ne\ns\nw\nr\nd\nm\naa\ntt\nqq\noo\nee\nss\nww\nrr\ndd\nmm\n".getBytes()));
        secretWord1 = hangman.generateRandWord(s, 20);
        s.close();
        s = new Scanner(new ByteArrayInputStream("a\nt\nq\no\ne\ns\nw\nr\nd\nm\naa\ntt\nqq\noo\nee\nss\nww\nrr\ndd\nmm\n".getBytes()));
        secretWord2 = hangman.generateRandWord(s, 20);
        s.close();

        assertNotEquals(secretWord1, "", "generateRandWord Failed (empty string)");
        assertNotEquals(secretWord1, secretWord2, "generateRandWord Failed (equal strings (possible to fail test but very unlikely when working), \n" +
                "(test uses 20 words while text file uses 1000))");
    }

    @Test
    void printRoundStart() {
        String secretWord = hangman.getSecretWord();
        HashSet<Character> charGuesses = hangman.getCharGuesses();
        int i;
        for(i = 0; i < secretWord.length() - 1; i++){
            charGuesses.add(secretWord.charAt(i));
            assertEquals(false, hangman.printRoundStart(), "Failed printRoundStart (won game unexpectedly))");
        }
        charGuesses.add(secretWord.charAt(i));
        assertEquals(true, hangman.printRoundStart(), "Failed printRoundStart (should have guessed all characters and won))");
    }

    @Test
    void readUserInput() {
        String userInput = hangman.readUserInput(scanner);
        assertEquals("a", userInput, "readUserInput Failed");
        scanner.close();
        scanner = new Scanner(new ByteArrayInputStream("a\nt\nt\no\ne\ns\nw\nr\nd".getBytes()));
    }

    @Test
    void checkGuess() {
        assertTrue(hangman.checkGuess(scanner), "readUserInput Failed (should be new guess->true)"); //new guess 'a' yields true
        assertTrue(hangman.checkGuess(scanner), "readUserInput Failed (should be new guess->true)"); //new guess 't' yields true
        assertFalse(hangman.checkGuess(scanner), "readUserInput Failed (should be old guess->false)"); //old guess 't' yields false
        assertTrue(hangman.checkGuess(scanner), "readUserInput Failed (should be new guess->true)"); //new guess 'o' yields true
        assertEquals(1, hangman.getNumWrongGuesses(), "readUserInput Failed (wrong number of wrong guesses)"); //wrong guess 'a' yields 1
        scanner.close();
        scanner = new Scanner(new ByteArrayInputStream("a\nt\nt\no\ne\ns\nw\nr\nd".getBytes()));
    }

    @Test
    void checkPlayAgain() {
        Scanner s = new Scanner(new ByteArrayInputStream("a\nyes\nno".getBytes()));
        assertTrue(hangman.checkPlayAgain(s), "checkPlayAgain Failed (should ret true, user said yes after invalid input)");
        assertFalse(hangman.checkPlayAgain(s), "checkPlayAgain Failed (should ret true, user said no)");
        s.close();
    }


    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
        scanner.close();
    }
}