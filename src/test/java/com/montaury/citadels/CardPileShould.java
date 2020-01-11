package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static org.assertj.core.api.Assertions.assertThat;

public class CardPileShould {

    @Test
    public void prevent_drawing_if_empty() {
        Option<Card> card = CardPile.empty().draw();

        assertThat(card).isEqualTo(Option.none());
    }

    @Test
    public void allow_drawing_a_card() {
        Option<Card> card = pileWith(Card.BATTLEFIELD_1).draw();

        assertThat(card).isEqualTo(Option.of(Card.BATTLEFIELD_1));
    }

    @Test
    public void place_discarded_cards_at_the_bottom() {
        CardPile cardPile = pileWith(Card.MAP_ROOM);

        cardPile.discard(List.of(Card.BATTLEFIELD_1, Card.CATHEDRAL_1));

        cardPile.draw();
        assertThat(cardPile.draw()).isEqualTo(Option.of(Card.BATTLEFIELD_1));
        assertThat(cardPile.draw()).isEqualTo(Option.of(Card.CATHEDRAL_1));
    }

    @Test
    public void allow_drawing_several_cards_at_once() {
        CardPile cardPile = pileWith(Card.MAP_ROOM, Card.TRADING_POST_2);

        Set<Card> cards = cardPile.draw(2);

        assertThat(cards).containsExactlyInAnyOrder(Card.TRADING_POST_2, Card.MAP_ROOM);
    }

    @Test
    public void allow_drawing_when_requested_card_number_is_greater_than_available() {
        CardPile cardPile = pileWith(Card.MAP_ROOM, Card.CHURCH_2);

        Set<Card> cards = cardPile.draw(4);

        assertThat(cards).containsExactlyInAnyOrder(Card.CHURCH_2, Card.MAP_ROOM);
    }

    @Test
    public void draw_at_top_and_discard_at_bottom_when_swapping_cards() {
        CardPile cardPile = pileWith(Card.MAP_ROOM, Card.TEMPLE_3);

        Set<Card> cards = cardPile.swapWith(List.of(Card.CASTLE_1, Card.MANOR_1));

        assertThat(cards).containsExactlyInAnyOrder(Card.MAP_ROOM, Card.TEMPLE_3);
        assertThat(cardPile.draw(2)).containsExactlyInAnyOrder(Card.CASTLE_1, Card.MANOR_1);
    }
}
