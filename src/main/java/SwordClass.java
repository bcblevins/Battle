import java.util.HashMap;
import java.util.HashSet;

public class SwordClass extends CharacterClass{
    public SwordClass() {
        this.Name = "Swordsman";
        this.StartingHealth = 120;
        this.AttackList = new HashSet<>() {{add("Slash"); add("Stab");}};
        this.Inventory = new HashMap<>() {{put("Health Potion", 2); put("Smoke Bomb", 1);}};
    }
}
