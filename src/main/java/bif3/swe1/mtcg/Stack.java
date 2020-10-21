package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Stack {

    @Getter
    private List<AbstractCard> cards = new ArrayList<>();

    public Stack (List<AbstractCard> stack){
        if (stack != null){
            this.cards.addAll(stack);
        }
    }

    public void RemoveCard(AbstractCard card){
        cards.remove(card);
    }

    public void AddCard(AbstractCard card){
        cards.add(card);
    }

    public AbstractCard getRandomCard(){
        if (cards.size() > 0){
            AbstractCard card = cards.get((int)(Math.random() * cards.size()));
            RemoveCard(card);
            return card;
        }
        return null;
    }

    public void AddPackage(CardPackage pck){
        if (pck != null && pck.getCards() != null){
            for (int i = 0; i < pck.getCards().size() && i < 5; i++){
                cards.add(pck.getCards().get(i));
            }
        }
    }
}
