package com.montaury.citadels.round.action;

import com.montaury.citadels.*;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Keep1CardAmongManyAction implements Action {
    public Keep1CardAmongManyAction(int numberToDraw) {
        this.numberToDraw = numberToDraw;
    }

    @Override
    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return cardPile.canDraw(numberToDraw);
    }

    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        Set<Card> cardsDrawn = cardPile.draw(numberToDraw);
        if (!playerCharacterAssociation.player().city().isBuilt(District.LIBRARY)) {
            Card keptCard = playerCharacterAssociation.player().controller.selectAmong(cardsDrawn);
            cardPile.discard(cardsDrawn.remove(keptCard).toList());
            cardsDrawn = HashSet.of(keptCard);
        }
        playerCharacterAssociation.player().addCardsInHand(cardsDrawn);
    }

    private final int numberToDraw;
}
