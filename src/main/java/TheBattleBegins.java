import java.util.*;

public class TheBattleBegins {
    public static String command;
    public static int enemyHealth = 100;
    public static boolean isEnemyOnFire;
    public static boolean isEnemyBleeding;
    public static boolean isEnemyPoisoned;
    public static boolean isEnemySmokeBombed;

    public static boolean isEnemyAlive = true;
    public static boolean isPlayerAlive = true;
    public static ArcherClass archer = new ArcherClass();
    public static SwordClass swordsman = new SwordClass();
    public static MageClass mage = new MageClass();
    public static CharacterClass currentClass;
    public static int playerHealth;
    public static Scanner input = new Scanner(System.in);
    public static String globalCommands = "commands: show this command list\n" +
            "inventory: display inventory\n" +
            "attacks: displays spell list(if applicable)\n" +
            "spells: displays spell list(if applicable)\n" +
            "status: displays character stats\n" +
            "use: use something from your inventory";


    public static void main(String[] args){
        System.out.println("Please select a character class:");
        System.out.println("1. Mage");
        System.out.println("2. Swordsman");
        System.out.println("3. Archer");

        //Ask for character class in loop so that if invalid input is entered, they are given another try.
        while (true) {
            System.out.println(">");
            String classChoice = input.nextLine();
            if (classChoice.equals("1")) {
                currentClass = mage;
                break;
            } else if (classChoice.equals("2")) {
                currentClass = swordsman;
                break;
            } else if (classChoice.equals("3")) {
                currentClass = archer;
                break;
            } else {
                System.out.println("Looks like you didn't choose a valid option. Try again:");
            }
        }

        playerHealth = currentClass.StartingHealth;

        //Output current class.
        String separator = "------------------------------------";
        System.out.println("====================================");
        System.out.println(currentClass.Name);
        System.out.println(separator);


        //Inform user about class specific info.
        if (currentClass.Name.equals("Mage")) {
            System.out.println("Each spell the Mage casts will cost Mana. You can check how much mana you have at any \n" +
                    "time using the 'status' command.");
        } else if (currentClass.Name.equals("Swordsman")) {
            System.out.println("Swing sword. Enemy says 'Ouch!'. Pretty simple.");
        } else if (currentClass.Name.equals("Archer")) {
            System.out.println("You only have a certain number of each type of arrow. You can check how many arrows you \n" +
                    "have at any time using the 'inventory' command.");
        } else {
            System.out.println("Something went wrong when assigning or when evaluating class");
        }
        waitForUser();
        System.out.println(separator);

        //We will reference this whenever we want to give the user time to read.

        //Set up premise of game
        System.out.println("You are traveling through the woods on your way into town. You notice a cart crashed on the \n" +
                "side of the road. As you approach, you hear someone shouting for help from inside the cart.");
        System.out.println("Do you:");
        System.out.println("1. Approach the cart");
        System.out.println("2. Mind your business");
        System.out.println(">");

        String userAmbushInput = input.nextLine();
        if (userAmbushInput.equals("2")) {
            System.out.println("Who are you to help in that situation anyway? Best to mind your business. You make it \n" +
                    "into town safely, only partially laden with guilt.");
            return;
        }
        System.out.println(separator);

        System.out.println("You run over to the cart like the caring person that you are, only to find no one inside.");
        System.out.println("Suddenly, a bandit jumps out from the brush and shouts \"Empty your pockets!\"");

        waitForUser();

        System.out.println(separator);

        System.out.println("Enter a command when prompted by '>'");
        System.out.println();
        System.out.println("Global Commands:");
        System.out.println(globalCommands);
        System.out.println();
        System.out.println("To use a spell or attack, enter it when prompted for a command.");

        System.out.println("                   \n" +
                "..=====..|...===...|...|..=====..\n" +
                "..|......|../...\\..|...|....|....\n" +
                "..|====..|..|......|===|....|....\n" +
                "..|......|..|...==.|...|....|....\n" +
                "..|......|..\\===/..|...|....|....");

        while(isEnemyAlive && isPlayerAlive) {
            System.out.println("Enemy health: " + enemyHealth);
            System.out.println(">");
            command = input.nextLine();

            processGlobalCommands();




        }


    }
    public static void waitForUser() {
        System.out.println("When you are ready to proceed, hit enter.");
        String proceed = input.nextLine();
    }

    public static void processGlobalCommands() {
        if (command.equals("commands")) {
            System.out.println(globalCommands);
        } else if (command.equals("inventory")){
            for (Map.Entry<String, Integer> item : currentClass.Inventory.entrySet()) {
                System.out.println(item.getKey() + ": " + item.getValue());
            }
        } else if (command.equals("attacks")) {
            if (currentClass.Name.equals("Mage")) {
                System.out.println("Mages use spells");
                return;
            } else {
                for (String attack : currentClass.AttackList) {
                    System.out.println(attack);
                }
            }
        } else if (command.equals("spells")) {
            if (!currentClass.Name.equals("Mage")) {
                System.out.println("You don't know any spells.");
            } else {
                for (Map.Entry<String, Integer> spell : currentClass.SpellList.entrySet()) {
                    System.out.println(spell.getKey() + ": " + spell.getValue() + " mana");
                }
            }
        } else if (command.equals("status")) {
            System.out.println(playerHealth);
            if (currentClass.Name.equals("Mage")) {
                System.out.println(currentClass.Mana);
            }
        } else if (command.equals("use")) {
            useCommand();
        }
    }
    public static void useCommand() {
        System.out.println("What would you like to use?");
        String useItem = input.nextLine();

        if (currentClass.Inventory.containsKey(useItem)){
            int numberOfUseItems = currentClass.Inventory.get(useItem);

            if (numberOfUseItems < 1) {
                System.out.println("You don't have any left");
                return;
            }else if (useItem.equals("Health Potion")) {
                currentClass.Inventory.put(useItem, numberOfUseItems-1);

                playerHealth += 10;

                System.out.println("You drink a health potion and regain 10 health");

            } else if (useItem.equals("Smoke Bomb")) {
                isEnemySmokeBombed = true;
                System.out.println("You throw a smoke bomb at the bandit, blinding him.");
            } else if (useItem.equals("Normal Arrows") || useItem.equals("Poison Arrows")) {
                System.out.println("These aren't used this way, try using their attack instead.");
            }
        }
    }

}


