package com.montaury.citadelles.tour;

import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.action.TypeAction;
import io.vavr.collection.Set;

class TourDuJoueur {

    TourDuJoueur(AssociationJoueurPersonnage associationJoueurPersonnage) {
        this.associationJoueurPersonnage = associationJoueurPersonnage;
    }

    void deroulerTour(AssociationsDeTour tourDeJeu, Pioche pioche) {
        associationJoueurPersonnage.voleur().peek(voleur -> voleur.voler(associationJoueurPersonnage.joueur()));
        faireChoisirActions(tourDeJeu, pioche);
    }

    private void faireChoisirActions(AssociationsDeTour tourDeJeu, Pioche pioche) {
        action(associationJoueurPersonnage.actionsDebutDeTour(), tourDeJeu, pioche);
        actions(associationJoueurPersonnage.actionsOptionnelles(), tourDeJeu, pioche);
    }

    private void actions(Set<TypeAction> actionsPossibles, AssociationsDeTour tourDeJeu, Pioche pioche) {
        TypeAction typeAction;
        do {
            typeAction = action(actionsPossibles, tourDeJeu, pioche);
            actionsPossibles = actionsPossibles.remove(typeAction);
        }
        while (!actionsPossibles.isEmpty() && typeAction != TypeAction.TERMINER_TOUR);
    }

    private TypeAction action(Set<TypeAction> actionsPossibles, AssociationsDeTour associations, Pioche pioche) {
        actionsPossibles = actionsPossibles.filter(action -> action.estRéalisablePar(associationJoueurPersonnage.joueur(), associations, pioche));
        TypeAction typeAction = associationJoueurPersonnage.joueur().controlleur.choisirAction(actionsPossibles.toList());
        typeAction.réaliser(associationJoueurPersonnage, associations, pioche);
        return typeAction;
    }

    private final AssociationJoueurPersonnage associationJoueurPersonnage;
}
