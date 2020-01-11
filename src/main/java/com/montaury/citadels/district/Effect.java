package com.montaury.citadels.district;

import com.montaury.citadels.Possession;
import com.montaury.citadels.round.action.ActionType;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.score.Score;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Effect {

    public static Effect none() {
        return new Effect(false);
    }

    public static Effect indestructible() {
        return new Effect(true);
    }

    public static Effect scoreBonus(ToIntFunction<Possession> scoreBonusCalculator) {
        return new Effect(possession -> Score.of(scoreBonusCalculator.applyAsInt(possession)));
    }

    public static Effect scoreBonus(int fixedBonus) {
        return scoreBonus(p -> fixedBonus);
    }

    public static Effect replacingAction(ActionType actionToReplace, ActionType newAction) {
        return new Effect(Tuple.of(actionToReplace, newAction));
    }

    public static Effect extraAction(ActionType extraAction) {
        return new Effect(extraAction);
    }

    private Effect(boolean indestructible) {
        this.indestructible = indestructible;
        scoreBonusCalculator = possession -> Score.nil();
        extraAction = Option.none();
        this.replacingAction = Option.none();
        this.incomeBonus = 0;
    }

    private Effect(Function<Possession, Score> scoreBonusCalculator) {
        this.scoreBonusCalculator = scoreBonusCalculator;
        extraAction = Option.none();
        indestructible = false;
        this.replacingAction = Option.none();
        this.incomeBonus = 0;
    }

    private Effect(int incomeBonus) {
        this.scoreBonusCalculator = possession -> Score.nil();
        extraAction = Option.none();
        indestructible = false;
        this.replacingAction = Option.none();
        this.incomeBonus = incomeBonus;
    }

    private Effect(ActionType extraAction) {
        this.extraAction = Option.of(extraAction);
        scoreBonusCalculator = possession -> Score.nil();
        indestructible = false;
        this.replacingAction = Option.none();
        this.incomeBonus = 0;
    }

    private Effect(Tuple2<ActionType, ActionType> replacingAction) {
        this.replacingAction = Option.of(replacingAction);
        scoreBonusCalculator = possession -> Score.nil();
        indestructible = false;
        extraAction = Option.none();
        this.incomeBonus = 0;
    }

    public static Effect bonusDePerception(int bonus) {
        return new Effect(bonus);
    }

    public boolean isIndestructible() {
        return indestructible;
    }

    public Score applyScoreBonus(Possession possession) {
        return scoreBonusCalculator.apply(possession);
    }

    public Option<ActionType> extraAction() {
        return extraAction;
    }

    public ActionType replaceAction(ActionType action) {
        return replacingAction.filter(r -> r._1 == action).map(Tuple2::_2).getOrElse(action);
    }

    public int receiveIncomeFor(Character character) {
        return character.associatedDistrictType().isDefined() ? incomeBonus : 0;
    }

    private final boolean indestructible;
    private final Function<Possession, Score> scoreBonusCalculator;
    private final Option<ActionType> extraAction;
    private final Option<Tuple2<ActionType, ActionType>> replacingAction;
    private final int incomeBonus;
}
