package com.montaury.citadels.round;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.Cost;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DestructibleDistrict;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import static java.util.function.Predicate.not;

public class GameRoundAssociations {
    public static GameRoundAssociations roundAssociations(List<PlayerCharacterAssociation> associations) {
        return new GameRoundAssociations(associations);
    }

    public static GameRoundAssociations roundAssociations(PlayerCharacterAssociation... associations) {
        return roundAssociations(List.of(associations));
    }

    private GameRoundAssociations(List<PlayerCharacterAssociation> associations) {
        this.associations = associations;
    }

    void playTurnsInOrder(CardPile cardPile) {
        ActionsFeedback actionsFeedback = new ActionsFeedback(associations);
        associations
                .filter(not(PlayerCharacterAssociation::isMurdered))
                .sortBy(association -> association.character.number())
                .map(association -> new PlayerTurn(association, actionsFeedback))
                .forEach(playerTurn -> playerTurn.take(this, cardPile));
    }

    public List<Player> playersBut(Player player) {
        return associations.map(PlayerCharacterAssociation::player).remove(player);
    }

    public List<Character> charactersThatCanBeMurdered() {
        return Character.inOrder().remove(Character.ASSASSIN);
    }

    public void murder(Character character) {
        associationToCharacter(character).peek(PlayerCharacterAssociation::murder);
    }

    private Option<PlayerCharacterAssociation> associationToCharacter(Character character) {
        return associations.find(a -> a.character == character);
    }

    public void steal(Player thief, Character character) {
        associationToCharacter(character).peek(association -> association.stolenBy(thief));
    }

    public List<Character> charactersThatCanBeStolen() {
        return Character.inOrder()
                .remove(Character.ASSASSIN)
                .remove(Character.THIEF)
                .removeAll(murdered());
    }

    private Option<Character> murdered() {
        return associations.find(PlayerCharacterAssociation::isMurdered).map(PlayerCharacterAssociation::character);
    }

    public Map<Player, List<DestructibleDistrict>> districtsDestructibleBy(Player player) {
        return associations
                .filter(association -> association.isNot(Character.BISHOP) || association.isMurdered())
                .map(PlayerCharacterAssociation::player)
                .toMap(j -> Tuple.of(j, j.city().districtsDestructibleBy(player)));
    }

    public Option<Player> playerTakingDestroyedDistrict(Card destroyedDistrict) {
        return associationHavingBuiltGraveyard()
                .filter(association -> association.isNot(Character.WARLORD))
                .map(PlayerCharacterAssociation::player)
                .filter(j -> j.canAfford(TAKING_DESTROYED_DISTRICT_COST))
                .filter(j -> j.controller.acceptCard(destroyedDistrict))
                .peek(j -> j.addCardInHand(destroyedDistrict))
                .peek(j -> j.pay(TAKING_DESTROYED_DISTRICT_COST));
    }

    private Option<PlayerCharacterAssociation> associationHavingBuiltGraveyard() {
        return associations
                .find(association -> association.player().city().isBuilt(District.GRAVEYARD));
    }

    private static final Cost TAKING_DESTROYED_DISTRICT_COST = Cost.of(1);

    private final List<PlayerCharacterAssociation> associations;
}
