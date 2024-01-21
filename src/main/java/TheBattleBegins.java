import java.util.*;

public class TheBattleBegins {
    public static String command;

    //Enemy stats
    public static int enemyHealth = 100;
    public static boolean isEnemyOnFire;
    public static boolean isEnemyBleeding;
    public static boolean isEnemyPoisoned;
    public static boolean isEnemyBlinded;
    public static boolean isSkipTurn;

    public static boolean isEnemyAlive = true;

    //Player Stats
    public static boolean isPlayerAlive = true;
    public static int playerHealth;
    public static String playerClass;
    public static Map<String, Integer> playerSpells;
    public static Set<String> playerAttacks;
    public static Map<String, Integer> playerInventory;
    public static int playerMana;
    public static boolean isPlayerBleeding;
    public static boolean isGlobalCommandUsed;

    //Initialize character classes
    public static CharacterClass currentClass;
    public static ArcherClass archer = new ArcherClass();
    public static SwordClass swordsman = new SwordClass();
    public static MageClass mage = new MageClass();

    //Housekeeping
    public static Scanner input = new Scanner(System.in);
    public static String globalCommands = "commands: show this command list\n" +
            "inventory: display inventory\n" +
            "attacks: displays attacks list(if applicable)\n" +
            "spells: displays spell list(if applicable)\n" +
            "status: displays character stats\n" +
            "use: use something from your inventory";


    public static void main(String[] args){
        System.out.println("Please select a character class:");
        System.out.println("1. Mage");
        System.out.println("2. Swordsman");
        System.out.println("3. Archer");

        //Take character class in loop so that if invalid input is entered, they are given another try.
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

        //Gets class's specific starting info and updates local variables
        playerHealth = currentClass.StartingHealth;
        playerClass = currentClass.Name;
        playerSpells = currentClass.SpellList;
        playerAttacks = currentClass.AttackList;
        playerMana = currentClass.Mana;
        playerInventory = currentClass.Inventory;


        //Output current class.
        String separator = "------------------------------------\n" +
                "------------------------------------";
        System.out.println("====================================");
        System.out.println(playerClass);
        System.out.println(separator);


        //Inform user about class specific info.
        if (playerClass.equals("Mage")) {
            System.out.println(">Description: Each spell the Mage casts will cost Mana. You can check how much mana you \n" +
                    "have at any time using the 'status' command.");
            System.out.println("Starting Mana: " + playerMana);

        } else if (playerClass.equals("Swordsman")) {
            System.out.println(">Description: Swing sword. Enemy says 'Ouch!'. Pretty simple.");

        } else if (playerClass.equals("Archer")) {
            System.out.println(">Description: You only have a certain number of each type of arrow. You can check how \n" +
                    "many arrows you have at any time using the 'inventory' command.");

        } else {
            System.out.println(">Something went wrong when assigning or when evaluating class");
            System.out.println("Goobye!");
            return;
        }
        System.out.println("Starting Health: " + playerHealth);

        waitForUser();
        System.out.println(separator);

        //Set up premise of game
        System.out.println(">You are traveling through the woods on your way into town. You notice a cart crashed on the \n" +
                "side of the road. As you approach, you hear someone shouting for help from inside the cart.");
        System.out.println("Do you:");
        System.out.println("1. Approach the cart");
        System.out.println("2. Mind your business");
        System.out.println(">");

        String userAmbushInput = input.nextLine();
        if (userAmbushInput.equals("2")) {
            System.out.println(">Who are you to help in that situation anyway? Best to mind your business. You make it \n" +
                    "into town safely, only partially laden with guilt.");
            return;
        }
        System.out.println(separator);

        System.out.println(">You run over to the cart like the caring person that you are, only to find no one inside.");
        System.out.println("Suddenly, a bandit jumps out from the brush and shouts \"Empty your pockets!\"");

        waitForUser();

        System.out.println(separator);

        System.out.println("Enter a command when prompted by '>'");
        System.out.println();
        System.out.println("Global Commands:");
        System.out.println(globalCommands);
        System.out.println();
        System.out.println("To use a spell or attack, enter it when prompted for a command.");

        waitForUser();

        System.out.println("                   \n" +
                "..=====..|...===...|...|..=====..\n" +
                "..|......|../...\\..|...|....|....\n" +
                "..|====..|..|......|===|....|....\n" +
                "..|......|..|...==.|...|....|....\n" +
                "..|......|..\\===/..|...|....|....");
        System.out.println();


        /*
        MAIN COMMANDS LOOP
        --------------------------------------------------------------------
        --------------------------------------------------------------------
        --------------------------------------------------------------------
         */
        while(playerHealth > 0 && enemyHealth > 0) {
            System.out.println(separator);
            if(isPlayerBleeding && !isGlobalCommandUsed) {
                System.out.println("You are bleeding. You take 2pts bleeding damage.");
            }
            enemyTurn();

            //Skip this if global command used to keep terminal from clogging up
            if (!isGlobalCommandUsed) {
                System.out.println("Enemy health: " + enemyHealth);
                System.out.println("Player health: " + playerHealth);
            }
            System.out.println();
            System.out.println(">");
            command = input.nextLine();

            processGlobalCommands();

            if (!isGlobalCommandUsed) {
                if (playerClass.equals("Mage")) {
                    processMageCommands();
                } else if (playerClass.equals("Swordsman")) {
                    processSwordsmanCommands();
                } else if (playerClass.equals("Archer")) {
                    processArcherCommands();
                }
            }
            //only wait for user if a normal command is used, so they can read about what their turn did.
            if (!isGlobalCommandUsed) {
                waitForUser();
            }

        }

        if (playerHealth < 0) {
            System.out.println("The bandit runs his sword through your chest. You fall to the ground, the last thing you\n" +
                    " see is the bandit going through your things and escaping.");
            System.out.println();
            System.out.println("YOU LOSE");
        }
        else {
            System.out.println(separator);
            if (playerClass.equals("Mage")) {
                System.out.println(">You reach your arms to the sky and concentrate your energy on the ground beneath the\n" +
                        "bandit. The earth churns and swallows the bandit. As the ground settles, there remains no trace\n" +
                        "of the bandit.");
            } else if (playerClass.equals("Swordsman")) {
                System.out.println(">The bandit swings his sword at your head, but you expertly dodge the attack. You\n" +
                        "slash the bandit's leg causing him to fall to his knee. You plunge your sword into the bandit's\n" +
                        "back.");
            } else if (playerClass.equals("Archer")) {
                System.out.println(">The bandit as a last resort runs at you. As he raises his sword, you loose an \n" +
                        "arrow into his neck and step to the side as flies past.");
            }
        }
        System.out.println();
        System.out.println(">You leave the bandit to his fate and make your way into town and you notice wanted signs\n" +
                "posted everywhere. Looks like there was a bounty on the bandit's head. You bring proof of his demise to\n" +
                "the local lord and are rewarded generously. From his list of crimes, it is safe to say you rid the \n" +
                "world of an intense evil.");
        System.out.println();
        System.out.println("YOU WIN!!!");


    }

