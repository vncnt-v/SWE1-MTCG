package cards;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;
import bif3.swe1.mtcg.cards.Monsters.*;
import bif3.swe1.mtcg.cards.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageCalculationTest {

    @Test
    public void Dragon_ability() {
        AbstractCard card1 = new Dragon("Dragon",50, ElementType.water);
        AbstractCard card2 = new Goblin("Goblin",100,ElementType.water);
        int damage = card2.calculateDamage(card1);
        assertEquals(0,damage);
    }

    @Test
    public void FireElf_ability() {
        AbstractCard card1 = new FireElf("FireElf",50, ElementType.water);
        AbstractCard card2 = new Dragon("Dragon",100,ElementType.water);
        int damage = card2.calculateDamage(card1);
        assertEquals(0,damage);
    }

    @Test
    public void Knight_ability() {
        AbstractCard card1 = new Knight("FireElf",50, ElementType.water);
        AbstractCard card2 = new Spell("Spell",100,ElementType.water);
        int damage = card1.calculateDamage(card2);
        assertEquals(-1,damage);
    }

    @Test
    public void Kraken_ability() {
        AbstractCard card1 = new Kraken("Kraken",50, ElementType.water);
        AbstractCard card2 = new Spell("Spell",100,ElementType.water);
        int damage = card2.calculateDamage(card1);
        assertEquals(0,damage);
    }

    @Test
    public void Ork_ability() {
        AbstractCard card1 = new Ork("Ork",50, ElementType.water);
        AbstractCard card2 = new Wizard("Wizard",100,ElementType.water);
        int damage = card1.calculateDamage(card2);
        assertEquals(0,damage);
    }

    @Test
    public void WaterSpell_ability() {
        AbstractCard card1 = new Spell("WaterSpell",50, ElementType.water);
        AbstractCard card2 = new Wizard("FireSpell",50,ElementType.fire);
        AbstractCard card3 = new Spell("NormalSpell",50, ElementType.normal);
        int damage1 = card1.calculateDamage(card2);
        int damage2 = card1.calculateDamage(card3);

        assertEquals(100,damage1);
        assertEquals(25,damage2);
    }
    @Test
    public void FireSpell_ability() {
        AbstractCard card1 = new Spell("FireSpell",50, ElementType.fire);
        AbstractCard card2 = new Wizard("NormalSpell",50,ElementType.normal);
        AbstractCard card3 = new Spell("WaterSpell",50, ElementType.water);
        int damage1 = card1.calculateDamage(card2);
        int damage2 = card1.calculateDamage(card3);

        assertEquals(100,damage1);
        assertEquals(25,damage2);
    }
    @Test
    public void NormalSpell_ability() {
        AbstractCard card1 = new Spell("NormalSpell",50, ElementType.normal);
        AbstractCard card2 = new Wizard("WaterSpell",50,ElementType.water);
        AbstractCard card3 = new Spell("FireSpell",50, ElementType.fire);
        int damage1 = card1.calculateDamage(card2);
        int damage2 = card1.calculateDamage(card3);

        assertEquals(100,damage1);
        assertEquals(25,damage2);
    }
    @Test
    public void NormalMonsterDamage() {
        AbstractCard card1 = new Dragon("Dragon",50, ElementType.fire);
        AbstractCard card2 = new Knight("Knight",50,ElementType.normal);
        AbstractCard card3 = new Wizard("Wizard",50, ElementType.water);
        int damage1 = card1.calculateDamage(card2);
        int damage2 = card1.calculateDamage(card3);
        int damage3 = card2.calculateDamage(card1);
        int damage4 = card2.calculateDamage(card3);
        int damage5 = card3.calculateDamage(card1);
        int damage6 = card3.calculateDamage(card2);

        assertEquals(50,damage1);
        assertEquals(50,damage2);
        assertEquals(50,damage3);
        assertEquals(50,damage4);
        assertEquals(50,damage5);
        assertEquals(50,damage6);
    }
}
