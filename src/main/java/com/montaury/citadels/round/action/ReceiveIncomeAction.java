package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;

public class ReceiveIncomeAction implements Action {

    @Override
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        playerCharacterAssociation.player().receiveIncomeFor(playerCharacterAssociation.character);
    }
}
