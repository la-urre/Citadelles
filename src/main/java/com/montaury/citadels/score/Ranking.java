package com.montaury.citadels.score;

import com.montaury.citadels.round.PlayerCharacterAssociation;
import com.montaury.citadels.player.Player;
import io.vavr.Tuple;
import io.vavr.collection.List;

public class Ranking {

    public List<Player> rank(List<PlayerCharacterAssociation> associations) {
        return associations.sortBy(a -> Tuple.of(a.player().score(), !a.isMurdered(), a.character))
                .reverse()
                .map(PlayerCharacterAssociation::player);
    }
}
