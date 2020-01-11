package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.character.Character;

public class StealAction implements Action {
    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Character character = playerCharacterAssociation.player().controller.selectAmong(associations.charactersThatCanBeStolen());
        associations.steal(playerCharacterAssociation.player(), character);
    }
}
