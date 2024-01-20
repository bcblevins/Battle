import java.util.HashMap;
import java.util.HashSet;

public class ArcherClass extends CharacterClass{
    public ArcherClass() {
        this.Name = "Archer";
        this.StartingHealth = 120;
        this.AttackList = new HashSet<>() {{add("Normal Arrow"); add("Poison Arrow"); add("Dodge");}};
        this.Inventory = new HashMap<>() {{put("Health Potion", 2); put("Normal Arrow", 20); put("Poison Arrow", 5);}};
    }
}
