package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static com.montaury.citadels.player.GoldFixtures.gold;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class Draw3CardsFor2GoldCoinsShould {

    @Before
    public void setUp() {
        cardPile = pileWith(Card.CASTLE_1, Card.CATHEDRAL_1, Card.BATTLEFIELD_1);
        action = new Draw3CardsFor2GoldCoins();
    }

    @Test
    public void be_executable_if_pile_has_enough_cards_and_player_has_enough_gold() {
        boolean executable = action.canExecute(aPlayerWith(gold(2)), roundAssociations(), cardPile);

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_pile_does_not_have_enough_cards() {
        boolean executable = action.canExecute(aPlayer(), roundAssociations(), pileWith(Card.CATHEDRAL_1, Card.GRAVEYARD));

        assertThat(executable).isFalse();
    }

    @Test
    public void not_be_executable_if_player_does_not_have_enough_gold() {
        boolean executable = action.canExecute(aPlayerWith(gold(1)), roundAssociations(), cardPile);

        assertThat(executable).isFalse();
    }

    @Test
    public void draw_3_cards_in_the_pile() {
        action.execute(associationBetween(aPlayer(), Character.BISHOP), roundAssociations(), cardPile);

        assertThat(cardPile.draw()).isEmpty();
    }

    @Test
    public void add_3_cards_in_player_hand() {
        Player player = aPlayer();

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), cardPile);

        assertThat(player.hand().cardsCount()).isEqualTo(3);
    }

    @Test
    public void spend_2_gold_coins_from_player() {
        Player player = aPlayerWith(gold(2));

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), cardPile);

        assertThat(player.gold().coins()).isEqualTo(0);
    }

    private CardPile cardPile;
    private Draw3CardsFor2GoldCoins action;
}
