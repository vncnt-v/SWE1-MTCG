package sets;

import bif3.swe1.mtcg.CardPackage;
import bif3.swe1.mtcg.Stack;
import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.Monsters.*;
import bif3.swe1.mtcg.cards.Spell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class StackTest {
    Stack stack = new Stack();

    @BeforeEach
    void setUp() {
        stack.AddCard(new Dragon("00000","Blue Dragon",117));
        stack.AddCard(new FireElf("00001","Old Fire Elf",107));
        stack.AddCard(new Goblin("00010","Green Goblin",87));
        stack.AddCard(new Knight("00011","Heavy Knight",120));
        stack.AddCard(new Kraken("00100","Deep Blue Kraken",140));
        stack.AddCard(new Ork("00101","White Ork",98));
        stack.AddCard(new Wizard("00110","Dark Wizard",117));
        stack.AddCard(new Spell("00111","Blue Wave Spell",89));
        stack.AddCard(new Spell("01000","Red Fire Spell",100));
        stack.AddCard(new Spell("01001","Normal Spell",100));
        stack.AddCard(new Dragon("01010","Red Dragon",123));
        stack.AddCard(new FireElf("01011","Old Water Elf",106));
        stack.AddCard(new Goblin("01100","Dark Goblin",102));
        stack.AddCard(new Knight("01101","Strong Knight",119));
        stack.AddCard(new Kraken("01110","Deep Black Kraken",143));
        stack.AddCard(new Ork("01111","Gray Ork",87));
        stack.AddCard(new Wizard("10000","White Wizard",110));
        stack.AddCard(new Spell("10001","Deep Ocean Spell",90));
        stack.AddCard(new Spell("10010","Flame Spell",112));
        stack.AddCard(new Spell("10011","Normal Magic Spell",100));
    }

    @Test
    public void stack_size() {
        assertEquals(20,stack.getCards().size());
    }
    @Test
    public void get_card() {
        assertNotNull(stack.getCard("01100"));
        assertEquals(19,stack.getCards().size());
    }
    @Test
    public void get_not_card() {
        assertNull(stack.getCard("11111"));
        assertEquals(20,stack.getCards().size());
    }
    @Test
    public void stack_remove() {
        stack.RemoveCard(stack.getRandomCard());
        assertEquals(19,stack.getCards().size());
    }

    @Test
    public void stack_isEmpty_true() {
        while(stack.getCards().size() > 0){
            stack.RemoveCard(stack.getRandomCard());
        }
        assertTrue(stack.getCards().isEmpty());
    }

    @Test
    public void stack_isNull_true() {
        Stack tmp = new Stack();
        assertNull(tmp.getRandomCard());
        assertTrue(tmp.getCards().isEmpty());
    }

    @Test
    public void stack_isEmpty_false() {
        stack.RemoveCard(stack.getRandomCard());
        assertFalse(stack.getCards().isEmpty());
    }
    @Test
    public void add_package() {
        List<AbstractCard> pck_cards = new ArrayList<>();
        pck_cards.add(new Ork("01111","Gray Ork",87));
        pck_cards.add(new Wizard("10000","White Wizard",110));
        pck_cards.add(new Spell("10001","Deep Ocean Spell",90));
        pck_cards.add(new Spell("10010","Flame Spell",112));
        pck_cards.add(new Spell("10011","Normal Magic Spell",100));
        CardPackage pck = new CardPackage(pck_cards);
        stack.AddPackage(pck);
        assertEquals(25,stack.getCards().size());
    }
}
