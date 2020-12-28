package bif3.swe1.mtcg.cards.collections;

import bif3.swe1.mtcg.cards.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    @Getter
    private List<Card> cards = new ArrayList<>();

    public Deck (List<Card> deck) {
        if (deck != null) {
            for (int i = 0; deck.size() > i && i < 4; i++) {
                this.cards.add(deck.get(i));
            }
        }
    }

    public void removeCard (Card card){
        if (cards != null && cards.contains(card)){
            cards.remove(card);
        }
    }

    public Card getRandomCard(){
        if (cards != null && cards.size() > 0){
            return cards.get((int)(Math.random() * cards.size()));
        }
        return null;
    }
}
