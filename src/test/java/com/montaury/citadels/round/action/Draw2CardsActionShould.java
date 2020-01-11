package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static org.assertj.core.api.Assertions.assertThat;

public class Draw2CardsActionShould {

    @Before
    public void setUp() {
        action = new Draw2CardsAction();
    }

    @Test
    public void be_executable_if_card_pile_has_enough_cards() {
        boolean executable = action.canExecute(aPlayer(), roundAssociations(), pileWith(Card.CASTLE_1, Card.BATTLEFIELD_1));

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_card_pile_does_not_have_enough_cards() {
        boolean executable = action.canExecute(aPlayer(), roundAssociations(), pileWith(Card.CATHEDRAL_1));

        assertThat(executable).isFalse();
    }

    @Test
    public void give_2_cards_to_player() {
        Player player = aPlayer();

        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith(Card.CASTLE_1, Card.BATTLEFIELD_1));

        assertThat(player.hand().cards()).containsExactlyInAnyOrder(Card.CASTLE_1, Card.BATTLEFIELD_1);
    }

    @Test
    public void draw_2_cards_from_pile() {
        CardPile cardPile = pileWith(Card.CASTLE_1, Card.BATTLEFIELD_1);

        action.execute(associationBetween(aPlayer(), Character.KING), roundAssociations(), cardPile);

        assertThat(cardPile.draw()).isEmpty();
    }

    private Draw2CardsAction action;
}