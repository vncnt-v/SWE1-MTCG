package bif3.swe1.mtcg.cards.Monsters;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;

public class Wizard extends AbstractCard {

    public Wizard(String name, int damage, ElementType element) {
        super(name,damage,element);
    }

    @Override
    public int calculateDamage(AbstractCard card){
        return damage;
    }
}
