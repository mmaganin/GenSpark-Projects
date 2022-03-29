import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlayHumansVsGoblins {
    public Land land;
    public HashMap<GridCoords, String> chests;
    public HashMap<GridCoords, GameCharacter> characters;
    public static final String[] possibleChestItems =
            {"Gold", "Master Sword", "Band of Regeneration", "AK-47", "Kevlar"};

    public PlayHumansVsGoblins(){
        land = new Land();
        chests = new HashMap<>();
        characters = new HashMap<>();
    }

    //places a chest with drops onto the grid, returns an updated land grid
    public String generateRandomChest(String currLandGrid){
        GridCoords newChestPosition = land.randomFreePosition(currLandGrid);
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

    //alters UTF grid based on a character's movement, returns updated grid
    public String changeGridAfterMove(String currLandGrid, int direction, GameCharacter character){
        char[] gridAsArr = currLandGrid.toCharArray();
        int posOnLandIndex = Land.posOnLandToIndex(character.getPositionOnLand());
        GridCoords newPosition;

        //checks that goblin cant move to chest space and goblins and humans cant move to other goblin or human spaces
        if(gridAsArr[posOnLandIndex + direction] != character.getMapMarker() &&
                !(gridAsArr[posOnLandIndex + direction] == 'X' && character instanceof Goblin)){
            gridAsArr[posOnLandIndex] = ' ';

            characters.remove(character.getPositionOnLand());
            posOnLandIndex += direction;
            //handles collision if human runs into chest or goblin runs into human
            if(gridAsArr[posOnLandIndex] != ' '){
                return handleCollision(character, Land.newPosAfterMove(direction, character.getPositionOnLand()), String.valueOf(gridAsArr));
            } else {
                //if no collision, moves
                gridAsArr[posOnLandIndex] = character.getMapMarker();
                newPosition = Land.newPosAfterMove(direction, character.getPositionOnLand());
                character.setPositionOnLand(newPosition);

                characters.put(newPosition, character);
            }
        }

        return String.valueOf(gridAsArr);
    }


    public String handleCollision(GameCharacter character, GridCoords newPosition, String currLandGrid){
        char[] gridAsArr = currLandGrid.toCharArray();
        Human human;
        String itemToInv;
        HashMap<String, Integer> inventory;
        String newGrid;

        //handles if human collides with chest
        if(chests.containsKey(newPosition) && character instanceof Human){
            character.setPositionOnLand(newPosition);
            human = (Human)character;
            itemToInv = chests.get(newPosition);
            chests.remove(newPosition);

            gridAsArr[Land.posOnLandToIndex(newPosition)] = 'H';
            inventory = human.getInventory();
            if(inventory.containsKey(itemToInv)){
                inventory.replace(itemToInv, inventory.get(itemToInv) + 1);
            } else {
                inventory.put(itemToInv, 1);
            }
            human.setInventory(inventory);
            characters.put(newPosition, alterPlayerStats(itemToInv, human));

            System.out.println("You have just picked up " + itemToInv + " from a chest!");

            newGrid = String.valueOf(gridAsArr);
            //handles if goblin collides with human or vice versa
        } else{
            GameCharacter characterAtIndex = characters.get(newPosition);
            //handles human/goblin battle with randomized attack damage (maximum damage is their stored damage)
            int max;
            int min = 0;
            int randDamage;
            while(character.getHealth() > 0 && characterAtIndex.getHealth() > 0){
                max = character.getDamage();
                randDamage = (int) (Math.random() * (max - min + 1) + min);
                characterAtIndex.setHealth(characterAtIndex.getHealth() - randDamage);

                max = characterAtIndex.getDamage();
                randDamage = (int) (Math.random() * (max - min + 1) + min);
                character.setHealth(character.getHealth() - randDamage);
            }
            System.out.println("The player and a Goblin just battled at " + newPosition.toString());
            //handles if human dies -> lose the game
            if((character instanceof Human && character.getHealth() <= 0) ||
                    (characterAtIndex instanceof Human && characterAtIndex.getHealth() <= 0)){
                System.out.println("The player has died and you have lost! Game Over!");
                return "";
            //handles if dying goblin is moving
            } else if(character.getHealth() <= 0){
                itemToInv = ((Goblin)character).getDrop();
                human = (Human)alterPlayerStats(itemToInv, characterAtIndex);
            //handles if dying goblin is at the conflicted position
            } else {
                itemToInv = ((Goblin)character).getDrop();
                character.setPositionOnLand(newPosition);
                human = (Human)alterPlayerStats(itemToInv, character);
                gridAsArr[Land.posOnLandToIndex(newPosition)] = 'H';
            }

            inventory = human.getInventory();
            if(inventory.containsKey(itemToInv)){
                inventory.replace(itemToInv, inventory.get(itemToInv) + 1);
            } else {
                inventory.put(itemToInv, 1);
            }
            human.setInventory(inventory);
            characters.replace(newPosition, human);

            System.out.println("The Goblin died and a random chest has spawned!");
            newGrid = generateRandomChest(String.valueOf(gridAsArr));
        }
        System.out.println("You now have " + human.getHealth() + " health and " + human.getDamage() + " maximum damage.");
        if(!inventory.isEmpty()) {
            System.out.print("Your inventory now contains: ");
            StringBuilder str = new StringBuilder();
            for (var invItem : human.getInventory().entrySet()) {
                str.append(invItem.getValue() + " " + invItem.getKey() + ", ");
            }
            System.out.println(str.substring(0, str.length() - 2));
        }

        return newGrid;
    }


    //Alters a players stats based upon chest or goblin drops
    public GameCharacter alterPlayerStats(String itemDrop, GameCharacter character){
        Human human = (Human) character;
        switch(itemDrop) {
            case "Gold":
                human.setHealth(human.getHealth() + 2);
                break;
            case "Master Sword":
                human.setDamage(human.getDamage() + 10);
                break;
            case "Band of Regeneration":
                human.setHealth(human.getHealth() + 10);
                break;
            case "AK-47":
                human.setDamage(human.getDamage() + 10);
                break;
            case "Kevlar":
                human.setHealth(human.getHealth() + 10);
                break;
            case "Bag of Coins":
                human.setHealth(human.getHealth() + 2);
                break;
            case "Goblin Leggings":
                human.setHealth(human.getHealth() + 5);
                break;
            case "Goblin Chestplate":
                human.setHealth(human.getHealth() + 5);
                break;
            case "Goblin Boots":
                human.setHealth(human.getHealth() + 5);
                break;
            case "Goblin Helmet":
                human.setHealth(human.getHealth() + 5);
                break;
            case "Goblin Egg":
                human.setHealth(human.getHealth() - 5);
                break;
        }

        return human;
    }

    //places character in random location at start of game, returns the resultant grid
    public String placeCharacterRandom(String currLandGrid, GameCharacter character){
        GridCoords position = land.randomFreePosition(currLandGrid);
        character.setPositionOnLand(position);
        characters.put(position, character);

        char[] gridAsArr = currLandGrid.toCharArray();
        gridAsArr[Land.posOnLandToIndex(position)] = character.getMapMarker();

        return String.valueOf(gridAsArr);
    }

    //places character at specific location at start of game, returns the resultant grid
    public String placeCharacter(String currLandGrid, GameCharacter character, GridCoords positionOnLand){
        int gridIndex = Land.posOnLandToIndex(positionOnLand);
        if(currLandGrid.charAt(gridIndex) != ' '){
            return currLandGrid;
        }
        character.setPositionOnLand(positionOnLand);
        characters.put(positionOnLand, character);

        char[] landGridArr = currLandGrid.toCharArray();
        landGridArr[gridIndex] = character.getMapMarker();

        return String.valueOf(landGridArr);
    }

    public static void main(String[] args) {
        PlayHumansVsGoblins newGame = new PlayHumansVsGoblins();

        System.out.println(newGame.generateRandomChest(Land.initLandGrid));

    }

}
