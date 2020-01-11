package com.montaury.citadels.round.action;

import com.montaury.citadels.*;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;
import io.vavr.collection.Set;

public class ExchangeCardsWithPileAction implements Action {
    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return player.hand().hasCards() && cardPile.canDraw(1);
    }

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Set<Card> cardsToSwap = playerCharacterAssociation.player().controller.selectManyAmong(playerCharacterAssociation.player().hand().cards());
        playerCharacterAssociation.player().hand().draw(cardsToSwap);
        playerCharacterAssociation.player().addCardsInHand(cardPile.swapWith(cardsToSwap.toList()));
    }
}
