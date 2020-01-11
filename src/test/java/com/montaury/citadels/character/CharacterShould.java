package com.montaury.citadels.character;

import com.montaury.citadels.round.action.ActionType;
import com.montaury.citadels.district.DistrictType;
import io.vavr.collection.List;
import org.junit.Test;

import static com.montaury.citadels.character.Character.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CharacterShould {
    @Test
    public void provide_characters_in_order() {
        List<Character> characters = Character.inOrder();

        assertThat(characters).containsExactly(ASSASSIN, THIEF, MAGICIAN, KING, BISHOP, MERCHANT, ARCHITECT, WARLORD);
    }

    @Test
    public void give_the_murder_power_to_the_assassin() {
        assertThat(ASSASSIN.powers()).contains(ActionType.MURDER);
    }

    @Test
    public void give_the_steal_power_to_the_thief() {
        assertThat(THIEF.powers()).contains(ActionType.STEAL);
    }

    @Test
    public void give_the_exchange_hand_power_to_the_magician() {
        assertThat(MAGICIAN.powers()).contains(ActionType.EXCHANGE_HAND_WITH_PLAYER);
    }

    @Test
    public void give_the_exchange_cards_power_to_the_magician() {
        assertThat(MAGICIAN.powers()).contains(ActionType.EXCHANGE_CARDS_WITH_CARD_PILE);
    }

    @Test
    public void give_the_receive_income_power_to_the_king() {
        assertThat(KING.powers()).contains(ActionType.RECEIVE_INCOME);
    }

    @Test
    public void give_the_receive_income_power_to_the_bishop() {
        assertThat(BISHOP.powers()).contains(ActionType.RECEIVE_INCOME);
    }

    @Test
    public void give_the_receive_income_power_to_the_merchant() {
        assertThat(MERCHANT.powers()).contains(ActionType.RECEIVE_INCOME);
    }

    @Test
    public void give_the_receive_gold_coin_power_to_the_merchant() {
        assertThat(MERCHANT.powers()).contains(ActionType.RECEIVE_GOLD_COIN);
    }

    @Test
    public void give_the_pick_two_cards_power_to_the_architect() {
        assertThat(ARCHITECT.powers()).contains(ActionType.PICK_2_CARDS);
    }

    @Test
    public void give_the_build_two_districts_power_to_the_architect() {
        assertThat(ARCHITECT.powers()).containsSubsequence(ActionType.BUILD_DISTRICT, ActionType.BUILD_DISTRICT);
    }

    @Test
    public void give_the_receive_income_power_to_the_warlord() {
        assertThat(WARLORD.powers()).contains(ActionType.RECEIVE_INCOME);
    }

    @Test
    public void give_the_destroy_district_power_to_the_warlord() {
        assertThat(WARLORD.powers()).contains(ActionType.DESTROY_DISTRICT);
    }

    @Test
    public void associate_the_king_with_noble_districts() {
        assertThat(KING.associatedDistrictType()).contains(DistrictType.NOBLE);
    }

    @Test
    public void associate_the_bishop_with_religious_districts() {
        assertThat(BISHOP.associatedDistrictType()).contains(DistrictType.RELIGIOUS);
    }

    @Test
    public void associate_the_merchant_with_trade_districts() {
        assertThat(MERCHANT.associatedDistrictType()).contains(DistrictType.TRADE);
    }

    @Test
    public void associate_the_warlord_with_military_districts() {
        assertThat(WARLORD.associatedDistrictType()).contains(DistrictType.MILITARY);
    }
}