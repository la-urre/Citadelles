package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import io.vavr.collection.List;

public class CardPileFixtures {
    public static CardPile pileWith(Card... cards) {
        return new CardPile(List.of(cards));
    }
}
