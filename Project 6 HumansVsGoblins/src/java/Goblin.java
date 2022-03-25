public class Goblin extends Character{
    private String[] drops;
    public static final String[] possibleDrops =
            {"Bag of Coins", "Goblin Leggings", "Goblin Chestplate", "Goblin Boots", "Goblin Helmet", "Goblin Egg"};

    public String[] getDrops() {
        return drops;
    }

    public void setDrops(String[] drops) {
        this.drops = drops;
    }

    public Goblin(){
        int max = 5;
        int min = 0;
        int randNum = (int) (Math.random() * (max - min + 1) + min); //generates random goblin drop from array of possible items
        drops = new String[2];
        drops[0] = possibleDrops[randNum];
        randNum = (int) (Math.random() * (max - min + 1) + min);
        drops[1] = possibleDrops[randNum];

        this.setPositionOnLand(new int[2]);
        this.setMapMarker('G');
        this.setDamage(5);
        this.setHealth(10);
    }

    @Override
    public String toString(){
        return "Goblin: \n" +
                "Health: " + this.getHealth() + ", Damage: " + this.getDamage() + "\n" +
                "Drops: " + drops.toString() + "\n" +
                "Position: " + (this.getPositionOnLand().length > 0 ? this.getPositionOnLand().toString() : "Not on the board yet.");
    }
}
