package bif3.swe1.mtcg.cards;

import lombok.Getter;

public abstract class AbstractCard {

    @Getter
    private final String id;

    @Getter
    private final String name;

    @Getter
    protected final float damage;

    @Getter
    protected final ElementType element;


    public AbstractCard(String id, String name, float damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = getElementBelongTo(name);
    }

    public ElementType getElementBelongTo(String name){
        if (name.contains("Water") || name.contains("water")){
            return ElementType.water;
        } else if (name.contains("Fire") || name.contains("fire")){
            return ElementType.fire;
        }
        return ElementType.normal;
    }

    public float calculateDamage(AbstractCard card){
        return damage;
    }
}