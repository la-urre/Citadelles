package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.PlayerFixtures;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameShould {

    @Before
    public void setUp() {
        player = PlayerFixtures.aPlayer();
        game = new Game(List.of(player, PlayerFixtures.aPlayer()));
    }

    @Test
    public void end_when_one_player_has_built_8_districts() {
        buildDistricts(8);

        boolean over = game.isOver();

        assertThat(over).isTrue();
    }

    @Test
    public void end_when_one_player_has_built_more_than_8_districts() {
        buildDistricts(9);

        boolean over = game.isOver();

        assertThat(over).isTrue();
    }

    @Test
    public void continue_while_no_player_has_built_8_districts() {
        buildDistricts(7);

        boolean over = game.isOver();

        assertThat(over).isFalse();
    }

    private void buildDistricts(int number) {
        List.range(0, number).forEach(i -> player.city().buildDistrict(Card.TEMPLE_1));
    }

    private Player player;
    private Game game;
}
