import java.util.HashMap;

public class PlayHumansVsGoblins {
    private Land land;
    public static final String[] possibleChestItems = {"Gold", "Sword", "Band of Regeneration", "AK-47", "Kevlar"};
    private HashMap<int[], String> chests;

    public PlayHumansVsGoblins(){
        land = new Land();
        chests = new HashMap<>();
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
    public String changeGrid(String currLandGrid, int direction, Character character){
        int[] positionOnLand;
        char mapMarker;

        char[] gridAsArr = currLandGrid.toCharArray();
        int posOnLandIndex = Land.posOnLandToIndex(character.getPositionOnLand());

        gridAsArr[posOnLandIndex] = ' ';
        posOnLandIndex += direction;
        gridAsArr[posOnLandIndex] = character.getMapMarker();

        return String.valueOf(gridAsArr);
    }

    //places character in random location at start of game
    public String placeCharacter(String currLandGrid, Character character){
        int[] position = land.randomFreePosition(currLandGrid);
        character.setPositionOnLand(position);
        return "";



    }
    //places character at specific location at start of game
    public String placeCharacter(String currLandGrid, Object character, int[] positionOnLand){
        int gridIndex = Land.posOnLandToIndex(positionOnLand);
        if(currLandGrid.charAt(gridIndex) != ' '){
            return "";
        }

        return "";

    }

    public static void main(String[] args) {
        PlayHumansVsGoblins newGame = new PlayHumansVsGoblins();


    }

}
