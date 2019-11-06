package com.montaury.citadelles.tour;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.action.TypeAction;
import io.vavr.collection.Set;

public class TourDuJoueur {

    public TourDuJoueur(AssociationJoueurPersonnage associationJoueurPersonnage) {
        this.associationJoueurPersonnage = associationJoueurPersonnage;
    }

    public void deroulerTour(TourDeJeu tourDeJeu, Pioche pioche) {
        associationJoueurPersonnage.voleur().peek(voleur -> voleur.voler(associationJoueurPersonnage.joueur()));
        faireChoisirActions(tourDeJeu, pioche);
    }

    private void faireChoisirActions(TourDeJeu tourDeJeu, Pioche pioche) {
        action(associationJoueurPersonnage.actionsDebutDeTour(), tourDeJeu, pioche);
        actions(associationJoueurPersonnage.actionsOptionnelles(), tourDeJeu, pioche);
    }

    private void actions(Set<TypeAction> actionsPossibles, TourDeJeu tourDeJeu, Pioche pioche) {
        TypeAction typeAction;
        do {
            typeAction = action(actionsPossibles, tourDeJeu, pioche);
            actionsPossibles = actionsPossibles.remove(typeAction);
        }
        while (!actionsPossibles.isEmpty() && typeAction != TypeAction.TERMINER_TOUR);
    }

    private TypeAction action(Set<TypeAction> actionsPossibles, TourDeJeu tourDeJeu, Pioche pioche) {
        actionsPossibles = actionsPossibles.filter(action -> action.estRéalisablePar(associationJoueurPersonnage.joueur(), tourDeJeu, pioche));
        TypeAction typeAction = associationJoueurPersonnage.joueur().controlleur.choisirAction(actionsPossibles.toList());
        typeAction.réaliser(associationJoueurPersonnage, tourDeJeu, pioche);
        return typeAction;
    }

    private final AssociationJoueurPersonnage associationJoueurPersonnage;
}
