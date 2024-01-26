import java.util.*;

public class TheBattleBegins {

    //Enemy stats
    public static int enemyHealth = 100;
    public static boolean isEnemyOnFire;
    public static boolean isEnemyBleeding;
    public static boolean isEnemyPoisoned;
    public static boolean isEnemyBlinded;


    //Player Stats
    public static int playerHealth;
    public static String playerClass;
    public static String playerClassDescription;
    public static Map<String, Integer> playerSpells;
    public static Set<String> playerAttacks;
    public static Map<String, Integer> playerInventory;
    public static int playerMana;
    public static boolean isPlayerBleeding;
    public static boolean isPlayerBlocking;
    public static boolean isPlayerDodging;
    public static boolean isGlobalCommandUsed;


    //Initialize character classes
    public static CharacterClass currentClass;
    public static ArcherClass archer = new ArcherClass();
    public static SwordClass swordsman = new SwordClass();
    public static MageClass mage = new MageClass();


    //Housekeeping
    public static Scanner input = new Scanner(System.in);
    public static String command;
    public static String globalCommands = "" +
            "commands: show this command list\n" +
            "inventory: display inventory\n" +
            "attacks: displays attacks list(if applicable)\n" +
            "spells: displays spell list(if applicable)\n" +
            "status: displays character stats\n" +
            "use: use something from your inventory";
    public static String separator = "" +
            "------------------------------------\n" +
            "------------------------------------";
    public static final String BLUE_ANSI_CODE = "\u001B[36m";
    public static final String RED_ANSI_CODE = "\u001B[31m";
    public static final String ANSI_RESET_CODE = "\u001B[0m";
    public static final String GREEN_ANSI_CODE = "\u001B[32m";


    //Main method
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
        playerClassDescription = currentClass.Description;
        playerSpells = currentClass.SpellList;
        playerAttacks = currentClass.AttackList;
        playerMana = currentClass.Mana;
        playerInventory = currentClass.Inventory;

        //Output current class and class-specific info.
        System.out.println("====================================");
        System.out.println(playerClass);
        System.out.println(separator);
        System.out.println(playerClassDescription);
        System.out.println("Starting Health: " + playerHealth);

        //This function waits for the user to hit enter before proceeding so they have time to read outputs. Defined below.
        waitForUser();

        System.out.println(separator);

        //Explain commands
        System.out.println("Global Commands:");
        System.out.println(globalCommands); //defined above
        System.out.println();
        System.out.println(BLUE_ANSI_CODE + "Enter a command when prompted by '>'" + ANSI_RESET_CODE);
        System.out.println(BLUE_ANSI_CODE + "To use a spell or attack, enter its name when prompted for a command." + ANSI_RESET_CODE);

        waitForUser();

        System.out.println(separator);

        //Set up story of game
        System.out.println(">You are traveling through the woods on your way into town. You notice a cart crashed on the \n" +
                "side of the road. As you approach, you hear someone shouting for help from inside the cart.");
        System.out.println("Do you:");
        System.out.println("1. Investigate the cart");
        System.out.println("2. Mind your business");
        System.out.println(">");

        //Quit if player does not approach cart.
        String userAmbushInput = input.nextLine();
        if (userAmbushInput.equals("2")) {
            System.out.println(">Who are you to help in that situation anyway? Best to mind your business. You make it \n" +
                    "into town safely, only partially laden with guilt.");
            return;
        }

        System.out.println(separator);

        //Bandit attacks if you do not ignore the cries for help.
        System.out.println(">You run over to the cart like the caring person that you are, only to find no one inside.");
        System.out.println("Suddenly, a bandit jumps out from the brush and shouts \"Empty your pockets!\"");

        waitForUser();


        System.out.println(RED_ANSI_CODE + "                   \n" +
                "..=====..|...===...|...|..=====..\n" +
                "..|......|../...\\..|...|....|....\n" +
                "..|====..|..|......|===|....|....\n" +
                "..|......|..|...==.|...|....|....\n" +
                "..|......|..\\===/..|...|....|...." + ANSI_RESET_CODE);
        System.out.println();


