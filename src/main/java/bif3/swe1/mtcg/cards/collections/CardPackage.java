package bif3.swe1.mtcg.cards.collections;

import bif3.swe1.mtcg.cards.Card;
import lombok.Getter;

import java.util.List;

public class CardPackage {
    @Getter
    private List<Card> cards;

    public CardPackage(List<Card> cards){
        this.cards = cards;
    }
}
