package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.TourDeJeu;
import com.montaury.citadelles.joueur.Joueur;

public class ActionEchangerMainAvecJoueur implements Action {
    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Joueur joueurPourEchange = associationJoueurPersonnage.joueur().controlleur.choisirParmiJoueurs(tourDeJeu.joueursSans(associationJoueurPersonnage.joueur()));
        associationJoueurPersonnage.joueur().echangerMainAvec(joueurPourEchange);
    }
}
