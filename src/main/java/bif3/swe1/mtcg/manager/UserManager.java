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

public class UserManager {

    private static UserManager single_instance = null;

    public static UserManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new UserManager();
        }
        return single_instance;
    }

    public User authorizeUser(String token){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT username, name, bio, image, coins, wins, games FROM users WHERE token = ? AND logged = TRUE;");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            ps.close();
            if (!rs.next()) {
                rs.close();
                conn.close();
                return null;
            }
            User user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7));
            rs.close();
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAdmin(String token){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(username) FROM users WHERE token = ? AND admin = TRUE AND logged = TRUE;");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            ps.close();
            if (!rs.next() || rs.getInt(1) != 1) {
                rs.close();
                conn.close();
                return false;
            }
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String username, String pwd) {
        String token = "Basic " + username + "-mtcgToken";
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps;
            if (username.equals("admin")){
                ps = conn.prepareStatement("INSERT INTO users(username, pwd, token, admin) VALUES(?,?,?,TRUE);");
            } else {
                ps = conn.prepareStatement("INSERT INTO users(username, pwd, token) VALUES(?,?,?);");
            }
            ps.setString(1, username);
            ps.setString(2, pwd);
            ps.setString(3, token);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(String username, String pwd){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET logged = TRUE WHERE username = ? AND pwd = ?;");
            ps.setString(1, username);
            ps.setString(2, pwd);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean logoutUser(String username, String pwd){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET logged = FALSE WHERE username = ? AND pwd = ?;");
            ps.setString(1, username);
            ps.setString(2, pwd);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean setUserInfo(User user, String name, String bio, String image){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET name = ?, bio = ?, image = ? WHERE username = ?;");
            ps.setString(1, name);
            ps.setString(2, bio);
            ps.setString(3, image);
            ps.setString(4, user.getUsername());
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getScoreboard(){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT name, wins, games, elo FROM users WHERE username NOT LIKE 'admin' ORDER BY elo DESC;");
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
