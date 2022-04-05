import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayHumansVsGoblinsTest {
    PlayHumansVsGoblins playHumansVsGoblins;
    //populated grid used often during tests
    String samplePopulatedGrid = "" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|H|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|X|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|X|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n";

    @BeforeEach
    void setUp() {
        playHumansVsGoblins = new PlayHumansVsGoblins();
    }


    @DisplayName("Test generateRandomChest")
    @Test
    void generateRandomChest() {
        //Allows method to randomly generate locations until the single possible location is filled with a chest
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        GridCoords checkHashmap = new GridCoords(4, 5);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';

        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.generateRandomChest(String.valueOf(gridAsArr)).toCharArray(), "generateRandomChest Failed");
        assertTrue(playHumansVsGoblins.chests.containsKey(checkHashmap), "generateRandomChest Failed (not in hashmap");
    }

    @DisplayName("Test generateRandomChest2")
    @Test
    void generateRandomChest2() {
        //Tested at second location
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(new GridCoords(8, 8))] = ' ';
        GridCoords checkHashmap = new GridCoords(8, 8);
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.generateRandomChest(String.valueOf(gridAsArr)).toCharArray(), "generateRandomChest Failed");
        assertTrue(playHumansVsGoblins.chests.containsKey(checkHashmap), "generateRandomChest2 Failed (not in hashmap");
    }

    @DisplayName("Test changeGridAfterMove")
    @Test
    void changeGridAfterMove() {
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        GridCoords checkHashmap = new GridCoords(4, 4);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        String newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), checkHashmap);

        //checks that goblins cant collide with chests
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.changeGridAfterMove(newGrid, Land.moveSouth, playHumansVsGoblins.characters.get(checkHashmap)).toCharArray(), "changeGridAfterMove Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "changeGridAfterMove Failed (not in hashmap");
    }

    @DisplayName("Test changeGridAfterMove2")
    @Test
    void changeGridAfterMove2() {
        GridCoords checkHashmap = new GridCoords(4, 4);
        char[] gridAsArr = samplePopulatedGrid.toCharArray();

        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        checkHashmap = new GridCoords(3, 4);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';

        String newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), new GridCoords(4, 4));
        newGrid = playHumansVsGoblins.placeCharacter(newGrid, new Goblin(), checkHashmap);

        //checks that characters cant move to spaces with characters of same object type
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.changeGridAfterMove(newGrid, Land.moveEast, playHumansVsGoblins.characters.get(checkHashmap)).toCharArray(), "changeGridAfterMove2 Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "changeGridAfterMove2 Failed (not in hashmap");
    }

    @DisplayName("Test changeGridAfterMove3")
    @Test
    void changeGridAfterMove3() {
        GridCoords checkHashmap = new GridCoords(1, 1);
        char[] gridAsArr = samplePopulatedGrid.toCharArray();

        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        checkHashmap = new GridCoords(1, 2);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';

        String newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), new GridCoords(1, 1));

        //checks a successful move
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = 'G';
        assertArrayEquals(gridAsArr, playHumansVsGoblins.changeGridAfterMove(newGrid, Land.moveSouth, playHumansVsGoblins.characters.get(new GridCoords(1, 1))).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "changeGridAfterMove3 Failed (not in hashmap");
        assertNull(playHumansVsGoblins.characters.get(new GridCoords(1,1)), "changeGridAfterMove3 Failed (not in hashmap");
    }

    @DisplayName("Test alterPlayerStats")
    @Test
    void alterPlayerStats() {
        Human testHuman = new Human();
        Human defaultHuman = new Human();
        //test damage boost
        testHuman.setDamage(testHuman.getDamage() + 10);
        assertEquals(testHuman, playHumansVsGoblins.alterPlayerStats("Master Sword", defaultHuman), "alterPlayerStats Failed");
        //test damage to health
        testHuman.setHealth(testHuman.getHealth() - 5);
        assertEquals(testHuman, playHumansVsGoblins.alterPlayerStats("Goblin Egg", defaultHuman), "alterPlayerStats Failed");
    }

    @DisplayName("Test placeCharacter")
    @Test
    void placeCharacter() {
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        GridCoords checkHashmap = new GridCoords(3, 3);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';

        //tests placement of goblin
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), new GridCoords(3, 3)).toCharArray(), "placeCharacter Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacter Failed (not in hashmap");

        //tests placement of human
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = 'G';
        checkHashmap = new GridCoords(8, 2);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Human(), new GridCoords(8, 2)).toCharArray(), "placeCharacter Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacter Failed (not in hashmap");
    }

    @DisplayName("Test placeCharacterRandom")
    @Test
    void placeCharacterRandom() {
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        GridCoords checkHashmap = new GridCoords(3, 3);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';

        //tests placement of goblin in only possible location
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.placeCharacterRandom(String.valueOf(gridAsArr), new Goblin()).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacterRandom Failed (not in hashmap");

        //tests placement of human in only possible location
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = 'G';
        checkHashmap = new GridCoords(8, 2);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.placeCharacterRandom(String.valueOf(gridAsArr), new Human()).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacterRandom Failed (not in hashmap");
    }

    @DisplayName("Test openChest")
    @Test
    void openChest() {
        //checks that chest is replaced with human, human stats changed from item, human has item in inventory
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        GridCoords gridCoords = new GridCoords(8, 8);
        gridAsArr[Land.posOnLandToIndex(gridCoords)] = ' ';
        String newGrid = playHumansVsGoblins.generateRandomChest(String.valueOf(gridAsArr));
        gridAsArr[Land.posOnLandToIndex(gridCoords)] = 'H';
        Human human = new Human();
        Human defaultHuman = new Human();
        human.setPositionOnLand(new GridCoords(7, 8));

        assertArrayEquals(gridAsArr, playHumansVsGoblins.openChest(human, gridCoords, newGrid).toCharArray(), "openChest Failed");
        human = (Human)playHumansVsGoblins.characters.get(gridCoords);
        assertTrue(!human.getInventory().isEmpty() && (human.getHealth() != defaultHuman.getHealth() || human.getDamage() != defaultHuman.getDamage()), "openChest Failed");
    }

    //tests that outputs the correct, surviving character with altered health stat (can fail when working but unlikely)
    @DisplayName("Test handleFindSurvivor")
    @Test
    void handleFindSurvivor() {
        Goblin goblin = new Goblin();
        Human human = new Human();
        Human testHuman = new Human();

        human.setHealth(1000);
        playHumansVsGoblins.handleFindSurvivor(goblin, human);
        testHuman.setHealth(human.getHealth());

        assertEquals(testHuman, playHumansVsGoblins.handleFindSurvivor(goblin, human), "handleFindSurvivor Failed");
        assertTrue(1000 > testHuman.getHealth());
    }

    @DisplayName("Test printPlayerStatus")
    @Test
    void printPlayerStatus() {
        //tests the correct printing of player status
        Human human = new Human();
        HashMap<String, Integer> inventory = new HashMap<>();
        String testDrop = "TestItem";
        inventory.put(testDrop, 1);

        human.setHealth(100);
        human.setDamage(150);
        human.setInventory(inventory);

        String expected = "You now have " + 100 + " health and " + 150 + " maximum damage.\n" +
                "Your inventory now contains: " + 1 + " " + testDrop;
        String actual = playHumansVsGoblins.printPlayerStatus(human);
        System.out.println(actual);

        assertEquals(expected, actual, "printPlayerStatus Failed");
    }

    //tests combat outcome if goblin runs into human. human has stats changed, inventory is changed
    @DisplayName("Test handleCombatOutcome")
    @Test
    void handleCombatOutcome() {
        Human human = new Human();
        Goblin goblin = new Goblin();
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,1))] = ' ';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,2))] = ' ';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,3))] = ' ';

        human.setHealth(200);
        human.setDamage(5);
        String newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), human, new GridCoords(1,1));
        String actual = playHumansVsGoblins.handleCombatOutcome(goblin, new GridCoords(1,1), newGrid);

        human = (Human)playHumansVsGoblins.characters.get(new GridCoords(1,1));
        assertTrue(!human.getInventory().isEmpty() && (human.getHealth() != 100 || human.getDamage() != 5), "handleCombatOutcome Failed");
    }

    //Tests collision if human runs into goblin
    @DisplayName("Test handleCollision")
    @Test
    void handleCollision() {
        Human human = new Human();
        Goblin goblin = new Goblin();
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,1))] = ' ';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,2))] = ' ';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,3))] = ' ';

        human.setHealth(200);
        String newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), goblin, new GridCoords(1,1));
        newGrid = playHumansVsGoblins.handleCollision(human, new GridCoords(1,1), newGrid);

        human = (Human)playHumansVsGoblins.characters.get(new GridCoords(1,1));
        assertTrue(!human.getInventory().isEmpty() && (human.getHealth() != 100 || human.getDamage() != 5), "handleCollision Failed");
    }

    //Tests collision if human runs into goblin
    @DisplayName("Test moveGoblin")
    @Test
    void moveGoblin() {
        String currLandGrid = Land.initLandGrid;
        Goblin goblin = new Goblin();
        GridCoords position = new GridCoords(6,6);
        currLandGrid = playHumansVsGoblins.placeCharacter(currLandGrid, goblin, position);
        char[] gridAsArr = currLandGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(position)] = ' ';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(6, 5))] = 'G';

        String output = playHumansVsGoblins.moveGoblin(currLandGrid, goblin);

        assertEquals(String.valueOf(gridAsArr), output, "moveGoblin Failed");
    }

}