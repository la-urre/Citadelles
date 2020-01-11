package com.montaury.citadels.round.action;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.player.Player;

public interface Action {
    default boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return true;
    }

    void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile);
}
