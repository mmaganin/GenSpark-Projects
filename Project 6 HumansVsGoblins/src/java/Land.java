public class Land {
    private String currLandGrid;

    //indices for corners of grid
    public static final int cornerTLindex = 23;
    public static final int cornerTRindex = 41;
    public static final int cornerBLindex = 419;
    public static final int cornerBRindex = 437;

    //values to add to position to move one square different directions
    public static final int placeCharacter = 0;
    public static final int moveNorth = -44;
    public static final int moveSouth = 44;
    public static final int moveEast = 2;
    public static final int moveWest = -2;
    public static final int moveSE = 46;
    public static final int moveSW = 42;
    public static final int moveNE = -42;
    public static final int moveNW = -46;

    //10 x 10 game world grid
    public static final String initLandGrid = "" +
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
    public static int posOnLandToIndex(GridCoords positionOnLand){
        if(positionOnLand.getxCoord() < 1 || positionOnLand.getxCoord() > 10 ||
                positionOnLand.getyCoord() < 1 || positionOnLand.getyCoord() > 10){
            return 0;
        }
        int indexOnGrid = 0;
        indexOnGrid += (positionOnLand.getxCoord() * 2) - 1;             //converting x axis
        indexOnGrid += ((positionOnLand.getyCoord() * 2) - 1) * 22;      //converting y axis

        return indexOnGrid;
    }
    //calculates the new {x, y} position after a move
    public static GridCoords newPosAfterMove(int direction, GridCoords position){
        GridCoords newPosition = position;
        switch(direction){
            case moveNorth:
                if(position.getyCoord() > 1){
                    newPosition = new GridCoords(position.getxCoord(), position.getyCoord() - 1);
                }
                break;
            case moveSouth:
                if(position.getyCoord() < 10){
                    newPosition = new GridCoords(position.getxCoord(), position.getyCoord() + 1);
                }
                break;
            case moveEast:
                if(position.getxCoord() < 10){
                    newPosition = new GridCoords(position.getxCoord() + 1, position.getyCoord());
                }
                break;
            case moveWest:
                if(position.getxCoord() > 1){
                    newPosition = new GridCoords(position.getxCoord() - 1, position.getyCoord());
                }
                break;
            case moveSE:
                if(position.getxCoord() < 10 && position.getyCoord() < 10){
                    newPosition = new GridCoords(position.getxCoord() + 1, position.getyCoord() + 1);
                }
                break;
            case moveSW:
                if(position.getxCoord() > 1 && position.getyCoord() < 10){
                    newPosition = new GridCoords(position.getxCoord() - 1, position.getyCoord() + 1);
                }
                break;
            case moveNE:
                if(position.getxCoord() < 10 && position.getyCoord() > 1){
                    newPosition = new GridCoords(position.getxCoord() + 1, position.getyCoord() - 1);
                }
                break;
            case moveNW:
                if(position.getxCoord() > 1 && position.getyCoord() > 1){
                    newPosition = new GridCoords(position.getxCoord() - 1, position.getyCoord() - 1);
                }
                break;
        }
        return newPosition;
    }

    public GridCoords randomFreePosition(String currLandGrid){
        int max = 10;
        int min = 1;

        int xCoord;
        int yCoord;

        xCoord = (int) (Math.random() * (max - min + 1) + min); //generates x position on grid
        yCoord = (int) (Math.random() * (max - min + 1) + min); //generates y position on grid
        while(currLandGrid.charAt(Land.posOnLandToIndex(new GridCoords(xCoord, yCoord))) != ' '){
            xCoord = (int) (Math.random() * (max - min + 1) + min);
            yCoord = (int) (Math.random() * (max - min + 1) + min);
        }

        return new GridCoords(xCoord, yCoord);
    }

    @Override
    public String toString(){
        return this.currLandGrid;
    }
}
