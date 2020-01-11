package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.district.Cost;

public class Draw3CardsFor2GoldCoins implements Action {

    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return cardPile.canDraw(3) && player.canAfford(DRAWING_COST);
    }

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        playerCharacterAssociation.player().addCardsInHand(cardPile.draw(3));
        playerCharacterAssociation.player().pay(DRAWING_COST);
    }

    private static final Cost DRAWING_COST = Cost.of(2);
}
