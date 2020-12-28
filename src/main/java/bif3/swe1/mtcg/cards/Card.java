package bif3.swe1.mtcg.cards;

import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.MonsterType;
import lombok.Getter;

public abstract class Card {

    @Getter
    private final String id;

    @Getter
    private final String name;

    @Getter
    private final float damage;

    @Getter
    private final ElementType element;

    public Card(String id, String name, float damage, ElementType element) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
    }

    public MonsterType getMonsterType(){
        return null;
    }

    public float getWeakness(){
        return 0;
    }
}