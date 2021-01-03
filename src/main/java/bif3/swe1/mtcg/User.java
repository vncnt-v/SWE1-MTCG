package bif3.swe1.mtcg;
import bif3.swe1.database.DatabaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User {

    @Getter
    private final String username;
    private final String name;
    private final String bio;
    private final String image;
    private final Integer coins;
    private final int games;
    private final int wins;


    public User(String username, String name, String bio, String image, int coins, int games, int wins){
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.coins = coins;
        this.games = games;
        this.wins = wins;
    }

    public String getInfo(){
        try {
            Map<String,String> map = new HashMap<>();
            map.put("Name:",name);
            map.put("Bio:",bio);
            map.put("Image:",image);
            map.put("Coins:",coins.toString());
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStats(){
        try {
            Map<String,Integer> map = new HashMap<>();
            map.put("Wins:",wins);
            map.put("Games:",games);
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean buyPackage(){
        try {
            if (coins < 5){
                return false;
            }
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET coins = ? WHERE username = ?;");
            ps.setInt(1,coins-5);
            ps.setString(2,username);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createDeck(List<String> id){
        if (id.size() != 4){
            return false;
        }
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            // Check if user owns Cards
            List<String> checkTwice = new LinkedList<>();
            for (String cardID: id){
                if (checkTwice.contains(cardID)){
                    conn.close();
                    return false;
                }
                checkTwice.add(cardID);
                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(cardid) FROM cards WHERE cardid = ? AND owner = ? AND (collection IS NULL OR collection NOT LIKE 'marktplace');");
                ps.setString(1,cardID);
                ps.setString(2,username);
                ResultSet rs = ps.executeQuery();
                System.out.println(username);
                System.out.println(cardID);
                if (!rs.next() || rs.getInt(1) != 1) {
                    ps.close();
                    rs.close();
                    conn.close();
                    return false;
                }
            }
            // Set all Cards to Stack
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET collection = 'stack' WHERE owner = ?;");
            ps.setString(1,username);
            ps.executeUpdate();
            // Change Cards to Deck
            for (String cardID: id){
                ps = conn.prepareStatement("UPDATE cards SET collection = 'deck' WHERE owner = ? AND cardID = ?;");
                ps.setString(1,username);
                ps.setString(2,cardID);
                ps.executeUpdate();
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
