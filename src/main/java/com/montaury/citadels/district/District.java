package com.montaury.citadels.district;

import com.montaury.citadels.Possession;
import com.montaury.citadels.round.action.ActionType;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.score.Score;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import static com.montaury.citadels.district.DistrictType.*;
import static com.montaury.citadels.round.action.ActionType.*;

public enum District {
    MANOR(3, NOBLE),
    CASTLE(4, NOBLE),
    PALACE(5, NOBLE),

    WATCHTOWER(1, MILITARY),
    PRISON(2, MILITARY),
    BATTLEFIELD(3, MILITARY),
    FORTRESS(5, MILITARY),

    TAVERN(1, TRADE),
    TRADING_POST(2, TRADE),
    MARKET(2, TRADE),
    DOCKS(3, TRADE),
    HARBOR(4, TRADE),
    TOWN_HALL(5, TRADE),

    TEMPLE(1, RELIGIOUS),
    CHURCH(2, RELIGIOUS),
    MONASTERY(3, RELIGIOUS),
    CATHEDRAL(5, RELIGIOUS),

    HAUNTED_CITY(2, SPECIAL), // Pour le calcul du score, la Cour des Miracles est considérée comme un quartier de la couleur de votre choix
    DRAGON_GATE(6, SPECIAL, Effect.scoreBonus(2)), // Coûte 6 pièces d'or à bâtir mais vaut 8 points pour le calcul du score
    UNIVERSITY(6, SPECIAL, Effect.scoreBonus(2)), // Coûte 6 pièces d'or à bâtir mais vaut 8 points pour le calcul du score
    TREASURY(5, SPECIAL, Effect.scoreBonus(Possession::goldCoins)), // A la fin de la partie, marquez 1 point supp. pour chaque pièce d'or dans votre trésor
    MAP_ROOM(5, SPECIAL, Effect.scoreBonus(Possession::cardsInHandCount)), // A la fin de la partie, marquez 1 point supp. pour chaque carte dans votre main
    OBSERVATORY(4, SPECIAL, Effect.replacingAction(DRAW_2_CARDS_KEEP_1, DRAW_3_CARDS_KEEP_1)), // si vous choisissez de piocher des cartes au début de votre tour, piochez-en 3 au lieu de 2. Choisissez-en une et défaussez les 2 autres
    GRAVEYARD(5, SPECIAL), // Lorsque le Condottiere détruit un quartier, vous pouvez payer 1 pièce d'or pour le prendre dans votre main. Vous ne pouvez pas le faire si vous êtes vous-même Condottiere
    SMITHY(5, SPECIAL, Effect.extraAction(DRAW_3_CARDS_FOR_2_GOLD_COINS)), // Une fois par tour, vous pouvez payer 2 pièces d'or pour piocher 3 cartes
    LIBRARY(6, SPECIAL), // Si vous choisissez de piocher des cartes au début de votre tour, conservez-les toutes
    MAGIC_SCHOOL(6, SPECIAL, Effect.bonusDePerception(1)), // Pour la perception des revenus, l'école de Magie est considérée comme un quartier de la couleur de votre choix. Elle vous rapporte donc si vous êtes Roi, Eveque, Marchand ou Condottiere
    LABORATORY(5, SPECIAL, Effect.extraAction(ActionType.DISCARD_CARD_FOR_2_GOLD_COINS)), // Une fois par tour, vous pouvez défausser 1 carte et recevoir 2 pièces d'or
    GREAT_WALL(6, SPECIAL), // Le prix à payer par le Condottiere pour détruire vos autres quartiers est augmenté de 1
    KEEP(3, SPECIAL, Effect.indestructible()); // Le donjon ne peut pas être détruit par le Condottiere

    District(int cost, DistrictType districtType) {
        this(cost, districtType, Effect.none());
    }

    District(int cost, DistrictType districtType, Effect effect) {
        this.cost = Cost.of(cost);
        this.districtType = districtType;
        this.effect = effect;
    }

    public final Cost cost() {
        return cost;
    }

    public boolean isOf(DistrictType districtType) {
        return districtType().equals(districtType);
    }

    public final DistrictType districtType() {
        return districtType;
    }

    public boolean isDestructible() {
        return !effect.isIndestructible();
    }

    public Score applyScoreBonus(Possession possession) {
        return effect.applyScoreBonus(possession);
    }

    public Option<ActionType> extraAction() {
        return effect.extraAction();
    }

    public Set<ActionType> replaceAction(Set<ActionType> actions) {
        return actions.map(effect::replaceAction);
    }

    public int incomeFor(Character character) {
        return (character.associatedDistrictType().contains(districtType) ? 1 : 0)
                + effect.receiveIncomeFor(character);
    }

    private final Cost cost;
    private final DistrictType districtType;
    private final Effect effect;
}
