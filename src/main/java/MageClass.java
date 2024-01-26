import java.util.*;


public class MageClass extends CharacterClass{
    public MageClass() {
        this.Name = "Mage";
        this.Description = ">Description: Each spell the Mage casts will cost Mana. You can check how much mana you \n" +
                "have at any time using the 'status' command.";
        this.StartingHealth = 80;
        this.Mana = 100;
        this.SpellList = new HashMap<>() {{put("Heal", 10); put("Fireball", 15); put("Shock", 15);}};
        this.Inventory = new HashMap<>() {{put("Health Potion", 2); put("Mana Potion", 1);}};
    }
}
