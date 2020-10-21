import bif3.swe1.mtcg.Combat;
import bif3.swe1.mtcg.Stack;
import bif3.swe1.mtcg.User;
import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.Monsters.*;
import bif3.swe1.mtcg.cards.Spell;
import bif3.swe1.mtcg.cards.ElementType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CombatTest {

    @Test
    public void combat_system_winner() {
        List<AbstractCard> cards1 = new ArrayList<>();
        List<AbstractCard> cards2 = new ArrayList<>();
        cards1.add(new Dragon("Blue Dragon",117,ElementType.water));
        cards2.add(new Dragon("Blue Dragon",120,ElementType.water));
        // Create Stack
        Stack stack1 = new Stack(cards1);
        Stack stack2 = new Stack(cards2);
        // Create User
        User user1 = new User("admin","admin",stack1,0,0);
        User user2 = new User("admin","admin",stack2,0,0);
        // Create Random Deck
        user1.CreateRandomDeck();
        user2.CreateRandomDeck();
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
        List<AbstractCard> cards1 = new ArrayList<>();
        List<AbstractCard> cards2 = new ArrayList<>();
        cards1.add(new Dragon("Blue Dragon",117,ElementType.water));
        cards2.add(new Dragon("Blue Dragon",117,ElementType.water));
        // Create Stack
        Stack stack1 = new Stack(cards1);
        Stack stack2 = new Stack(cards2);
        // Create User
        User user1 = new User("admin","admin",stack1,0,0);
        User user2 = new User("admin","admin",stack2,0,0);
        // Create Random Deck
        assertEquals(1,user1.getStack().getCards().size());
        assertEquals(1,user2.getStack().getCards().size());
        user1.CreateRandomDeck();
        user2.CreateRandomDeck();
        assertEquals(0,user1.getStack().getCards().size());
        assertEquals(0,user2.getStack().getCards().size());
        assertEquals(1,user1.getDeck().getCards().size());
        assertEquals(1,user2.getDeck().getCards().size());
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
        List<AbstractCard> cards1 = new ArrayList<>();
        List<AbstractCard> cards2 = new ArrayList<>();
        // Create Stack
        Stack stack1 = new Stack(cards1);
        Stack stack2 = new Stack(cards2);
        // Create User
        User user1 = new User("admin","admin",stack1,0,0);
        User user2 = new User("admin","admin",stack2,0,0);
        // Create Random Deck
        user1.CreateRandomDeck();
        user2.CreateRandomDeck();
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
        List<AbstractCard> cards1 = new ArrayList<>();
        List<AbstractCard> cards2 = new ArrayList<>();
        cards1.add(new Dragon("Blue Dragon",117,ElementType.water));
        cards1.add(new FireElf("Old Fire Elf",107,ElementType.fire));
        cards1.add(new Goblin("Green Goblin",87,ElementType.normal));
        cards1.add(new Knight("Heavy Knight",120,ElementType.normal));
        cards1.add(new Kraken("Deep Blue Kraken",140,ElementType.water));
        cards1.add(new Ork("White Ork",98,ElementType.normal));
        cards1.add(new Wizard("Dark Wizard",117,ElementType.fire));

        cards1.add(new Spell("Blue Wave Spell",89, ElementType.water));
        cards1.add(new Spell("Red Fire Spell",100, ElementType.fire));
        cards1.add(new Spell("Normal Spell",100, ElementType.normal));

        cards2.add(new Dragon("Red Dragon",123,ElementType.fire));
        cards2.add(new FireElf("Old Water Elf",106,ElementType.water));
        cards2.add(new Goblin("Dark Goblin",102,ElementType.fire));
        cards2.add(new Knight("Strong Knight",119,ElementType.normal));
        cards2.add(new Kraken("Deep Black Kraken",143,ElementType.water));
        cards2.add(new Ork("Gray Ork",87,ElementType.water));
        cards2.add(new Wizard("White Wizard",110,ElementType.water));

        cards2.add(new Spell("Deep Ocean Spell",90, ElementType.water));
        cards2.add(new Spell("Flame Spell",112, ElementType.fire));
        cards2.add(new Spell("Normal Magic Spell",100, ElementType.normal));
        // Create Cards
        Stack stack1 = new Stack(cards1);
        Stack stack2 = new Stack(cards2);
        // Create User
        User user1 = new User("admin","admin",stack1,0,0);
        assertEquals(10,user1.getStack().getCards().size());
        User user2 = new User("admin2","admin2",stack2,0,0);
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
    }
}
