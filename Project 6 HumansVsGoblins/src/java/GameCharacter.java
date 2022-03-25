public class GameCharacter {
    private int[] positionOnLand;       //arr of length 2 for coordinates on grid based on top left (10x10)
    private int health;
    private int damage;
    private char mapMarker;             //Goblin 'G' or Human 'H' on game's land grid

    public GameCharacter(){
        positionOnLand = new int[2];
        health = 0;
        damage = 0;
        mapMarker = 0;
    }

    public int[] getPositionOnLand() {
        return positionOnLand;
    }

    public void setPositionOnLand(int[] positionOnLand) {
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
}