        /*
        MAIN COMMANDS LOOP
        --------------------------------------------------------------------
        This loop has 2 main sections:
            - Above the /// line happens before command input in order to apply bleeding effect to character and to let the
        bandit attack. He attacks first since he ambushed you. This whole section is skipped If the user used a global
        command in the last iteration.
            - Below the /// line takes and processes user command. First checks for global command, then processes a
        turn (attack/spell) command. If a global command is used, skips the turn command processing.
        --------------------------------------------------------------------
         */
        while(playerHealth > 0 && enemyHealth > 0) {
            System.out.println(separator);

            if (!isGlobalCommandUsed) {

                //Makes player take bleeding damage if bleeding
                if (isPlayerBleeding) {
                    System.out.println("You are bleeding. You take 2pts bleeding damage.");
                    playerHealth -= 2;
                }

                //This function creates the enemy's turn. Defined below.
                enemyTurn();

                //Every turn, display character and enemy status.
                System.out.println();
                System.out.println("Enemy health: " + enemyHealth);
                System.out.println("----------------------------");
                System.out.println("Player health: " + playerHealth);
                if (playerClass.equals("Mage")) {
                    System.out.println("Player mana: " + playerMana);
                }
                System.out.println();
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //Take user command and see if they used a global command first (since global command does not count as turn)
            System.out.println(">");
            command = input.nextLine();
            processGlobalCommands(); //Defined below

            //If global command wasn't used, process command based on character class. All class command functions defined below.
            if (!isGlobalCommandUsed) {
                if (playerClass.equals("Mage")) {
                    processMageCommands();
                } else if (playerClass.equals("Swordsman")) {
                    processSwordsmanCommands();
                } else if (playerClass.equals("Archer")) {
                    processArcherCommands();
                }
            }

            //Only wait for user if a turn (non-global) command is used, so they can read about what their turn did before proceeding.
            if (!isGlobalCommandUsed) {
                waitForUser();
            }

        //This loop will repeat as long as user and enemy healths are above zero.
        }



        //Ending if player loses (health < 0)
        if (playerHealth < 0) {
            System.out.println("The bandit runs his sword through your chest. You fall to the ground, the last thing you\n" +
                    " see is the bandit going through your things and escaping.");
            System.out.println();
            System.out.println(RED_ANSI_CODE + "YOU LOSE" + ANSI_RESET_CODE);
            return;

        //Finishing attack if player wins (enemy health < 0), depends on character class.
        } else {
            System.out.println(separator);
            if (playerClass.equals("Mage")) {
                System.out.println(">You reach your arms to the sky and concentrate your energy on the ground beneath the\n" +
                        "bandit. The earth churns and swallows him whole. As the ground settles, there remains no trace\n" +
                        "of the bandit.");
            } else if (playerClass.equals("Swordsman")) {
                System.out.println(">The bandit swings his sword at your head, but you expertly dodge the attack. You\n" +
                        "slash the bandit's leg causing him to fall to his knee. You plunge your sword into the bandit's\n" +
                        "back.");
            } else if (playerClass.equals("Archer")) {
                System.out.println(">The bandit as a last resort runs at you. As he raises his sword, you loose an \n" +
                        "arrow into his neck and step to the side as flies past. He collapses onto the ground, no longer\n" +
                        "moving.");
            }
        }

        waitForUser();

        //Winning ending
        System.out.println();
        System.out.println(">You leave the bandit to his fate and make your way into town and you notice wanted signs\n" +
                "posted everywhere. Looks like there was a bounty on the bandit's head. You bring proof of his demise to\n" +
                "the local lord and are rewarded generously. From his list of crimes, it is safe to say you rid the \n" +
                "world of a great evil.");
        System.out.println();
        System.out.println(GREEN_ANSI_CODE + "YOU WIN!!!" + ANSI_RESET_CODE);


    }

    /*
    ALL FUNCTIONS DEFINED UNDER THIS
    --------------------------------
    --------------------------------
    --------------------------------
     */

    //Waits for user to press enter before proceeding, to allow time for reading.
    public static void waitForUser() {
        System.out.println();
        System.out.println(BLUE_ANSI_CODE + "When you are ready to proceed, hit enter." + ANSI_RESET_CODE);
        String proceed = input.nextLine();
    }

