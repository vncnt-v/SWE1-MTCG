package bif3.swe1.mtcg.cards.collections;

import bif3.swe1.mtcg.cards.Card;
import lombok.Getter;

public class CardPackage {
    @Getter
    private Card cards[] = new Card[5];

    public CardPackage(Card cards[]){
        this.cards = cards;
    }
}
