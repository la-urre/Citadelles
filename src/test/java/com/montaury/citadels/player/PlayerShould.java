package com.montaury.citadels.player;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static com.montaury.citadels.player.GoldFixtures.gold;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerShould {

    @Test
    public void be_able_to_build_a_district_when_having_enough_gold() {
        Player player = aPlayerWith(gold(7));
        player.addCardInHand(Card.PALACE_1);

        Set<Card> buildableDistricts = player.buildableDistrictsInHand();

        assertThat(buildableDistricts).containsExactly(Card.PALACE_1);
    }

    @Test
    public void not_be_able_to_build_a_district_when_not_having_enough_gold() {
        Player player = aPlayerWith(gold(4));
        player.addCardInHand(Card.PALACE_1);

        Set<Card> buildableDistricts = player.buildableDistrictsInHand();

        assertThat(buildableDistricts).isEmpty();
    }

    @Test
    public void not_be_able_to_build_a_district_if_already_built() {
        Player player = aPlayerWith(gold(7));
        player.addCardsInHand(HashSet.of(Card.PALACE_1, Card.PALACE_2));
        player.buildDistrict(Card.PALACE_1);

        Set<Card> buildableDistricts = player.buildableDistrictsInHand();

        assertThat(buildableDistricts).isEmpty();
    }

    @Test
    public void remove_card_from_hand_when_building_a_district() {
        Player player = aPlayerWith(gold(2));
        player.addCardInHand(Card.CHURCH_1);

        player.buildDistrict(Card.CHURCH_1);

        assertThat(player.hand().cards()).isEmpty();
    }

    @Test
    public void spend_gold_when_building_a_district() {
        Player player = aPlayerWith(gold(2));
        player.addCardInHand(Card.CHURCH_1);

        player.buildDistrict(Card.CHURCH_1);

        assertThat(player.gold().coins()).isEqualTo(0);
    }

    @Test
    public void place_in_the_city_when_building_a_district() {
        Player player = aPlayerWith(gold(2));
        player.addCardInHand(Card.CHURCH_1);

        player.buildDistrict(Card.CHURCH_1);

        assertThat(player.city().districts()).containsExactly(District.CHURCH);
    }

    @Test
    public void change_hands_when_swapping_with_another_player() {
        Player player = aPlayerWith(gold(2));
        Player player2 = PlayerFixtures.aPlayer();
        player.addCardInHand(Card.CASTLE_1);
        player2.addCardInHand(Card.BATTLEFIELD_1);
        player2.addCardInHand(Card.GRAVEYARD);

        player.exchangeHandWith(player2);

        assertThat(player.hand().cards()).containsExactlyInAnyOrder(Card.BATTLEFIELD_1, Card.GRAVEYARD);
        assertThat(player2.hand().cards()).containsExactly(Card.CASTLE_1);
    }

    @Test
    public void transfer_gold_of_another_player_when_stealing() {
        Player player = aPlayerWith(gold(2));
        Player player2 = aPlayerWith(gold(10));

        player.steal(player2);

        assertThat(player.gold().coins()).isEqualTo(12);
        assertThat(player2.gold().coins()).isEqualTo(0);
    }

    @Test
    public void change_pile_and_hand_when_swapping_cards() {
        Player player = aPlayerWith(gold(2));
        player.addCardsInHand(HashSet.of(Card.CATHEDRAL_1, Card.TEMPLE_1, Card.BATTLEFIELD_2));
        CardPile cardPile = pileWith(Card.CASTLE_1, Card.DOCKS_1);

        player.swapCards(List.of(Card.TEMPLE_1, Card.CATHEDRAL_1), cardPile);

        assertThat(player.hand().cards()).containsExactlyInAnyOrder(Card.BATTLEFIELD_2, Card.CASTLE_1, Card.DOCKS_1);
        assertThat(cardPile.draw(2)).containsExactlyInAnyOrder(Card.TEMPLE_1, Card.CATHEDRAL_1);
    }
}