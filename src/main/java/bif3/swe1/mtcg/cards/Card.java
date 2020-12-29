package bif3.swe1.mtcg.cards;

import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.CardType;

import lombok.Getter;


public class Card implements CardInterface{

    @Getter
    private String id;

    @Getter
    private String name;

    @Getter
    private float damage;

    public Card() {

    }

    public Card(String id, String name, float damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }
}