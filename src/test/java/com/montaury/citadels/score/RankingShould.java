package com.montaury.citadels.score;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static com.montaury.citadels.CityFixtures.cityWith;
import static com.montaury.citadels.player.PlayerFixtures.aPlayerWith;
import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;
import static org.assertj.core.api.Assertions.assertThat;

public class RankingShould {

    @Before
    public void setUp() {
        ranking = new Ranking();
    }

    @Test
    public void rank_players_from_the_biggest_to_the_smallest_score() {
        Player player1 = aPlayerWith(cityWith(Card.TAVERN_1));
        Player player2 = aPlayerWith(cityWith(Card.CHURCH_1));
        List<PlayerCharacterAssociation> associations = List.of(
                associationBetween(player1, Character.WARLORD),
                associationBetween(player2, Character.KING)
        );

        List<Player> playersRanking = ranking.rank(associations);

        assertThat(playersRanking)
                .hasSize(2)
                .containsExactly(player2, player1);
    }

    @Test
    public void rank_first_the_alive_player_in_case_of_a_tie() {
        Player player1 = aPlayerWith(cityWith(Card.TAVERN_1));
        Player player2 = aPlayerWith(cityWith(Card.TEMPLE_1));
        PlayerCharacterAssociation associationWithKing = associationBetween(player1, Character.KING);
        PlayerCharacterAssociation associationWithWarlord = associationBetween(player2, Character.WARLORD);
        associationWithWarlord.murder();

        List<Player> playersRanking = ranking.rank(List.of(associationWithKing, associationWithWarlord));

        assertThat(playersRanking)
                .hasSize(2)
                .containsExactly(player1, player2);
    }

    @Test
    public void rank_first_the_player_with_last_round_smallest_order_number_in_case_of_a_tie_and_both_players_alive() {
        Player player1 = aPlayerWith(cityWith(Card.TAVERN_1));
        Player player2 = aPlayerWith(cityWith(Card.TEMPLE_1));
        List<PlayerCharacterAssociation> associations = List.of(
                associationBetween(player1, Character.KING),
                associationBetween(player2, Character.WARLORD)
        );

        List<Player> playersRanking = ranking.rank(associations);

        assertThat(playersRanking)
                .hasSize(2)
                .containsExactly(player2, player1);
    }

    private Ranking ranking;
}