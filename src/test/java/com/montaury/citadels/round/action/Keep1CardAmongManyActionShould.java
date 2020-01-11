package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.StubPlayerController;
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

public class Keep1CardAmongManyActionShould {

    @Before
    public void setUp() {
        controller = new StubPlayerController();
        player = aPlayer(controller);
        action = new Keep1CardAmongManyAction(2);
        pileWith2Cards = pileWith(Card.MANOR_1, Card.PALACE_2);
    }

    @Test
    public void be_executable_if_pile_has_enough_cards() {
        boolean executable = action.canExecute(player, roundAssociations(), pileWith2Cards);

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_pile_does_not_have_enough_cards() {
        boolean executable = action.canExecute(player, roundAssociations(), pileWith(Card.CATHEDRAL_1));

        assertThat(executable).isFalse();
    }

    @Test
    public void ask_the_player_to_choose_between_the_2_drawn_cards() {
        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith2Cards);

        assertThat(controller.availableCards).containsExactlyInAnyOrder(Card.MANOR_1, Card.PALACE_2);
    }

    @Test
    public void add_the_chosen_card_to_player_hand() {
        controller.setNextCard(Card.PALACE_2);

        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith2Cards);

        assertThat(player.hand().cards()).containsExactly(Card.PALACE_2);
    }

    @Test
    public void discard_the_rejected_card_under_the_pile() {
        controller.setNextCard(Card.PALACE_2);

        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith2Cards);

        assertThat(pileWith2Cards.draw()).containsExactly(Card.MANOR_1);
    }

    @Test
    public void keep_all_drawn_cards_when_player_has_library() {
        player.city().buildDistrict(Card.LIBRARY);

        action.execute(associationBetween(player, Character.KING), roundAssociations(), pileWith2Cards);

        assertThat(controller.availableCards).isEmpty();
        assertThat(pileWith2Cards.draw()).isEmpty();
        assertThat(player.hand().cards()).contains(Card.PALACE_2, Card.MANOR_1);
    }

    private StubPlayerController controller;
    private Player player;
    private Keep1CardAmongManyAction action;
    private CardPile pileWith2Cards;
}
