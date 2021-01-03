import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CombatTest {
/*
    @Test
    public void combat_system_winner() {
        // Create User
        User user1 = new User("admin","admin");
        User user2 = new User("admin","admin");
        // Add Cards
        user1.getStack().AddCard(new Dragon("00001","Blue Dragon",117));
        user1.getStack().AddCard(new Dragon("00010","Blue Dragon",117));
        user1.getStack().AddCard(new Dragon("00011","Blue Dragon",117));
        user1.getStack().AddCard(new Dragon("00100","Blue Dragon",117));
        user2.getStack().AddCard(new Dragon("00110","Blue Dragon",120));
        user2.getStack().AddCard(new Dragon("00111","Blue Dragon",120));
        user2.getStack().AddCard(new Dragon("01000","Blue Dragon",120));
        user2.getStack().AddCard(new Dragon("01001","Blue Dragon",120));
        // Create Random Deck
        assertTrue(user1.CreateRandomDeck());
        assertTrue(user2.CreateRandomDeck());
        // Fight
        Combat combat = new Combat(user1,user2);
        combat.Fight();
        assertEquals(1,user1.getCount_of_games());
        assertEquals(1,user2.getCount_of_games());
        assertEquals(0,user1.getCount_of_wins());
        assertEquals(1,user2.getCount_of_wins());
    }
    @Test
    public void combat_system_no_winner() {
        // Create User
        User user1 = new User("admin","admin");
        User user2 = new User("admin","admin");
        // Add Cards
        user1.getStack().AddCard(new Dragon("0001","Blue Dragon",117));
        user1.getStack().AddCard(new Dragon("0001","Blue Dragon",117));
        user1.getStack().AddCard(new Dragon("0001","Blue Dragon",117));
        user1.getStack().AddCard(new Dragon("0001","Blue Dragon",117));
        user2.getStack().AddCard(new Dragon("0010","Blue Dragon",117));
        user2.getStack().AddCard(new Dragon("0010","Blue Dragon",117));
        user2.getStack().AddCard(new Dragon("0010","Blue Dragon",117));
        user2.getStack().AddCard(new Dragon("0010","Blue Dragon",117));
        // Create Random Deck
        assertEquals(4,user1.getStack().getCards().size());
        assertEquals(4,user2.getStack().getCards().size());
        user1.CreateRandomDeck();
        user2.CreateRandomDeck();
        assertEquals(0,user1.getStack().getCards().size());
        assertEquals(0,user2.getStack().getCards().size());
        assertEquals(4,user1.getDeck().getCards().size());
        assertEquals(4,user2.getDeck().getCards().size());
        // Fight
        Combat combat = new Combat(user1,user2);
        combat.Fight();
        assertEquals(1,user1.getCount_of_games());
        assertEquals(1,user2.getCount_of_games());
        assertEquals(0,user1.getCount_of_wins());
        assertEquals(0,user2.getCount_of_wins());
    }
    @Test
    public void combat_system_no_decks() {
        // Create User
        User user1 = new User("admin","admin");
        User user2 = new User("admin","admin");
        // Create Random Deck
        assertFalse(user1.CreateRandomDeck());
        assertFalse(user2.CreateRandomDeck());
        // Fight
        Combat combat = new Combat(user1,user2);
        combat.Fight();
        assertEquals(0,user1.getCount_of_games());
        assertEquals(0,user2.getCount_of_games());
        assertEquals(0,user1.getCount_of_wins());
        assertEquals(0,user2.getCount_of_wins());
    }
    @Test
    public void combat_system() {
        // Package
        CardPackage pck_1;
        CardPackage pck_2;
        CardPackage pck_3;
        CardPackage pck_4;
        List<AbstractCard> cards1 = new ArrayList<>();
        List<AbstractCard> cards2 = new ArrayList<>();
        List<AbstractCard> cards3 = new ArrayList<>();
        List<AbstractCard> cards4 = new ArrayList<>();
        cards1.add(new Dragon("00001","Blue Water Dragon",117));
        cards1.add(new FireElf("00001","Old Fire Elf",107));
        cards1.add(new Goblin("00001","Green Goblin",87));
        cards1.add(new Knight("00001","Heavy Knight",120));
        cards1.add(new Kraken("00001","Deep Water Kraken",140));

        cards2.add(new Ork("00001","White Ork",98));
        cards2.add(new Wizard("00001","Dark Fire Wizard",117));

        cards2.add(new SpellCard("00001","Blue Water Spell",89));
        cards2.add(new SpellCard("00001","Red Fire Spell",100));
        cards2.add(new SpellCard("00001","Normal Spell",100));

        cards3.add(new Dragon("00001","Red Fire Dragon",123));
        cards3.add(new FireElf("00001","Old Water Elf",106));
        cards3.add(new Goblin("00001","Fire Goblin",102));
        cards3.add(new Knight("00001","Strong Knight",119));
        cards3.add(new Kraken("00001","Deep Water Kraken",143));
        cards4.add(new Ork("00001","Water Ork",87));
        cards4.add(new Wizard("00001","Water Wizard",110));

        cards4.add(new SpellCard("00001","Deep Water Spell",90));
        cards4.add(new SpellCard("00001","Fire Spell",112));
        cards4.add(new SpellCard("00001","Normal Magic Spell",100));
        pck_1 = new CardPackage(cards1);
        pck_2 = new CardPackage(cards2);
        pck_3 = new CardPackage(cards3);
        pck_4 = new CardPackage(cards4);

        // Create User
        User user1 = new User("admin","admin");
        user1.BuyPackage(pck_1);
        user1.BuyPackage(pck_4);
        assertEquals(10,user1.getStack().getCards().size());
        User user2 = new User("admin2","admin2");
        user2.BuyPackage(pck_2);
        user2.BuyPackage(pck_3);
        assertEquals(10,user2.getStack().getCards().size());

        // Create Random Deck
        user1.CreateRandomDeck();
        assertEquals(6,user1.getStack().getCards().size());
        assertEquals(4,user1.getDeck().getCards().size());
        user2.CreateRandomDeck();
        assertEquals(6,user2.getStack().getCards().size());
        assertEquals(4,user2.getDeck().getCards().size());
        // Fight
        Combat combat = new Combat(user1,user2);
        combat.Fight();

        // Result
        assertEquals(1,user1.getCount_of_games());
        assertEquals(1,user2.getCount_of_games());
        assertNull(user1.getDeck());
        assertNull(user2.getDeck());
        assertEquals(10,user1.getStack().getCards().size());
        assertEquals(10,user2.getStack().getCards().size());
    }*/
}
