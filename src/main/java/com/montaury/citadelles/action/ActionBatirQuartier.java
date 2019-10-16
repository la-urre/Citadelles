package com.montaury.citadelles.action;

import com.montaury.citadelles.*;
import com.montaury.citadelles.joueur.Joueur;

public class ActionBatirQuartier implements Action {
    @Override
    public boolean estRéalisablePar(Joueur joueur, TourDeJeu tourDeJeu, Pioche pioche) {
        return !joueur.quartierBatissablesEnMain().isEmpty();
    }

    @Override
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, TourDeJeu tourDeJeu, Pioche pioche) {
        Carte carte = associationJoueurPersonnage.joueur().controlleur.choisirParmi(associationJoueurPersonnage.joueur().quartierBatissablesEnMain());
        associationJoueurPersonnage.joueur().batirQuartier(carte);
    }
}
