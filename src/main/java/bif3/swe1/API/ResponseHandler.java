package bif3.swe1.API;
import bif3.swe1.API.context.RequestContext;
import bif3.swe1.API.context.ResponseContext;
import bif3.swe1.mtcg.*;
import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.collections.CardPackage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Handle request and send response
public class ResponseHandler {

    BufferedWriter writer;

    public ResponseHandler(BufferedWriter writer){
        this.writer = writer;
    }

    public void response(RequestContext request) {
        ResponseContext response = new ResponseContext("400 Bad Request");
        if ( request != null && request.getHeader_values() != null && request.getHeader_values().containsKey("content-type:") && request.getHeader_values().get("content-type:").equalsIgnoreCase("application/json") ){
            String[] parts = request.getRequested().split("/");
            User user = null;
            if ((parts.length == 2 || parts.length == 3)) {
                switch (parts[1]){
                    case "users":
                        response = users(request);
                        break;
                    case "sessions":
                        response = sessions(request);
                        break;
                    case "packages":
                        response = packages(request);
                        break;
                    case "transactions":
                        if (parts.length != 3){
                            break;
                        }
                        if (parts[2].equals("packages")){
                            user = authorize(request);
                            if (user != null){
                                response = transactionsPackages(user,request);
                            } else {
                                response = new ResponseContext("401 Unauthorized");
                            }
                        }
                        break;
                    case "cards":
                        user = authorize(request);
                        if (user != null){
                            response = showCards(user,request);
                        } else {
                            response = new ResponseContext("401 Unauthorized");
                        }
                        break;
                    case "deck":
                        user = authorize(request);
                        if (user != null){
                            response = requestDeck(user,request);
                        } else {
                            response = new ResponseContext("401 Unauthorized");
                        }
                        break;
                    case "stats":
                        user = authorize(request);
                        if (user != null){
                            response = stats(user,request);
                        } else {
                            response = new ResponseContext("401 Unauthorized");
                        }
                        break;
                    case "score":
                        user = authorize(request);
                        if (user != null){
                            response = scoreboard(request);
                        } else {
                            response = new ResponseContext("401 Unauthorized");
                        }
                        break;
                    case "tradings":
                        user = authorize(request);
                        if (user != null){
                            response = trade(request,user);
                        } else {
                            response = new ResponseContext("401 Unauthorized");
                        }
                        break;
                    case "battles":
                        user = authorize(request);
                        if (user != null){
                            //response = battle(request,user);
                        } else {
                            response = new ResponseContext("401 Unauthorized");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        // Send response
        try {
            writer.write(response.getHttp_version() + " " + response.getStatus() + "\r\n");
            writer.write("Server: " + response.getServer() + "\r\n");
            writer.write("Content-Type: " + response.getContentType() + "\r\n");
            writer.write("Content-Length: " + response.getContentLength() + "\r\n\r\n");
            writer.write(response.getPayload());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ResponseContext users(RequestContext request){
        UserManager manager = UserManager.getInstance();
        ResponseContext response = new ResponseContext("400 Bad Request");
        ObjectMapper mapper;
        User user;
        switch (request.getHttp_verb()) {
            case "GET":
                user = authorize(request);
                if (user != null){
                    String[] parts = request.getRequested().split("/");
                    if (parts.length == 3){
                        if (response.setPayload(manager.getUserInfo(parts[2]))){
                            response.setStatus("200 OK");
                        } else {
                            response.setStatus("404 Not Found");
                        }
                    }
                } else {
                    response.setStatus("401 Unauthorized");
                }
                break;
            case "POST":
                mapper = new ObjectMapper();
                try {
                    JsonNode jsonNode = mapper.readTree(request.getPayload());
                    if ( jsonNode.has("Username") && jsonNode.has("Password")){
                        if (manager.registerUser(jsonNode.get("Username").asText(),jsonNode.get("Password").asText())) {
                            response.setStatus("200 OK");
                        } else {
                            response.setStatus("409 Conflict");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "PUT":
                user = authorize(request);
                if (user != null){
                    String[] editUser = request.getRequested().split("/");
                    if (editUser.length == 3 && user.getUsername().equals(editUser[2])){
                        mapper = new ObjectMapper();
                        try {
                            JsonNode jsonNode = mapper.readTree(request.getPayload());
                            if ( jsonNode.has("name") && jsonNode.has("bio") && jsonNode.has("image")){
                                manager.setUserInfo(user,jsonNode.get("name").asText(),jsonNode.get("bio").asText(),jsonNode.get("image").asText());
                                response.setStatus("200 OK");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    response.setStatus("401 Unauthorized");
                }
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext sessions(RequestContext request){
        UserManager manager = UserManager.getInstance();
        ResponseContext response = new ResponseContext("400 Bad Request");
        ObjectMapper mapper = new ObjectMapper();
        switch (request.getHttp_verb()){
            case "POST":
                try {
                    JsonNode jsonNode = mapper.readTree(request.getPayload());
                    if ( jsonNode.has("Username") && jsonNode.has("Password")){
                        if (manager.loginUser(jsonNode.get("Username").asText(),jsonNode.get("Password").asText())) {
                            response.setStatus("200 OK");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                try {
                    JsonNode jsonNode = mapper.readTree(request.getPayload());
                    if ( jsonNode.has("Username") && jsonNode.has("Password")){
                        if (manager.logoutUser(jsonNode.get("Username").asText(),jsonNode.get("Password").asText())) {
                            response.setStatus("200 OK");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext packages(RequestContext request){
        CardManager manager = CardManager.getInstance();
        ResponseContext response = new ResponseContext("400 Bad Request");
        switch (request.getHttp_verb()){
            case "POST":
                ObjectMapper mapper = new ObjectMapper();
                // ToDo
                // Authorize User and check if Admin
                try {
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                    List<Card> cards = mapper.readValue(request.getPayload(), new TypeReference<>(){});
                    if (cards.size() == 5){
                        List<Card> createdCards = new ArrayList<>();
                        for (Card card: cards){
                            if (manager.createCard(card.getId(),card.getName(),card.getDamage())) {
                                createdCards.add(card);
                            } else {
                                for (Card card_tmp: createdCards){
                                    manager.deleteCard(card_tmp.getId());
                                }
                                return response;
                            }
                        }
                        if(manager.createPackage(cards)){
                            response.setStatus("201 Created");
                        } else {
                            for (Card card_tmp: createdCards){
                                manager.deleteCard(card_tmp.getId());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext transactionsPackages(User user,RequestContext request){
        CardManager manager = CardManager.getInstance();
        ResponseContext response = new ResponseContext("400 Bad Request");
        switch (request.getHttp_verb()) {
            case "POST":
                if (manager.acquirePackage2User(user)){
                    response.setStatus("200 OK");
                } else {
                    response.setStatus("409 Conflict");
                }
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext showCards(User user,RequestContext request){
        ResponseContext response = new ResponseContext("400 Bad Request");
        switch (request.getHttp_verb()) {
            case "GET":
                String json = user.getStackCards();
                if (json != null){
                    response.setStatus("200 OK");
                    response.setPayload(json);
                } else {
                    response.setStatus("410 Error");
                }
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext requestDeck(User user,RequestContext request){
        ResponseContext response = new ResponseContext("400 Bad Request");
        switch (request.getHttp_verb()) {
            case "GET":
                String json = user.getDeckCards();
                if (json != null){
                    response.setStatus("200 OK");
                    response.setPayload(json);
                } else {
                    response.setStatus("410 Error");
                }
                break;
            case "PUT":
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<String> ids = mapper.readValue(request.getPayload(), new TypeReference<>(){});
                    if (ids.size() == 4){
                        if (user.createDeck(ids)){
                            response.setStatus("200 OK");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext stats(User user,RequestContext request){
        ResponseContext response = new ResponseContext("400 Bad Request");
        switch (request.getHttp_verb()) {
            case "GET":
                response.setStatus("200 OK");
                response.setPayload(user.getStats());
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext scoreboard(RequestContext request){
        EloManager manager = EloManager.getInstance();
        ResponseContext response = new ResponseContext("400 Bad Request");
        switch (request.getHttp_verb()) {
            case "GET":
                response.setPayload(manager.getEloScores());
                response.setStatus("200 OK");
                break;
            default:
                break;
        }
        return response;
    }

    private ResponseContext trade(RequestContext request, User user){
        TradeManager manager = TradeManager.getInstance();
        ResponseContext response = new ResponseContext("400 Bad Request");
        String[] parts;
        switch (request.getHttp_verb()) {
            case "GET":
                response.setPayload(manager.showMarktplace());
                response.setStatus("200 OK");
                break;
            case "POST":
                parts = request.getRequested().split("/");
                if (parts.length == 3){
                    String[] card_parts = request.getPayload().split("\"");
                    if (card_parts.length == 2){
                        if (manager.tradeCard(user,parts[2],card_parts[1])){
                            response.setStatus("200 OK");
                        }
                    }
                } else {
                    // JSON
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(request.getPayload());
                        if ( jsonNode.has("Id") && jsonNode.has("CardToTrade") && jsonNode.has("Type") && jsonNode.has("MinimumDamage")){
                            //manager.card2market(user,jsonNode.get("Id").asText(),jsonNode.get("CardToTrade").asText(),jsonNode.get("MonsterType").asText(),(float)jsonNode.get("MinimumDamage").asDouble());
                            //public boolean card2market(User user, String tradeID, String cardID, ElementType element, MonsterType monster, float minimumDamage, float minimumWeakness){

                                response.setStatus("200 OK");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "DELETE":
                parts = request.getRequested().split("/");
                if (parts.length == 3){
                    if (manager.removeTrade(parts[2],user)){
                        response.setStatus("200 OK");
                    }
                }
                break;
            default:
                break;
        }
        return response;
    }

    private User authorize(RequestContext request){
        User user = null;
        if (request.getHeader_values().containsKey("authorization:")){
            UserManager manager = UserManager.getInstance();
            user = manager.authorizeUser(request.getHeader_values().get("authorization:"));
        }
        return user;
    }
}
