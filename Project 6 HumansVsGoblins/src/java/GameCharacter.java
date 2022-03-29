public class GameCharacter {
    private GridCoords positionOnLand;       //arr of length 2 for coordinates on grid based on top left (10x10)
    private int health;
    private int damage;
    private char mapMarker;             //Goblin 'G' or Human 'H' on game's land grid

    public GameCharacter(){
        positionOnLand = new GridCoords(0,0);
        health = 0;
        damage = 0;
        mapMarker = 0;
    }

    public GridCoords getPositionOnLand() {
        return positionOnLand;
    }

    public void setPositionOnLand(GridCoords positionOnLand) {
        this.positionOnLand = positionOnLand;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public char getMapMarker() {
        return mapMarker;
    }

    public void setMapMarker(char mapMarker) {
        this.mapMarker = mapMarker;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GameCharacter) && ((GameCharacter) obj).getPositionOnLand().equals(getPositionOnLand())
                && ((GameCharacter) obj).getHealth() == getHealth()
                && ((GameCharacter) obj).getDamage() == getDamage()
                && ((GameCharacter) obj).getMapMarker() == getMapMarker();
    }
}
