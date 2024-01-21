import java.util.HashMap;
import java.util.HashSet;

public class ArcherClass extends CharacterClass{
    public ArcherClass() {
        this.Name = "Archer";
        this.StartingHealth = 100;
        this.AttackList = new HashSet<>() {{add("Normal Arrow"); add("Poison Arrow");}};
        this.Inventory = new HashMap<>() {{put("Health Potion", 2); put("Normal Arrows", 20); put("Poison Arrows", 5);}};
    }
}
