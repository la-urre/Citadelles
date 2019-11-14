package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.AssociationsDeTour;

public class ActionDefausser1CartePourRecevoir2Pieces implements Action {
    @Override
    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return !joueur.main().estVide();
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Joueur joueur = associationJoueurPersonnage.joueur();
        Carte carte = joueur.controlleur.choisirParmi(joueur.main().cartes());
        joueur.main().retirer(carte);
        pioche.mettreDessous(carte);
        joueur.ajouterPieces(2);
    }
}
