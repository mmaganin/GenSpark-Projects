import java.util.*;

public class PlayHumansVsGoblins {
    public Land land;
    public HashMap<GridCoords, String> chests;
    public HashMap<GridCoords, GameCharacter> characters;
    Iterator<Map.Entry<GridCoords, GameCharacter>> iterator;
    public StringBuilder moveOutcomeText;
    public static final String[] possibleChestItems =
            {"Gold", "Master Sword", "Band of Regeneration", "AK-47", "Kevlar"};

    public PlayHumansVsGoblins(){
        land = new Land();
        chests = new HashMap<>();
        characters = new HashMap<>();
        moveOutcomeText = new StringBuilder();
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

        if(character.getPositionOnLand() == Land.newPosAfterMove(direction, character.getPositionOnLand())){
            return currLandGrid;
        }

        //checks that goblin cant move to chest space and goblins and humans cant move to other goblin or human spaces
        if(gridAsArr[posOnLandIndex + direction] != character.getMapMarker() &&
                !(gridAsArr[posOnLandIndex + direction] == 'X' && character instanceof Goblin)){
            gridAsArr[posOnLandIndex] = ' ';

            characters.replace(character.getPositionOnLand(), null);
            posOnLandIndex += direction;
            //handles collision if human runs into chest or goblin runs into human
            if(gridAsArr[posOnLandIndex] != ' '){
                return handleCollision(character, Land.newPosAfterMove(direction, character.getPositionOnLand()), String.valueOf(gridAsArr));
            } else {
                //if no collision, moves
                gridAsArr[posOnLandIndex] = character.getMapMarker();
                newPosition = Land.newPosAfterMove(direction, character.getPositionOnLand());
                character.setPositionOnLand(newPosition);

                if(characters.containsKey(newPosition)){
                    characters.replace(newPosition, character);
                } else {
                    characters.put(newPosition, character);
                }
            }
        }

        return String.valueOf(gridAsArr);
    }

    public String openChest(Human human, GridCoords newPosition, String currLandGrid){
        char[] gridAsArr = currLandGrid.toCharArray();
        human.setPositionOnLand(newPosition);
        HashMap<String, Integer> inventory;
        String itemToInv = chests.get(newPosition);
        chests.remove(newPosition);

        gridAsArr[Land.posOnLandToIndex(newPosition)] = 'H';
        inventory = human.getInventory();
        if(inventory.containsKey(itemToInv)){
            inventory.replace(itemToInv, inventory.get(itemToInv) + 1);
        } else {
            inventory.put(itemToInv, 1);
        }
        human.setInventory(inventory);
        if(characters.containsKey(newPosition)){
            characters.replace(newPosition, alterPlayerStats(itemToInv, human));
        } else {
            characters.put(newPosition, alterPlayerStats(itemToInv, human));
        }

        moveOutcomeText.append("You have just picked up " + itemToInv + " from a chest!\n");

        return String.valueOf(gridAsArr);
    }

    public String printPlayerStatus(Human human){
        HashMap<String, Integer> inventory = human.getInventory();
        StringBuilder str = new StringBuilder();
        String output;

        str.append("You now have " + human.getHealth() + " health and " + human.getDamage() + " maximum damage.\n");
        output = str.toString();
        if(!inventory.isEmpty()) {
            str.append("Your inventory now contains: ");
            for (var invItem : human.getInventory().entrySet()) {
                str.append(invItem.getValue() + " " + invItem.getKey() + ", ");
            }
            output = str.substring(0, str.length() - 2);
        }

        return output;
    }

    public GameCharacter handleFindSurvivor(GameCharacter character, GameCharacter characterAtIndex){
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
        moveOutcomeText.append("The player and a Goblin just battled at " + characterAtIndex.getPositionOnLand() + "\n");

        if(character.getHealth() <= 0 && characterAtIndex.getHealth() <= 0){
            return null;
        } else if(character.getHealth() <= 0){
            return characterAtIndex;
        } else {
            return character;
        }
    }

