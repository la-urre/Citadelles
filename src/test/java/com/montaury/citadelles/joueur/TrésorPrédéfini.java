package com.montaury.citadelles.joueur;

public class TrésorPrédéfini {
    public static Trésor trésorDe(int pièces) {
        Trésor trésor = Trésor.vide();
        trésor.ajouter(pièces);
        return trésor;
    }
}
