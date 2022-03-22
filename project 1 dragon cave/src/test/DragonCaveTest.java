import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DragonCaveTest {
    DragonCave dragonCave;
    static Scanner scanner;

    @BeforeAll
    static void setUpAll(){
        scanner = new Scanner(new ByteArrayInputStream("testInput".getBytes()));
    }

    @BeforeEach
    void setUp() {
        dragonCave = new DragonCave("3");
    }

    @DisplayName("Test setUserInput")
    @Test
    void setUserInput() {
        dragonCave.setUserInput("1");
        assertEquals("1", dragonCave.getUserInput(), "setUserInput Failed");
    }

    @DisplayName("Test getUserInput")
    @Test
    void getUserInput() {
        assertEquals("3", dragonCave.getUserInput(), "getUserInput Failed");
    }

    @DisplayName("Test Dragon Cave Intro")
    @Test
    void playIntro() {
        String expectedIntro = "You are in a land full of dragons. In front of you, \n" +
                "you see two caves. In one cave, the dragon is friendly \n" +
                "and will share his treasure with you. The other dragon \n" +
                "is greedy and hungry and will eat you on sight. \n" +
                "Which cave will you go into? (1 or 2)";

        assertEquals(expectedIntro, dragonCave.playIntro(), "playIntro Failed");
    }

    @DisplayName("Test User Input")
    @Test
    void readUserInput() {
        String userInput = dragonCave.readUserInput(scanner);
        assertEquals("testInput", userInput, "readUserInput Failed");
    }

    @DisplayName("Test User Input Evaluation 1")
    @Test
    void evalUserInput1() {
        String expectedOutput = "You approach the cave...\n" +
                "It is dark and spooky...\n" +
                "A large dragon jumps out in front of you! He opens his jaws and...\n" +
                "Gobbles you down in one bite!";

        assertEquals(expectedOutput, dragonCave.evalUserInput("1"), "evalUserInput Failed");
    }

    @DisplayName("Test User Input Evaluation 2")
    @Test
    void evalUserInput2() {
        String expectedOutput = "You entered the nice cave and the dragon bakes you cookies with his firebreath";

        assertEquals(expectedOutput, dragonCave.evalUserInput("2"), "evalUserInput Failed");
    }

    @DisplayName("Test User Input Evaluation 3")
    @Test
    void evalUserInput3() {
        String expectedOutput = "Please enter only a 1 or a 2.";

        assertEquals(expectedOutput, dragonCave.evalUserInput("3"), "evalUserInput Failed");
    }

    @AfterAll
    static void tearDownAll(){
        scanner.close();
    }
}