    public String handleCombatOutcome(GameCharacter character, GridCoords newPosition, String currLandGrid){
        GameCharacter characterAtIndex = characters.get(newPosition);
        String itemToInv;
        HashMap<String, Integer> inventory;
        Human human;
        char[] gridAsArr = currLandGrid.toCharArray();
        Goblin goblin = new Goblin();

        GameCharacter survivor = handleFindSurvivor(character, characterAtIndex);
        if(survivor == null){
            return "";
        }

        //handles if human dies -> lose the game
        if(survivor instanceof Goblin){
            return "";
            //handles if dying goblin is moving
        } else if(survivor.equals(characterAtIndex)){
            itemToInv = ((Goblin)character).getDrop();
            human = (Human)alterPlayerStats(itemToInv, characterAtIndex);
            //handles if dying goblin is at the conflicted position
        } else {
            itemToInv = ((Goblin)characterAtIndex).getDrop();
            character.setPositionOnLand(newPosition);
            human = (Human)alterPlayerStats(itemToInv, character);
            gridAsArr[Land.posOnLandToIndex(newPosition)] = 'H';
        }

        //adds necessary items to player inventory, updates characters hashmap, spawns random chest, spawns new goblin
        goblin = new Goblin();
        currLandGrid = placeCharacterRandom(String.valueOf(gridAsArr), goblin);

        inventory = human.getInventory();
        if(inventory.containsKey(itemToInv)){
            inventory.replace(itemToInv, inventory.get(itemToInv) + 1);
        } else {
            inventory.put(itemToInv, 1);
        }
        human.setInventory(inventory);
        characters.replace(newPosition, human);

        moveOutcomeText.append("The Goblin died and a random chest has spawned!\n");
        return generateRandomChest(currLandGrid);
    }


    public String handleCollision(GameCharacter character, GridCoords newPosition, String currLandGrid){
        char[] gridAsArr = currLandGrid.toCharArray();
        Human human;
        String itemToInv;
        HashMap<String, Integer> inventory;
        String newGrid;
        GameCharacter characterAtIndex = characters.get(newPosition);

        if(character instanceof Human){
            human = (Human)character;
        } else if(characters.containsKey(newPosition)){
            human = (Human)characters.get(newPosition);
        } else{
            human = new Human();
        }

        //handles if human collides with chest
        if(chests.containsKey(newPosition) && character instanceof Human){
            human = (Human)character;
            newGrid = openChest(human, newPosition, currLandGrid);

            //handles if goblin collides with human or vice versa
        } else{
            newGrid = handleCombatOutcome(character, newPosition, currLandGrid);
        }
        if(newGrid.equals("")){
            return "";
        }
        moveOutcomeText.append(printPlayerStatus(human));

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
        if(characters.containsKey(position)){
            characters.replace(position, character);
        } else {
            characters.put(position, character);
        }

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
        if(characters.containsKey(positionOnLand)){
            characters.replace(positionOnLand, character);
        } else {
            characters.put(positionOnLand, character);
        }

        char[] landGridArr = currLandGrid.toCharArray();
        landGridArr[gridIndex] = character.getMapMarker();

        return String.valueOf(landGridArr);
    }

