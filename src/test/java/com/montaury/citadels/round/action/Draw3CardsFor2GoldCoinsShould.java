package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.PlayerFixtures;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class Draw3CardsFor2GoldCoinsShould {

    @Before
    public void setUp() {
        player = PlayerFixtures.aPlayer();
        association = associationBetween(player, Character.BISHOP);
        cardPile = pileWith(Card.CASTLE_1, Card.CATHEDRAL_1, Card.BATTLEFIELD_1);
        action = new Draw3CardsFor2GoldCoins();
    }

    @Test
    public void be_executable_if_pile_has_enough_cards_and_player_has_enough_gold() {
        player.earn(2);

        boolean executable = action.canExecute(player, roundAssociations(), cardPile);

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_pile_does_not_have_enough_cards() {
        boolean executable = action.canExecute(player, roundAssociations(), pileWith(Card.CATHEDRAL_1, Card.GRAVEYARD));

        assertThat(executable).isFalse();
    }

    @Test
    public void not_be_executable_if_player_does_not_have_enough_gold() {
        player.earn(1);

        boolean executable = action.canExecute(player, roundAssociations(), cardPile);

        assertThat(executable).isFalse();
    }

    @Test
    public void draw_3_cards_in_the_pile() {
        action.execute(association, roundAssociations(), cardPile);

        assertThat(cardPile.draw()).isEmpty();
    }

    @Test
    public void add_3_cards_in_player_hand() {
        action.execute(association, roundAssociations(), cardPile);

        assertThat(player.hand().cardsCount()).isEqualTo(3);
    }

    @Test
    public void spend_2_gold_coins_from_player() {
        player.earn(2);

        action.execute(association, roundAssociations(), cardPile);

        assertThat(player.gold().coins()).isEqualTo(0);
    }

    private Player player;
    private PlayerCharacterAssociation association;
    private CardPile cardPile;
    private Draw3CardsFor2GoldCoins action;
}
