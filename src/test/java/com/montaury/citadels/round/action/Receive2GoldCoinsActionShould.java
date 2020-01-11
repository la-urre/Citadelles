package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.player.Player;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class Receive2GoldCoinsActionShould {

    @Before
    public void setUp() {
        action = new Receive2GoldCoinsAction();
    }

    @Test
    public void make_the_player_earn_2_gold_coins() {
        Player player = aPlayer();

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), CardPile.empty());

        assertThat(player.gold().coins()).isEqualTo(2);
    }

    private Receive2GoldCoinsAction action;
}
