package com.montaury.citadelles.personnage;

import io.vavr.collection.List;

public class PersonnageAleatoire {

    public Personnage parmi(List<Personnage> personnages) {
        return personnages.get((int) Math.floor(Math.random() * personnages.size()));
    }
}
