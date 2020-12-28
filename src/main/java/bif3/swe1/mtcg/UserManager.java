package bif3.swe1.mtcg;

import bif3.swe1.database.DatabaseService;
import bif3.swe1.mtcg.cards.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager single_instance = null;

    private List<User> user_database = new ArrayList<>();
    private List<User> user_active = new ArrayList<>();

    public static UserManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new UserManager();
        }
        return single_instance;
    }

    public User authorizeUser(String token){
        for (User user : user_active){
            if (user.authorize(token)){
                return user;
            }
        }
        return null;
    }

    public boolean addCard2User(Card card, String username){
        for (User user : user_database){
            if (user.getUsername().equals(username)){
                user.addCard(card);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String username, String pwd){
        for (User user : user_database){
            if (user.getUsername().equals(username)){
                if (user.checkPwd(pwd)){
                    user_database.remove(user);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean registerUser(String username, String pwd) {
        String token = "Basic " + username + "-mtcgToken";
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username, pwd, token) VALUES(?,?,?);");
            ps.setString(1, username);
            ps.setString(2, pwd);
            ps.setString(3, token);
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

    public boolean loginUser(String username, String pwd){
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET active = true WHERE username = ? AND pwd = ?;");
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
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET active = false WHERE username = ? AND pwd = ?;");
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

    public String getUserInfo(String username){
        String payload = "ERR\r\n";
        for (User user:user_database){
            if (user.getUsername().equals(username)){
                return ("Username: " + user.getUsername() + "\r\nName: " + user.getName() + "\r\nBio: " + user.getBio() + "\r\nImage: " + user.getImage()+ "\r\n");
            }
        }
        return payload;
    }

    public void setUserInfo(User user, String name, String bio, String image){
        user.setBio(bio);
        user.setName(name);
        user.setImage(image);
    }

}
