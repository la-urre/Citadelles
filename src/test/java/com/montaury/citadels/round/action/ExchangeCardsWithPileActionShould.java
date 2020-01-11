package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeCardsWithPileActionShould {

    @Before
    public void setUp() {
        controller = new StubPlayerController();
        player = aPlayer(controller);
        action = new ExchangeCardsWithPileAction();
        pileWith3Cards = pileWith(Card.BATTLEFIELD_1, Card.PALACE_2, Card.MANOR_1);
    }

    @Test
    public void be_executable_if_player_hand_is_not_empty() {
        player.addCardInHand(Card.CASTLE_1);

        boolean executable = action.canExecute(player, roundAssociations(), pileWith3Cards);

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_player_hand_is_empty() {
        boolean executable = action.canExecute(player, roundAssociations(), pileWith3Cards);

        assertThat(executable).isFalse();
    }

    @Test
    public void not_be_executable_if_card_pile_is_empty() {
        player.addCardInHand(Card.CASTLE_1);

        boolean executable = action.canExecute(player, roundAssociations(), CardPile.empty());

        assertThat(executable).isFalse();
    }

    @Test
    public void ask_the_player_which_cards_to_swap() {
        controller.setNextCards(HashSet.of(Card.CATHEDRAL_1));

        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith3Cards);

        assertThat(controller.nextCardToSwap).containsExactly();
    }

    @Test
    public void swap_the_selected_cards_between_hand_and_pile() {
        player.addCardsInHand(HashSet.of(Card.CATHEDRAL_1, Card.CASTLE_1));
        controller.setNextCards(HashSet.of(Card.CATHEDRAL_1));

        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith3Cards);

        assertThat(player.hand().cards()).containsExactlyInAnyOrder(Card.CASTLE_1, Card.BATTLEFIELD_1);
        assertThat(pileWith3Cards.draw(3)).containsExactlyInAnyOrder(Card.PALACE_2, Card.MANOR_1, Card.CATHEDRAL_1);
    }

    private StubPlayerController controller;
    private Player player;
    private ExchangeCardsWithPileAction action;
    private CardPile pileWith3Cards;
}