package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import lombok.Getter;

public class MarketplaceContent {

    @Getter
    private String tradeID;
    @Getter
    private String username;
    @Getter
    private AbstractCard card;
    @Getter
    private String type;
    @Getter
    private float minimumDamage;

    public MarketplaceContent(String tradeID, String username, AbstractCard card, String type, float minimumDamage){
        this.tradeID = tradeID;
        this.username = username;
        this.card = card;
        this.type = type;
        this.minimumDamage = minimumDamage;
    }
}
