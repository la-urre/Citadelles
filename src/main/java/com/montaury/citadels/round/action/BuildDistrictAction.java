package com.montaury.citadels.round.action;

import com.montaury.citadels.*;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;

public class BuildDistrictAction implements Action {
    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return !player.buildableDistrictsInHand().isEmpty();
    }

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Card card = playerCharacterAssociation.player().controller.selectAmong(playerCharacterAssociation.player().buildableDistrictsInHand());
        playerCharacterAssociation.player().buildDistrict(card);
    }
}
