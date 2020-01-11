package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.player.Player;

public class ExchangeHandWithPlayerAction implements Action {
    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Player playerToSwapWith = playerCharacterAssociation.player().controller.selectPlayerAmong(associations.playersBut(playerCharacterAssociation.player()));
        playerCharacterAssociation.player().exchangeHandWith(playerToSwapWith);
    }
}
