public class Land {
    private String currLandGrid;

    //indices for corners of grid
    public static final int cornerTLindex = 23;
    public static final int cornerTRindex = 41;
    public static final int cornerBLindex = 221;
    public static final int cornerBRindex = 239;

    //values to add to position to move one square different directions
    public static final int placeCharacter = 0;
    public static final int moveNorth = -44;
    public static final int moveSouth = 44;
    public static final int moveSE = 46;
    public static final int moveSW = 42;
    public static final int moveNE = -42;
    public static final int moveNW = -46;

    //10 x 10 game world grid
    private static final String initLandGrid = "" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n" +
            "| | | | | | | | | | |\n" +
            "---------------------\n";

    public Land(){
        currLandGrid = initLandGrid;
    }

    public String getCurrLandGrid() {
        return currLandGrid;
    }

    public void setCurrLandGrid(String currLandGrid) {
        this.currLandGrid = currLandGrid;
    }

    //converts an {x, y} position to an index in the grid String ({0,0} at top left)
    public static int posOnLandToIndex(int[] positionOnLand){
        if(positionOnLand.length != 2 ||
                positionOnLand[0] < 1 || positionOnLand[0] > 10 ||
                positionOnLand[1] < 1 || positionOnLand[1] > 10){
            return 0;
        }
        int indexOnGrid = 0;
        indexOnGrid += (positionOnLand[0] * 2) - 1;             //converting x axis
        indexOnGrid += ((positionOnLand[1] * 2) - 1) * 22;      //converting y axis

        return  indexOnGrid;
    }
    //calculates the new {x, y} position after a move
    public static int[] newPosAfterMove(int direction, int[] position){
        switch(direction){
            case moveNorth:
                if(position[1] > 1){
                    position[1]--;
                }
                break;
            case moveSouth:
                if(position[1] < 10){
                    position[1]++;
                }
                break;
            case moveSE:
                if(position[0] < 10 && position[1] < 10){
                    position[1]++;
                    position[0]++;
                }
                break;
            case moveSW:
                if(position[0] > 1 && position[1] < 10){
                    position[1]++;
                    position[0]--;
                }
                break;
            case moveNE:
                if(position[0] < 10 && position[1] > 1){
                    position[1]--;
                    position[0]++;
                }
                break;
            case moveNW:
                if(position[0] > 1 && position[1] > 1){
                    position[1]--;
                    position[0]--;
                }
                break;
        }
        return position;
    }

    public int[] randomFreePosition(String currLandGrid){
        int max = 10;
        int min = 1;

        int[] position = new int[2];
        position[0] = (int) (Math.random() * (max - min + 1) + min); //generates x position on grid
        position[1] = (int) (Math.random() * (max - min + 1) + min); //generates y position on grid
        while(currLandGrid.charAt(Land.posOnLandToIndex(position)) != ' '){
            position[0] = (int) (Math.random() * (max - min + 1) + min);
            position[1] = (int) (Math.random() * (max - min + 1) + min);
        }

        return position;
    }

    @Override
    public String toString(){
        return this.currLandGrid;
    }
}
