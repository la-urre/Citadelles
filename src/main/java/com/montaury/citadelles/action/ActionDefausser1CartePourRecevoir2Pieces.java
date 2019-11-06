package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.TourDeJeu;

public class ActionDefausser1CartePourRecevoir2Pieces implements Action {
    @Override
    public boolean estRéalisablePar(Joueur joueur, TourDeJeu tourDeJeu, Pioche pioche) {
        return !joueur.main().estVide();
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Carte carte = associationJoueurPersonnage.joueur().controlleur.choisirParmi(associationJoueurPersonnage.joueur().main().cartes());
        associationJoueurPersonnage.joueur().main().retirer(carte);
        pioche.mettreDessous(carte);
        associationJoueurPersonnage.joueur().ajouterPieces(2);
    }
}
