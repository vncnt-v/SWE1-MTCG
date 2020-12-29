package bif3.swe1.mtcg;

import bif3.swe1.database.DatabaseService;
import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.types.CardType;
import bif3.swe1.mtcg.cards.types.ElementType;

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
                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(cardID) FROM cards WHERE cardID = ?;");
                ps.setString(1, card.getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(1) != 1){
                        ps.close();
                        rs.close();
                        return false;
                    }
                }
                ps = conn.prepareStatement("SELECT COUNT(cardID) FROM dependencies WHERE cardID = ?;");
                ps.setString(1, card.getId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(1) != 0){
                        ps.close();
                        rs.close();
                        return false;
                    }
                }
                ps.close();
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
            if (affectedRows == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean acquirePackage2User(User user){
        /*if (pck.size() > 0){
            int random = (int)(Math.random() * pck.size());
            if (user.acquirePackage(pck.get(random))){
                pck.remove(random);
                return true;
            }
        }*/
        return false;
    }

    public ElementType createElementType (String element){
        if (element.toLowerCase().contains("water")){
            return ElementType.water;
        } else if (element.toLowerCase().contains("fire")){
            return ElementType.fire;
        } else {
            return ElementType.normal;
        }
    }

    public CardType createCardType (String name){
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

    public boolean createCard(String id, String name, float damage) {
        if (!id.isEmpty() && !name.isEmpty()) {
            ElementType element = createElementType(name);
            CardType cardType = createCardType(name);
            if ( element != null && cardType != null){
                try {
                    Connection conn = DatabaseService.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO cards(cardID, name, damage,element,type) VALUES(?,?,?,?,?);");
                    ps.setString(1, id);
                    ps.setString(2, name);
                    ps.setFloat(3, damage);
                    ps.setString(4, element.toString());
                    ps.setString(5, cardType.toString());
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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cards WHERE cardid = ?");
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
