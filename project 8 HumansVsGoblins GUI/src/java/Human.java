import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class Human extends GameCharacter {
    private HashMap<String, Integer> inventory;


    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Human() {
        this.inventory = new HashMap<>();

        this.setPositionOnLand(new GridCoords(0, 0));
        this.setMapMarker('H');
        this.setDamage(5);
        this.setHealth(30);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String output;

        str.append("You have " + this.getHealth() + " health and " + this.getDamage() + " maximum damage.");
        output = str.toString();
        if (!inventory.isEmpty()) {
            str.append("\nYour inventory contains: ");
            for (var invItem : this.getInventory().entrySet()) {
                str.append(invItem.getValue() + " " + invItem.getKey() + ", ");
            }
            output = str.substring(0, str.length() - 2);
        }
        return output;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Human) && ((Human) obj).getInventory().equals(inventory)
                && ((Human) obj).getHealth() == getHealth()
                && ((Human) obj).getDamage() == getDamage()
                && ((Human) obj).getPositionOnLand().equals(getPositionOnLand());
    }
}
