package bif3.swe1.API;
import bif3.swe1.mtcg.CardPackage;
import bif3.swe1.mtcg.GameManager;
import bif3.swe1.mtcg.User;
import bif3.swe1.mtcg.cards.AbstractCard;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Handle request and send response
public class ResponseHandler {

    BufferedWriter writer;

    public ResponseHandler(BufferedWriter writer){
        this.writer = writer;
    }

    public void response(RequestContext request) {
        String payload = "ERR\r\n";
        // Check resource requested
        if (request != null){
            String[] parts = request.getRequested().split("/");
            User user = null;
            if ((parts.length == 2 || parts.length == 3)) {
                switch (parts[1]){
                    case "users":
                        payload = users(request);
                        break;
                    case "sessions":
                        payload = sessions(request);
                        break;
                    case "packages":
                        payload = packages(request);
                        break;
                    case "transactions":
                        if (parts.length != 3){
                            break;
                        }
                        switch (parts[2]){
                            case "packages":
                                user = authorize(request);
                                if (user != null){
                                    payload = transactionsPackages(user,request);
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case "cards":
                        user = authorize(request);
                        if (user != null){
                            payload = showCards(user,request);
                        }
                        break;
                    case "deck":
                        user = authorize(request);
                        if (user != null){
                            payload = requestDeck(user,request);
                        }
                        break;
                    case "stats":
                        user = authorize(request);
                        if (user != null){
                            payload = stats(user,request);
                        }
                        break;
                    case "score":
                        user = authorize(request);
                        if (user != null){
                            payload = scoreboard(request);
                        }
                        break;
                    case "tradings":
                        user = authorize(request);
                        if (user != null){
                            payload = trade(request,user);
                        }
                        break;
                    case "battles":
                        user = authorize(request);
                        if (user != null){
                            //payload = battle(request,user);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        // Send response
        try {
            writer.write("HTTP/1.1 200 OK\r\n");
            writer.write("Server: MailServer\r\n");
            writer.write("Content-Type: text/html\r\n");
            writer.write("Content-Length: " + payload.length() + "\r\n\r\n");
            writer.write(payload);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String users(RequestContext request){
        GameManager manager = GameManager.getInstance();
        User user;
        JSONObject json;
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()){
            case "POST":
                json = new JSONObject(request.getPayload());
                if (json.length() == 2 && json.has("Username") && json.has("Password")){
                    payload = booleanToString(manager.registerUser(json.get("Username").toString(),json.get("Password").toString()));
                }
                break;
            case "GET":
                user = authorize(request);
                if (user == null){
                    break;
                }
                String[] parts = request.getRequested().split("/");
                if (parts.length == 3){
                    payload = manager.getBio(parts[2]);
                }
                break;
            case "PUT":
                user = authorize(request);
                if (user == null){
                    break;
                }
                String[] editUser = request.getRequested().split("/");
                if (editUser.length == 3 && user.getUsername().equals(editUser[2])){
                    json = new JSONObject(request.getPayload());
                    if (json.length() == 3 && json.has("Name") && json.has("Bio") && json.has("Image")) {
                        payload = booleanToString(manager.setBio(editUser[2],json.get("Name").toString(),json.get("Bio").toString(),json.get("Image").toString()));
                    }
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private String sessions(RequestContext request){
        GameManager manager = GameManager.getInstance();
        JSONObject json = new JSONObject(request.getPayload());
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()){
            case "POST":
                if (json.length() == 2 && json.has("Username") && json.has("Password")){
                    payload = booleanToString(manager.loginUser(json.get("Username").toString(),json.get("Password").toString()));
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private String packages(RequestContext request){
        GameManager manager = GameManager.getInstance();
        JSONArray jsonArr = new JSONArray(request.getPayload());
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()){
            case "POST":
                if (jsonArr.length() == 5){
                    List<AbstractCard> cards = new ArrayList<>();
                    boolean failed = false;
                    for (int i = 0; i < jsonArr.length(); i++){
                        JSONObject json = new JSONObject(jsonArr.get(i).toString());
                        if ((json.length() == 3 || json.length() == 4) && json.has("Id") && json.has("Name") && json.has("Damage")){
                            AbstractCard card = manager.createCard(json.getString("Id"),json.getString("Name"),json.getFloat("Damage"));
                            if (card == null){
                                failed = true;
                                break;
                            }
                            cards.add(card);
                        } else {
                            failed = true;
                        }
                    }
                    if (!failed){
                        payload = "OK\r\n";
                        CardPackage pck = new CardPackage(cards);
                        manager.addPackage(pck);
                    }
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private String transactionsPackages(User user,RequestContext request){
        GameManager manager = GameManager.getInstance();
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()) {
            case "POST":
                CardPackage pck = manager.acquirePackage();
                if (pck == null) {
                    return payload;
                }
                if (user.BuyPackage(pck)) {
                    manager.removePackage(pck);
                    payload = "OK\r\n";
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private String showCards(User user,RequestContext request){
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()) {
            case "GET":
                payload = user.getStack().getCards().size() + " Cards\r\n";
                for (int i = 0; i < user.getStack().getCards().size(); i++){
                    payload += (i+1) + ")\r\n";
                    payload += "    ID: " + user.getStack().getCards().get(i).getId() + "\r\n";
                    payload += "    Name: " + user.getStack().getCards().get(i).getName() + "\r\n";
                    payload += "    Damage: " + user.getStack().getCards().get(i).getDamage() + "\r\n";
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private String requestDeck(User user,RequestContext request){
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()) {
            case "GET":
                if (user.getDeck() == null){
                    return "0 Cards\r\n";
                }
                payload = user.getDeck().getCards().size() + " Cards\r\n";
                for (int i = 0; i < user.getDeck().getCards().size(); i++){
                    payload += (i+1) + ")\r\n";
                    payload += "    ID: " + user.getDeck().getCards().get(i).getId() + "\r\n";
                    payload += "    Name: " + user.getDeck().getCards().get(i).getName() + "\r\n";
                    payload += "    Damage: " + user.getDeck().getCards().get(i).getDamage() + "\r\n";
                }
                break;
            case "PUT":
                JSONArray jsonArr = new JSONArray(request.getPayload());
                if (jsonArr.length() == 4){
                    List<String> cardID = new ArrayList<>();
                    for (int i = 0; i < jsonArr.length(); i++){
                        cardID.add(jsonArr.get(i).toString());
                    }
                    if (user.createDeck(cardID)){
                        payload = "OK\r\n";
                    }
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private String stats(User user,RequestContext request){
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()) {
            case "GET":
                payload = "Games: " + user.getCount_of_games() + "\r\nWins:" + user.getCount_of_wins() + "\r\n";
                break;
            default:
                break;
        }
        return payload;
    }

    private String scoreboard(RequestContext request){
        GameManager manager = GameManager.getInstance();
        String payload = "ERR\r\n";
        switch (request.getHttp_verb()) {
            case "GET":
                payload = manager.getScoreboard();
                break;
            default:
                break;
        }
        return payload;
    }

    private String trade(RequestContext request, User user){
        GameManager manager = GameManager.getInstance();
        String payload = "ERR\r\n";
        String[] parts;
        switch (request.getHttp_verb()) {
            case "GET":
                payload = manager.showMarktplace();
                break;
            case "POST":
                parts = request.getRequested().split("/");
                if (parts.length == 3){
                    System.out.println(request.getPayload());
                    String[] card_parts = request.getPayload().split("\"");
                    System.out.println(card_parts.length);
                    System.out.println(card_parts[0]);
                    System.out.println(card_parts[1]);
                    if (card_parts.length == 2){
                        payload = booleanToString(manager.tradeCard(parts[2],user.getUsername(),card_parts[1]));
                    }
                } else {
                    JSONObject json = new JSONObject(request.getPayload());
                    if (json.has("Id") && json.has("CardToTrade") && json.has("Type") && json.has("MinimumDamage")){
                        payload = booleanToString(manager.offerCard(json.getString("Id"),user.getUsername(),json.getString("CardToTrade"),json.getString("Type"),json.getFloat("MinimumDamage")));
                    }
                }
                break;
            case "DELETE":
                parts = request.getRequested().split("/");
                if (parts.length == 3){
                    payload = booleanToString(manager.removeTrade(parts[2],user));
                }
                break;
            default:
                break;
        }
        return payload;
    }

    private User authorize(RequestContext request){
        User user = null;
        if (request.getHeader_values().containsKey("authorization:")){
            GameManager manager = GameManager.getInstance();
            user = manager.authorize(request.getHeader_values().get("authorization:"));
        }
        return user;
    }

    private String booleanToString(boolean bool){
        if (bool){
            return "OK\r\n";
        }
        return "ERR\r\n";
    }
}
