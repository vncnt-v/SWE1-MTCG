package bif3.swe1.mtcg.cards.collections;

import bif3.swe1.mtcg.cards.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Stack {

    @Getter
    private List<Card> cards;

    public Stack (){
        cards = new ArrayList<>();
    }

    public Card getCard(String id){
        for (Card card : cards){
            if (card.getId().equals(id)){
                cards.remove(card);
                return card;
            }
        }
        return null;
    }

    public void removeCard(Card card){
        if (cards.contains(card)){
            cards.remove(card);
        }
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card getRandomCard(){
        if (cards.size() > 0){
            Card card = cards.get((int)(Math.random() * cards.size()));
            cards.remove(card);
            return card;
        }
        return null;
    }

    public void addPackage(CardPackage pck){
        if (pck == null){
            return;
        }
        Card newCards[] = pck.getCards();
        for (int i = 0; i < newCards.length; i++){
            if (newCards[i] != null){
                cards.add(newCards[i]);
            }
        }
    }
}
