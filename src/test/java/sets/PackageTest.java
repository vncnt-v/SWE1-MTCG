package sets;

import bif3.swe1.mtcg.CardPackage;
import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;
import bif3.swe1.mtcg.cards.Monsters.*;
import bif3.swe1.mtcg.cards.Spell;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackageTest {

    @Test
    public void Package_Size(){

        List<AbstractCard> cards1 = new ArrayList<>();
        cards1.add(new Dragon("Blue Dragon",117, ElementType.water));
        cards1.add(new FireElf("Old Fire Elf",107,ElementType.fire));
        cards1.add(new Goblin("Green Goblin",87,ElementType.normal));
        cards1.add(new Knight("Heavy Knight",120,ElementType.normal));
        cards1.add(new Kraken("Deep Blue Kraken",140,ElementType.water));
        cards1.add(new Ork("White Ork",98,ElementType.normal));
        cards1.add(new Wizard("Dark Wizard",117,ElementType.fire));
        cards1.add(new Spell("Blue Wave Spell",89, ElementType.water));
        cards1.add(new Spell("Red Fire Spell",100, ElementType.fire));
        cards1.add(new Spell("Normal Spell",100, ElementType.normal));
        cards1.add(new Dragon("Red Dragon",123,ElementType.fire));
        cards1.add(new FireElf("Old Water Elf",106,ElementType.water));
        cards1.add(new Goblin("Dark Goblin",102,ElementType.fire));
        cards1.add(new Knight("Strong Knight",119,ElementType.normal));
        cards1.add(new Kraken("Deep Black Kraken",143,ElementType.water));
        cards1.add(new Ork("Gray Ork",87,ElementType.water));
        cards1.add(new Wizard("White Wizard",110,ElementType.water));
        cards1.add(new Spell("Deep Ocean Spell",90, ElementType.water));
        cards1.add(new Spell("Flame Spell",112, ElementType.fire));
        cards1.add(new Spell("Normal Magic Spell",100, ElementType.normal));

        CardPackage pck = new CardPackage(cards1);
        assertEquals(5,pck.getCards().size());
    }
}
