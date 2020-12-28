package bif3.swe1.mtcg;

import bif3.swe1.mtcg.cards.Card;
import bif3.swe1.mtcg.cards.Monster;
import bif3.swe1.mtcg.cards.Spell;
import bif3.swe1.mtcg.cards.types.MonsterType;
import bif3.swe1.mtcg.cards.types.ElementType;
import bif3.swe1.mtcg.cards.collections.CardPackage;

import java.util.ArrayList;
import java.util.List;

public class CardManager {

    private static CardManager single_instance = null;

    private List<String> card_IDs = new ArrayList<>();
    private List<CardPackage> pck = new ArrayList<>();

    public static CardManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new CardManager();
        }
        return single_instance;
    }

    public void createPackage(Card cards[]){
        CardPackage pck = new CardPackage(cards);
        this.pck.add(pck);
    }

    public boolean acquirePackage2User(User user){
        if (pck.size() > 0){
            int random = (int)(Math.random() * pck.size());
            if (user.acquirePackage(pck.get(random))){
                pck.remove(random);
                return true;
            }
        }
        return false;
    }

    public void deleteCardId(String id){
        if (card_IDs.contains(id)){
            card_IDs.remove(id);
        }
    }

    public ElementType createElementType (String element){
        if (element.equalsIgnoreCase("water")){
            return ElementType.water;
        } else if (element.equalsIgnoreCase("fire")){
            return ElementType.fire;
        } else {
            return ElementType.normal;
        }
    }

    public MonsterType createMonsterType (String monster){
        if (monster.equalsIgnoreCase("dragon")){
            return MonsterType.Dragon;
        } else if (monster.equalsIgnoreCase("fireelf")){
            return MonsterType.FireElf;
        } else if (monster.equalsIgnoreCase("goblin")){
            return MonsterType.Goblin;
        } else if (monster.equalsIgnoreCase("knight")){
            return MonsterType.Knight;
        } else if (monster.equalsIgnoreCase("kraken")){
            return MonsterType.Kraken;
        } else if (monster.equalsIgnoreCase("ork")){
            return MonsterType.Ork;
        } else if (monster.equalsIgnoreCase("wizard")){
            return MonsterType.Wizard;
        }
        return null;
    }

    public Card createSpell(String id, String name, float damage, float weakness, ElementType element) {
        if ( !card_IDs.contains(id) || !id.isEmpty() || !name.isEmpty() || element != null) {
            card_IDs.add(id);
            return new Spell(id, name, damage, weakness, element);
        }
        return null;
    }

    public Card createMonster(String id, String name, float damage, ElementType element, MonsterType monster){
        if ( !card_IDs.contains(id) || !id.isEmpty() || !name.isEmpty() || element != null || element != null){
            card_IDs.add(id);
            return new Monster(id,name,damage,element,monster);
        }
        return null;
    }
}
