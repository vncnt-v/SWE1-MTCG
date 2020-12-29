package bif3.swe1.mtcg;
import com.fasterxml.jackson.databind.ObjectMapper;
import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.collections.CardPackage;
import bif3.swe1.mtcg.cards.collections.Deck;
import bif3.swe1.mtcg.cards.collections.Stack;
import lombok.Setter;
import lombok.Getter;

import java.io.IOException;
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
    private Deck deck;
    private Stack stack;
    @Getter
    private int count_of_games;
    @Getter
    private int count_of_wins;

    //private final String token;

    public User(String username, String pwd, int coins, String name, String bio, String image, int count_of_games, int count_of_wins){
        this.username = username;
        this.pwd = pwd;
        this.coins = coins;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.count_of_games = count_of_games;
        this.count_of_wins = count_of_wins;
        this.stack = new Stack();
        //this.token = token;
    }

    public int getDeckSize(){
        if (deck == null || deck.getCards() == null){
            return 0;
        }
        return deck.getCards().size();
    }

    public boolean acquirePackage(CardPackage pck){
        if (coins < 5){
            return false;
        }
        stack.addPackage(pck);
        coins -= 5;
        return true;
    }

    public String getStackCards(){
        ObjectMapper mapper = new ObjectMapper();
        String response = null;
        try {
            // ToDo
            // Java objects to JSON string - compact-print
            response = mapper.writeValueAsString(stack);
            System.out.println(response);
            // Java objects to JSON string - pretty-print
            response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stack);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getDeckCards(){
        ObjectMapper mapper = new ObjectMapper();
        String response = null;
        try {
            // ToDo
            // Java objects to JSON string - compact-print
            response = mapper.writeValueAsString(deck);
            System.out.println(response);
            // Java objects to JSON string - pretty-print
            response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(deck);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Card drawDeckRandom(){
        if (deck == null || deck.getCards().isEmpty()){
            return null;
        } else {
            return deck.getRandomCard();
        }
    }

    public Card getStackCard(String id){
        return stack.getCard(id);
    }

    public boolean createDeck(List<String> id){
        if (id.size() != 4){
            return false;
        }
        List<Card> newDeck = new ArrayList<>();
        for (String value : id){
            Card card = stack.getCard(value);
            if (card != null){
                newDeck.add(card);
            } else {
                for (Card tmp : newDeck){
                    stack.addCard(tmp);
                }
                return false;
            }
        }
        this.deck = new Deck(newDeck);
        return true;
    }

    public boolean createRandomDeck(){
        List<Card> newDeck = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            Card card = stack.getRandomCard();
            if (card != null) {
                newDeck.add(card);
            } else {
                for (Card tmp : newDeck){
                    stack.addCard(tmp);
                }
                return false;
            }
        }
        this.deck = new Deck(newDeck);
        return true;
    }

    public void winGame(){
        count_of_games++;
        count_of_wins++;
    }

    public void loseGame(){
        count_of_games++;
    }

    public void addCard(Card card){
        stack.addCard(card);
    }

    public void removeStackCard(Card card){
        stack.removeCard(card);
    }

    public void removeDeckCard(Card card){
        if (deck != null){
            deck.removeCard(card);
        }
    }

    public String getStats(){
        // ToDo JSON
        return "";
    }

    public void removeDeck(){
        if (deck != null){
            while(!deck.getCards().isEmpty()){
                Card card = deck.getRandomCard();
                stack.addCard(card);
                deck.removeCard(card);
            }
            deck = null;
        }
    }

    public boolean checkPwd(String pwd){
        return this.pwd.equals(pwd);
    }
}
