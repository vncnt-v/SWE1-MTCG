package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.Monster;
import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.MonsterType;

public class Combat {

    private final User user1;
    private final User user2;

    public Combat(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    public void Fight(){
        int turns = 0;
        if (user1.getDeckSize() <= 0 || user2.getDeckSize() <= 0) {
            //System.out.println("No cards in one or both decks. Fight canceled");
            user1.removeDeck();
            user2.removeDeck();
            return;
        }
        while (user1.getDeckSize() > 0 && user2.getDeckSize() > 0 && ++turns <= 100){
            Card card1 = user1.drawDeckRandom();
            Card card2 = user2.drawDeckRandom();
            float damage1 = calculateDamage(card1,card2);
            float damage2 = calculateDamage(card2,card1);
            if (damage1 > damage2){
                user2.removeDeckCard(card2);
                user2.addCard(card2);
                user2.addCard(card2);
            } else if (damage1 < damage2){
                user1.removeDeckCard(card1);
                user1.addCard(card1);
            }
        }
        if (user1.getDeckSize() <= 0){
            //System.out.println("Player 2 wins");
            user1.loseGame();
            user2.winGame();
            user1.removeDeck();
            user2.removeDeck();
        } else if (user2.getDeckSize() <= 0){
            //System.out.println("Player 1 wins");
            user1.winGame();
            user2.loseGame();
            user1.removeDeck();
            user2.removeDeck();
        } else {
            //System.out.println("It's a draw");
            user1.loseGame();
            user2.loseGame();
            user1.removeDeck();
            user2.removeDeck();
        }
    }

    public float calculateDamage(Card card1, Card card2){
        if (card1 instanceof Monster){
            if (card2 instanceof Monster) {
                switch (card1.getMonsterType()){
                    case Dragon:
                        if (card2.getMonsterType() == MonsterType.FireElf){
                            return 0;
                        }
                        break;
                    case Goblin:
                        if (card2.getMonsterType() == MonsterType.Dragon){
                            return 0;
                        }
                        break;
                    case Ork:
                        if (card2.getMonsterType() == MonsterType.Wizard){
                            return 0;
                        }
                        break;
                    default:
                        break;
                }
                return card1.getDamage();
            } else {
                if (card1.getMonsterType() == MonsterType.Knight && card2.getElement() == ElementType.water){
                    return -1;
                }
            }
        }
        switch (card1.getElement()){
            case water:
                if (card2.getElement() == ElementType.fire){
                    return card1.getDamage() * 2;
                }
                break;
            case fire:
                if (card2.getElement() == ElementType.normal){
                    return card1.getDamage() * 2;
                }
                break;
            default:
                if (card2.getElement() == ElementType.water){
                    return card1.getDamage() * 2;
                }
                break;
        }
        return card1.getDamage();
    }
}
