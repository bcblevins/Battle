import java.util.*;

public class CharacterClass {
        public String Name;
        public String Description;
        public int StartingHealth;
        public int Mana;
        public Set<String> AttackList = new HashSet<>();
        public Map<String, Integer> SpellList = new HashMap<>();
        public Map<String, Integer> Inventory = new HashMap<>();

}
