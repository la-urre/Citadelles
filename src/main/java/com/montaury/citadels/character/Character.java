package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.collection.List;
import io.vavr.control.Option;

public enum Character {
    ASSASSIN(1, "Assassin", List.of(ActionType.MURDER)),
    THIEF(2, "Thief", List.of(ActionType.STEAL)),
    MAGICIAN(3, "Magician", List.of(ActionType.EXCHANGE_HAND_WITH_PLAYER, ActionType.EXCHANGE_CARDS_WITH_CARD_PILE)),
    KING(4, "King", List.of(ActionType.RECEIVE_INCOME), DistrictType.NOBLE),
    BISHOP(5, "Bishop", List.of(ActionType.RECEIVE_INCOME), DistrictType.RELIGIOUS),
    MERCHANT(6, "Merchant", List.of(ActionType.RECEIVE_INCOME, ActionType.RECEIVE_GOLD_COIN), DistrictType.TRADE),
    ARCHITECT(7, "Architect", List.of(ActionType.PICK_2_CARDS, ActionType.BUILD_DISTRICT, ActionType.BUILD_DISTRICT)),
    WARLORD(8, "Warlord", List.of(ActionType.RECEIVE_INCOME, ActionType.DESTROY_DISTRICT), DistrictType.MILITARY);

    Character(int number, String name, List<ActionType> powers)
    {
        this(number, name, powers, null);
    }

    Character(int number, String name, List<ActionType> powers, DistrictType associatedDistrictType)
    {
        this.number = number;
        this.name = name;
        this.powers = powers;
        this.associatedDistrictType = Option.of(associatedDistrictType);
    }

    public int number() {
        return number;
    }

    public String getName() {
        return name;
    }

    public static List<Character> inOrder() {
        return List.of(Character.values()).sortBy(Character::number);
    }

    public List<ActionType> powers() {
        return powers;
    }

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    private final int number;
    private final String name;
    private final List<ActionType> powers;
    private Option<DistrictType> associatedDistrictType;
}