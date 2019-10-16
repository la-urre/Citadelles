package com.montaury.citadelles;

import io.vavr.collection.List;

public class PiochePrédéfinie {
    public static Pioche piocheAvec(Carte... cartes) {
        return new Pioche(List.of(cartes));
    }
}
