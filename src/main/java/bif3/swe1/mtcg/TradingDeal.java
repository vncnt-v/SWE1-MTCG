package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.types.MonsterType;
import lombok.Getter;

public class TradingDeal {

    @Getter
    private String tradeID;
    @Getter
    private String username;
    @Getter
    private Card card;
    @Getter
    private MonsterType monster;
    @Getter
    private ElementType element;
    @Getter
    private float minimumDamage;
    @Getter
    private float minimumWeakness;

    public TradingDeal(String tradeID, String username, Card card, ElementType element, MonsterType monster, float minimumDamage, float minimumWeakness){
        this.tradeID = tradeID;
        this.username = username;
        this.card = card;
        this.element = element;
        this.monster = monster;
        this.minimumDamage = minimumDamage;
        this.minimumWeakness = minimumWeakness;
    }
}
