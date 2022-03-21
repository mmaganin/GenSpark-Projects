import java.util.Scanner;

public class DragonCave {
    public static void main(String[] args) {
        System.out.println("You are in a land full of dragons. In front of you, ");
        System.out.println("you see two caves. In one cave, the dragon is friendly ");
        System.out.println("and will share his treasure with you. The other dragon ");
        System.out.println("is greedy and hungry and will eat you on sight. ");
        System.out.println("Which cave will you go into? (1 or 2)");

        Scanner s = new Scanner(System.in);

        System.out.println();
        String userInput = s.nextLine();
        System.out.println();

        if(userInput.length() == 1){
            switch(userInput.charAt(0)){
                case '1':
                    System.out.println("You approach the cave...");
                    System.out.println("It is dark and spooky...");
                    System.out.println("A large dragon jumps out in front of you! He opens his jaws and...");
                    System.out.println("Gobbles you down in one bite!");
                    break;
                case '2':
                    System.out.println("You entered the nice cave and the dragon bakes you cookies with his firebreath");
                    break;
                default:
                    System.out.println("Please enter only a 1 or a 2.");
            }
        } else {
            System.out.println("Please enter only a 1 or a 2.");
        }
    }
}
