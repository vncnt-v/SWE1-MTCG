package bif3.swe1.mtcg.cards;

import bif3.swe1.mtcg.cards.Monsters.Kraken;

public class Spell extends AbstractCard {

    public Spell(String id,String name, float damage) {
        super(id,name,damage);
    }

    @Override
    public float calculateDamage(AbstractCard card){
        float attackDamage = damage;
        if (card instanceof Kraken){
            attackDamage = 0;
        } else if (element == ElementType.water && card.getElement() == ElementType.fire){
            attackDamage *= 2;
        } else if (element == ElementType.fire && card.getElement() == ElementType.normal){
            attackDamage *= 2;
        } else if (element == ElementType.normal && card.getElement() == ElementType.water){
            attackDamage *= 2;
        } else if (element == ElementType.fire && card.getElement() == ElementType.water){
            attackDamage /= 2;
        } else if (element == ElementType.normal && card.getElement() == ElementType.fire){
            attackDamage /= 2;
        } else if (element == ElementType.water && card.getElement() == ElementType.normal){
            attackDamage /= 2;
        }
        return attackDamage;
    }
}
