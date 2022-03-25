import java.util.HashMap;

public class PlayHumansVsGoblins {
    private Land land;
    private HashMap<int[], String> chests;
    private HashMap<int[], Goblin> goblins;
    private HashMap<int[], Human> humans;
    public static final String[] possibleChestItems =
            {"Gold", "Master Sword", "Band of Regeneration", "AK-47", "Kevlar"};

    public PlayHumansVsGoblins(){
        land = new Land();
        chests = new HashMap<>();
        goblins = new HashMap<>();
        humans = new HashMap<>();
    }

    //places a chest with drops onto the grid
    public String generateRandomChest(String currLandGrid){
        int[] newChestPosition = land.randomFreePosition(currLandGrid);
        int chestIndex = Land.posOnLandToIndex(newChestPosition);
        String newChestItem;

        //generates random chest drops from array of possible items
        int max = 4;
        int min = 0;
        int randNum = (int) (Math.random() * (max - min + 1) + min); //generates random chest drops from array of possible items
        newChestItem = possibleChestItems[randNum];

        //puts chest (position in grid, drops) into a hashmap
        chests.put(newChestPosition, newChestItem);
        char[] landGridArr = currLandGrid.toCharArray();
        landGridArr[chestIndex] = 'X';

        return String.valueOf(landGridArr);
    }

    //alters UTF grid based on a character's movement
    public String changeGridAfterMove(String currLandGrid, int direction, GameCharacter character){
        char[] gridAsArr = currLandGrid.toCharArray();
        int posOnLandIndex = Land.posOnLandToIndex(character.getPositionOnLand());

        if(gridAsArr[posOnLandIndex + direction] != character.getMapMarker()){
            gridAsArr[posOnLandIndex] = ' ';
            posOnLandIndex += direction;
            gridAsArr[posOnLandIndex] = character.getMapMarker();
            character.setPositionOnLand(Land.newPosAfterMove(direction, character.getPositionOnLand()));
        }

        return String.valueOf(gridAsArr);
    }

    //places character in random location at start of game
    public String placeCharacter(String currLandGrid, GameCharacter character){
        int[] position = land.randomFreePosition(currLandGrid);
        character.setPositionOnLand(position);
        char[] gridAsArr = currLandGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(position)] = character.getMapMarker();

        return String.valueOf(gridAsArr);
    }
    //places character at specific location at start of game
    public String placeCharacter(String currLandGrid, GameCharacter character, int[] positionOnLand){
        int gridIndex = Land.posOnLandToIndex(positionOnLand);
        if(currLandGrid.charAt(gridIndex) == character.getMapMarker()){
            return currLandGrid;
        }
        character.setPositionOnLand(positionOnLand);

        char[] landGridArr = currLandGrid.toCharArray();
        landGridArr[gridIndex] = character.getMapMarker();

        return String.valueOf(landGridArr);
    }

    public static void main(String[] args) {
        PlayHumansVsGoblins newGame = new PlayHumansVsGoblins();


    }

}