    //Creates enemy turn
    public static void enemyTurn() {

        //Skip enemy's turn if on fire (Mage: Spell: Fireball) or blinded (Swordsman: Smoke Bomb: Inventory).
        //Skip enemy's turn if player is Blocking (Swordsman) or Dodging (Archer).
        if (!isEnemyOnFire && !isEnemyBlinded && !isPlayerBlocking && !isPlayerDodging) {

            //Randomly generates enemy attack - defined just below.
            String currentEnemyAttack = randomAttack();
            if (currentEnemyAttack.equals("Slash")) {
                System.out.println(">The bandit slashes at you with his sword. " + RED_ANSI_CODE + "You take 15 points damage." + ANSI_RESET_CODE);
                playerHealth -= 15;
            } else if (currentEnemyAttack.equals("Stab")) {
                System.out.println(">The bandit stabs you with his sword. " + RED_ANSI_CODE +"You take 10 points damage. You are bleeding." + ANSI_RESET_CODE);
                playerHealth -= 10;
                isPlayerBleeding = true;
            }
        //Skip enemy's turn if player used Block (Swordsman) or Dodge (Archer) on the last turn.
        }

        //Damage enemy if bleeding (swordsman), poisoned (archer), or on fire (mage).
        if (isEnemyPoisoned) {
            System.out.println("The bandit is poisoned and takes 3pts poison damage.");
            enemyHealth -= 3;
        } else if (isEnemyBleeding) {
            System.out.println("The bandit is bleeding and takes 2pt bleeding damage.");
            enemyHealth -= 2;

        //Enemy's turn skip conditions explained to user and reset (each of the 4 cases result in 1 skipped enemy turn).
        } else if (isEnemyOnFire) {
            System.out.println("The bandit is on fire and takes 5 pts fire damage. He takes his turn to put himself out");
            enemyHealth -= 5;
            isEnemyOnFire = false;
        } else if (isEnemyBlinded) {
            System.out.println(">The bandit is blinded and cannot attack");
            isEnemyBlinded = false;
        } else if (isPlayerBlocking) {
            System.out.println("The bandit swings his sword at you, but you catch it with the broad side of your\n" +
                    "blade, redirecting it away from your body.");
            isPlayerBlocking = false;
        } else if (isPlayerDodging) {
            System.out.println("The bandit thrusts his sword towards your stomach, but at the last moment you glide\n" +
                    "out of harms way.");
            isPlayerDodging = false;
        }
    }


    //Random attack generator for enemy.
    public static String randomAttack(){
        //get random number
        Random rand = new Random();
        int randomNumber = rand.nextInt(10);

        //List of random attacks (same as swordsman)
        String[] enemyAttacks = {"Slash", "Stab"};

        //Weight random number to make slash happen more often than stab.
        int weightedNumber;
        if (randomNumber > 1) {
            weightedNumber = 0;
        } else {
            weightedNumber = 1;
        }

        //get random attack
        return enemyAttacks[weightedNumber];
    }

    //This function processes global commands, unless it is a "use" command in which case it calls the processUseCommand function.
    public static void processGlobalCommands() {
        isGlobalCommandUsed = true;

        if (command.equalsIgnoreCase("commands")) {
            System.out.println(globalCommands); //defined above

        } else if (command.equalsIgnoreCase("inventory")){
            //display player inventory
            for (Map.Entry<String, Integer> item : currentClass.Inventory.entrySet()) {
                System.out.println(item.getKey() + ": " + item.getValue());
            }
        } else if (command.equalsIgnoreCase("attacks")) {
            //display player attacks if not a mage
            if (playerClass.equals("Mage")) {
                System.out.println("Mages use spells");
                return;
            } else {
                for (String attack : currentClass.AttackList) {
                    System.out.println(attack);
                }
            }
        } else if (command.equalsIgnoreCase("spells")) {
            //display player spells if mage
            if (!playerClass.equals("Mage")) {
                System.out.println("You don't know any spells.");
            } else {
                for (Map.Entry<String, Integer> spell : currentClass.SpellList.entrySet()) {
                    System.out.println(spell.getKey() + ": " + spell.getValue() + " mana");
                }
            }
        } else if (command.equalsIgnoreCase("status")) {
            //displays user health and mana
            System.out.println("Your Health: " + playerHealth);
            if (playerClass.equals("Mage")) {
                System.out.println("Your Mana: " + playerMana);
            }
            if (isPlayerBleeding) {
                System.out.println("You are bleeding.");
            }
        } else if (command.equalsIgnoreCase("use")) {
            //Defined just below
            processUseCommand();
        } else {
            isGlobalCommandUsed = false;
        }
    }

