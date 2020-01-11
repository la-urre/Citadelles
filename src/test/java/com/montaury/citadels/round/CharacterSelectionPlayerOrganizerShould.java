package com.montaury.citadels.round;

import com.montaury.citadels.player.Player;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static org.assertj.core.api.Assertions.assertThat;

public class CharacterSelectionPlayerOrganizerShould {

    @Before
    public void setUp() {
        player1 = aPlayer();
        player2 = aPlayer();
        player3 = aPlayer();
        players = List.of(player1, player2, player3);
    }

    @Test
    public void keep_the_same_order_if_the_first_player_has_the_crown() {
        List<Player> orderedPlayers = new CharacterSelectionPlayerOrganizer().orderCrownFirst(players, player1);

        assertThat(orderedPlayers).isEqualTo(players);
    }

    @Test
    public void rotate_the_players_to_move_the_one_with_the_crown_to_first_position() {
        List<Player> orderedPlayers = new CharacterSelectionPlayerOrganizer().orderCrownFirst(players, player2);

        assertThat(orderedPlayers).isEqualTo(List.of(player2, player3, player1));
    }

    private Player player1;
    private Player player2;
    private Player player3;
    private List<Player> players;
}
