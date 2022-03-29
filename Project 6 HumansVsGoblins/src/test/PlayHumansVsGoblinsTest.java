import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayHumansVsGoblinsTest {
    PlayHumansVsGoblins playHumansVsGoblins;
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

        //Tested at second location
        gridAsArr = samplePopulatedGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(new GridCoords(4, 5))] = 'X';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(8, 8))] = ' ';
        checkHashmap = new GridCoords(8, 8);
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.generateRandomChest(String.valueOf(gridAsArr)).toCharArray(), "generateRandomChest Failed");
        assertTrue(playHumansVsGoblins.chests.containsKey(checkHashmap), "generateRandomChest Failed (not in hashmap");
    }

    @DisplayName("Test changeGridAfterMove")
    @Test
    void changeGridAfterMove() {
        char[] gridAsArr = samplePopulatedGrid.toCharArray();
        GridCoords checkHashmap = new GridCoords(4, 4);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        String newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), checkHashmap);

        //checks that goblins cant collide with chests
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.changeGridAfterMove(newGrid, Land.moveSouth, playHumansVsGoblins.characters.get(checkHashmap)).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacterRandom Failed (not in hashmap");

        //checks that characters cant move to spaces with characters of same object type
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = 'G';
        checkHashmap = new GridCoords(3, 4);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        newGrid = playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), checkHashmap);
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.changeGridAfterMove(newGrid, Land.moveEast, playHumansVsGoblins.characters.get(checkHashmap)).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacterRandom Failed (not in hashmap");

        //tests no collision, successful move
        playHumansVsGoblins.characters.clear();
        gridAsArr = Land.initLandGrid.toCharArray();
        checkHashmap = new GridCoords(1,1);
        newGrid = playHumansVsGoblins.placeCharacter(Land.initLandGrid, new Goblin(), checkHashmap);
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,2))] = 'G';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(1,1))] = ' ';

        assertArrayEquals(gridAsArr, playHumansVsGoblins.changeGridAfterMove(newGrid, Land.moveSouth, playHumansVsGoblins.characters.get(checkHashmap)).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(new GridCoords(1,2)), "placeCharacterRandom Failed (not in hashmap");
        assertFalse(playHumansVsGoblins.characters.containsKey(new GridCoords(1,1)), "placeCharacterRandom Failed (not in hashmap");
    }

    @DisplayName("Test handleCollision")
    @Test
    void handleCollision() {
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
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Goblin(), new GridCoords(3, 3)).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacterRandom Failed (not in hashmap");

        //tests placement of human
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = 'G';
        checkHashmap = new GridCoords(8, 2);
        gridAsArr[Land.posOnLandToIndex(checkHashmap)] = ' ';
        assertArrayEquals(samplePopulatedGrid.toCharArray(), playHumansVsGoblins.placeCharacter(String.valueOf(gridAsArr), new Human(), new GridCoords(8, 2)).toCharArray(), "placeCharacterRandom Failed");
        assertTrue(playHumansVsGoblins.characters.containsKey(checkHashmap), "placeCharacterRandom Failed (not in hashmap");
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
}