    //This function gives the user time to read previous lines
    public static void waitForUser() {
        System.out.println();
        System.out.println("When you are ready to proceed, hit enter.");
        String proceed = input.nextLine();
    }

    //This function processes global commands, unless it is a "use" command in which case it calls the processUseCommand function.
    public static void processGlobalCommands() {
        isGlobalCommandUsed = true;
        if (command.equals("commands")) {
            System.out.println(globalCommands);
        } else if (command.equals("inventory")){
            for (Map.Entry<String, Integer> item : currentClass.Inventory.entrySet()) {
                System.out.println(item.getKey() + ": " + item.getValue());
            }
        } else if (command.equals("attacks")) {
            if (playerClass.equals("Mage")) {
                System.out.println("Mages use spells");
                return;
            } else {
                for (String attack : currentClass.AttackList) {
                    System.out.println(attack);
                }
            }
        } else if (command.equals("spells")) {
            if (!playerClass.equals("Mage")) {
                System.out.println("You don't know any spells.");
            } else {
                for (Map.Entry<String, Integer> spell : currentClass.SpellList.entrySet()) {
                    System.out.println(spell.getKey() + ": " + spell.getValue() + " mana");
                }
            }
        } else if (command.equals("status")) {
            System.out.println("Your Health: " + playerHealth);
            if (playerClass.equals("Mage")) {
                System.out.println("Your Mana: " + playerMana);
            }
        } else if (command.equals("use")) {
            processUseCommand();
        } else {
            isGlobalCommandUsed = false;
        }
    }