    public void startGame(){
        Human player;
        Goblin goblin;
        GridCoords playerLocation;
        Scanner s = new Scanner(System.in);
        String input = "";
        String currLandGrid;
        boolean stillAlive;
        boolean badMove;

        boolean keepPlaying = true;
        while(keepPlaying) {
            keepPlaying = false;
            player = new Human();
            goblin = new Goblin();
            playerLocation = new GridCoords(5, 2);
            currLandGrid = placeCharacter(Land.initLandGrid, player, playerLocation);
            currLandGrid = placeCharacterRandom(currLandGrid, goblin);
            goblin = new Goblin();
            currLandGrid = placeCharacterRandom(currLandGrid, goblin);

            System.out.println("Humans Vs. Goblins");

            stillAlive = true;
            while (stillAlive) {
                System.out.println(currLandGrid);
                System.out.println("\'H\' = Player, \'G\' = Goblin, \'X\' = Chest");
                System.out.println();
                System.out.println(moveOutcomeText.toString().equals("") ? ((Human)characters.get(playerLocation)).toString() : moveOutcomeText);
                System.out.println();
                System.out.println("Where would you like to move? (enter N, S, E, W directions, I to not move, or Q to quit)");

                moveOutcomeText = new StringBuilder("");
                String oldGrid = currLandGrid;
                badMove = true;
                while (badMove) {
                    badMove = false;

                    try {
                        input = s.nextLine().trim().toLowerCase();
                        while (!(input.equals("n") || input.equals("s") || input.equals("e") || input.equals("w") || input.equals("q") || input.equals("i"))) {
                            System.out.println("Please enter only N, S, E, W directions, I to not move, or Q to quit)");
                            input = s.nextLine().trim().toLowerCase();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    player = (Human) characters.get(playerLocation);
                    switch (input.charAt(0)) {
                        case 'n':
                            currLandGrid = changeGridAfterMove(currLandGrid, Land.moveNorth, player);
                            playerLocation = Land.newPosAfterMove(Land.moveNorth, playerLocation);
                            break;
                        case 's':
                            currLandGrid = changeGridAfterMove(currLandGrid, Land.moveSouth, player);
                            playerLocation = Land.newPosAfterMove(Land.moveSouth, playerLocation);
                            break;
                        case 'e':
                            currLandGrid = changeGridAfterMove(currLandGrid, Land.moveEast, player);
                            playerLocation = Land.newPosAfterMove(Land.moveEast, playerLocation);
                            break;
                        case 'w':
                            currLandGrid = changeGridAfterMove(currLandGrid, Land.moveWest, player);
                            playerLocation = Land.newPosAfterMove(Land.moveWest, playerLocation);
                            break;

                        case 'q':
                            System.exit(1);
                            break;
                    }
                    if (currLandGrid.equals("")) {
                        stillAlive = false;
                        break;
                    }
                    if (oldGrid.equals(currLandGrid) && input.charAt(0) != 'i') {
                        badMove = true;
                        System.out.println("Please enter a different, valid input (N, S, E, W directions, I to not move, or Q to quit)");
                    }
                }

                if (currLandGrid.equals("")) {
                    stillAlive = false;
                    break;
                }
                HashMap<GridCoords, GameCharacter> charactersCopy = new HashMap<>(characters);
                for (var character : charactersCopy.entrySet()) {
                    if (character.getValue() != null && !(character.getKey().equals(playerLocation))) {
                        currLandGrid = moveGoblin(currLandGrid, playerLocation, (Goblin) character.getValue());
                        if (currLandGrid.equals("")) {
                            stillAlive = false;
                            break;
                        }
                    }
                }
            }
            System.out.println("Your player has died, you have lost the game! Would you like to play again (y or n)");
            try {
                input = s.nextLine().trim().toLowerCase();
                while (!(input.equals("y") || input.equals("n"))) {
                    System.out.println("Please enter only y or n");
                    input = s.nextLine().trim().toLowerCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (input.charAt(0) == 'y') {
                keepPlaying = true;
                land = new Land();
                chests = new HashMap<>();
                characters = new HashMap<>();
                moveOutcomeText = new StringBuilder();
            }
        }
        s.close();
    }

    public String moveGoblin(String currLandGrid, GridCoords playerLocation, Goblin goblin){
        String newGrid;
        GridCoords goblinCoords = goblin.getPositionOnLand();
        int xDiff = playerLocation.getxCoord() - goblinCoords.getxCoord();
        int yDiff = playerLocation.getyCoord() - goblinCoords.getyCoord();
        int direction;
        int alternateDirection = Land.moveNorth;
        if(Land.newPosAfterMove(Land.moveNorth, goblin.getPositionOnLand()).equals(goblin.getPositionOnLand())){
            alternateDirection = Land.moveSouth;
        }

        if(yDiff != 0){
            if(yDiff > 0){
                direction = Land.moveSouth;
            } else {
                direction = Land.moveNorth;
            }

            if(xDiff > 0){
                alternateDirection = Land.moveEast;
            } else {
                alternateDirection = Land.moveWest;
            }
        } else {
            if(xDiff > 0){
                direction = Land.moveEast;
            } else {
                direction = Land.moveWest;
            }
        }

        //attempts movement in different directions if stuck
        if((newGrid = changeGridAfterMove(currLandGrid, direction, goblin)).equals(currLandGrid)){
            newGrid = changeGridAfterMove(currLandGrid, alternateDirection, goblin);
        }
        return newGrid;
    }

    public static void main(String[] args) {
        PlayHumansVsGoblins newGame = new PlayHumansVsGoblins();

        newGame.startGame();

    }

}
