package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class User {

    @Getter
    private final String username;
    private final String pwd;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String bio;
    @Setter
    @Getter
    private String image;

    private int coins;
    @Getter
    private Deck deck;
    @Getter
    private Stack stack;
    @Getter
    private int count_of_games;
    @Getter
    private int count_of_wins;

    public User(String username, String pwd){
        this.username = username;
        this.pwd = pwd;
        this.coins = 20;
        this.count_of_games = 0;
        this.count_of_wins = 0;
        this.stack = new Stack();
        this.name = "";
        this.bio = "";
        this.image = "";
    }

    public boolean BuyPackage(CardPackage pck){
        if (coins < 5){
            return false;
        }
        stack.AddPackage(pck);
        coins -= 5;
        return true;
    }

    public AbstractCard Draw(){
        if (deck == null ||deck.getCards().isEmpty()){
            return null;
        } else {
            return deck.getRandomCard();
        }
    }

    public boolean createDeck(List<String> id){
        if (id.size() != 4){
            return false;
        }
        List<AbstractCard> newDeck = new ArrayList<>();
        for (String value : id){
            AbstractCard card = stack.getCard(value);
            if (card != null){
                newDeck.add(card);
            } else {
                for (AbstractCard tmp : newDeck){
                    stack.AddCard(tmp);
                }
                return false;
            }
        }
        this.deck = new Deck(newDeck);
        return true;
    }

    public boolean CreateRandomDeck(){
        List<AbstractCard> newDeck = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            AbstractCard card = stack.getRandomCard();
            if (card != null) {
                newDeck.add(card);
            } else {
                for (AbstractCard tmp : newDeck){
                    stack.AddCard(tmp);
                }
                return false;
            }
        }
        this.deck = new Deck(newDeck);
        return true;
    }

    public void won(){
        count_of_games++;
        count_of_wins++;
    }
    public void lose(){
        count_of_games++;
    }

    public void RemoveDeck(){
        if (deck != null){
            while(!deck.getCards().isEmpty()){
                AbstractCard card = deck.getRandomCard();
                stack.AddCard(card);
                deck.RemoveCard(card);
            }
            deck = null;
        }
    }

    public boolean checkPwd(String pwd){
        return this.pwd.equals(pwd);
    }
}
