package com.montaury.citadels.round;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.StubRandomCharacterSelector;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.player.StubPlayerController;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static org.assertj.core.api.Assertions.assertThat;

public class CharacterSelectionPhaseShould {

    @Before
    public void setUp() {
        List<Character> preparedSelection = List.of(Character.BISHOP, Character.ARCHITECT, Character.MAGICIAN, Character.KING, Character.WARLORD, Character.ASSASSIN, Character.THIEF, Character.MERCHANT);
        characterSelectionPhase = new CharacterSelectionPhase(new StubRandomCharacterSelector(preparedSelection));
    }

    @Test
    public void ask_a_player_to_select_character() {
        StubPlayerController controller = new StubPlayerController();
        controller.setNextCharacter(Character.MERCHANT);
        Player player = aPlayer(controller);

        List<PlayerCharacterAssociation> association = characterSelectionPhase.selectCharacters(List.of(player));

        assertThat(controller.availableCharacters).isNotNull();
        assertThat(association.get(0).player()).isEqualTo(player);
    }

    @Test
    public void discard_a_character_face_down_for_a_4_players_game() {
        StubPlayerController controller = new StubPlayerController();

        characterSelectionPhase.selectCharacters(List.of(aPlayer(controller), aPlayer(), aPlayer(), aPlayer()));

        assertThat(controller.availableCharacters)
                .hasSize(5)
                .doesNotContain(Character.BISHOP);
    }

    @Test
    public void not_discard_any_character_face_up_for_a_7_players_game() {
        StubPlayerController controller = new StubPlayerController();
        List<Player> players = List.of(aPlayer(controller), aPlayer(), aPlayer(), aPlayer(), aPlayer(), aPlayer());

        characterSelectionPhase.selectCharacters(players);

        assertThat(controller.faceUpDiscardedCharacters).isEmpty();
    }

    @Test
    public void discard_1_character_face_up_for_a_5_players_game() {
        StubPlayerController controller = new StubPlayerController();
        List<Player> players = List.of(aPlayer(controller), aPlayer(), aPlayer(), aPlayer(), aPlayer());

        characterSelectionPhase.selectCharacters(players);

        assertThat(controller.faceUpDiscardedCharacters)
                .containsExactly(Character.ARCHITECT);
    }

    @Test
    public void discard_2_characters_face_up_for_a_4_players_game() {
        StubPlayerController controller = new StubPlayerController();

        characterSelectionPhase.selectCharacters(List.of(aPlayer(controller), aPlayer(), aPlayer(), aPlayer()));

        assertThat(controller.faceUpDiscardedCharacters)
                .containsExactly(Character.ARCHITECT, Character.MAGICIAN);
    }

    @Test
    public void discard_a_character_when_chosen_by_a_player() {
        StubPlayerController player1Controller = new StubPlayerController();
        player1Controller.setNextCharacter(Character.ARCHITECT);
        StubPlayerController player2Controller = new StubPlayerController();

        characterSelectionPhase.selectCharacters(List.of(aPlayer(player1Controller), aPlayer(player2Controller), aPlayer(), aPlayer()));

        assertThat(player2Controller.availableCharacters)
                .doesNotContain(Character.ARCHITECT);
    }

    @Test
    public void never_discard_the_king_face_up() {
        StubPlayerController player1Controller = new StubPlayerController();
        List<Player> players = List.of(aPlayer(player1Controller), aPlayer(), aPlayer(), aPlayer());
        CharacterSelectionPhase characterSelectionPhase = new CharacterSelectionPhase(new StubRandomCharacterSelector(List.of(Character.BISHOP, Character.KING, Character.MAGICIAN, Character.ARCHITECT, Character.WARLORD, Character.ASSASSIN, Character.THIEF, Character.MERCHANT)));

        characterSelectionPhase.selectCharacters(players);

        assertThat(player1Controller.faceUpDiscardedCharacters).doesNotContain(Character.KING);
    }

    @Test
    public void let_the_seventh_player_choose_the_character_discarded_face_down() {
        StubPlayerController player7Controller = new StubPlayerController();
        List<Player> players = List.of(aPlayer(), aPlayer(), aPlayer(), aPlayer(), aPlayer(), aPlayer(), aPlayer(player7Controller));

        characterSelectionPhase.selectCharacters(players);

        assertThat(player7Controller.availableCharacters)
                .hasSize(2)
                .containsExactly(Character.WARLORD, Character.BISHOP);
    }

    private CharacterSelectionPhase characterSelectionPhase;
}
