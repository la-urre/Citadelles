package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.character.Character;

public class MurderAction implements Action {

    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Character characterToMurder = playerCharacterAssociation.player().controller.selectAmong(associations.charactersThatCanBeMurdered());
        associations.murder(characterToMurder);
    }

}
