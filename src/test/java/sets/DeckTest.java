package sets;

import bif3.swe1.mtcg.cards.collections.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
/*
    List<AbstractCard> cards = new ArrayList<>();

    @BeforeEach
    void setUp() {
        cards.add(new Dragon("00000","Blue Dragon",117));
        cards.add(new FireElf("00001","Old Fire Elf",107));
        cards.add(new Goblin("00010","Green Goblin",87));
        cards.add(new Knight("00011","Heavy Knight",120));
        cards.add(new Kraken("00100","Deep Blue Kraken",140));
        cards.add(new Ork("00101","White Ork",98));
        cards.add(new Wizard("00110","Dark Wizard",117));
        cards.add(new SpellCard("00111","Blue Wave Spell",89));
        cards.add(new SpellCard("01000","Red Fire Spell",100));
        cards.add(new SpellCard("01001","Normal Spell",100));
        cards.add(new Dragon("01010","Red Dragon",123));
        cards.add(new FireElf("01011","Old Water Elf",106));
        cards.add(new Goblin("01100","Dark Goblin",102));
        cards.add(new Knight("01101","Strong Knight",119));
        cards.add(new Kraken("01110","Deep Black Kraken",143));
        cards.add(new Ork("01111","Gray Ork",87));
        cards.add(new Wizard("10000","White Wizard",110));
        cards.add(new SpellCard("10001","Deep Ocean Spell",90));
        cards.add(new SpellCard("10010","Flame Spell",112));
        cards.add(new SpellCard("10011","Normal Magic Spell",100));
    }

    @Test
    public void deck_size() {
        List<AbstractCard> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck1 = new Deck(tmp);
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck2 = new Deck(tmp);
        assertEquals(4,deck1.getCards().size());
        assertEquals(4,deck2.getCards().size());
    }

    @Test
    public void deck_remove() {
        List<AbstractCard> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck = new Deck(tmp);
        deck.RemoveCard(deck.getRandomCard());
        assertEquals(3,deck.getCards().size());
    }

    @Test
    public void deck_isEmpty_true() {
        List<AbstractCard> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck = new Deck(tmp);
        deck.RemoveCard(deck.getRandomCard());
        assertTrue(deck.getCards().isEmpty());
    }

    @Test
    public void deck_isNull_true() {
        Deck deck = new Deck(null);
        assertNull(deck.getRandomCard());
        assertTrue(deck.getCards().isEmpty());
    }

    @Test
    public void deck_isEmpty_false() {
        List<AbstractCard> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck = new Deck(tmp);
        deck.RemoveCard(deck.getRandomCard());
        assertFalse(deck.getCards().isEmpty());
    }
    */
}
