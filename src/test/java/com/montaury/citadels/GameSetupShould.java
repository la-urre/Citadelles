package com.montaury.citadels;

import com.montaury.citadels.player.Player;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWithAge;
import static org.assertj.core.api.Assertions.assertThat;

public class GameSetupShould {

    @Before
    public void setUp() {
        player1 = aPlayer();
        player2 = aPlayerWithAge(player1.age() + 1);
        gameSetup = new GameSetup();
    }

    @Test
    public void give_2_gold_coins_to_each_player() {
        gameSetup.setup(CardPile.empty(), List.of(player1, player2));

        assertThat(player1.gold().coins()).isEqualTo(2);
        assertThat(player2.gold().coins()).isEqualTo(2);
    }

    @Test
    public void give_4_cards_to_each_player() {
        gameSetup.setup(CardPile.completeShuffled(), List.of(player1, player2));

        assertThat(player1.hand().cards()).hasSize(4);
        assertThat(player2.hand().cards()).hasSize(4);
    }

    @Test
    public void give_the_crown_to_the_oldest_player() {
        Player playerWithCrown = gameSetup.setup(CardPile.empty(), List.of(player1, player2));

        assertThat(playerWithCrown).isEqualTo(player2);
    }

    @Test
    public void give_the_crown_to_the_first_player_if_many_have_the_same_age() {
        Player player3 = aPlayerWithAge(player1.age());

        Player playerWithCrown = gameSetup.setup(CardPile.empty(), List.of(player3, player1));

        assertThat(playerWithCrown).isEqualTo(player3);
    }

    private GameSetup gameSetup;
    private Player player1;
    private Player player2;
}
