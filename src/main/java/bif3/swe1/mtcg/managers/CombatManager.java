package bif3.swe1.mtcg.managers;

import bif3.swe1.database.DatabaseService;
import bif3.swe1.mtcg.User;
import bif3.swe1.mtcg.Card;
import bif3.swe1.mtcg.collections.Deck;
import bif3.swe1.mtcg.types.CardType;
import bif3.swe1.mtcg.types.ElementType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CombatManager {

    private static CombatManager single_instance = null;

    private User user1;
    private User user2;
    private String response;
    private boolean working = false;
    Object LOCK = new Object();

    public static CombatManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new CombatManager();
        }
        return single_instance;
    }

    public String addUser(User user){
        if (user1 == null){
            user1 = user;
            response = null;
            working = true;
            synchronized (LOCK) {
                while (working) {
                    try { LOCK.wait(); }
                    catch (InterruptedException e) {
                        // treat interrupt as exit request
                        break;
                    }
                }
            }
            return response;
        } else if (user2 == null){
            user2 = user;
            battle();
            working = false;
            synchronized (LOCK) {
                LOCK.notifyAll();
            }
            user1 = null;
            user2 = null;
            return response;
        }
        return null;
    }

    public void battle(){
        CardManager manager = new CardManager();
        Deck deck1 = manager.getDeckUser(user1);
        Deck deck2 = manager.getDeckUser(user2);
        int turns = 0;
        if ( deck1 == null || deck2 == null) {
            clear();
            return;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode round = mapper.createObjectNode();
            while (!deck1.isEmpty() && !deck2.isEmpty() && ++turns <= 100){
                Card card1 = deck1.getRandomCard();
                Card card2 = deck2.getRandomCard();
                float damage1 = calculateDamage(card1,card2);
                float damage2 = calculateDamage(card2,card1);

                round.put("Round",turns);
                round.put("User1",user1.getName());
                round.put("Deck1",deck1.getSize());
                round.put("Card1",card1.getName());
                round.put("Damage1",damage1);
                round.put("User2",user2.getName());
                round.put("Deck2",deck2.getSize());
                round.put("Card2",card2.getName());
                round.put("Damage2",damage2);

                if (damage1 > damage2){
                    deck2.removeCard(card2);
                    deck1.addCard(card2);
                    round.put("Won",user1.getName());
                } else if (damage1 < damage2){
                    deck2.addCard(card1);
                    deck1.removeCard(card1);
                    round.put("Won",user2.getName());
                } else {
                    round.put("Won","Draw");
                }
                arrayNode.add(round);
            }
            response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            if (deck1.isEmpty()){
                user1.battleWon();
                user2.battleLost();
                return;
            } else if (deck2.isEmpty()){
                user1.battleLost();
                user2.battleWon();
                return;
            }
            user1.battleDraw();
            user2.battleDraw();
            return;
        }  catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response = null;
    }

    public void clear(){
        user1 = null;
        user2 = null;
        response = null;
    }

    public float calculateDamage(Card card1, Card card2){
        if (card1.getCardType() != CardType.Spell){
            if (card2.getCardType() != CardType.Spell) {
                switch (card1.getCardType()){
                    case Dragon:
                        if (card2.getCardType() == CardType.FireElf){
                            return 0;
                        }
                        break;
                    case Goblin:
                        if (card2.getCardType() == CardType.Dragon){
                            return 0;
                        }
                        break;
                    case Ork:
                        if (card2.getCardType() == CardType.Wizard){
                            return 0;
                        }
                        break;
                    default:
                        break;
                }
                return card1.getDamage();
            } else {
                if (card1.getCardType() == CardType.Knight && card2.getElementType() == ElementType.water){
                    return -1;
                }
            }
        }
        switch (card1.getElementType()){
            case water:
                if (card2.getElementType() == ElementType.fire){
                    return card1.getDamage() * 2;
                }
                break;
            case fire:
                if (card2.getElementType() == ElementType.normal){
                    return card1.getDamage() * 2;
                }
                break;
            default:
                if (card2.getElementType() == ElementType.water){
                    return card1.getDamage() * 2;
                }
                break;
        }
        return card1.getDamage();
    }

    public String getScoreboard(){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT name, wins, games, elo FROM users WHERE name IS NOT NULL ORDER BY elo DESC;");
            ResultSet rs = ps.executeQuery();
            ps.close();
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            while (rs.next()){
                ObjectNode entry = mapper.createObjectNode();
                entry.put("Name",rs.getString(1));
                entry.put("Wins",rs.getString(2));
                entry.put("Games",rs.getString(3));
                entry.put("Elo",rs.getString(4));
                arrayNode.add(entry);
            }
            rs.close();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
