package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Gold;
import com.montaury.citadels.player.Hand;
import com.montaury.citadels.score.Score;
import io.vavr.collection.List;
import org.junit.Test;

import static com.montaury.citadels.CityFixtures.cityWith;
import static com.montaury.citadels.CityFixtures.emptyCity;
import static com.montaury.citadels.player.GoldFixtures.gold;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static org.assertj.core.api.Assertions.assertThat;

public class CityShould {

    @Test
    public void score_nil_if_no_district_is_built() {
        City city = emptyCity();

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.nil());
    }

    @Test
    public void score_the_total_building_cost_if_districts_are_built() {
        City city = cityWith(Card.TAVERN_1, Card.LABORATORY, Card.WATCHTOWER_1, Card.MANOR_1);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(10));
    }

    @Test
    public void score_a_bonus_of_3_if_the_5_types_of_districts_are_built() {
        City city = cityWith(Card.TAVERN_1, Card.LABORATORY, Card.WATCHTOWER_1, Card.MANOR_1, Card.TEMPLE_1);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(14));
    }

    @Test
    public void score_a_bonus_of_3_if_4_types_of_districts_and_the_haunted_city_are_built() {
        City city = cityWith(Card.TAVERN_1, Card.LABORATORY, Card.WATCHTOWER_1, Card.MANOR_1, Card.HAUNTED_CITY);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(15));
    }

    @Test
    public void not_score_a_bonus_of_3_if_4_types_of_districts_are_built_and_the_haunted_city_is_the_only_built_special_district() {
        City city = cityWith(Card.TAVERN_1, Card.WATCHTOWER_1, Card.MANOR_1, Card.HAUNTED_CITY);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(7));
    }

    @Test
    public void score_8_if_the_dragon_gate_is_built() {
        City city = cityWith(Card.DRAGON_GATE);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(8));
    }

    @Test
    public void score_8_if_the_university_is_built() {
        City city = cityWith(Card.UNIVERSITY);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(8));
    }

    @Test
    public void score_a_bonus_of_4_if_it_the_first_complete() {
        City city = completeCity();

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(12));
    }

    @Test
    public void score_a_bonus_of_2_if_it_is_complete_but_not_first() {
        Board board = new Board();
        completeCity(board);
        City cityCompletedSecond = completeCity(board);

        Score score = cityCompletedSecond.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(10));
    }

    @Test
    public void score_a_bonus_equal_to_the_gold_coins_if_treasury_is_built() {
        City city = cityWith(Card.TREASURY);

        Score score = city.score(new Possession(gold(2), Hand.empty()));

        assertThat(score).isEqualTo(Score.of(7));
    }

    @Test
    public void score_a_bonus_equal_to_the_number_of_cards_in_hand_if_map_room_is_built() {
        City city = cityWith(Card.MAP_ROOM);

        Score score = city.score(new Possession(Gold.empty(), Hand.with(Card.CASTLE_1, Card.CASTLE_2, Card.CASTLE_3)));

        assertThat(score).isEqualTo(Score.of(8));
    }

    @Test
    public void consider_district_not_built_when_built_and_destroyed() {
        City city = cityWith(Card.MONASTERY_1);
        city.destroyDistrict(Card.MONASTERY_1);

        boolean built = city.isBuilt(District.MONASTERY);

        assertThat(built).isFalse();
    }

    @Test
    public void not_have_destructible_districts_if_empty() {
        City city = emptyCity();

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(aPlayer());

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void not_have_destructible_districts_if_complete() {
        City city = completeCity();

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(aPlayer());

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void not_have_destructible_districts_if_player_cannot_pay_destruction() {
        City city = cityWith(Card.MONASTERY_1);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(aPlayerWith(gold(1)));

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void have_destructible_districts_if_player_can_pay_destruction() {
        City city = cityWith(Card.MONASTERY_1);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(aPlayerWith(gold(2)));

        assertThat(destructibleDistricts.map(DestructibleDistrict::card)).containsExactly(Card.MONASTERY_1);
    }

    @Test
    public void not_have_destructible_districts_if_player_cannot_pay_destruction_because_of_great_wall() {
        City city = cityWith(Card.MONASTERY_1, Card.GREAT_WALL);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(aPlayerWith(gold(2)));

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void be_able_to_destroy_great_wall_if_player_can_pay_destruction() {
        City city = cityWith(Card.GREAT_WALL);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(aPlayerWith(gold(5)));

        assertThat(destructibleDistricts.map(DestructibleDistrict::card)).containsExactly(Card.GREAT_WALL);
    }

    private static City completeCity() {
        City city = emptyCity();
        completeCity(city);
        return city;
    }

    private static City completeCity(Board board) {
        City city = new City(board);
        completeCity(city);
        return city;
    }

    private static void completeCity(City city) {
        List.range(0, 8).forEach(i -> city.buildDistrict(Card.TAVERN_1));
    }

    private static Possession emptyPossession() {
        return new Possession(Gold.empty(), Hand.empty());
    }
}