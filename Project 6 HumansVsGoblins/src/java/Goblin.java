public class Goblin extends GameCharacter{
    private String drop;
    public static final String[] possibleDrops =
            {"Bag of Coins", "Goblin Leggings", "Goblin Chestplate", "Goblin Boots", "Goblin Helmet", "Goblin Egg"};

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public Goblin(){
        //generates 2 random goblin drops from array of possible items
        int max = 5;
        int min = 0;
        int randNum = (int) (Math.random() * (max - min + 1) + min);
        drop = possibleDrops[randNum];


        this.setPositionOnLand(new int[2]);
        this.setMapMarker('G');
        this.setDamage(5);
        this.setHealth(10);
    }

    @Override
    public String toString(){
        return "Goblin: \n" +
                "Health: " + this.getHealth() + ", Damage: " + this.getDamage() + "\n" +
                "Drop: " + drop.toString() + "\n" +
                "Position: " + (this.getPositionOnLand().length > 0 ? this.getPositionOnLand().toString() : "Not on the board yet.");
    }
}
