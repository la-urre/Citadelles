package com.montaury.citadelles.quartier;

import com.montaury.citadelles.Possession;
import com.montaury.citadelles.tour.action.TypeAction;
import com.montaury.citadelles.personnage.Personnage;
import com.montaury.citadelles.score.Score;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.function.Function;

public class Effet {

    public static Effet aucun() {
        return new Effet(false);
    }

    public static Effet indestructible() {
        return new Effet(true);
    }

    public static Effet bonusDeScore(Function<Possession, Integer> calculateurDeBonusDeScore) {
        return new Effet(possession -> Score.de(calculateurDeBonusDeScore.apply(possession)));
    }

    public static Effet bonusDeScore(int bonusFixe) {
        return bonusDeScore(p -> bonusFixe);
    }

    public static Effet actionRemplacante(TypeAction actionARemplacer, TypeAction nouvelleAction) {
        return new Effet(Tuple.of(actionARemplacer, nouvelleAction));
    }

    public static Effet actionSupplementaire(TypeAction actionSupplementaire) {
        return new Effet(actionSupplementaire);
    }

    private Effet(boolean indestructible) {
        this.indestructible = indestructible;
        calculateurDeBonusDeScore = possession -> Score.nul();
        actionSupplementaire = Option.none();
        this.remplacementDAction = Option.none();
        this.bonusDePerception = 0;
    }

    private Effet(Function<Possession, Score> calculateurDeBonusDeScore) {
        this.calculateurDeBonusDeScore = calculateurDeBonusDeScore;
        actionSupplementaire = Option.none();
        indestructible = false;
        this.remplacementDAction = Option.none();
        this.bonusDePerception = 0;
    }

    private Effet(int bonusDePerception) {
        this.calculateurDeBonusDeScore = possession -> Score.nul();
        actionSupplementaire = Option.none();
        indestructible = false;
        this.remplacementDAction = Option.none();
        this.bonusDePerception = bonusDePerception;
    }

    private Effet(TypeAction actionSupplementaire) {
        this.actionSupplementaire = Option.of(actionSupplementaire);
        calculateurDeBonusDeScore = possession -> Score.nul();
        indestructible = false;
        this.remplacementDAction = Option.none();
        this.bonusDePerception = 0;
    }

    private Effet(Tuple2<TypeAction,TypeAction> remplacementDAction) {
        this.remplacementDAction = Option.of(remplacementDAction);
        calculateurDeBonusDeScore = possession -> Score.nul();
        indestructible = false;
        actionSupplementaire = Option.none();
        this.bonusDePerception = 0;
    }

    public static Effet bonusDePerception(int bonus) {
        return new Effet(bonus);
    }

    public boolean estIndestructible() {
        return indestructible;
    }

    public Score appliquerBonusDeScore(Possession possession) {
        return calculateurDeBonusDeScore.apply(possession);
    }

    public Option<TypeAction> actionSupplementaire() {
        return actionSupplementaire;
    }

    public TypeAction remplacerAction(TypeAction action) {
        return remplacementDAction.filter(r -> r._1 == action).map(Tuple2::_2).getOrElse(action);
    }

    public int percevoirRevenusPour(Personnage personnage) {
        return personnage.typeQuartierAssocie().isDefined() ? bonusDePerception : 0;
    }

    private final boolean indestructible;
    private final Function<Possession, Score> calculateurDeBonusDeScore;
    private final Option<TypeAction> actionSupplementaire;
    private final Option<Tuple2<TypeAction, TypeAction>> remplacementDAction;
    private final int bonusDePerception;
}
