package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Gold;
import com.montaury.citadels.player.Hand;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.PlayerFixtures;
import com.montaury.citadels.score.Score;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.GoldFixtures.gold;
import static org.assertj.core.api.Assertions.assertThat;

public class CityShould {

    @Before
    public void setUp() {
        board = new Board();
        city = new City(board);
    }

    @Test
    public void score_nil_if_no_district_is_built() {
        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.nil());
    }

    @Test
    public void score_the_total_building_cost_if_districts_are_built() {
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.LABORATORY);
        city.buildDistrict(Card.WATCHTOWER_1);
        city.buildDistrict(Card.MANOR_1);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(10));
    }

    @Test
    public void score_a_bonus_of_3_if_the_5_types_of_districts_are_built() {
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.LABORATORY);
        city.buildDistrict(Card.WATCHTOWER_1);
        city.buildDistrict(Card.MANOR_1);
        city.buildDistrict(Card.TEMPLE_1);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(14));
    }

    @Test
    public void score_a_bonus_of_3_if_4_types_of_districts_and_the_haunted_city_are_built() {
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.LABORATORY);
        city.buildDistrict(Card.WATCHTOWER_1);
        city.buildDistrict(Card.MANOR_1);
        city.buildDistrict(Card.HAUNTED_CITY);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(15));
    }

    @Test
    public void not_score_a_bonus_of_3_if_4_types_of_districts_are_built_and_the_haunted_city_is_the_only_built_special_district() {
        city.buildDistrict(Card.TAVERN_1);
        city.buildDistrict(Card.WATCHTOWER_1);
        city.buildDistrict(Card.MANOR_1);
        city.buildDistrict(Card.HAUNTED_CITY);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(7));
    }

    @Test
    public void score_8_if_the_dragon_gate_is_built() {
        city.buildDistrict(Card.DRAGON_GATE);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(8));
    }

    @Test
    public void score_8_if_the_university_is_built() {
        city.buildDistrict(Card.UNIVERSITY);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(8));
    }

    @Test
    public void score_a_bonus_of_4_if_it_the_first_complete() {
        completeCity(city);

        Score score = city.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(12));
    }

    @Test
    public void score_a_bonus_of_2_if_it_is_complete_but_not_first() {
        City city2 = new City(board);
        completeCity(city);
        completeCity(city2);

        Score score = city2.score(emptyPossession());

        assertThat(score).isEqualTo(Score.of(10));
    }

    @Test
    public void score_a_bonus_equal_to_the_gold_coins_if_treasury_is_built() {
        city.buildDistrict(Card.TREASURY);

        Score score = city.score(new Possession(gold(2), Hand.empty()));

        assertThat(score).isEqualTo(Score.of(7));
    }

    @Test
    public void score_a_bonus_equal_to_the_number_of_cards_in_hand_if_map_room_is_built() {
        city.buildDistrict(Card.MAP_ROOM);

        Score score = city.score(new Possession(Gold.empty(), Hand.with(Card.CASTLE_1, Card.CASTLE_2, Card.CASTLE_3, Card.CASTLE_4)));

        assertThat(score).isEqualTo(Score.of(9));
    }

    @Test
    public void consider_district_not_built_when_built_and_destroyed() {
        city.buildDistrict(Card.MONASTERY_1);
        city.destroyDistrict(Card.MONASTERY_1);

        boolean built = city.isBuilt(District.MONASTERY);

        assertThat(built).isFalse();
    }

    @Test
    public void not_have_destructible_districts_if_empty() {
        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(PlayerFixtures.aPlayer());

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void not_have_destructible_districts_if_complete() {
        completeCity(city);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(PlayerFixtures.aPlayer());

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void not_have_destructible_districts_if_player_cannot_pay_destruction() {
        Player player = PlayerFixtures.aPlayer();
        player.earn(1);
        city.buildDistrict(Card.MONASTERY_1);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(player);

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void have_destructible_districts_if_player_can_pay_destruction() {
        Player player = PlayerFixtures.aPlayer();
        player.earn(2);
        city.buildDistrict(Card.MONASTERY_1);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(player);

        assertThat(destructibleDistricts.map(DestructibleDistrict::card)).containsExactly(Card.MONASTERY_1);
    }

    @Test
    public void not_have_destructible_districts_if_player_cannot_pay_destruction_because_of_great_wall() {
        Player player = PlayerFixtures.aPlayer();
        player.earn(2);
        city.buildDistrict(Card.MONASTERY_1);
        city.buildDistrict(Card.GREAT_WALL);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(player);

        assertThat(destructibleDistricts).isEmpty();
    }

    @Test
    public void be_able_to_destroy_great_wall_if_player_can_pay_destruction() {
        Player player = PlayerFixtures.aPlayer();
        player.earn(5);
        city.buildDistrict(Card.GREAT_WALL);

        List<DestructibleDistrict> destructibleDistricts = city.districtsDestructibleBy(player);

        assertThat(destructibleDistricts.map(DestructibleDistrict::card)).containsExactly(Card.GREAT_WALL);
    }

    private void completeCity(City city) {
        List.range(0, 8).forEach(i -> city.buildDistrict(Card.TAVERN_1));
    }

    private static Possession emptyPossession() {
        return new Possession(Gold.empty(), Hand.empty());
    }

    private City city;
    private Board board;
}