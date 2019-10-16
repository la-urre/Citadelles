package com.montaury.citadelles.quartier;

import com.montaury.citadelles.Carte;

public class QuartierDestructible {
    public QuartierDestructible(Carte quartier, Cout coutDeDestruction) {
        this.quartier = quartier;
        this.coutDeDestruction = coutDeDestruction;
    }

    public Carte quartier() {
        return quartier;
    }

    public Cout coutDeDestruction() {
        return coutDeDestruction;
    }

    private final Carte quartier;
    private final Cout coutDeDestruction;
}
