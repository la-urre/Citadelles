package com.montaury.citadels.round;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.collection.Set;

class PlayerTurn {

    PlayerTurn(PlayerCharacterAssociation playerCharacterAssociation, ActionsFeedback actionsFeedback) {
        this.playerCharacterAssociation = playerCharacterAssociation;
        this.actionsFeedback = actionsFeedback;
    }

    void take(GameRoundAssociations tourDeJeu, CardPile cardPile) {
        playerCharacterAssociation.thief().peek(thief -> thief.steal(playerCharacterAssociation.player()));
        takeActions(tourDeJeu, cardPile);
    }

    private void takeActions(GameRoundAssociations tourDeJeu, CardPile cardPile) {
        takeAction(playerCharacterAssociation.baseActions(), tourDeJeu, cardPile);
        takeActions(playerCharacterAssociation.optionalActions(), tourDeJeu, cardPile);
    }

    private void takeActions(Set<ActionType> availableActions, GameRoundAssociations gameRoundAssociations, CardPile cardPile) {
        ActionType actionType;
        do {
            actionType = takeAction(availableActions, gameRoundAssociations, cardPile);
            availableActions = availableActions.remove(actionType);
        }
        while (!availableActions.isEmpty() && actionType != ActionType.END_ROUND);
    }

    private ActionType takeAction(Set<ActionType> availableActions, GameRoundAssociations gameRoundAssociations, CardPile cardPile) {
        availableActions = availableActions.filter(action -> action.canExecute(playerCharacterAssociation.player(), gameRoundAssociations, cardPile));
        ActionType actionType = playerCharacterAssociation.player().controller.selectActionAmong(availableActions.toList());
        actionType.execute(playerCharacterAssociation, gameRoundAssociations, cardPile);
        actionsFeedback.actionExecuted(playerCharacterAssociation, actionType);
        return actionType;
    }

    private final PlayerCharacterAssociation playerCharacterAssociation;
    private final ActionsFeedback actionsFeedback;
}
