package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.round.GameRoundAssociations;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CityFixtures.cityWith;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerHavingBuilt;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class DestroyDistrictActionShould {

    @Before
    public void setUp() {
        action = new DestroyDistrictAction();
    }

    @Test
    public void not_be_executable_if_there_is_no_district_destructible_by_the_player() {
        StubPlayerController player1Controller = new StubPlayerController();
        Player player1 = aPlayer(player1Controller);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(aPlayer(), Character.MAGICIAN),
                associationBetween(aPlayer(), Character.ARCHITECT)
        );

        boolean executable = action.canExecute(player1, associations, CardPile.empty());

        assertThat(executable).isFalse();
    }

    @Test
    public void be_executable_if_there_is_a_district_destructible_by_the_player() {
        StubPlayerController player1Controller = new StubPlayerController();
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(aPlayer(), Character.MAGICIAN),
                associationBetween(aPlayerHavingBuilt(Card.CASTLE_1), Character.ARCHITECT)
        );

        boolean executable = action.canExecute(player1, associations, CardPile.empty());

        assertThat(executable).isTrue();
    }

    @Test
    public void ask_the_player_to_choose_between_players_to_whom_he_can_destroy_districts() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.TAVERN_1);
        Player player1 = aPlayer(player1Controller);
        Player player2 = aPlayer();
        Player player3 = aPlayerHavingBuilt(Card.TAVERN_1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.MAGICIAN),
                associationBetween(player3, Character.ARCHITECT)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player1Controller.destructibleDistricts.get(player1)).contains(List.empty());
        assertThat(player1Controller.destructibleDistricts.get(player2)).contains(List.empty());
        assertThat(player1Controller.destructibleDistricts.get(player3).get().map(DestructibleDistrict::card)).contains(Card.TAVERN_1);
    }

    @Test
    public void not_include_districts_from_bishop_if_it_is_alive() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.TAVERN_1);
        Player player1 = aPlayer(player1Controller);
        Player player2 = aPlayer();
        Player player3 = aPlayerHavingBuilt(Card.TAVERN_1);
        Player player4 = aPlayerHavingBuilt(Card.TAVERN_2);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.MAGICIAN),
                associationBetween(player3, Character.ARCHITECT),
                associationBetween(player4, Character.BISHOP)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player1Controller.destructibleDistricts.get(player4)).isEmpty();
    }

    @Test
    public void include_districts_from_bishop_if_it_is_murdered() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.TAVERN_1);
        Player player1 = aPlayer(player1Controller);
        Player player2 = aPlayer();
        Player player3 = aPlayer();
        Player player4 = aPlayerHavingBuilt(Card.TAVERN_1);

        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.ASSASSIN),
                associationBetween(player3, Character.ARCHITECT),
                associationBetween(player4, Character.BISHOP)
        );
        associations.murder(Character.BISHOP);

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        Seq<Card> destructibleDistricts = player1Controller.destructibleDistricts.flatMap(Tuple2::_2).map(DestructibleDistrict::card);
        assertThat(destructibleDistricts).contains(Card.TAVERN_1);
    }

    @Test
    public void destroy_the_chosen_district_from_the_city() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        Player player2 = aPlayerHavingBuilt(Card.CASTLE_1);

        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.ASSASSIN)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player2.city().isBuilt(District.CASTLE)).isFalse();
    }

    @Test
    public void make_the_player_spend_the_construction_cost_minus_1() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(aPlayerHavingBuilt(Card.CASTLE_1), Character.ASSASSIN)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player1.gold().coins()).isEqualTo(0);
    }

    @Test
    public void discard_the_destroyed_district_under_the_pile_if_no_one_takes_it() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(aPlayerHavingBuilt(Card.CASTLE_1), Character.ASSASSIN)
        );
        CardPile cardPile = CardPile.empty();

        action.execute(associationBetween(player1, Character.WARLORD), associations, cardPile);

        assertThat(cardPile.draw()).containsExactly(Card.CASTLE_1);
    }

    @Test
    public void propose_the_destroyed_district_to_the_player_with_graveyard_if_having_1_gold_coin() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        Player player2 = aPlayerHavingBuilt(Card.CASTLE_1, Card.BATTLEFIELD_1);
        StubPlayerController player3Controller = new StubPlayerController();
        Player player3 = new Player("Titi", 12, cityWith(Card.GRAVEYARD), player3Controller);
        player3.earn(1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.ASSASSIN),
                associationBetween(player3, Character.MERCHANT)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player3Controller.proposedCard).contains(Card.CASTLE_1);
    }

    @Test
    public void not_propose_the_destroyed_district_to_the_player_with_graveyard_if_not_having_1_gold_coin() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        Player player2 = aPlayerHavingBuilt(Card.CASTLE_1, Card.BATTLEFIELD_1);
        StubPlayerController player3Controller = new StubPlayerController();
        Player player3 = new Player("Titi", 12, cityWith(Card.GRAVEYARD), player3Controller);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.ASSASSIN),
                associationBetween(player3, Character.MERCHANT)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player3Controller.proposedCard).isEmpty();
    }

    @Test
    public void not_propose_the_destroyed_district_to_the_warlord_player_even_if_having_graveyard_and_1_gold_coin() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = new Player("Toto", 12, cityWith(Card.GRAVEYARD), player1Controller);
        player1.earn(4);
        Player player2 = aPlayerHavingBuilt(Card.CASTLE_1, Card.BATTLEFIELD_1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.ASSASSIN)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player1Controller.proposedCard).isEmpty();
    }

    @Test
    public void add_destroyed_district_in_hand_of_player_having_graveyard_and_willing_to_pay() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCard(Card.CASTLE_1);
        Player player1 = aPlayer(player1Controller);
        player1.earn(3);
        Player player2 = aPlayerHavingBuilt(Card.CASTLE_1, Card.BATTLEFIELD_1);
        StubPlayerController player3Controller = new StubPlayerController();
        player3Controller.acceptNextCard();
        Player player3 = new Player("Titi", 12, cityWith(Card.GRAVEYARD), player3Controller);
        player3.earn(1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.ASSASSIN),
                associationBetween(player3, Character.MERCHANT)
        );

        action.execute(associationBetween(player1, Character.WARLORD), associations, CardPile.empty());

        assertThat(player3.gold().coins()).isEqualTo(0);
        assertThat(player3.hand().cards()).contains(Card.CASTLE_1);
    }

    private DestroyDistrictAction action;
}