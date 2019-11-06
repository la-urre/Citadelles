package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.TourDeJeu;
import com.montaury.citadelles.personnage.Personnage;

public class ActionVoler implements Action {
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Personnage personnage = associationJoueurPersonnage.joueur().controlleur.choisirParmi(tourDeJeu.volables());
        tourDeJeu.voler(associationJoueurPersonnage.joueur(), personnage);
    }
}
