package bif3.swe1.mtcg.cards.Monsters;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;

public class Ork extends AbstractCard {

    public Ork(String id, String name, float damage) {
        super(id,name,damage);
    }

    @Override
    public float calculateDamage(AbstractCard card){
        float attackDamage = damage;
        if (card instanceof Wizard){
            attackDamage = 0;
        }
        return attackDamage;
    }
}
