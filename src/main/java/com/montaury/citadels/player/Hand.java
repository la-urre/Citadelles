package com.montaury.citadels.player;

import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Hand {
    public static Hand empty() {
        return new Hand(HashSet.empty());
    }

    static Hand with(Set<Card> cards) {
        return new Hand(cards);
    }

    public static Hand with(Card... cards) {
        return with(HashSet.of(cards));
    }

    private Hand(Set<Card> cards) {
        this.cards = cards;
    }

    public void add(Card card) {
        cards = cards.add(card);
    }

    public void add(Set<Card> cards) {
        this.cards = this.cards.addAll(cards);
    }

    public void draw(Card cardToDraw) {
        cards = cards.remove(cardToDraw);
    }

    public void draw(Iterable<Card> cardsToDraw) {
        cards = cards.removeAll(cardsToDraw);
    }

    public int cardsCount() {
        return cards.size();
    }

    public Set<Card> cards() {
        return cards;
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }

    private Set<Card> cards;
}
