package com.montaury.citadels.player;

import com.montaury.citadels.round.action.ActionType;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.control.Option;

public class StubPlayerController implements PlayerController {

    public List<Character> availableCharacters;
    public List<Character> faceUpDiscardedCharacters;
    public List<List<ActionType>> availableActions = List.empty();
    public Option<Card> proposedCard = Option.none();
    private Character nextCharacter;
    private Card nextCard;
    public Set<Card> availableCards = HashSet.empty();
    private List<ActionType> nextActionTypes = List.empty();
    private Player nextPlayer;
    public List<Player> nextPlayersForCardSwap = List.empty();
    public Set<Card> nextCardToSwap;
    private Set<Card> nextCards;
    public Map<Player, List<DestructibleDistrict>> destructibleDistricts = HashMap.empty();
    private boolean shouldAcceptCard;

    public void setNextCharacter(Character character) {
        this.nextCharacter = character;
    }

    public void setNextPlayer(Player player) {
        nextPlayer = player;
    }

    public void setNextCard(Card card) {
        this.nextCard = card;
    }

    public void setNextCards(Set<Card> cards) {
        nextCards = cards;
    }

    @Override
    public Character selectOwnCharacter(List<Character> availableCharacters, List<Character> faceUpRevealedCharacters) {
        this.availableCharacters = availableCharacters;
        this.faceUpDiscardedCharacters = faceUpRevealedCharacters;
        return nextCharacter == null ? availableCharacters.head() : nextCharacter;
    }

    public void setNextAction(ActionType actionType) {
        setNextActions(List.of(actionType));
    }

    public void setNextActions(List<ActionType> actionTypes) {
        nextActionTypes = actionTypes;
    }

    @Override
    public ActionType selectActionAmong(List<ActionType> actions) {
        this.availableActions = this.availableActions.append(actions);
        Option<ActionType> head = nextActionTypes.headOption();
        this.nextActionTypes = this.nextActionTypes.tailOption().getOrElse(List.empty());
        return head.getOrElse(ActionType.END_ROUND);
    }

    @Override
    public Card selectAmong(Set<Card> cards) {
        this.availableCards = cards;
        return nextCard;
    }

    @Override
    public Character selectAmong(List<Character> characters) {
        this.availableCharacters = characters;
        return nextCharacter;
    }

    @Override
    public Player selectPlayerAmong(List<Player> players) {
        nextPlayersForCardSwap = players;
        return nextPlayer;
    }

    @Override
    public Set<Card> selectManyAmong(Set<Card> cards) {
        nextCardToSwap = cards;
        return nextCards;
    }

    @Override
    public DestructibleDistrict selectDistrictToDestroyAmong(Map<Player, List<DestructibleDistrict>> playersDistricts) {
        this.destructibleDistricts = playersDistricts;
        return playersDistricts.flatMap(Tuple2::_2).find(destructibleDistrict -> destructibleDistrict.card() == nextCard).get();
    }

    @Override
    public boolean acceptCard(Card card) {
        proposedCard = Option.of(card);
        return shouldAcceptCard;
    }

    public void acceptNextCard() {
        shouldAcceptCard = true;
    }
}
