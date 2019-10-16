package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.personnage.Personnage;

public class ActionAssassiner implements Action {

    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Personnage personnageAAssassiner = associationJoueurPersonnage.joueur().controlleur.choisirParmi(tourDeJeu.assassinables());
        tourDeJeu.assassiner(personnageAAssassiner);
    }

}
