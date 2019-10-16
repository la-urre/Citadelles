package com.montaury.citadelles;

import io.vavr.control.Option;

public class CitésComplètes {
    private Option<Cité> premiereCitéComplete = Option.none();

    public void marquer(Cité cité) {
        premiereCitéComplete = premiereCitéComplete.orElse(() -> Option.of(cité));
    }

    public boolean estPremiere(Cité cité) {
        return premiereCitéComplete.contains(cité);
    }
}
