import java.util.Scanner;

public class DragonCave {
    private String userInput;

    public DragonCave(){
    }

    public DragonCave(String userInput){
        this.userInput = userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput(){
        return this.userInput;
    }

    //plays the dragon cave intro
    public String playIntro(){
        String output = "You are in a land full of dragons. In front of you, \n" +
                "you see two caves. In one cave, the dragon is friendly \n" +
                "and will share his treasure with you. The other dragon \n" +
                "is greedy and hungry and will eat you on sight. \n" +
                "Which cave will you go into? (1 or 2)";

        return output;
    }

    //Reads a user's input
    public String readUserInput(Scanner scanner){
        String userInput = "";

        System.out.println();
        try {
            userInput = scanner.nextLine();
        }catch(Exception e){
            System.out.println("User input failed");
            System.exit(0);
        }
        System.out.println();

        return userInput;
    }

    //evaluates the users input (must be 1 or 2)
    public String evalUserInput(String userInput){
        String output = "Please enter only a 1 or a 2.";

        switch (userInput) {
            //input 1 enters scary cave
            case "1":
                output = "You approach the cave...\n" +
                        "It is dark and spooky...\n" +
                        "A large dragon jumps out in front of you! He opens his jaws and...\n" +
                        "Gobbles you down in one bite!";
                break;
            //input 2 enters nice cave
            case "2":
                output = "You entered the nice cave and the dragon bakes you cookies with his firebreath";
                break;
        }

        return output;
    }

    public static void main(String[] args) {
        DragonCave dragonCave = new DragonCave();
        Scanner s = new Scanner(System.in);

        System.out.println(dragonCave.playIntro());
        dragonCave.setUserInput(dragonCave.readUserInput(s));
        System.out.println(dragonCave.evalUserInput(dragonCave.getUserInput()));

        s.close();
    }
}
