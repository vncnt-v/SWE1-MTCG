package bif3.swe1.mtcg.cards.Monsters;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.ElementType;

public class Kraken extends AbstractCard {

    public Kraken(String id, String name, float damage) {
        super(id,name,damage);
    }

    @Override
    public float calculateDamage(AbstractCard card){
        return damage;
    }
}
