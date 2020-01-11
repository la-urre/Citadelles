package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;

public class Receive2GoldCoinsAction implements Action {
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        playerCharacterAssociation.player().earn(2);
    }
}
