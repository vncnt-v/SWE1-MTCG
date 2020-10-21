package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;

public class Combat {

    private final User user1;
    private final User user2;

    public Combat(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    public void Fight(){
        int turns = 0;
        if (user1.getDeck().getCards() == null || user2.getDeck().getCards() == null || user1.getDeck().getCards().isEmpty() || user2.getDeck().getCards().isEmpty()) {
            //System.out.println("No cards in one or both decks. Fight canceled");
            user1.RemoveDeck();
            user2.RemoveDeck();
            return;
        }
        while (!user1.getDeck().getCards().isEmpty() && !user2.getDeck().getCards().isEmpty() && ++turns <= 100){
            AbstractCard card1 = user1.Draw();
            AbstractCard card2 = user2.Draw();
            int damage1 = card1.calculateDamage(card2);
            int damage2 = card2.calculateDamage(card1);
            //System.out.println("Round: " + turns);
            //System.out.println(card1.getName() + " " + damage1);
            //System.out.println(card2.getName() + " " + damage2);
            if (damage1 > damage2){
                user2.getDeck().RemoveCard(card2);
                user2.getStack().AddCard(card2);
            } else if (damage1 < damage2){
                user1.getDeck().RemoveCard(card1);
                user1.getStack().AddCard(card1);
            }
        }
        if (user1.getDeck().getCards().isEmpty()){
            //System.out.println("Player 2 wins");
            user1.lose();
            user2.won();
            user1.RemoveDeck();
            user2.RemoveDeck();
        } else if (user2.getDeck().getCards().isEmpty()){
            //System.out.println("Player 1 wins");
            user1.won();
            user2.lose();
            user1.RemoveDeck();
            user2.RemoveDeck();
        } else {
            //System.out.println("It's a draw");
            user1.lose();
            user2.lose();
            user1.RemoveDeck();
            user2.RemoveDeck();
        }
    }
}
