package bif3.swe1.mtcg.cards;

import lombok.Getter;

public abstract class AbstractCard {
    @Getter
    private final String name;

    @Getter
    protected final int damage;

    @Getter
    protected final ElementType element;


    public AbstractCard(String name, int damage, ElementType element) {
        this.name = name;
        this.damage = damage;
        this.element = element;
    }

    public int calculateDamage(AbstractCard card){
        return damage;
    }
}