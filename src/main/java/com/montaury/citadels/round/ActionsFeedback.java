package com.montaury.citadels.round;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Hand;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.collection.List;
import io.vavr.collection.Set;

public class ActionsFeedback {
    public ActionsFeedback(List<PlayerCharacterAssociation> associations) {
        this.associations = associations;
    }

    public void actionExecuted(PlayerCharacterAssociation association, ActionType actionType) {
        System.out.println("Player " + association.player().name() + " executed action " + actionType);
        associations.map(PlayerCharacterAssociation::player)
                .forEach(this::displayStatus);
    }

    private void displayStatus(Player player) {
        System.out.println("  Player " + player.name() + ":");
        System.out.println("    Gold coins: " + player.gold().coins());
        System.out.println("    City: " + textCity(player));
        System.out.println("    Hand size: " + player.hand().cardsCount());
        if (player.controller instanceof HumanController) {
            System.out.println("    Hand: " + textHand(player));
        }
        System.out.println();
    }

    private String textCity(Player player) {
        List<District> districts = player.city().districts();
        return districts.isEmpty() ? "Empty" : districts.map(this::textDistrict).mkString(", ");
    }

    private String textDistrict(District district) {
        return district.name() + "(" + district.districtType().name() + ", " + district.cost().amount() + ")";
    }

    private String textHand(Player player) {
        Set<Card> cards = player.hand().cards();
        return cards.isEmpty() ? "Empty" : cards.map(this::textCard).mkString(", ");
    }

    private String textCard(Card card) {
        return textDistrict(card.district());
    }

    private final List<PlayerCharacterAssociation> associations;
}
