package com.montaury.citadelles.tour.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;
import com.montaury.citadelles.joueur.Joueur;

public class ActionPiocher2Cartes implements Action {
    @Override
    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return pioche.peutFournirCartes(2);
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        associationJoueurPersonnage.joueur().ajouterCartesALaMain(pioche.tirerCartes(2));
    }
}
