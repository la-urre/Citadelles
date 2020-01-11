package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.PlayerFixtures;
import com.montaury.citadels.player.StubPlayerController;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRoundAssociations;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.player.PlayerFixtures.aPlayer;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static org.assertj.core.api.Assertions.assertThat;

public class MurderActionShould {

    @Before
    public void setUp() {
        controller = new StubPlayerController();
        murderAction = new MurderAction();
    }

    @Test
    public void ask_the_player_which_character_to_murder() {
        murderAction.execute(associationBetween(aPlayer(controller), Character.KING), roundAssociations(), CardPile.empty());

        assertThat(controller.availableCharacters).isNotNull();
    }

    @Test
    public void propose_the_player_to_choose_between_all_characters_but_the_assassin() {
        murderAction.execute(associationBetween(aPlayer(controller), Character.KING), roundAssociations(), CardPile.empty());

        assertThat(controller.availableCharacters)
                .containsExactly(Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD);
    }

    @Test
    public void murder_the_chosen_character() {
        controller.setNextCharacter(Character.MERCHANT);
        PlayerCharacterAssociation associationToMurder = associationBetween(PlayerFixtures.aPlayer(), Character.MERCHANT);
        GameRoundAssociations associations = roundAssociations(associationToMurder);

        murderAction.execute(associationBetween(aPlayer(controller), Character.KING), associations, CardPile.empty());

        assertThat(associationToMurder.isMurdered()).isTrue();
    }

    private StubPlayerController controller;
    private MurderAction murderAction;
}
