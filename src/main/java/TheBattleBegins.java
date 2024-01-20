import java.util.*;

public class TheBattleBegins {
    public static int enemyHealth = 100;
    public static boolean isOnFire;
    public static boolean isBleeding;
    public static boolean isPoisoned;
    public static boolean isEnemyDead;
    public static boolean isPlayerDead;
    public static ArcherClass archer = new ArcherClass();
    public static SwordClass swordsman = new SwordClass();
    public static MageClass mage = new MageClass();
    public static CharacterClass currentClass;
    public static int playerHealth;

    public static void main(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please select a character class:");
        System.out.println("1. Mage");
        System.out.println("2. Swordsman");
        System.out.println("3. Archer");
        String classChoice = input.nextLine();

    }
}
