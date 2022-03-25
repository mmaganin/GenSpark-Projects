import java.util.HashMap;

public class Human extends Character{
    private HashMap<String, Integer> inventory;

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Human(){
        this.inventory = new HashMap<>();

        this.setPositionOnLand(new int[2]);
        this.setMapMarker('H');
        this.setDamage(5);
        this.setHealth(30);
    }


    @Override
    public String toString(){
        return "Human: \n" +
                "Health: " + this.getHealth() + ", Damage: " + this.getDamage() + "\n" +
                "Position: " + (this.getPositionOnLand().length > 0 ? this.getPositionOnLand().toString() : "Not on the board yet.");
    }
}
