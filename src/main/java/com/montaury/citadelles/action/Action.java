package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.TourDeJeu;
import com.montaury.citadelles.joueur.Joueur;

public interface Action {
    default boolean estRéalisablePar(Joueur joueur, TourDeJeu tourDeJeu, Pioche pioche) {
        return true;
    }

    void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche);
}
