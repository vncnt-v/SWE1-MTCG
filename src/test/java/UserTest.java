import bif3.swe1.mtcg.CardPackage;
import bif3.swe1.mtcg.Stack;
import bif3.swe1.mtcg.User;
import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;
import bif3.swe1.mtcg.cards.Monsters.*;
import bif3.swe1.mtcg.cards.Spell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    List<AbstractCard> cards = new ArrayList<>();
    User user;

    @BeforeEach
    void setUp() {
        cards.add(new Dragon("Blue Dragon",117, ElementType.water));
        cards.add(new FireElf("Old Fire Elf",107,ElementType.fire));
        cards.add(new Goblin("Green Goblin",87,ElementType.normal));
        cards.add(new Knight("Heavy Knight",120,ElementType.normal));
        cards.add(new Kraken("Deep Blue Kraken",140,ElementType.water));
        cards.add(new Ork("White Ork",98,ElementType.normal));
        cards.add(new Wizard("Dark Wizard",117,ElementType.fire));
        cards.add(new Spell("Blue Wave Spell",89, ElementType.water));
        cards.add(new Spell("Red Fire Spell",100, ElementType.fire));
        cards.add(new Spell("Normal Spell",100, ElementType.normal));
        Stack stack = new Stack(cards);
        user = new User("user1","pwd",stack,0,0);
    }

    @Test
    void addPck(){
        List<AbstractCard> tmp = new ArrayList<>();
        tmp.add(new Goblin("Dark Goblin",102,ElementType.fire));
        tmp.add(new Knight("Strong Knight",119,ElementType.normal));
        tmp.add(new Kraken("Deep Black Kraken",143,ElementType.water));
        tmp.add(new Ork("Gray Ork",87,ElementType.water));
        tmp.add(cards.get(5));

        CardPackage pck = new CardPackage(tmp);
        assertEquals(10,user.getStack().getCards().size());
        user.AddPackage(pck);
        assertEquals(15,user.getStack().getCards().size());
    }

    @Test
    void createRandomDeck(){
        user.CreateRandomDeck();
        assertEquals(6,user.getStack().getCards().size());
        assertEquals(4,user.getDeck().getCards().size());
    }

    @Test
    void draw(){
        List<AbstractCard> tmp = new ArrayList<>();
        tmp.add(new Goblin("Dark Goblin",102,ElementType.fire));
        user.CreateRandomDeck();
        AbstractCard card = user.Draw();
        user.getDeck().RemoveCard(card);
        assertEquals(3,user.getDeck().getCards().size());
    }
}
