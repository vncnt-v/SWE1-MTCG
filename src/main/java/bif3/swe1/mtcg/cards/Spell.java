package bif3.swe1.mtcg.cards;

import bif3.swe1.mtcg.cards.Monsters.Kraken;

public class Spell extends AbstractCard {

    public Spell(String name, int damage, ElementType element) {
        super(name,damage,element);
    }

    @Override
    public int calculateDamage(AbstractCard card){
        int attackDamage = damage;
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
