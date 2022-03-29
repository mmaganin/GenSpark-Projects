import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


class LandTest {
    Land land;
    //empty slot at only (4, 6)
    String samplePopulatedGrid = "" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X| |X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n" +
            "|X|X|X|X|X|X|X|X|X|X|\n" +
            "---------------------\n";


    @BeforeEach
    void setUp() {
        land = new Land();
    }


    @DisplayName("Test posOnLandToIndex")
    @Test
    void posOnLandToIndex() {
        //tests positions that correlate to corners of the grid
        assertEquals(Land.cornerTLindex, Land.posOnLandToIndex(new GridCoords(1,1)), "posOnLandToIndex Failed");
        assertEquals(Land.cornerTRindex, Land.posOnLandToIndex(new GridCoords(10,1)), "posOnLandToIndex Failed");
        assertEquals(Land.cornerBLindex, Land.posOnLandToIndex(new GridCoords(1,10)), "posOnLandToIndex Failed");
        assertEquals(Land.cornerBRindex, Land.posOnLandToIndex(new GridCoords(10,10)), "posOnLandToIndex Failed");

    }

    @DisplayName("Test newPosAfterMove")
    @Test
    void newPosAfterMove() {
        //Tests for not allowing movement out of bounds
        assertEquals(new GridCoords(1,1), Land.newPosAfterMove(Land.moveNorth, new GridCoords(1,1)), "newPosAfterMove Failed (moved out of bounds)");
        assertEquals(new GridCoords(1,1), Land.newPosAfterMove(Land.moveWest, new GridCoords(1,1)), "newPosAfterMove Failed (moved out of bounds)");
        assertEquals(new GridCoords(10,10), Land.newPosAfterMove(Land.moveSouth, new GridCoords(10,10)), "newPosAfterMove Failed (moved out of bounds)");
        assertEquals(new GridCoords(10,10), Land.newPosAfterMove(Land.moveEast, new GridCoords(10,10)), "newPosAfterMove Failed (moved out of bounds)");
        //Tests movement in different directions
        assertEquals(new GridCoords(2,3), Land.newPosAfterMove(Land.moveNorth, new GridCoords(2,4)), "newPosAfterMove Failed");
        assertEquals(new GridCoords(3,4), Land.newPosAfterMove(Land.moveEast, new GridCoords(2,4)), "newPosAfterMove Failed");
        assertEquals(new GridCoords(1,5), Land.newPosAfterMove(Land.moveSW, new GridCoords(2,4)), "newPosAfterMove Failed");
    }

    @DisplayName("Test randomFreePosition")
    @Test
    void randomFreePosition() {
        char[] gridAsArr = samplePopulatedGrid.toCharArray();

        //creates random positions until the only unpopulated position is returned 4,6
        assertEquals(new GridCoords(4,6), land.randomFreePosition(samplePopulatedGrid), "newPosAfterMove Failed");

        //makes (6, 9) the only free space and tests it
        gridAsArr[Land.posOnLandToIndex(new GridCoords(4,6))] = 'X';
        gridAsArr[Land.posOnLandToIndex(new GridCoords(6,9))] = ' ';
        assertEquals(new GridCoords(6,9), land.randomFreePosition(String.valueOf(gridAsArr)), "newPosAfterMove Failed");
    }
}