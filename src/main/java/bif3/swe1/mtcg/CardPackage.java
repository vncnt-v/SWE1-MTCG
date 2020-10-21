package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.AbstractCard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CardPackage {
    @Getter
    private List<AbstractCard> cards = new ArrayList<>();

    public CardPackage(List<AbstractCard> pck){
        if (pck != null){
            for (int i = 0; pck.size() > i && i < 5; i++){
                this.cards.add(pck.get(i));
            }
        }
    }
}
