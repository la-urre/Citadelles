package com.montaury.citadels.round;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.collection.List;
import org.junit.Test;

import static com.montaury.citadels.CardPileFixtures.pileWith;
import static com.montaury.citadels.CityFixtures.cityWith;
import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class GameRoundAssociationsShould {

    @Test
    public void give_turn_to_players_respecting_characters_order() {
        StubPlayerController controller = new StubPlayerController();
        controller.setNextActions(List.of(ActionType.RECEIVE_2_GOLD_COINS, ActionType.END_ROUND, ActionType.RECEIVE_2_GOLD_COINS, ActionType.END_ROUND));
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(controller), Character.WARLORD),
                associationBetween(aPlayer(controller), Character.ASSASSIN)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(controller.availableActions.get(1))
                .containsExactlyInAnyOrder(ActionType.MURDER, ActionType.END_ROUND);
        assertThat(controller.availableActions.get(3))
                .containsExactlyInAnyOrder(ActionType.RECEIVE_INCOME, ActionType.END_ROUND);
    }

    @Test
    public void ask_the_player_to_choose_the_first_action() {
        StubPlayerController stubPlayerController = new StubPlayerController();
        stubPlayerController.setNextAction(ActionType.DRAW_2_CARDS_KEEP_1);
        stubPlayerController.setNextCard(Card.CASTLE_1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(stubPlayerController), Character.THIEF)
        );

        associations.playTurnsInOrder(pileWith(Card.CASTLE_1, Card.WATCHTOWER_3));

        assertThat(stubPlayerController.availableActions.get(0))
                .containsExactlyInAnyOrder(ActionType.DRAW_2_CARDS_KEEP_1, ActionType.RECEIVE_2_GOLD_COINS);
    }

    @Test
    public void not_propose_a_not_executable_action() {
        StubPlayerController stubPlayerController = new StubPlayerController();
        stubPlayerController.setNextActions(List.of(ActionType.RECEIVE_2_GOLD_COINS, ActionType.END_ROUND));
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(stubPlayerController), Character.THIEF)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(stubPlayerController.availableActions.get(0)).doesNotContain(ActionType.DRAW_2_CARDS_KEEP_1);
    }

    @Test
    public void let_the_player_end_the_turn() {
        StubPlayerController stubPlayerController = new StubPlayerController();
        stubPlayerController.setNextActions(List.of(ActionType.RECEIVE_2_GOLD_COINS, ActionType.END_ROUND));
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(stubPlayerController), Character.THIEF)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(stubPlayerController.availableActions.size()).isEqualTo(2);
    }

    @Test
    public void ask_the_player_which_power_to_use() {
        StubPlayerController stubPlayerController = new StubPlayerController();
        stubPlayerController.setNextActions(List.of(ActionType.RECEIVE_2_GOLD_COINS));
        Player player = aPlayer(stubPlayerController);
        player.earn(1);
        player.addCardInHand(Card.WATCHTOWER_1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player, Character.THIEF)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(stubPlayerController.availableActions.get(1)).containsExactlyInAnyOrder(ActionType.BUILD_DISTRICT, ActionType.END_ROUND, ActionType.STEAL);
    }

    @Test
    public void not_propose_an_action_already_executed() {
        StubPlayerController stubPlayerController = new StubPlayerController();
        stubPlayerController.setNextActions(List.of(ActionType.RECEIVE_2_GOLD_COINS, ActionType.RECEIVE_INCOME));
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(stubPlayerController), Character.KING)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(stubPlayerController.availableActions.get(2)).doesNotContain(ActionType.RECEIVE_INCOME);
    }

    @Test
    public void not_propose_a_mandatory_action_at_second_choice() {
        StubPlayerController stubPlayerController = new StubPlayerController();
        stubPlayerController.setNextAction(ActionType.RECEIVE_2_GOLD_COINS);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(stubPlayerController), Character.KING)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(stubPlayerController.availableActions.get(1)).doesNotContain(ActionType.RECEIVE_2_GOLD_COINS, ActionType.DRAW_2_CARDS_KEEP_1);
    }

    @Test
    public void give_turn_to_murdered() {
        StubPlayerController player2Controller = new StubPlayerController();
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(), Character.ASSASSIN),
                associationBetween(aPlayer(player2Controller), Character.BISHOP)
        );
        associations.murder(Character.BISHOP);

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(player2Controller.availableActions).isEmpty();
    }

    @Test
    public void propose_an_extra_action_if_the_player_has_built_smithy() {
        StubPlayerController playerController = new StubPlayerController();
        Player player = new Player("Toto", 12, cityWith(Card.SMITHY), playerController);
        player.earn(2);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player, Character.MAGICIAN),
                associationBetween(aPlayer(), Character.BISHOP)
        );

        associations.playTurnsInOrder(pileWith(Card.CATHEDRAL_1, Card.GRAVEYARD, Card.CASTLE_1));

        assertThat(playerController.availableActions.get(1)).contains(ActionType.DRAW_3_CARDS_FOR_2_GOLD_COINS);
    }

    @Test
    public void propose_an_extra_action_if_the_player_has_built_laboratory() {
        StubPlayerController playerController = new StubPlayerController();
        Player player = new Player("Toto", 12, cityWith(Card.LABORATORY), playerController);
        player.addCardInHand(Card.CASTLE_1);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player, Character.MAGICIAN),
                associationBetween(aPlayer(), Character.BISHOP)
        );

        associations.playTurnsInOrder(CardPile.empty());

        assertThat(playerController.availableActions.get(1)).contains(ActionType.DISCARD_CARD_FOR_2_GOLD_COINS);
    }

    @Test
    public void let_the_user_draw_an_extra_card_if_the_player_has_built_observatory() {
        StubPlayerController playerController = new StubPlayerController();
        Player player = new Player("Toto", 12, cityWith(Card.OBSERVATORY), playerController);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player, Character.MAGICIAN),
                associationBetween(aPlayer(), Character.BISHOP)
        );

        associations.playTurnsInOrder(pileWith(Card.CATHEDRAL_1, Card.CASTLE_1, Card.SMITHY));

        assertThat(playerController.availableActions.get(0)).contains(ActionType.DRAW_3_CARDS_KEEP_1);
        assertThat(playerController.availableActions.get(0)).doesNotContain(ActionType.DRAW_2_CARDS_KEEP_1);
    }

    @Test
    public void transfer_the_gold_to_thief_when_giving_turn_to_a_stolen_player() {
        Player player1 = aPlayer();
        Player player2 = aPlayer();
        player2.earn(10);
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player1, Character.THIEF),
                associationBetween(player2, Character.BISHOP)
        );
        associations.steal(player1, Character.BISHOP);

        associations.playTurnsInOrder(pileWith(Card.CATHEDRAL_1, Card.CASTLE_1, Card.SMITHY));

        assertThat(player1.gold().coins()).isEqualTo(10);
        assertThat(player2.gold().coins()).isEqualTo(0);
    }

    @Test
    public void let_the_player_steal_all_characters_except_assassin_thief_and_murdered() {
        GameRoundAssociations associations = roundAssociations(
                associationBetween(aPlayer(), Character.ASSASSIN),
                associationBetween(aPlayer(), Character.THIEF),
                associationBetween(aPlayer(), Character.MAGICIAN),
                associationBetween(aPlayer(), Character.ARCHITECT)
        );
        associations.murder(Character.MAGICIAN);

        List<Character> charactersThatCanBeStolen = associations.charactersThatCanBeStolen();

        assertThat(charactersThatCanBeStolen)
                .containsExactly(Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD);
    }

}