final class GridCoords {
    private final Integer xCoord, yCoord;

    public GridCoords(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    @Override
    public int hashCode() {
        return xCoord.hashCode() ^ yCoord.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GridCoords) && ((GridCoords) obj).xCoord.equals(xCoord)
                && ((GridCoords) obj).yCoord.equals(yCoord);
    }

    @Override
    public String toString(){
        return "(" + getxCoord() + ", " + getyCoord() + ")";
    }
}