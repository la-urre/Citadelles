package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.round.GameRoundAssociations;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.HandFixtures.hand;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeHandWithPlayerActionShould {

    @Before
    public void setUp() {
        action = new ExchangeHandWithPlayerAction();
    }

    @Test
    public void ask_the_player_which_player_to_exchange_with() {
        StubPlayerController controller = new StubPlayerController();
        Player player1 = aPlayer(controller);
        Player player2 = aPlayer();
        controller.setNextPlayer(player2);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.MAGICIAN),
                associationBetween(player2, Character.KING)
        );

        action.execute(associationBetween(player1, Character.KING), associations, CardPile.empty());

        assertThat(controller.nextPlayersForCardSwap).containsExactly(player2);
    }

    @Test
    public void swap_the_hands_of_the_2_players() {
        StubPlayerController controller = new StubPlayerController();
        Player player1 = aPlayerWith(hand(Card.CATHEDRAL_1, Card.BATTLEFIELD_1), controller);
        Player player2 = aPlayerWith(hand(Card.TEMPLE_1, Card.DOCKS_1));
        controller.setNextPlayer(player2);

        action.execute(associationBetween(player1, Character.KING), roundAssociations(), CardPile.empty());

        assertThat(player1.hand().cards()).containsExactlyInAnyOrder(Card.TEMPLE_1, Card.DOCKS_1);
        assertThat(player2.hand().cards()).containsExactlyInAnyOrder(Card.CATHEDRAL_1, Card.BATTLEFIELD_1);
    }

    private ExchangeHandWithPlayerAction action;
}