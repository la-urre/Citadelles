package com.montaury.citadelles.score;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.joueur.Joueur;
import io.vavr.Tuple;
import io.vavr.collection.List;

public class Classement {

    public List<Joueur> classer(List<AssociationJoueurPersonnage> associations) {
        return associations.sortBy(a -> Tuple.of(a.joueur().score(), !a.estAssassiné(), a.personnage))
                .reverse()
                .map(AssociationJoueurPersonnage::joueur);
    }
}
