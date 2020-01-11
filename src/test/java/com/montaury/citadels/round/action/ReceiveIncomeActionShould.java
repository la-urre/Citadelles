package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CityFixtures.cityWith;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class ReceiveIncomeActionShould {

    @Before
    public void setUp() {
        action = new ReceiveIncomeAction();
    }

    @Test
    public void make_the_king_earn_income_from_noble_districts() {
        Player player = aPlayerWith(cityWith(Card.MANOR_1, Card.PALACE_1));
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player, Character.KING)
        );

        action.execute(associationBetween(player, Character.KING), associations, CardPile.empty());

        assertThat(player.gold().coins()).isEqualTo(2);
    }

    @Test
    public void make_the_player_earn_income_from_the_magic_school_when_associated_character_has_a_color() {
        Player player = aPlayerWith(cityWith(Card.MAGIC_SCHOOL));
        GameRoundAssociations associations = roundAssociations(
                associationBetween(player, Character.KING)
        );

        action.execute(associationBetween(player, Character.KING), associations, CardPile.empty());

        assertThat(player.gold().coins()).isEqualTo(1);
    }

    @Test
    public void not_make_the_player_earn_income_from_the_magic_school_when_associated_character_does_not_have_a_color() {
        Player player = aPlayerWith(cityWith(Card.MAGIC_SCHOOL));
        PlayerCharacterAssociation association = associationBetween(player, Character.THIEF);
        GameRoundAssociations associations = roundAssociations(
                association
        );

        action.execute(association, associations, CardPile.empty());

        assertThat(player.gold().coins()).isEqualTo(0);
    }

    private ReceiveIncomeAction action;
}