package com.montaury.citadelles.action;

import com.montaury.citadelles.tour.AssociationJoueurPersonnage;
import com.montaury.citadelles.Pioche;
import com.montaury.citadelles.tour.AssociationsDeTour;

public class ActionPrendre2Pieces implements Action {
    public void réaliser(AssociationJoueurPersonnage associationJoueurPersonnage, AssociationsDeTour associations, Pioche pioche) {
        associationJoueurPersonnage.joueur().ajouterPieces(2);
    }
}
