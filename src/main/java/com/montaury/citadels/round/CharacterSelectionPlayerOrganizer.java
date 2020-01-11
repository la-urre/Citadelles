package com.montaury.citadels.round;

import com.montaury.citadels.player.Player;
import io.vavr.collection.List;

import java.util.Collections;

public class CharacterSelectionPlayerOrganizer {
    public List<Player> orderCrownFirst(List<Player> players, Player playerWithCrown) {
        java.util.List<Player> list = players.asJavaMutable();
        Collections.rotate(list, -players.indexOf(playerWithCrown));
        return List.ofAll(list);
    }
}
