package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    @Getter
    private List<AbstractCard> cards = new ArrayList<>();

    public Deck (List<AbstractCard> deck) {
        if (deck != null) {
            for (int i = 0; deck.size() > i && i < 4; i++) {
                this.cards.add(deck.get(i));
            }
        }
    }

    public void RemoveCard (AbstractCard card){
        if (cards != null && cards.size() > 0){
            cards.remove(card);
        }
    }

    public AbstractCard getRandomCard(){
        if (cards != null && cards.size() > 0){
            return cards.get((int)(Math.random() * cards.size()));
        }
        return null;
    }
}
