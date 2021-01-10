import bif3.swe1.mtcg.Card;
import bif3.swe1.mtcg.collections.Deck;
import bif3.swe1.mtcg.types.CardType;
import bif3.swe1.mtcg.types.ElementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    List<Card> cards = new ArrayList<>();

    @BeforeEach
    void setUp() {
        cards.add(new Card("0","Blue Dragon",117, CardType.Dragon, ElementType.water));
        cards.add(new Card("1","Old Fire Elf",107,CardType.FireElf,ElementType.fire));
        cards.add(new Card("2","Green Goblin",87,CardType.Goblin,ElementType.normal));
        cards.add(new Card("3","Heavy Knight",120,CardType.Knight,ElementType.normal));
        cards.add(new Card("4","Deep Blue Kraken",140,CardType.Kraken,ElementType.water));
        cards.add(new Card("5","White Ork",98,CardType.Ork,ElementType.normal));
        cards.add(new Card("6","Dark Wizard",117,CardType.Wizard,ElementType.fire));
        cards.add(new Card("7","Blue Wave Spell",89,CardType.Spell,ElementType.water));
        cards.add(new Card("8","Red Fire Spell",100,CardType.Spell,ElementType.fire));
        cards.add(new Card("9","Normal Spell",100,CardType.Spell,ElementType.normal));
        cards.add(new Card("10","Red Dragon",123,CardType.Dragon,ElementType.fire));
        cards.add(new Card("11","Old Water Elf",106,CardType.FireElf,ElementType.water));
        cards.add(new Card("12","Dark Goblin",102,CardType.Goblin,ElementType.normal));
        cards.add(new Card("13","Strong Knight",119,CardType.Knight,ElementType.normal));
        cards.add(new Card("14","Deep Black Kraken",143,CardType.Kraken,ElementType.water));
        cards.add(new Card("15","Gray Ork",87,CardType.Ork,ElementType.normal));
        cards.add(new Card("16","White Wizard",110,CardType.Wizard,ElementType.normal));
        cards.add(new Card("17","Deep Ocean Spell",90,CardType.Spell,ElementType.water));
        cards.add(new Card("18","Flame Spell",112,CardType.Spell,ElementType.fire));
        cards.add(new Card("19","Normal Magic Spell",100,CardType.Spell,ElementType.normal));
    }

    @Test
    public void deck_size() {
        List<Card> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck1 = new Deck(tmp);
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck2 = new Deck(tmp);
        assertEquals(4,deck1.getSize());
        assertEquals(4,deck2.getSize());
    }

    @Test
    public void deck_remove() {
        List<Card> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck = new Deck(tmp);
        deck.removeCard(deck.getRandomCard());
        assertEquals(3,deck.getSize());
    }

    @Test
    public void deck_add() {
        List<Card> tmp = new ArrayList<>();
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        tmp.add(cards.get((int)(Math.random() * cards.size())));
        Deck deck = new Deck(tmp);
        deck.addCard(cards.get((int)(Math.random() * cards.size())));
        deck.addCard(cards.get((int)(Math.random() * cards.size())));
        assertEquals(6,deck.getSize());
        deck.removeCard(deck.getRandomCard());
        assertEquals(5,deck.getSize());
    }
}
