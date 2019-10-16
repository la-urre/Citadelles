package com.montaury.citadelles;

import com.montaury.citadelles.quartier.Carte;
import io.vavr.collection.List;

public class CitésPredefinies {

    public static Cité citéVide() {
        return new Cité(new CitésComplètes());
    }

    public static Cité citéAvec(Carte... cartes) {
        Cité cité = citéVide();
        List.of(cartes).forEach(cité::batirQuartier);
        return cité;
    }
}
