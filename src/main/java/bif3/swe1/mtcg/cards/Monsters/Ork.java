package bif3.swe1.mtcg.cards.Monsters;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;

public class Ork extends AbstractCard {

    public Ork(String name, int damage, ElementType element) {
        super(name,damage,element);
    }

    @Override
    public int calculateDamage(AbstractCard card){
        int attackDamage = damage;
        if (card instanceof Wizard){
            attackDamage = 0;
        }
        return attackDamage;
    }
}
