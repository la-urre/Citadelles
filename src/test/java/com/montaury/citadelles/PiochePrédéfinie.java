package com.montaury.citadelles;

import com.montaury.citadelles.quartier.Carte;
import io.vavr.collection.List;

public class PiochePrédéfinie {
    public static Pioche piocheAvec(Carte... cartes) {
        return new Pioche(List.of(cartes));
    }
}
