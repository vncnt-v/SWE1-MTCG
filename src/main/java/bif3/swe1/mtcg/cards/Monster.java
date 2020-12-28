package bif3.swe1.mtcg.cards;

import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.MonsterType;

public class Monster extends Card{

    private final MonsterType monster;

    public Monster(String id, String name, float damage, ElementType element, MonsterType monster) {
        super(id,name,damage,element);
        this.monster = monster;
    }

    @Override
    public MonsterType getMonsterType() {
        return monster;
    }
}
