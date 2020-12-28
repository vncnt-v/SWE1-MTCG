package bif3.swe1.mtcg.cards;

import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.MonsterType;
import lombok.Getter;

public class Spell extends Card{

    @Getter
    private final float weakness;

    public Spell(String id, String name, float damage, float weakness, ElementType element) {
        super(id,name,damage,element);
        this.weakness = weakness;
    }

    @Override
    public float getWeakness() {
        return weakness;
    }
}
