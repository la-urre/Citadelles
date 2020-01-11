package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Gold;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.StubPlayerController;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.GoldFixtures.gold;
import static com.montaury.citadels.player.HandFixtures.hand;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class BuildDistrictActionShould {

    @Before
    public void setUp() {
        action = new BuildDistrictAction();
        controller = new StubPlayerController();
        controller.setNextCard(Card.TEMPLE_1);
        emptyPile = CardPile.empty();
    }

    @Test
    public void be_executable_if_player_has_a_buildable_district_in_hand() {
        Player player = aPlayerWith(hand(Card.TEMPLE_1), gold(2));

        boolean executable = action.canExecute(player, roundAssociations(), emptyPile);

        assertThat(executable).isTrue();
    }

    @Test
    public void not_be_executable_if_player_does_not_have_a_buildable_district_in_hand() {
        Player player = aPlayerWith(hand(Card.TEMPLE_1), Gold.empty());

        boolean executable = action.canExecute(player, roundAssociations(), emptyPile);

        assertThat(executable).isFalse();
    }

    @Test
    public void ask_the_player_to_choose_between_buildable_districts_only() {
        Player player = aPlayerWith(hand(Card.CASTLE_1, Card.TEMPLE_1, Card.BATTLEFIELD_1), gold(2), controller);

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), emptyPile);

        assertThat(controller.availableCards).containsExactly(Card.TEMPLE_1);
    }

    @Test
    public void make_the_player_pay_the_construction_cost_of_the_selected_district() {
        Player player = aPlayerWith(hand(Card.CASTLE_1, Card.TEMPLE_1, Card.BATTLEFIELD_1), gold(2), controller);

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), emptyPile);

        assertThat(player.gold().coins()).isEqualTo(1);
    }

    @Test
    public void build_the_district_in_the_player_city() {
        Player player = aPlayerWith(hand(Card.CASTLE_1, Card.TEMPLE_1, Card.BATTLEFIELD_1), gold(2), controller);

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), emptyPile);

        assertThat(player.city().isBuilt(District.TEMPLE)).isTrue();
    }

    @Test
    public void remove_the_card_from_the_player_hand() {
        Player player = aPlayerWith(hand(Card.CASTLE_1, Card.TEMPLE_1, Card.BATTLEFIELD_1), gold(2), controller);

        action.execute(associationBetween(player, Character.BISHOP), roundAssociations(), emptyPile);

        assertThat(player.hand().cards()).doesNotContain(Card.TEMPLE_1);
    }

    private StubPlayerController controller;
    private CardPile emptyPile;
    private BuildDistrictAction action;
}
