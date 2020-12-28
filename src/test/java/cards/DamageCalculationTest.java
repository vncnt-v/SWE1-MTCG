package cards;

import bif3.swe1.mtcg.EloManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageCalculationTest {
/*
    EloManager manager;

    @BeforeEach
    void setUp() {
        manager = new EloManager();
    }

    @Test
    public void Dragon_ability() {
        AbstractCard card1 = manager.createCard("123","WaterDragon",50);
        AbstractCard card2 = manager.createCard("1234","WaterGoblin",100);
        float damage = card2.calculateDamage(card1);
        assertEquals(0,damage);
    }

    @Test
    public void FireElf_ability() {
        AbstractCard card1 = manager.createCard("123","FireElf",50);
        AbstractCard card2 = manager.createCard("1234","Dragon",100);
        float damage = card2.calculateDamage(card1);
        assertEquals(0,damage);
    }

    @Test
    public void Knight_ability() {
        AbstractCard card1 = manager.createCard("123","Knight",50);
        AbstractCard card2 = manager.createCard("1234","WaterSpell",100);
        float damage = card1.calculateDamage(card2);
        assertEquals(-1,damage);
    }

    @Test
    public void Kraken_ability() {
        AbstractCard card1 = manager.createCard("123","Kraken",50);
        AbstractCard card2 = manager.createCard("1234","Spell",100);
        float damage = card2.calculateDamage(card1);
        assertEquals(0,damage);
    }

    @Test
    public void Ork_ability() {
        AbstractCard card1 = manager.createCard("123","Ork",50);
        AbstractCard card2 = manager.createCard("1234","Wizard",100);
        float damage = card1.calculateDamage(card2);
        assertEquals(0,damage);
    }

    @Test
    public void WaterSpell_ability() {
        AbstractCard card1 = manager.createCard("123","WaterSpell",50);
        AbstractCard card2 = manager.createCard("1234","FireSpell",50);
        AbstractCard card3 = manager.createCard("12345","NormalSpell",50);
        float damage1 = card1.calculateDamage(card2);
        float damage2 = card1.calculateDamage(card3);

        assertEquals(100,damage1);
        assertEquals(25,damage2);
    }
    @Test
    public void FireSpell_ability() {
        AbstractCard card1 = manager.createCard("123","WaterSpell",50);
        AbstractCard card2 = manager.createCard("1234","FireSpell",50);
        AbstractCard card3 = manager.createCard("12345","NormalSpell",50);
        float damage1 = card1.calculateDamage(card2);
        float damage2 = card1.calculateDamage(card3);

        assertEquals(100,damage1);
        assertEquals(25,damage2);
    }
    @Test
    public void NormalSpell_ability() {
        AbstractCard card1 = manager.createCard("123","WaterSpell",50);
        AbstractCard card2 = manager.createCard("1234","FireSpell",50);
        AbstractCard card3 = manager.createCard("12345","NormalSpell",50);
        float damage1 = card1.calculateDamage(card2);
        float damage2 = card1.calculateDamage(card3);

        assertEquals(100,damage1);
        assertEquals(25,damage2);
    }
    @Test
    public void NormalMonsterDamage() {
        AbstractCard card1 = manager.createCard("123","Dragon",50);
        AbstractCard card2 = manager.createCard("1234","Knight",50);
        AbstractCard card3 = manager.createCard("12345","Wizard",50);
        float damage1 = card1.calculateDamage(card2);
        float damage2 = card1.calculateDamage(card3);
        float damage3 = card2.calculateDamage(card1);
        float damage4 = card2.calculateDamage(card3);
        float damage5 = card3.calculateDamage(card1);
        float damage6 = card3.calculateDamage(card2);

        assertEquals(50,damage1);
        assertEquals(50,damage2);
        assertEquals(50,damage3);
        assertEquals(50,damage4);
        assertEquals(50,damage5);
        assertEquals(50,damage6);
    }
 */
}
