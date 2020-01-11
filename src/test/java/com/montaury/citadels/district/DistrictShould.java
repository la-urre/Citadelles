package com.montaury.citadels.district;

import com.montaury.citadels.character.Character;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DistrictShould {

    @Test
    public void not_be_destructible_if_it_is_the_keep() {
        boolean isDestructible = District.KEEP.isDestructible();

        assertThat(isDestructible).isFalse();
    }

    @Test
    public void bring_1_gold_coin_when_district_type_matches_character_color() {
        int income = District.CATHEDRAL.incomeFor(Character.BISHOP);

        assertThat(income).isEqualTo(1);
    }

    @Test
    public void not_bring_gold_coin_when_district_type_does_not_match_character_color() {
        int income = District.CATHEDRAL.incomeFor(Character.MERCHANT);

        assertThat(income).isEqualTo(0);
    }

    @Test
    public void bring_1_gold_coin_for_magic_school_when_character_has_a_color() {
        int income = District.MAGIC_SCHOOL.incomeFor(Character.MERCHANT);

        assertThat(income).isEqualTo(1);
    }

}