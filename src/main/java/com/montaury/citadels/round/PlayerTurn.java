package com.montaury.citadels.round;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.collection.Set;

class PlayerTurn {

    PlayerTurn(PlayerCharacterAssociation playerCharacterAssociation) {
        this.playerCharacterAssociation = playerCharacterAssociation;
    }

    void take(GameRoundAssociations tourDeJeu, CardPile cardPile) {
        playerCharacterAssociation.thief().peek(thief -> thief.steal(playerCharacterAssociation.player()));
        chooseActions(tourDeJeu, cardPile);
    }

    private void chooseActions(GameRoundAssociations tourDeJeu, CardPile cardPile) {
        action(playerCharacterAssociation.baseActions(), tourDeJeu, cardPile);
        actions(playerCharacterAssociation.optionalActions(), tourDeJeu, cardPile);
    }

    private void actions(Set<ActionType> availableActions, GameRoundAssociations gameRoundAssociations, CardPile cardPile) {
        ActionType actionType;
        do {
            actionType = action(availableActions, gameRoundAssociations, cardPile);
            availableActions = availableActions.remove(actionType);
        }
        while (!availableActions.isEmpty() && actionType != ActionType.END_ROUND);
    }

    private ActionType action(Set<ActionType> availableActions, GameRoundAssociations gameRoundAssociations, CardPile cardPile) {
        availableActions = availableActions.filter(action -> action.canExecute(playerCharacterAssociation.player(), gameRoundAssociations, cardPile));
        ActionType actionType = playerCharacterAssociation.player().controller.selectActionAmong(availableActions.toList());
        actionType.execute(playerCharacterAssociation, gameRoundAssociations, cardPile);
        return actionType;
    }

    private final PlayerCharacterAssociation playerCharacterAssociation;
}
