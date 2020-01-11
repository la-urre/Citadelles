package com.montaury.citadels.player;

import com.montaury.citadels.district.Card;

public class HandFixtures {
    public static Hand hand(Card... cards) {
        return Hand.with(cards);
    }
}
