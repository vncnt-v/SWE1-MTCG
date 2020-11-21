package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import bif3.swe1.mtcg.cards.Spell;
import bif3.swe1.mtcg.cards.Monsters.*;

import java.util.*;

public class GameManager {

    private static GameManager single_instance = null;

    private List<User> user_database = new ArrayList<>();
    private List<User> user_active = new ArrayList<>();
    private List<CardPackage> pck = new ArrayList<>();
    private List<String> card_IDs = new ArrayList<>();
    private List<MarketplaceContent> marketplace = new ArrayList<>();

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
        for (User user : user_active){
            if (user.getUsername().equals(username)){
                return false;
            }
        }
        for (User user : user_database){
            if (user.getUsername().equals(username) && user.checkPwd(pwd)){
                user_active.add(user);
                return true;
            }
        }
        return false;
    }

    public boolean logoutUser(String username, String pwd){
        for (User user : user_active){
            if (user.getUsername().equals(username) && user.checkPwd(pwd)){
                user_active.remove(user);
                return true;
            }
        }
        return false;
    }

    public String getBio(String username){
        String payload = "ERR\r\n";
        for (User user:user_database){
            if (user.getUsername().equals(username)){
                return ("Username: " + user.getUsername() + "\r\nName: " + user.getName() + "\r\nBio: " + user.getBio() + "\r\nImage: " + user.getImage()+ "\r\n");
            }
        }
        return payload;
    }

    public boolean setBio(String username, String name, String bio, String image){
        for (User user:user_database){
            if (user.getUsername().equals(username)){
                user.setBio(bio);
                user.setName(name);
                user.setImage(image);
                return true;
            }
        }
        return false;
    }

    public String getScoreboard(){
        String payload = "";
        for (int i = 0; i < user_database.size(); i++){
            payload += user_database.get(i).getUsername() + "\r\n   Games: " + user_database.get(i).getCount_of_games() + "\r\n    Wins: " + user_database.get(i).getCount_of_wins() + "\r\n";
        }
        return payload;
    }

    public User authorize(String username){
        for (User user : user_active){
            if (username.equals("Basic " + user.getUsername() + "-mtcgToken")){
                return user;
            }
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

    public boolean removePackage(CardPackage pck){
        if (this.pck.indexOf(pck) >= 0){
            this.pck.remove(pck);
        }
        return false;
    }

    public boolean offerCard(String tradeID, String username, String cardID, String type, Float minimumDamage){
        for (User user : user_active){
            if (user.getUsername().equals(username)){
                AbstractCard card = user.getStack().getCard(cardID);
                if (card == null){
                    return false;
                }
                for (MarketplaceContent content : marketplace){
                    if (content.getTradeID().equals(tradeID)){
                        return false;
                    }
                }
                MarketplaceContent content = new MarketplaceContent(tradeID,username,card,type,minimumDamage);
                marketplace.add(content);
                return true;
            }
        }
        return false;
    }

    public String showMarktplace(){
        if (marketplace.size() <= 0){
            return "Marketplace empty\r\n";
        }
        String payload = "";
        for (int i = 0; i < marketplace.size(); i++){
            payload += (i+1) + ")\r\n";
            payload += "    TradeID: " + marketplace.get(i).getTradeID() + "\r\n";
            payload += "    From: " + marketplace.get(i).getUsername() + "\r\n";
            payload += "    CardID: " + marketplace.get(i).getCard().getId() + "\r\n";
            payload += "    Name: " + marketplace.get(i).getCard().getName() + "\r\n";
            payload += "    Damage: " + marketplace.get(i).getCard().getDamage() + "\r\n";
            payload += "    Exchange for: " + marketplace.get(i).getType() + "\r\n";
            payload += "    Minimum Damage: " + marketplace.get(i).getMinimumDamage() + "\r\n";
        }
        return payload;
    }

    public boolean removeTrade(String tradeID, User user){
        for (int i = 0; i < marketplace.size(); i++){
            if (marketplace.get(i).getTradeID().equals(tradeID)){
                if (marketplace.get(i).getUsername().equals(user.getUsername())){
                    user.getStack().AddCard(marketplace.get(i).getCard());
                    marketplace.remove(i);
                    return true;
                }

            }
        }
        return false;
    }

    public boolean tradeCard(String tradeID, String username, String cardID){
        User user = null;
        System.out.println("-1");
        for (User entry : user_active) {
            if (entry.getUsername().equals(username)) {
                user = entry;
            }
        }
        if (user == null){
            return false;
        }
        for (MarketplaceContent entry:marketplace){
            if (entry.getTradeID().equals(tradeID)){
                if (entry.getUsername().equals(user.getUsername())){
                    return false;
                }
                AbstractCard card = user.getStack().getCard(cardID);
                if (card == null){
                    return false;
                }
                if (card.getDamage() < entry.getMinimumDamage()){
                    return false;
                }
                // check Type
                if (entry.getType().toLowerCase().equals("monster")){
                    if (card instanceof Spell){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("dragon")){
                    if (!(card instanceof Dragon)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("fireelf")){
                    if (!(card instanceof FireElf)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("goblin")){
                    if (!(card instanceof Goblin)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("knight")){
                    if (!(card instanceof Knight)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("kraken")){
                    if (!(card instanceof Kraken)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("ork")){
                    if (!(card instanceof Ork)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("wizard")){
                    if (!(card instanceof Wizard)){
                        return false;
                    }
                } else if (entry.getType().toLowerCase().equals("spell")){
                    if (!(card instanceof Spell)){
                        return false;
                    }
                }
                for (User user_entry : user_database) {
                    if (user_entry.getUsername().equals(entry.getUsername())) {
                        user_entry.getStack().AddCard(card);
                        user.getStack().AddCard(entry.getCard());
                        marketplace.remove(entry);
                        return true;
                    }
                }
                user.getStack().AddCard(card);
                return false;
            }
        }
        return false;
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