    public static void processUseCommand() {
        //Take input for item to use
        System.out.println("What would you like to use?");
        //display player inventory
        for (Map.Entry<String, Integer> item : currentClass.Inventory.entrySet()) {
            System.out.println(item.getKey() + ": " + item.getValue());
        }
        String useItem = input.nextLine();
        System.out.println();

        //Checks if player has item they are asking to use.
        if (playerInventory.containsKey(useItem)){
            int numberOfUseItems = playerInventory.get(useItem);

            //Doesn't let player use item if they don't have any left, otherwise uses the item.
            if (numberOfUseItems < 1) {
                System.out.println("You don't have any left");
                return;
            }else if (useItem.equals("Health Potion")) {
                playerInventory.put(useItem, numberOfUseItems-1);

                playerHealth += 20;

                System.out.println(GREEN_ANSI_CODE + "You drink a health potion and regain 20 health" + ANSI_RESET_CODE);

            } else if (useItem.equals("Mana Potion")) {
                playerInventory.put(useItem, numberOfUseItems-1);

                playerMana += 30;

                System.out.println(BLUE_ANSI_CODE + "You drink a mana potion and regain 30 mana" + ANSI_RESET_CODE);

            } else if (useItem.equals("Smoke Bomb")) {
                playerInventory.put(useItem, numberOfUseItems - 1);

                isEnemyBlinded = true;

                System.out.println("You throw a smoke bomb at the bandit, blinding him.");

            //Archers arrows are stored in inventory, but are used in attacks. They cannot be used as an inventory item.
            } else if (useItem.equals("Normal Arrows") || useItem.equals("Poison Arrows")) {
                System.out.println("These aren't used this way, try using their attack instead.");
            }
        } else {
            System.out.println("You don't have an item of that name.");
        }
    }





    public static void processMageCommands(){
        if (playerMana > 0) {
            if (command.equalsIgnoreCase("Fireball")) {
                System.out.println(">A fireball erupts from your palm and engulfs the bandit. He takes 5pts of damage. He is on fire.");
                enemyHealth -= 5;
                isEnemyOnFire = true;
                playerMana -= 15;
                return;
            } else if (command.equalsIgnoreCase("Shock")) {
                System.out.println(">Bolts of electricity escape your fingertips and strike the bandit. He takes 18pts damage.");
                enemyHealth -= 18;
                playerMana -= 15;
                return;
            } else if (command.equalsIgnoreCase("Heal")) {
                System.out.println(GREEN_ANSI_CODE + ">You heal yourself 25 pts." + ANSI_RESET_CODE);
                playerHealth += 25;
                playerMana -= 10;
                if (isPlayerBleeding) {
                    System.out.println("You are no longer bleeding.");
                    isPlayerBleeding = false;
                }
                return;
            }
        } else {
            System.out.println("You are out of mana.");
        }
    }

    public static void processSwordsmanCommands() {
        if (command.equalsIgnoreCase("Slash")) {
            System.out.println(">Your sword glides through the air and strikes the bandit across the chest.\n" +
                    "he takes 15 pts damage.");
            enemyHealth -= 15;
            return;
        } else if (command.equalsIgnoreCase("Stab")) {
            System.out.println(">You quickly jab your sword at the bandit's stomach. He takes 10 pts damage. He is bleeding.");
            enemyHealth -= 10;
            isEnemyBleeding = true;
            return;
        } else if (command.equalsIgnoreCase("Block")); {
            System.out.println(">You take a defensive stance.");
            isPlayerBlocking = true;
            return;
        }
    }

    public static void processArcherCommands() {
        if (command.equalsIgnoreCase("Normal Arrow")) {
            System.out.println(">You draw your bow with a normal arrow and loose it with great speed. It strikes the bandit\n" +
                    "in the chest, slightly penetrating his armor. He takes 18 pts damage.");
            enemyHealth -= 18;
            return;
        } else if (command.equalsIgnoreCase("Poison Arrow")) {
            System.out.println(">You draw your bow with a poison-tipped arrow and loose it with great precision. It slices\n" +
                    "the small amount of the bandit's exposed skin inflicting 12 pts damage. He is poisoned.");
            enemyHealth -= 12;
            isEnemyPoisoned = true;
            return;
        } else if (isPlayerDodging) {
            System.out.println(">Your senses are heightened and you become light on your feet.");
            isPlayerDodging = true;
            return;
        }

    }


}


