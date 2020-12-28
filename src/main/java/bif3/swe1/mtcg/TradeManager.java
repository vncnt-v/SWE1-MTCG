package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.Spell;
import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class TradeManager {

    private static TradeManager single_instance = null;

    private List<TradingDeal> trades = new ArrayList<>();

    public static TradeManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new TradeManager();
        }
        return single_instance;
    }

    public boolean card2market(User user, String tradeID, String cardID, ElementType element, MonsterType monster, float minimumDamage, float minimumWeakness){
        Card card = user.getStackCard(cardID);
        if (card == null){
            return false;
        }
        for (TradingDeal content : trades){
            if (content.getTradeID().equals(tradeID)){
                user.addCard(card);
                return false;
            }
        }
        TradingDeal content = new TradingDeal(tradeID,user.getUsername(),card,element,monster,minimumDamage,minimumWeakness);
        trades.add(content);
        return true;
    }

    public String showMarktplace(){
        if (trades.size() <= 0){
            return "Marketplace empty\r\n";
        }
        String payload = "";
        // ToDo JSON
        for (int i = 0; i < trades.size(); i++){
            payload += (i+1) + ")\r\n";
            payload += "    TradeID: " + trades.get(i).getTradeID() + "\r\n";
            payload += "    From: " + trades.get(i).getUsername() + "\r\n";
            payload += "    CardID: " + trades.get(i).getCard().getId() + "\r\n";
            payload += "    Name: " + trades.get(i).getCard().getName() + "\r\n";
            payload += "    Damage: " + trades.get(i).getCard().getDamage() + "\r\n";
            if (trades.get(i).getCard() instanceof Spell){
                payload += "    Weakness: " + trades.get(i).getCard().getDamage() + "\r\n";
            }
            if (trades.get(i).getMonster() == null){
                payload += "    Wanted Card: Spell\r\n";
                payload += "    Wanted Element: " + trades.get(i).getElement() + "\r\n";
                payload += "    Minimum Damage: " + trades.get(i).getMinimumDamage() + "\r\n";
                payload += "    Minimum Weakness: " + trades.get(i).getMinimumWeakness() + "\r\n";
            } else {
                payload += "    Wanted Card: " + trades.get(i).getMonster() + "\r\n";
                payload += "    Wanted Element: " + trades.get(i).getElement() + "\r\n";
                payload += "    Minimum Damage: " + trades.get(i).getMinimumDamage() + "\r\n";
            }
        }
        return payload;
    }

    public boolean removeTrade(String tradeID, User user){
        for (int i = 0; i < trades.size(); i++){
            if (trades.get(i).getTradeID().equals(tradeID)){
                if (trades.get(i).getUsername().equals(user.getUsername())){
                    user.addCard(trades.get(i).getCard());
                    trades.remove(i);
                    return true;
                }

            }
        }
        return false;
    }

    public boolean tradeCard(User user, String tradeID, String cardID){
        if (user == null){
            return false;
        }
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
        return false;
    }
}
