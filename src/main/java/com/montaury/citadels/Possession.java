package com.montaury.citadels;

import com.montaury.citadels.player.Hand;
import com.montaury.citadels.player.Gold;

public class Possession {
    public final Gold gold;
    public final Hand hand;

    public Possession(Gold gold, Hand hand) {
        this.gold = gold;
        this.hand = hand;
    }

    public int goldCoins() {
        return gold.coins();
    }

    public int cardsInHandCount() {
        return hand.cardsCount();
    }
}
