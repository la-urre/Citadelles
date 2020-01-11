package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscardCardFor2GoldCoinsActionShould {

    @Before
    public void setUp() {
        action = new DiscardCardFor2GoldCoinsAction();
        controller = new StubPlayerController();
        player = aPlayer(controller);
    }

    @Test
    public void be_executable_if_player_hand_is_not_empty() {
        player.addCardInHand(Card.CASTLE_1);

        boolean executable = action.canExecute(player, roundAssociations(), CardPile.empty());

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_player_hand_is_empty() {
        boolean executable = action.canExecute(player, roundAssociations(), CardPile.empty());

        assertThat(executable).isFalse();
    }

    @Test
    public void ask_the_player_which_card_to_discard() {
        player.addCardInHand(Card.CASTLE_1);

        action.execute(associationBetween(player, Character.KING), roundAssociations(), CardPile.empty());

        assertThat(controller.availableCards).containsExactly(Card.CASTLE_1);
    }

    @Test
    public void remove_chosen_card_from_player_hand() {
        player.addCardInHand(Card.CASTLE_1);
        controller.setNextCard(Card.CASTLE_1);

        action.execute(associationBetween(player, Character.KING), roundAssociations(), CardPile.empty());

        assertThat(player.hand().cardsCount()).isEqualTo(0);
    }

    @Test
    public void discard_the_chosen_card_at_the_bottom_of_the_pile() {
        player.addCardInHand(Card.CASTLE_1);
        controller.setNextCard(Card.CASTLE_1);
        CardPile cardPile = CardPile.empty();

        action.execute(associationBetween(player, Character.KING), roundAssociations(), cardPile);

        assertThat(cardPile.draw()).contains(Card.CASTLE_1);
    }

    @Test
    public void make_the_player_earn_2_gold_coins() {
        player.addCardInHand(Card.CASTLE_1);
        controller.setNextCard(Card.CASTLE_1);

        action.execute(associationBetween(player, Character.KING), roundAssociations(), CardPile.empty());

        assertThat(player.gold().coins()).isEqualTo(2);
    }

    private DiscardCardFor2GoldCoinsAction action;
    private StubPlayerController controller;
    private Player player;
}