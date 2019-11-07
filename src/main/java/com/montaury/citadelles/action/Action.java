package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.joueur.Joueur;

public interface Action {
    default boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return true;
    }

    void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche);
}
