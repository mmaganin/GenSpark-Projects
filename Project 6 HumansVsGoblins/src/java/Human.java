import java.util.HashMap;

public class Human extends GameCharacter{
    private HashMap<String, Integer> inventory;

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Human(){
        this.inventory = new HashMap<>();

        this.setPositionOnLand(new GridCoords(0,0));
        this.setMapMarker('H');
        this.setDamage(5);
        this.setHealth(30);
    }

    @Override
    public String toString(){
        return "Human: \n" +
                "Health: " + this.getHealth() + ", Damage: " + this.getDamage() + "\n" +
                "Position: " + (this.getPositionOnLand().getxCoord() == 0 ? this.getPositionOnLand().toString() : "Not on the board yet.");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Human) && ((Human) obj).getInventory().equals(inventory)
                && ((Human) obj).getHealth() == getHealth()
                && ((Human) obj).getDamage() == getDamage()
                && ((Human) obj).getPositionOnLand().equals(getPositionOnLand());
    }
}
