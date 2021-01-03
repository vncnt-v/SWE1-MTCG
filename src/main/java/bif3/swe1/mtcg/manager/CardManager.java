package bif3.swe1.mtcg.manager;

import bif3.swe1.database.DatabaseService;
import bif3.swe1.mtcg.User;
import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.types.CardType;
import bif3.swe1.mtcg.cards.types.ElementType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CardManager {

    private static CardManager single_instance = null;

    public static CardManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new CardManager();
        }
        return single_instance;
    }

    public boolean createPackage(List<Card> cards){
        if (cards.size() != 5){
            return false;
        }
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            for (Card card: cards){
                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(cardid) FROM cards WHERE cardid = ? AND collection IS NULL;");
                ps.setString(1, card.getId());
                ResultSet rs = ps.executeQuery();
                ps.close();
                if (!rs.next() || rs.getInt(1) != 1) {
                    rs.close();
                    conn.close();
                    return false;
                }
                rs.close();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO packages(cardID_1, cardID_2, cardID_3, cardID_4, cardID_5) VALUES(?,?,?,?,?);");
            ps.setString(1, cards.get(0).getId());
            ps.setString(2, cards.get(1).getId());
            ps.setString(3, cards.get(2).getId());
            ps.setString(4, cards.get(3).getId());
            ps.setString(5, cards.get(4).getId());
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String showUserCards (User user){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT cardID, name, damage FROM cards WHERE owner = ?;");
            ps.setString(1,user.getUsername());
            String json = result2Json(ps.executeQuery());
            ps.close();
            conn.close();
            return json;
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String showUserDeck (User user){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT cardID, name, damage FROM cards WHERE owner = ? AND collection = 'deck';");
            ps.setString(1,user.getUsername());
            String json = result2Json(ps.executeQuery());
            ps.close();
            conn.close();
            return json;
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String result2Json(ResultSet rs) throws SQLException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        while (rs.next()){
            ObjectNode card = mapper.createObjectNode();
            card.put("ID",rs.getString(1));
            card.put("Name",rs.getString(2));
            card.put("Damage",rs.getString(3));
            arrayNode.add(card);
        }
        rs.close();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
    }

    public boolean acquirePackage2User(User user){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            // Check Existing Package
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM packages;");
            ResultSet rs = ps.executeQuery();
            ps.close();
            if (!rs.next()) {
                rs.close();
                conn.close();
                return false;
            }
            int packageID = rs.getInt(1);
            // Decrease Coins User
            if (!user.buyPackage()){
                return false;
            }
            // Update Cards Owner and Collection
            PreparedStatement ps_Card = conn.prepareStatement("UPDATE cards SET owner = ?, collection = 'stack' WHERE cardID = ?;");
            for (int i = 2; i < 7; i++){
                ps_Card.setString(1,user.getUsername());
                ps_Card.setString(2,rs.getString(i));
                ps_Card.executeUpdate();
            }
            rs.close();
            ps_Card.close();
            // Delete Package
            PreparedStatement ps_Package = conn.prepareStatement("DELETE FROM packages WHERE packageID = ?;");
            ps_Package.setInt(1,packageID);
            ps_Package.executeUpdate();
            ps_Package.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ElementType createElementType (String element){
        if (element.toLowerCase().contains("water")){
            return ElementType.water;
        } else if (element.toLowerCase().contains("fire")){
            return ElementType.fire;
        } else {
            return ElementType.normal;
        }
    }

    private CardType createCardType (String name){
        if (name.toLowerCase().contains("spell")){
            return CardType.Spell;
        } else if (name.toLowerCase().contains("dragon")){
            return CardType.Dragon;
        } else if (name.toLowerCase().contains("fireelf")){
            return CardType.FireElf;
        } else if (name.toLowerCase().contains("goblin")){
            return CardType.Goblin;
        } else if (name.toLowerCase().contains("knight")){
            return CardType.Knight;
        } else if (name.toLowerCase().contains("kraken")){
            return CardType.Kraken;
        } else if (name.toLowerCase().contains("ork")){
            return CardType.Ork;
        } else if (name.toLowerCase().contains("wizard")){
            return CardType.Wizard;
        }
        return null;
    }

    public boolean registerCard(String id, String name, float damage) {
        if (!id.isEmpty() && !name.isEmpty()) {
            ElementType element = createElementType(name);
            CardType cardType = createCardType(name);
            if ( element != null && cardType != null){
                try {
                    Connection conn = DatabaseService.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(cardid, name, damage) VALUES(?,?,?);");
                    ps.setString(1, id);
                    ps.setString(2, name);
                    ps.setFloat(3, damage);
                    int affectedRows = ps.executeUpdate();
                    ps.close();
                    conn.close();
                    if (affectedRows == 0) {
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void deleteCard(String id) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cards WHERE cardid = ? AND collection IS NULL");
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
