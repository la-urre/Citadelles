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

public class ReceiveGoldCoinActionShould {

    @Before
    public void setUp() {
        action = new ReceiveGoldCoinAction();
    }

    @Test
    public void make_the_player_earn_1_gold_coin() {
        Player player = aPlayer();

        action.execute(associationBetween(player, Character.THIEF), roundAssociations(), CardPile.empty());

        assertThat(player.gold().coins()).isEqualTo(1);
    }

    private ReceiveGoldCoinAction action;
}