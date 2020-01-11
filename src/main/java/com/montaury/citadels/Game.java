package com.montaury.citadels;

import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.score.Ranking;
import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.round.GameRound;
import io.vavr.collection.List;
import io.vavr.control.Option;

public class Game {

    public Game(List<Player> players) {
        this.players = players;
    }

    void play() {
        CardPile cardPile = CardPile.completeShuffled();
        Player playerWithCrown = new GameSetup().setup(cardPile, players);

        List<PlayerCharacterAssociation> roundAssociations;
        do {
            roundAssociations = new GameRound().play(players, playerWithCrown, cardPile);
            playerWithCrown = playerWithKingAmong(roundAssociations).getOrElse(playerWithCrown);
        } while (!isOver());

        System.out.println("Classement: " + ranking.rank(roundAssociations));
    }

    private Option<Player> playerWithKingAmong(List<PlayerCharacterAssociation> associations) {
        return associations.find(a -> a.character == Character.KING).map(PlayerCharacterAssociation::player);
    }

    boolean isOver() {
        return players.map(Player::city).exists(City::isComplete);
    }

    private final List<Player> players;
    private final Ranking ranking = new Ranking();

}
