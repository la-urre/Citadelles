package com.montaury.citadelles.action;

import com.montaury.citadelles.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.TourDeJeu;
import com.montaury.citadelles.joueur.Joueur;

public class ActionEchangerMainAvecJoueur implements Action {
    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Joueur joueurPourEchange = associationJoueurPersonnage.joueur().controlleur.choisirParmiJoueurs(tourDeJeu.joueursSans(associationJoueurPersonnage.joueur()));
        associationJoueurPersonnage.joueur().echangerMainAvec(joueurPourEchange);
    }
}
