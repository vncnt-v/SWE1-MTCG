package bif3.swe1.mtcg.cards.Monsters;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;

public class Goblin extends AbstractCard {

    public Goblin(String name, int damage, ElementType element) {
        super(name,damage,element);
    }

    @Override
    public int calculateDamage(AbstractCard card){
        int attackDamage = damage;
        if (card instanceof Dragon){
            attackDamage = 0;
        }
        return attackDamage;
    }
}
