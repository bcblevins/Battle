import java.util.*;


public class MageClass extends CharacterClass{
    public MageClass() {
        this.Name = "Mage";
        this.StartingHealth = 80;
        this.Mana = 100;
        this.SpellList = new HashMap<>() {{put("Heal", 10); put("Fireball", 15); put("Shock", 15);}};
        this.Inventory = new HashMap<>() {{put("Health Potion", 2); put("Mana Potion", 1);}};
    }
}
