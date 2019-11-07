package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;
import com.montaury.citadelles.quartier.Carte;
import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.tour.AssociationsDeTour;

public class ActionBatirQuartier implements Action {
    @Override
    public boolean estRéalisablePar(Joueur joueur, AssociationsDeTour associations, Pioche pioche) {
        return !joueur.quartierBatissablesEnMain().isEmpty();
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        Carte carte = associationJoueurPersonnage.joueur().controlleur.choisirParmi(associationJoueurPersonnage.joueur().quartierBatissablesEnMain());
        associationJoueurPersonnage.joueur().batirQuartier(carte);
    }
}
