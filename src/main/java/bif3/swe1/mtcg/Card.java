package bif3.swe1.mtcg;

import bif3.swe1.mtcg.types.ElementType;
import bif3.swe1.mtcg.types.CardType;

import lombok.Getter;


public class Card implements CardInterface {

    @Getter
    private String id;

    @Getter
    private String name;

    @Getter
    private float damage;

    @Getter
    private CardType cardType;

    @Getter
    private ElementType elementType;

    public Card() {

    }

    public Card(String id, String name, float damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public Card(String id, String name, float damage,CardType type, ElementType element) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        cardType = type;
        elementType = element;
    }
}