package com.montaury.citadels;

import com.montaury.citadels.player.Player;
import io.vavr.collection.List;

public class GameSetup {
    private static final int INITIAL_GOLD_COINS = 2;
    private static final int INITIAL_CARDS_NUMBER = 4;

    public Player setup(CardPile cardPile, List<Player> players) {
        players.forEach(player -> {
            player.earn(INITIAL_GOLD_COINS);
            player.addCardsInHand(cardPile.draw(INITIAL_CARDS_NUMBER));
        });
        return players.maxBy(Player::age).get();
    }
}
