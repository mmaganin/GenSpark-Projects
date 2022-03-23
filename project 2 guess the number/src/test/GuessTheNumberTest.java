import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GuessTheNumberTest {
    GuessTheNumber guessTheNumber;
    static Scanner scanner;

    @BeforeAll
    static void setUpAll(){
        scanner = new Scanner(new ByteArrayInputStream("5\n15\n10".getBytes()));
    }

    @BeforeEach
    void setUp() {
        guessTheNumber = new GuessTheNumber();
    }

    @DisplayName("Test readUserInput")
    @Test
    void readUserInput() {
        String userInput = guessTheNumber.readUserInput(scanner);
        assertEquals("5", userInput, "readUserInput Failed");
    }

    @DisplayName("Test checkGuess")
    @Test
    void checkGuess() {
        guessTheNumber.setRandNum(10);
        guessTheNumber.setPlayerName("Michael");
        assertEquals("Your guess is too low.", guessTheNumber.checkGuess("5"), "checkGuess Failed");
        assertEquals("Your guess is too high.", guessTheNumber.checkGuess("15"), "checkGuess Failed");
        assertEquals("Good job, Michael! You guessed my number in 3 guesses!", guessTheNumber.checkGuess("10"), "checkGuess Failed");
    }

    @DisplayName("Test checkPlayAgain")
    @Test
    void checkPlayAgain() {
        assertEquals(false, guessTheNumber.checkPlayAgain(scanner, "n"), "checkPlayAgain Failed");
        assertEquals(true, guessTheNumber.checkPlayAgain(scanner, "y"), "checkPlayAgain Failed");
    }

    @DisplayName("Test playerGuesses")
    @Test
    void playerGuesses() {
        guessTheNumber.setRandNum(10);
        guessTheNumber.playerGuesses(scanner, 5);
        assertEquals(true, guessTheNumber.isWonTheGame(), "playerGuesses Failed");
        assertEquals(2, guessTheNumber.getNumGuesses(), "playerGuesses Failed");
    }

    @AfterAll
    static void tearDownAll() {
        scanner.close();
    }
}