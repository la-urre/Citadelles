package com.montaury.citadels.round;

import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.RandomCharacterSelector;
import io.vavr.collection.List;

import static com.montaury.citadels.round.PlayerCharacterAssociation.associationBetween;

public class CharacterSelectionPhase {

    public CharacterSelectionPhase(RandomCharacterSelector randomCharacterSelector) {
        this.randomCharacterSelector = randomCharacterSelector;
    }

    public List<PlayerCharacterAssociation> selectCharacters(List<Player> playersInOrder) {
        List<Character> availableCharacters = Character.inOrder();

        Character faceDownDiscardedCharacter = discardFaceDownCharacter(availableCharacters);
        availableCharacters = availableCharacters.remove(faceDownDiscardedCharacter);

        List<Character> faceUpDiscardedCharacters = discardFaceUpCharacters(playersInOrder.size(), availableCharacters);
        availableCharacters = availableCharacters.removeAll(faceUpDiscardedCharacters);

        List<PlayerCharacterAssociation> associations = List.empty();
        for (Player player : playersInOrder) {
            System.out.println(player.name() + " doit choisir un personnage");
            availableCharacters = availableCharacters.size() == 1 && playersInOrder.size() == 7 ? availableCharacters.append(faceDownDiscardedCharacter) : availableCharacters;
            Character selectedCharacter = player.controller.selectOwnCharacter(availableCharacters, faceUpDiscardedCharacters);
            availableCharacters = availableCharacters.remove(selectedCharacter);
            associations = associations.append(associationBetween(player, selectedCharacter));
        }
        return associations;
    }

    private Character discardFaceDownCharacter(List<Character> availableCharacters) {
        return discardCharacters(availableCharacters, 1).head();
    }

    private List<Character> discardFaceUpCharacters(int numberOfPlayers, List<Character> availableCharacters) {
        return discardCharacters(availableCharacters.remove(Character.KING), 7 - numberOfPlayers - 1);
    }

    private List<Character> discardCharacters(List<Character> availableCharacters, int numberToDiscard) {
        List<Character> discardedCharacters = List.empty();
        for (int i = 0; i < numberToDiscard; i++) {
            Character discardedCharacter = randomCharacterSelector.among(availableCharacters);
            discardedCharacters = discardedCharacters.append(discardedCharacter);
            availableCharacters = availableCharacters.remove(discardedCharacter);
        }
        return discardedCharacters;
    }

    private final RandomCharacterSelector randomCharacterSelector;

}
