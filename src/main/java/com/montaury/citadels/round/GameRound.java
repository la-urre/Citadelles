package com.montaury.citadels.round;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.RandomCharacterSelector;
import io.vavr.collection.List;

import static com.montaury.citadels.round.GameRoundAssociations.roundAssociations;

public class GameRound {

    public List<PlayerCharacterAssociation> play(List<Player> players, Player playerWithCrown, CardPile cardPile) {
        List<PlayerCharacterAssociation> associations = associatePlayersToCharacters(players, playerWithCrown);
        roundAssociations(associations).playTurnsInOrder(cardPile);
        return associations;
    }

    private List<PlayerCharacterAssociation> associatePlayersToCharacters(List<Player> players, Player playerWithCrown) {
        List<Player> playersInOrder = characterSelectionPlayerOrganizer.orderCrownFirst(players, playerWithCrown);
        return characterSelectionPhase.selectCharacters(playersInOrder);
    }

    private final CharacterSelectionPlayerOrganizer characterSelectionPlayerOrganizer = new CharacterSelectionPlayerOrganizer();
    private final CharacterSelectionPhase characterSelectionPhase = new CharacterSelectionPhase(new RandomCharacterSelector());

}
