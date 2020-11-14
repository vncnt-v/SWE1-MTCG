package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.Spell;
import bif3.swe1.mtcg.cards.Monsters.*;

import java.util.*;

public class GameManager {

    private static GameManager single_instance = null;

    private List<User> user_database = new ArrayList<>();
    private User user_1;
    private User user_2;
    private List<CardPackage> pck = new ArrayList<>();
    private List<String> card_IDs = new ArrayList<>();

    public static GameManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new GameManager();
        }
        return single_instance;
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

    public boolean registerUser(String username, String pwd){
        for (User user : user_database){
            if (user.getUsername().equals(username)){
                return false;
            }
        }
        user_database.add(new User(username,pwd));
        return true;
    }

    public boolean loginUser(String username, String pwd){
        for (User user : user_database){
            if (user.getUsername().equals(username) && user.checkPwd(pwd)){
                if (user_1 == null){
                    user_1 = user;
                    return true;
                } else if (user_2 == null){
                    user_2 = user;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public User authorize(String username){
        if (username.equals(user_1.getUsername())){
            return user_1;
        } else if (username.equals(user_2.getUsername())){
            return user_2;
        }
        return null;
    }

    public void addPackage(CardPackage pck){
        this.pck.add(pck);
    }

    public CardPackage acquirePackage(){
        if (pck.size() > 0){
            return pck.get((int)(Math.random() * pck.size()));
        }
        return null;
    }

    public AbstractCard createCard(String id, String name, float damage){
        if (card_IDs.contains(id)){
            return null;
        }
        card_IDs.add(id);
        if (name.contains("Spell")){
            return new Spell(id,name,damage);
        } else if (name.contains("Dragon")){
            return new Dragon(id,name,damage);
        } else if (name.contains("FireElf")){
            return new FireElf(id,name,damage);
        } else if (name.contains("Goblin")){
            return new Goblin(id,name,damage);
        } else if (name.contains("Knight")){
            return new Knight(id,name,damage);
        } else if (name.contains("Kraken")){
            return new Kraken(id,name,damage);
        } else if (name.contains("Ork")){
            return new Ork(id,name,damage);
        } else if (name.contains("Wizard")){
            return new Wizard(id,name,damage);
        }
        card_IDs.remove(id);
        return null;
    }
}
