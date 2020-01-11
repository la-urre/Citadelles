package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;

public enum ActionType {
    // BASE ACTIONS
    DRAW_2_CARDS_KEEP_1(new Keep1CardAmongManyAction(2)),
    RECEIVE_2_GOLD_COINS(new Receive2GoldCoinsAction()),
    BUILD_DISTRICT(new BuildDistrictAction()),
    END_ROUND(new EndRoundAction()),

    // CHARACTER ACTIONS
    MURDER(new MurderAction()),
    STEAL(new StealAction()),
    EXCHANGE_HAND_WITH_PLAYER(new ExchangeHandWithPlayerAction()),
    EXCHANGE_CARDS_WITH_CARD_PILE(new ExchangeCardsWithPileAction()),
    RECEIVE_INCOME(new ReceiveIncomeAction()),
    RECEIVE_GOLD_COIN(new ReceiveGoldCoinAction()),
    PICK_2_CARDS(new Draw2CardsAction()),
    DESTROY_DISTRICT(new DestroyDistrictAction()),

    // SPECIAL ACTIONS
    DRAW_3_CARDS_KEEP_1(new Keep1CardAmongManyAction(2)),
    DRAW_3_CARDS_FOR_2_GOLD_COINS(new Draw3CardsFor2GoldCoins()),
    DISCARD_CARD_FOR_2_GOLD_COINS(new DiscardCardFor2GoldCoinsAction());

    ActionType(Action action) {
        this.action = action;
    }

    public boolean canExecute(Player player, GameRoundAssociations associations, CardPile cardPile) {
        return action.canExecute(player, associations, cardPile);
    }

    public void execute(PlayerCharacterAssociation playerCharacterAssociation, GameRoundAssociations associations, CardPile cardPile) {
        action.execute(playerCharacterAssociation, associations, cardPile);
    }

    private final Action action;
}
