import bif3.swe1.mtcg.CardPackage;
import bif3.swe1.mtcg.GameManager;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    CardPackage pck_1;
    CardPackage pck_2;
    CardPackage pck_3;
    CardPackage pck_4;
    CardPackage pck_5;
    User user;

    @BeforeEach
    void setUp() {
        user = new User("admin","123");
        List<AbstractCard> cards_1 = new ArrayList<>();
        List<AbstractCard> cards_2 = new ArrayList<>();
        List<AbstractCard> cards_3 = new ArrayList<>();
        List<AbstractCard> cards_4 = new ArrayList<>();
        List<AbstractCard> cards_5 = new ArrayList<>();
        cards_1.add(new Dragon("00001","Blue Dragon",117));
        cards_1.add(new FireElf("00010","Old Fire Elf",107));
        cards_1.add(new Goblin("00011","Green Goblin",87));
        cards_1.add(new Knight("00100","Heavy Knight",120));
        cards_1.add(new Kraken("00101","Deep Blue Kraken",140));
        cards_2.add(new Ork("00110","White Ork",98));
        cards_2.add(new Wizard("00111","Dark Wizard",117));
        cards_2.add(new Spell("01000","Blue Wave Spell",89));
        cards_2.add(new Spell("01001","Red Fire Spell",100));
        cards_2.add(new Spell("01010","Normal Spell",100));
        cards_3.add(new Ork("00110","White Ork",98));
        cards_3.add(new Wizard("00111","Dark Wizard",117));
        cards_3.add(new Spell("01000","Blue Wave Spell",89));
        cards_3.add(new Spell("01001","Red Fire Spell",100));
        cards_3.add(new Spell("01010","Normal Spell",100));
        cards_4.add(new Ork("00110","White Ork",98));
        cards_4.add(new Wizard("00111","Dark Wizard",117));
        cards_4.add(new Spell("01000","Blue Wave Spell",89));
        cards_4.add(new Spell("01001","Red Fire Spell",100));
        cards_4.add(new Spell("01010","Normal Spell",100));
        cards_5.add(new Ork("00110","White Ork",98));
        cards_5.add(new Wizard("00111","Dark Wizard",117));
        cards_5.add(new Spell("01000","Blue Wave Spell",89));
        cards_5.add(new Spell("01001","Red Fire Spell",100));
        cards_5.add(new Spell("01010","Normal Spell",100));
        pck_1 = new CardPackage(cards_1);
        pck_2 = new CardPackage(cards_2);
        pck_3 = new CardPackage(cards_3);
        pck_4 = new CardPackage(cards_4);
        pck_5 = new CardPackage(cards_5);
    }

    @Test
    void addPck(){
        assertEquals(0,user.getStack().getCards().size());
        assertTrue(user.BuyPackage(pck_1));
        assertEquals(5,user.getStack().getCards().size());
        assertTrue(user.BuyPackage(pck_2));
        assertEquals(10,user.getStack().getCards().size());
    }

    @Test
    void noMoney(){
        assertTrue(user.BuyPackage(pck_1));
        assertTrue(user.BuyPackage(pck_2));
        assertTrue(user.BuyPackage(pck_3));
        assertTrue(user.BuyPackage(pck_4));
        assertFalse(user.BuyPackage(pck_5));
    }

    @Test
    void createDeck(){
        assertTrue(user.BuyPackage(pck_1));
        assertTrue(user.BuyPackage(pck_2));
        List<String> id = new ArrayList<>();
        id.add("01000");
        id.add("00111");
        id.add("00001");
        id.add("00011");
        assertTrue(user.createDeck(id));
        assertEquals(6,user.getStack().getCards().size());
        assertEquals(4,user.getDeck().getCards().size());
    }

    @Test
    void createDeckFail(){
        assertTrue(user.BuyPackage(pck_1));
        assertTrue(user.BuyPackage(pck_2));
        List<String> id = new ArrayList<>();
        id.add("01000");
        id.add("00111");
        id.add("00001");
        id.add("10011");
        assertFalse(user.createDeck(id));
        assertEquals(10,user.getStack().getCards().size());
        assertNull(user.getDeck());
    }

    @Test
    void restoreDeck(){
        assertTrue(user.BuyPackage(pck_1));
        assertTrue(user.BuyPackage(pck_2));
        List<String> id = new ArrayList<>();
        id.add("01000");
        id.add("00111");
        id.add("00001");
        id.add("00011");
        assertTrue(user.createDeck(id));
        assertEquals(6,user.getStack().getCards().size());
        assertEquals(4,user.getDeck().getCards().size());
        id.clear();
        id.add("01000");
        id.add("00111");
        id.add("00001");
        id.add("10011");
        assertFalse(user.createDeck(id));
        assertEquals(6,user.getStack().getCards().size());
        assertEquals(4,user.getDeck().getCards().size());
        assertNull(user.getStack().getCard("01000"));
        user.RemoveDeck();
        assertEquals(10,user.getStack().getCards().size());
    }

    @Test
    void createRandomDeck(){
        assertTrue(user.BuyPackage(pck_1));
        assertTrue(user.BuyPackage(pck_2));
        assertTrue(user.CreateRandomDeck());
        assertEquals(6,user.getStack().getCards().size());
        assertEquals(4,user.getDeck().getCards().size());
    }

    @Test
    void draw(){
        assertTrue(user.BuyPackage(pck_1));
        assertTrue(user.CreateRandomDeck());
        AbstractCard card = user.Draw();
        assertNotNull(card);
        user.getDeck().RemoveCard(card);
        assertEquals(3,user.getDeck().getCards().size());
        assertEquals(1,user.getStack().getCards().size());
    }

    @Test
    void draw_null(){
        assertNull(user.Draw());
    }

    @Test
    void check_pwd(){
        assertTrue(user.checkPwd("123"));
    }

    @Test
    void check_pwd_fail(){
        assertFalse(user.checkPwd("321"));
    }

    @Test
    void won(){
        assertEquals(0,user.getCount_of_games());
        assertEquals(0,user.getCount_of_wins());
        user.won();
        user.won();
        user.lose();
        user.won();
        user.lose();
        assertEquals(5,user.getCount_of_games());
        assertEquals(3,user.getCount_of_wins());
    }
}
