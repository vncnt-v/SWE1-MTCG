package bif3.swe1.mtcg.manager;

import bif3.swe1.database.DatabaseService;
import bif3.swe1.mtcg.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeManager {

    private static TradeManager single_instance = null;

    public static TradeManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new TradeManager();
        }
        return single_instance;
    }

    public String showMarketplace(){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM marketplace;");
            ResultSet rs = ps.executeQuery();
            ps.close();
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            while (rs.next()){
                ObjectNode deal = mapper.createObjectNode();
                deal.put("TradeID",rs.getString(1));
                deal.put("CardID",rs.getString(2));
                deal.put("MinimumDamage",rs.getString(3));
                deal.put("Type",rs.getString(4));
                arrayNode.add(deal);
            }
            rs.close();
            conn.close();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean removeTrade(User user, String id){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE marketplace WHERE cardid = ? AND owner = ?;");
            ps.setString(1, id);
            ps.setString(2, user.getUsername());
            int affectedRows = ps.executeUpdate();
            ps.close();
            if (affectedRows != 1) {
                conn.close();
                return false;
            }
            ps = conn.prepareStatement("UPDATE cards SET collection = 'stack' WHERE cardid = ?;");
            ps.setString(1, id);
            affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            return affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean card2market(User user, String tradeID, String cardID, float minimumDamage, String type){
        try {
            // Check if user owns card
            // ToDo maktplace check in cards? check if card is on marketplace
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(cardid) FROM cards WHERE owner = ? AND cardid = ? AND collection LIKE 'stack';");
            ps.setString(1, user.getUsername());
            ps.setString(2, cardID);
            int affectedRows = ps.executeUpdate();
            ps.close();
            if (affectedRows != 1) {
                conn.close();
                return false;
            }
            // Insert Marketplace
            ps = conn.prepareStatement("INSERT INTO marketplace(tradeid, cardid, mindamage, type) VALUES(?,?,?,?);");
            ps.setString(1, tradeID);
            ps.setString(2, cardID);
            ps.setFloat(3, minimumDamage);
            ps.setString(4, type);
            affectedRows = ps.executeUpdate();
            ps.close();
            if (affectedRows != 1) {
                return false;
            }
            ps = conn.prepareStatement("UPDATE cards SET collection = 'marketplace' WHERE cardid = ?;");
            ps.setString(1, cardID);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tradeCards(User user, String tradeID, String cardID){
        if (user == null){
            return false;
        }
        // ToDo
        // SQL get Card with ID from CardManager
        // check if Card Type with CardManager
        // check if damage is big enough
        // Change cards owner 2x
        // Delete marketplace entry




        /*
        for (TradingDeal trade:trades){
            if (trade.getTradeID().equals(tradeID)){
                if (trade.getUsername().equals(user.getUsername())){
                    return false;
                }
                Card card = user.getStackCard(cardID);
                if (card == null){
                    return false;
                }
                if (card.getDamage() < trade.getMinimumDamage()){
                    user.addCard(card);
                    return false;
                }
                // check Type

                if (trade.getMonster() == null && card instanceof Spell){
                    if (trade.getMinimumWeakness() > card.getWeakness()) {
                        UserManager manager = UserManager.getInstance();
                        if(manager.addCard2User(card, trade.getUsername())){
                            user.addCard(trade.getCard());
                            return true;
                        }
                        user.addCard(card);
                        return false;
                    }
                    user.addCard(card);
                    return false;
                }
                if (trade.getMonster() == card.getMonsterType()){
                    UserManager manager = UserManager.getInstance();
                    if(manager.addCard2User(card, trade.getUsername())){
                        user.addCard(trade.getCard());
                        return true;
                    }
                    user.addCard(card);
                    return false;
                }
                user.addCard(card);
                return false;
            }
        }
        */
        return false;
    }
}
