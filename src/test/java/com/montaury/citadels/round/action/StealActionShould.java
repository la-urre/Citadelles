package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.PlayerFixtures;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static org.assertj.core.api.Assertions.assertThat;

public class StealActionShould {

    @Before
    public void setUp() {
        controller = new StubPlayerController();
        player = aPlayer(controller);
        stealAction = new StealAction();
        association = associationBetween(player, Character.THIEF);
    }

    @Test
    public void ask_the_player_which_character_to_steal() {
        stealAction.execute(association, roundAssociations(), CardPile.empty());

        assertThat(controller.availableCharacters).isNotNull();
    }

    @Test
    public void propose_the_player_to_choose_between_all_characters_but_the_assassin_the_thief_and_the_murdered() {
        GameRoundAssociations associations = roundAssociations(
                associationBetween(PlayerFixtures.aPlayer(), Character.ARCHITECT)
        );
        associations.murder(Character.ARCHITECT);

        stealAction.execute(association, associations, CardPile.empty());

        assertThat(controller.availableCharacters).containsExactly(Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.WARLORD);
    }

    @Test
    public void steal_the_chosen_character() {
        controller.setNextCharacter(Character.MERCHANT);
        PlayerCharacterAssociation associationToSteal = associationBetween(PlayerFixtures.aPlayer(), Character.MERCHANT);

        stealAction.execute(association, roundAssociations(associationToSteal), CardPile.empty());

        assertThat(associationToSteal.thief()).isEqualTo(Option.of(player));
    }

    private StubPlayerController controller;
    private Player player;
    private PlayerCharacterAssociation association;
    private StealAction stealAction;
}
