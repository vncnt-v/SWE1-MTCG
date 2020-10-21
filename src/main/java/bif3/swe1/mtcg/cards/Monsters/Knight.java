package bif3.swe1.mtcg.cards.Monsters;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;
import bif3.swe1.mtcg.cards.Spell;

public class Knight extends AbstractCard {

    public Knight(String name, int damage, ElementType element) {
        super(name,damage,element);
    }

    @Override
    public int calculateDamage(AbstractCard card){
        int attackDamage = damage;
        if (card instanceof Spell && card.getElement() == ElementType.water){
            attackDamage = -1;
        }
        return attackDamage;
    }
}