    public static void processUseCommand() {
        System.out.println("What would you like to use?");
        String useItem = input.nextLine();

        if (currentClass.Inventory.containsKey(useItem)){
            int numberOfUseItems = playerInventory.get(useItem);

            if (numberOfUseItems < 1) {
                System.out.println("You don't have any left");
                return;
            }else if (useItem.equals("Health Potion")) {
                playerInventory.put(useItem, numberOfUseItems-1);

                playerHealth += 20;

                System.out.println("You drink a health potion and regain 20 health");

            } else if (useItem.equals("Mana Potion")) {
                playerInventory.put(useItem, numberOfUseItems-1);

                playerMana += 30;

                System.out.println("You drink a mana potion and regain 30 mana");

            } else if (useItem.equals("Smoke Bomb")) {
                isEnemyBlinded = true;
                playerInventory.put(useItem, numberOfUseItems - 1);
                System.out.println("You throw a smoke bomb at the bandit, blinding him.");
            } else if (useItem.equals("Normal Arrows") || useItem.equals("Poison Arrows")) {
                System.out.println("These aren't used this way, try using their attack instead.");
            }
        }
    }


    public static void enemyTurn() {
        if (!isSkipTurn && !isGlobalCommandUsed) {
            if (isEnemyBlinded) {
                System.out.println(">The bandit is blinded and cannot attack");
                isEnemyBlinded = false;
                return;
            }
            String currentEnemyAttack = randomAttack();
            if (currentEnemyAttack.equals("Slash")) {
                System.out.println(">The bandit slashes at you with his sword. You take 15 points damage.");
                playerHealth -= 15;
            } else if (currentEnemyAttack.equals("Stab")) {
                System.out.println(">The bandit stabs you with his sword. You take 10 points damage. You are bleeding.");
                playerHealth -= 10;
                isPlayerBleeding = true;
            }
            isSkipTurn = false;
        }

        if (isEnemyPoisoned && !isGlobalCommandUsed) {
            System.out.println("The bandit is poisoned and takes 3pts poison damage.");
            enemyHealth -= 3;
        } else if( isEnemyBleeding) {
            System.out.println("The bandit is bleeding and takes 2pt bleeding damage.");
            enemyHealth -=2 ;
        } else if (isEnemyOnFire) {
            System.out.println("The bandit is on fire and takes 5 pts fire damage. He takes his turn to put himself out");
            enemyHealth -= 5;
            isEnemyOnFire = false;
        }

        isSkipTurn = false;
    }


    public static void processMageCommands(){
        if (playerMana > 0) {
            if (command.equals("Fireball")) {
                System.out.println(">A fireball erupts from your palm and engulfs the bandit. He takes 5pts of damage. He is on fire.");
                enemyHealth -= 5;
                isEnemyOnFire = true;
                //The fireball will catch the enemy on fire which will make him have to skip his next turn
                isSkipTurn = true;
                playerMana -= 15;
                return;
            } else if (command.equals("Shock")) {
                System.out.println(">Bolts of electricity escape your fingertips and strike the bandit. He takes 18pts damage.");
                enemyHealth -= 18;
                playerMana -= 15;
                return;
            } else if (command.equals("Heal")) {
                System.out.println(">You heal yourself 25 pts.");
                playerHealth += 25;
                playerMana -= 10;
                return;
            }
        } else {
            System.out.println("You are out of mana.");
        }
    }

    public static void processSwordsmanCommands() {
        if (command.equals("Slash")) {
            System.out.println(">Your sword glides through the air and strikes the bandit across the chest.\n" +
                    "he takes 15 pts damage.");
            enemyHealth -= 15;
            return;
        } else if (command.equals("Stab")) {
            System.out.println(">You quickly jab your sword at the bandit's stomach. He takes 10 pts damage. He is bleeding.");
            enemyHealth -= 10;
            isEnemyBleeding = true;
            return;
        }
    }

    public static void processArcherCommands() {
        if (command.equals("Normal Arrow")) {
            System.out.println(">You draw your bow with a normal arrow and loose it with great speed. It strikes the bandit\n" +
                    "in the chest, slightly penetrating his armor. He takes 18 pts damage.");
            enemyHealth -= 18;
            return;
        } else if (command.equals("Poison Arrow")) {
            System.out.println(">You draw your bow with a poison-tipped arrow and loose it with great precision. It slices\n" +
                    "the small amount of the bandit's exposed skin taking 12 pts damage. He is poisoned.");
            enemyHealth -= 12;
            isEnemyPoisoned = true;
            return;
        }

    }

    public static String randomAttack(){
        //get random number
        Random rand = new Random();
        int randomNumber = rand.nextInt(10);

        //List of random attacks (same as swordsman)
        String[] enemyAttacks = {"Slash", "Stab"};

        //Weight random number to make slash happen more often than stab.
        int weightedNumber;
        if (randomNumber > 2) {
            weightedNumber = 0;
        } else {
            weightedNumber = 1;
        }

        //get random attack
        return enemyAttacks[weightedNumber];
    }
}


