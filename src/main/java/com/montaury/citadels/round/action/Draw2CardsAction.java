package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.player.Player;

public class Draw2CardsAction implements Action {
    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return cardPile.canDraw(2);
    }

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        playerCharacterAssociation.player().addCardsInHand(cardPile.draw(2));
    }
}
