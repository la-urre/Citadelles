package com.montaury.citadels.round;

import com.montaury.citadels.round.action.ActionType;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.District;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.control.Option;

public class PlayerCharacterAssociation {
    public static PlayerCharacterAssociation associationBetween(Player player, Character character) {
        return new PlayerCharacterAssociation(player, character);
    }

    private PlayerCharacterAssociation(Player player, Character character) {
        this.player = player;
        this.character = character;
    }

    public Player player() {
        return player;
    }

    public Character character() {
        return character;
    }

    public boolean isNot(Character character) {
        return this.character != character;
    }

    public void murder() {
        this.murdered = true;
    }

    public boolean isMurdered() {
        return murdered;
    }

    public void stolenBy(Player player) {
        stolenBy = Option.of(player);
    }

    public Option<Player> thief() {
        return stolenBy;
    }

    Set<ActionType> baseActions() {
        Set<ActionType> baseActions = BASE_ACTIONS;
        List<District> districts = player().city().districts();
        return districts.flatMap(district -> district.replaceAction(baseActions)).orElse(baseActions).toSet();
    }

    Set<ActionType> optionalActions() {
        return OPTIONAL_ACTIONS
                .addAll(character.powers())
                .addAll(player().city().districts().flatMap(District::extraAction));
    }

    private final Player player;
    public final Character character;
    private boolean murdered;
    private Option<Player> stolenBy = Option.none();
    private static final Set<ActionType> BASE_ACTIONS = HashSet.of(ActionType.DRAW_2_CARDS_KEEP_1, ActionType.RECEIVE_2_GOLD_COINS);
    private static final Set<ActionType> OPTIONAL_ACTIONS = HashSet.of(ActionType.BUILD_DISTRICT, ActionType.END_ROUND);
}
