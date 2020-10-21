package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String username;
    private final String pwd;

    private int coins;
    @Getter
    private Deck deck;
    @Getter
    private Stack stack;
    @Getter
    private int count_of_games;
    @Getter
    private int count_of_wins;

    public User(String username, String pwd, Stack stack, int count_of_games, int count_of_wins){
        this.username = username;
        this.pwd = pwd;
        this.stack = stack;
        this.coins = 20;
        this.count_of_games = count_of_games;
        this.count_of_wins = count_of_wins;
    }

    public boolean AddPackage(CardPackage pck) {
        if (coins < 5 || pck == null){
            // ERR
            return false;
        } else {
            stack.AddPackage(pck);
            coins -= 5;
            return true;
        }
    }

    public AbstractCard Draw(){
        if (deck.getCards().isEmpty()){
            return null;
        } else {
            return deck.getRandomCard();
        }
    }

    public void CreateRandomDeck(){
        List<AbstractCard> newDeck = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            AbstractCard card = stack.getRandomCard();
            if (card != null) {
                newDeck.add(card);
            }
        }
        this.deck = new Deck(newDeck);
    }

    public void won(){
        count_of_games++;
        count_of_wins++;
    }
    public void lose(){
        count_of_games++;
    }
    public void RemoveDeck(){
        while(!deck.getCards().isEmpty()){
            AbstractCard card = deck.getRandomCard();
            stack.AddCard(card);
            deck.RemoveCard(card);
        }
        deck = null;
    }
}
