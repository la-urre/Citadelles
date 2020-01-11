package com.montaury.citadels.round.action;

import com.montaury.citadels.*;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;

public class DiscardCardFor2GoldCoinsAction implements Action {
    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return player.hand().hasCards();
    }

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Player player = playerCharacterAssociation.player();
        Card card = player.controller.selectAmong(player.hand().cards());
        player.hand().draw(card);
        cardPile.discard(card);
        player.earn(2);
    }
}
