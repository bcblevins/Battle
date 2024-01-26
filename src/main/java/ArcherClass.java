import java.util.HashMap;
import java.util.HashSet;

public class ArcherClass extends CharacterClass{
    public ArcherClass() {
        this.Name = "Archer";
        this.Description = ">Description: The Archer only has a certain number of each type of arrow. You can \n" +
                "check how many arrows you have at any time using the 'inventory' command.";
        this.StartingHealth = 100;
        this.AttackList = new HashSet<>() {{add("Normal Arrow"); add("Poison Arrow"); add("Dodge");}};
        this.Inventory = new HashMap<>() {{put("Health Potion", 2); put("Normal Arrows", 20); put("Poison Arrows", 5);}};
    }
}
